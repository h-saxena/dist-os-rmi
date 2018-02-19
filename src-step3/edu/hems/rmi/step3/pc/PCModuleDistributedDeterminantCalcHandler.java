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
			
			// --------- Setting up Callables -------------
			Worker2PC wk = workers.get(workerPtr);
			final int threadNum = i;
			final int signValue = s;
			final int aValue = pMatrix[0][i];
			callables.add(() -> {
				String threadName = "Thread-Dist-Determinant-" + threadNum;
				Thread.currentThread().setName(threadName);
				SubDeterminantValueWorkload wl = new SubDeterminantValueWorkload(signValue, aValue, smaller, threadName);
		        wl = (SubDeterminantValueWorkload) wk.execute( wl);
		        return wl;
			});

		} // for loop
		
		if(! callables.isEmpty()) {
			ExecutorService executor = Executors.newFixedThreadPool(workers.size());
			List<Future<SubDeterminantValueWorkload>> fs = executor.invokeAll(callables);
			
			for (Iterator iterator = fs.iterator(); iterator.hasNext();) {
				Future<SubDeterminantValueWorkload> future = (Future<SubDeterminantValueWorkload>) iterator.next();
				sum += future.get().getDeterminent();
			}
		}
		return (sum); 
	}


}
