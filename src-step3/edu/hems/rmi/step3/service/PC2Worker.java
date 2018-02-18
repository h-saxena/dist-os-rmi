package edu.hems.rmi.step3.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PC2Worker  extends Remote {
	Boolean register() throws RemoteException;
	Boolean deregister() throws RemoteException; 
}
	