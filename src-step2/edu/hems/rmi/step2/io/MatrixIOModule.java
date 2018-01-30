package edu.hems.rmi.step2.io;

import java.io.IOException;
import java.util.Random;

import edu.hems.rmi.step2.io.utils.MatrixFileOperationUtils;

public class MatrixIOModule {

	public MatrixIOModule() {
	}
	
	public void performOperations(int rows, int cols) throws IOException {
		int[][] matrixA = generateMatrix(rows, cols);
		int[][] matrixB = generateMatrix(rows, cols);
		MatrixFileOperationUtils.saveMatrixToFile(matrixA, "MatrixA");
		MatrixFileOperationUtils.saveMatrixToFile(matrixB, "MatrixB");
	}
	
	 // create and return a random M-by-N matrix 
	private static int[][] generateMatrix(int M, int N) {
		int[][] data = new int[M][N];

        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                data[i][j] = (int) getRandomNumberInRange(-100, 100);

		return data;
	}
	
	private static int getRandomNumberInRange(int min, int max) {
		Random r = new Random();
		return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
	}

}
