package edu.hems.rmi.step3.worker;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import edu.hems.rmi.step3.model.IWorkload;
import edu.hems.rmi.step3.service.Worker2PC;

public class MatrixWorkerModule implements Worker2PC {
	

	public MatrixWorkerModule() {
	}
	
	public static void init(String pcHost, int pcPort, int workerCount) {
        try {
        		String hostName = InetAddress.getLocalHost().getHostName();
            Registry registry = LocateRegistry.getRegistry(pcHost, pcPort);
            
            for (int i = 0; i < workerCount; i++) {
            		String workerName = "worker-" + hostName + "-" + i;
				registry.rebind(workerName, UnicastRemoteObject.exportObject(new MatrixWorkerModule(), 0));
				System.out.println("Worker initiated : " + workerName);
			}
            
        } catch (Exception e) {
            System.err.println("MatrixIOModule exception:");
            e.printStackTrace();
        }

	}

	@Override
	public IWorkload execute(IWorkload workload) throws RemoteException {
		return workload.execute();
		
	}

	@Override
	public void ping() throws RemoteException {
		
	}


}
