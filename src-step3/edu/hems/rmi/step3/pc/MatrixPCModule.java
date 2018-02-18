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

import edu.hems.rmi.step3.pc.util.MatricesOperationUtils;
import edu.hems.rmi.step3.service.PC2IO;
import edu.hems.rmi.step3.service.Worker2PC;

public class MatrixPCModule implements PC2IO {

	
	public MatrixPCModule() {
	}

	public void init(int pcPort) {
        try {
        		setupRMISocketFactory(5000);
            String name = "matrixOperations";
            PC2IO stub =  (PC2IO) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.createRegistry(pcPort);
            registry.rebind(name, stub);
            System.out.println("MatrixPCModule bound");
            
            boolean monitor = true;
            while(monitor) {
            		Thread.sleep(2000);
            		System.out.printf("\r Workers Count: %d ", getWorkers(registry).size());
            }

            System.out.println("MatrixPCModule out of monitor loop");

        } catch (Exception e) {
            System.err.println("MatrixPCModule exception:");
            e.printStackTrace();
        }
		
	}

	@Override
	public int[][] matricesMultipleOperation(int[][] a, int[][] b) {
		return MatricesOperationUtils.multiply(a, b);
	}

	@Override
	public int matrixDeterminantOperation(int[][] a) {
		return MatricesOperationUtils.determinant(a);
	}

	private List<Worker2PC> getWorkers(Registry registry) throws Exception  {
		System.out.println("---------- Begin Test workers connection ----------- ");
		List<Worker2PC> workers = new ArrayList<Worker2PC>();
		String[] regNames = registry.list();
		
		List<Callable<Worker2PC>> callables = new ArrayList<Callable<Worker2PC>>();
		for (int i = 0; i < regNames.length; i++) {
			String registryName = regNames[i];
			if(registryName.startsWith("worker-")) {
				callables.add(() -> {
					Worker2PC worker = null;
					try {
						worker = (Worker2PC)registry.lookup(registryName);
						worker.ping();
						workers.add(worker );
					} catch (RemoteException e) {
						registry.unbind(registryName);
					}
					System.out.println("Test connection done for: " + registryName );
					return worker;
				});
			}
		}
		if(!callables.isEmpty()) {
			ExecutorService executor = Executors.newWorkStealingPool();
			List<Future<Worker2PC>> fs = executor.invokeAll(callables);
//			fs.stream().map(future -> {
//		        try {
//		            return future.get();
//		        }
//		        catch (Exception e) {
//		            throw new IllegalStateException(e);
//		        }
//		    }) .forEach(System.out::println);
			
		}
		System.out.println("---------- Done Tested all workers connection ----------- ");
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
