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
	        
	        // --- setting up the Callables -------------------------
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
		
		ExecutorService executor = Executors.newFixedThreadPool(callables.size());
		List<Future<MultiplicationWorkload>> fs = executor.invokeAll(callables);

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
