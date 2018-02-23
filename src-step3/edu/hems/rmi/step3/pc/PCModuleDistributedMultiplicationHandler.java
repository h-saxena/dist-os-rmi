package edu.hems.rmi.step3.pc;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.hems.rmi.step3.model.MultiplicationWorkload;
import edu.hems.rmi.step3.pc.util.MatricesOperationUtils;
import edu.hems.rmi.step3.service.Worker2PC;

/*
 * Class responsible to:
 * 1) 	create  "MultiplicationWorkload" work unit consisting of one or more row from Matrix A and all of Matrix B
 * 		So let say if Matrix A is of size 5 and there are currently 2 active workers then it will create 2 instances of MultiplicationWorkload
 * 		to pass it to each worker:
 * 		a) first MultiplicationWorkload instance will contain 2 rows from Matrix A and all of the Martix B
 * 		b) Second MultiplicationWorkload instance will contain remain three rows from Matrix A and all the Matrix B 
 * 
 * 2) 	After all instances of MultiplicationWorkload worked on my threads, it append all the sub results into one final resultant Matrix.
 
 */

public class PCModuleDistributedMultiplicationHandler {

	public static int[][] matricesMultipleOperation(int[][] a, int[][] b, List<Worker2PC> workers) throws RemoteException, InterruptedException, ExecutionException {
		if(workers == null || workers.isEmpty())
			throw new RuntimeException("matricesMultipleOperation :: No Workers available to perform distributive Calculation.");
		
		int numOfWorkers = workers.size();
		int groupSize = a.length/numOfWorkers;
		if(groupSize == 0)
			groupSize = 1;
		
		int grpCtr = 1;
		List<MultiplicationWorkload> multiplicationWLs = new ArrayList<>();
		List<Callable<MultiplicationWorkload>> callables = new ArrayList<Callable<MultiplicationWorkload>>();
		
		for (int i = 0; i < a.length;) {
			int subMatRowCount = grpCtr == numOfWorkers ? a.length - i : groupSize;
			
			int subMatColumnCount = a[0].length;
			int[][] subMatrixA = new int[subMatRowCount][subMatColumnCount];
			
			// copy to subarray 
	        for (int k = 0; k < subMatRowCount; k++)
	            for (int l = 0; l < subMatColumnCount; l++)
	            		subMatrixA[k][l] = a[i+k][l];
	        
			// -- Setting up Callables/runnable to run execution of MultiplicationWorkload on available workers -
	        Worker2PC wk = workers.get(grpCtr - 1);	        
	        final int threadNum = grpCtr;
			callables.add(() -> {
				String threadName = "Thread-dist-Multiplication-" + threadNum;
				Thread.currentThread().setName(threadName);
		        MultiplicationWorkload wl = new MultiplicationWorkload(subMatrixA, b, threadName);
		        multiplicationWLs.add(wl);
		        wl = (MultiplicationWorkload) wk.execute( wl);
		        return wl;
			});

	        i+=groupSize;
	        
	        if(grpCtr == numOfWorkers)
	        	break;
	        
	        grpCtr++;
		}
		
		// -- thread pool size determine maximum number of thread/requests that can be invoked at any given time. 
		// so let say if pool size is 5 and there are 8 units of MultiplicationWorkload to be executed
		// then when executor.invokeAll is called, the first 5 MultiplicationWorkload will be executed
		// in parallel and others will wait in executer pool until a thread is available to work on next MultiplicationWorkload
		
		// - this could be tune based on how many parallel requests a given worker host can handle.
		int numberOfParallelRequest = 20;
		ExecutorService executor = Executors.newFixedThreadPool(numberOfParallelRequest);
		List<Future<MultiplicationWorkload>> fs = executor.invokeAll(callables); // this will wait until all the callables are done.

		int[][] finalResult = null;
		for (Iterator iterator = fs.iterator(); iterator.hasNext();) {
			Future<MultiplicationWorkload> future = (Future<MultiplicationWorkload>) iterator.next();
			if(finalResult == null) {
				finalResult = future.get().getResult();
			}
			else {
				finalResult = MatricesOperationUtils.append(finalResult, future.get().getResult());
			}
		}
		
		return finalResult;
		//return MatricesOperationUtils.multiply(a, b);
	}
	
}
