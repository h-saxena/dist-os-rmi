package edu.hems.rmi.step2.pc;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import edu.hems.rmi.step2.pc.util.MatricesOperationUtils;
import edu.hems.rmi.step2.service.PC2IO;

public class MatrixPCModule implements PC2IO {

	public MatrixPCModule() {
	}

	public void init(int pcPort) {
        try {
            String name = "matrixOperations";
            PC2IO stub =  (PC2IO) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.createRegistry(pcPort);
            registry.rebind(name, stub);
            System.out.println("MatrixPCModule bound");
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


}
