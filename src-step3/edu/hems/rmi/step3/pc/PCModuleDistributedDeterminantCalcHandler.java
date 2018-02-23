package edu.hems.rmi.step3.pc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.hems.rmi.step3.model.SubDeterminantValueWorkload;
import edu.hems.rmi.step3.service.Worker2PC;

/*
 * Class responsible for:
 * 1) 	going row by row of a given matrix and prepare units of "SubDeterminantValueWorkload" 
 * 		with respective smaller matrix (whose values in not in same row and column) and give it
 * 		available number of workers in round-robin fashion to determine sub-determinant in parallel
 * 
 * 2) 	After all rows are done, it does a summation of individual sub-determinant to calculate final determinant
 * 		of the matrix.
 * 
 *  Note: since distribution of unit of SubDeterminantValueWorkload instances are based on work split on first level row of the matrix and rest is calculated 
 *  via recursively, I did noticed that after Matrix size of 13 or 14 determinant calculation get really slow.
 */
public class PCModuleDistributedDeterminantCalcHandler {
	
	public static int determinant(int[][] pMatrix, List<Worker2PC> workers) throws InterruptedException, ExecutionException {
		int sum = 0;
		int s;
		if (pMatrix.length == 1) { 
			return (pMatrix[0][0]);
		}
		
		List<Callable<SubDeterminantValueWorkload>> callables = new ArrayList<Callable<SubDeterminantValueWorkload>>();
		int workerPtr = -1;
		
		// finds determinant using row-by-row expansion
		for (int i = 0; i < pMatrix.length; i++) { 
			// creates smaller matrix- values not in same row, column
			int[][] smaller = new int[pMatrix.length - 1][pMatrix.length - 1]; 
																				
			for (int a = 1; a < pMatrix.length; a++) {
				for (int b = 0; b < pMatrix.length; b++) {
					if (b < i) {
						smaller[a - 1][b] = pMatrix[a][b];
					} else if (b > i) {
						smaller[a - 1][b - 1] = pMatrix[a][b];
					}
				}
			}
			if (i % 2 == 0) { // sign changes based on i
				s = 1;
			} else {
				s = -1;
			}
			// recursive step: determinant of larger determined by smaller
			//sum += s * pMatrix[0][i] * (determinant(smaller)); 
			
			workerPtr++;
			if(workerPtr == workers.size())
				workerPtr = 0;
			
			// -- Setting up Callables/runnable to run execution of SubDeterminantValueWorkload on available workers -
			Worker2PC wk = workers.get(workerPtr);
			final int threadNum = i;
			final int signValue = s;
			final int aValue = pMatrix[0][i];
			
			// -- Callable returns copy of SubDeterminantValueWorkload instance on which a given worker worked on.
			// and returned instance will be used to fetch the resultant data from it.
			callables.add(() -> {
				String threadName = "Thread-Dist-Determinant-" + threadNum;
				Thread.currentThread().setName(threadName);
				
				SubDeterminantValueWorkload wl = new SubDeterminantValueWorkload(signValue, aValue, smaller, threadName);
				// -- a given worker will executed the workload item.
		        wl = (SubDeterminantValueWorkload) wk.execute( wl);
		        return wl;
			});

		} // for loop
		
		if(! callables.isEmpty()) {
			// -- thread pool size determine maximum number of thread/requests that can be invoked at any given time. 
			// so let say if pool size is 5 and there are 8 units of SubDeterminantValueWorkload to be executed
			// then when executor.invokeAll is called, the first 5 SubDeterminantValueWorkload will be executed
			// in parallel and others will wait in executer pool until a thread is available to work on next SubDeterminantValueWorkload
			
			// - this could be tune based on how many parallel requests a given worker host can handle.
			int numberOfParallelRequest = 20;
			ExecutorService executor = Executors.newFixedThreadPool(numberOfParallelRequest);
			List<Future<SubDeterminantValueWorkload>> fs = executor.invokeAll(callables); // this will wait until all the callables are done.
			
			// iterating through returned value to determine the final value.
			for (Iterator iterator = fs.iterator(); iterator.hasNext();) {
				Future<SubDeterminantValueWorkload> future = (Future<SubDeterminantValueWorkload>) iterator.next();
				sum += future.get().getDeterminent();
			}
		}
		return (sum); 
	}


}
