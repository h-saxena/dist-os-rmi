package edu.hems.rmi.step2.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PC2IO  extends Remote {
	int[][] matricesMultipleOperation(int[][] a, int[][] b) throws RemoteException;
	int matrixDeterminantOperation(int[][] a) throws RemoteException; 
}
