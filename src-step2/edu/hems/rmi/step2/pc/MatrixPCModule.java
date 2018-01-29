package edu.hems.rmi.step2.pc;

import edu.hems.rmi.step2.pc.util.MatricesOperationUtils;
import edu.hems.rmi.step2.service.PC2IO;

public class MatrixPCModule implements PC2IO {

	public MatrixPCModule() {
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
