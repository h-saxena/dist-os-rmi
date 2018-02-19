package edu.hems.rmi.step3.model;

import edu.hems.rmi.step3.pc.util.MatricesOperationUtils;
import edu.hems.rmi.step3.io.utils.MatrixFileOperationUtils;

public class SubDeterminantValueWorkload implements IWorkload {
	int[][] subMatrix;
	int signValue;
	int aValue;
	
	int determinant;
	String threadName;
	

	public SubDeterminantValueWorkload(int signValue, int aValue, int[][] subMatrix, String threadName) {
		this.signValue = signValue;
		this.aValue = aValue;
		this.subMatrix = subMatrix;
		
		this.threadName = threadName;
	}
	
	public IWorkload execute() {
		System.out.println("\n" +  this.threadName + " Sub Matrix ------------\n" + MatrixFileOperationUtils.convertMatrixToString(subMatrix));
		determinant = signValue * aValue * MatricesOperationUtils.determinant(subMatrix);
		System.out.println("\n" +  this.threadName + " Resultant------------\n" + determinant);
		return this;
	}

	public int getDeterminent() {
		return determinant;
	}
}
