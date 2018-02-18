package edu.hems.rmi.step3.model;

import edu.hems.rmi.step3.io.utils.MatrixFileOperationUtils;
import edu.hems.rmi.step3.pc.util.MatricesOperationUtils;

public class MultiplicationWorkload implements IWorkload {
	int[][] a,  b, c;
	String threadName;
	

	public MultiplicationWorkload(int[][] a, int[][] b, String threadName) {
		this.a = a;
		this.b = b;
		this.threadName = threadName;
	}
	
	public IWorkload execute() {
		System.out.println("\n" +  this.threadName + " Matrix A------------\n" + MatrixFileOperationUtils.convertMatrixToString(a));
		c = MatricesOperationUtils.multiply(a, b);
		System.out.println("\n" +  this.threadName + " Resultant------------\n" + MatrixFileOperationUtils.convertMatrixToString(c));
		return this;
	}

	public int[][] getResult() {
		return c;
	}
}
