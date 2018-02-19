package edu.hems.rmi.step3.pc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.hems.rmi.step3.service.PC2IO;
import edu.hems.rmi.step3.service.PC2Worker;
import edu.hems.rmi.step3.service.Worker2PC;

public class MatrixPCModule implements PC2IO, PC2Worker {

	//Registry registry = null;
	
	List<Worker2PC> activeWorkers = new ArrayList<Worker2PC>();
	
	public MatrixPCModule() {
	}

	public void init(int pcPort) {
        try {
        		setupRMISocketFactory(5000);
            Registry registry = LocateRegistry.createRegistry(pcPort);
            
            //PC2IO stub =  (PC2IO) UnicastRemoteObject.exportObject(this, 0);
            registry.rebind("MatrixPCModule", UnicastRemoteObject.exportObject(this, 0));
            
            //PC2Worker stub2 =  (PC2Worker) UnicastRemoteObject.exportObject(this, 0);
            //registry.rebind("PC2Worker", stub2);
            
            System.out.println("MatrixPCModule bound");
            
            boolean monitor = true;
            while(monitor) {
            		Thread.sleep(2000);
            		System.out.printf("\r Workers Count: %d ", getWorkers().size());
            }

            System.out.println("MatrixPCModule out of monitor loop");

        } catch (Exception e) {
            System.err.println("MatrixPCModule exception:");
            e.printStackTrace();
        }
		
	}

	@Override
	public Boolean register(Worker2PC worker) throws RemoteException {
		activeWorkers.add(worker);
		return true;
	}


	@Override
	public int[][] matricesMultipleOperation(int[][] a, int[][] b) {
		//return MatricesOperationUtils.multiply(a, b);
		try {
			return PCModuleDistributedMultiplicationHandler.matricesMultipleOperation(a, b, getWorkers());
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
	}

	@Override
	public int matrixDeterminantOperation(int[][] a) {
		//int v1 = MatricesOperationUtils.determinant(a);
		try {
			int v2 = PCModuleDistributedDeterminantCalcHandler.determinant(a, getWorkers());
			//System.out.println("Determinant values ---------> " + v1 + " :: " + v2);
			return v2;
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}

	}

	private List<Worker2PC> getWorkers() throws Exception  {
		//System.out.println("---------- Begin Test workers connection ----------- ");
		List<Worker2PC> workers = new ArrayList<Worker2PC>();
		List<Worker2PC> workersRefToRemove = new ArrayList<Worker2PC>();
		
		List<Callable<Worker2PC>> callables = new ArrayList<Callable<Worker2PC>>();
		for (int i = 0; i < activeWorkers.size(); i++) {
			Worker2PC worker = activeWorkers.get(i);
			callables.add(() -> {
				try {
					worker.ping();
					workers.add(worker );
				} catch (RemoteException e) {
					workersRefToRemove.add(worker);
				}
				return worker;
			});
		}
		if(!callables.isEmpty()) {
			ExecutorService executor = Executors.newWorkStealingPool();
			List<Future<Worker2PC>> fs = executor.invokeAll(callables);
		}
		
		if(! workersRefToRemove.isEmpty()) {
			activeWorkers.removeAll(workersRefToRemove);
		}
		//System.out.println("---------- Done Tested all workers connection ----------- ");
		return workers;
	}
	
	private RMISocketFactory setupRMISocketFactory(int timeoutMillis) throws Exception {
		RMISocketFactory fac = new RMISocketFactory()
        {
            public Socket createSocket( String host, int port )
                throws IOException
            {
                Socket socket = new Socket();
                socket.setSoTimeout( timeoutMillis );
                socket.setSoLinger( false, 0 );
                socket.connect( new InetSocketAddress( host, port ), timeoutMillis );
                return socket;
            }

            public ServerSocket createServerSocket( int port )
                throws IOException
            {
                return new ServerSocket( port );
            }
        };
        RMISocketFactory.setSocketFactory(fac);
        return fac;
		
	}

	
}
