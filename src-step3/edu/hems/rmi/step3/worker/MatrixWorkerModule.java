package edu.hems.rmi.step3.worker;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import edu.hems.rmi.step3.model.IWorkload;
import edu.hems.rmi.step3.service.PC2Worker;
import edu.hems.rmi.step3.service.Worker2PC;

/*
 * Class responsible for : 
 * 1) Lookup PCModule remote object (PC2IO) to register one or more worker remote instances
 * 2) responsible to execute IWorkload item passed to it.
 * 3) Also provide ping() operation to PC module to test a worker availability.
 * 
 */

public class MatrixWorkerModule implements Worker2PC {

	public MatrixWorkerModule() {
	}

	public static void init(String pcHost, int pcPort, int workerCount) {
		try {
			// -- Dynamically determine the host name and setting up the rmi server host name property
			String hostName = InetAddress.getLocalHost().getHostName();
			System.setProperty("java.rmi.server.hostname", hostName);

			Registry registry = LocateRegistry.getRegistry(pcHost, pcPort);
			PC2Worker service = (PC2Worker) registry.lookup("MatrixPCModule");

			// -- create number of workers remote instance and register it with PC Module
			for (int i = 0; i < workerCount; i++) {
				String workerName = "worker-" + hostName + "-" + i;
				service.register((Worker2PC) UnicastRemoteObject.exportObject(new MatrixWorkerModule(), 0));
				System.out.println("Worker initiated : " + workerName);
			}

		} catch (Exception e) {
			System.err.println("MatrixWorkerModule exception:");
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
