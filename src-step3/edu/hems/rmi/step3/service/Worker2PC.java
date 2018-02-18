package edu.hems.rmi.step3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.hems.rmi.step3.model.IWorkload;

public interface Worker2PC  extends Remote {
	void execute(IWorkload workload) throws RemoteException;
	void ping() throws RemoteException;
}
