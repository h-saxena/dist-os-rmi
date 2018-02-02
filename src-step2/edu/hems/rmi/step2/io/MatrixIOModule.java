package edu.hems.rmi.step2.io;

import java.io.IOException;
import java.nio.file.Path;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

import edu.hems.rmi.step2.io.utils.MatrixFileOperationUtils;
import edu.hems.rmi.step2.service.PC2IO;

public class MatrixIOModule {
	
	PC2IO service = null;

	public MatrixIOModule() {
	}
	
	public void init(String pcHost, int pcPort) {
        try {
            String name = "matrixOperations";
            Registry registry = LocateRegistry.getRegistry(pcHost, pcPort);
            service = (PC2IO) registry.lookup(name);
        } catch (Exception e) {
            System.err.println("MatrixIOModule exception:");
            e.printStackTrace();
        }

	}

	public void performMatrixDeterminent(int rows, int cols) throws IOException {
		int[][] matrixA = generateMatrix(rows, cols);
		System.out.println("\n\nMatrix A =\n" + MatrixFileOperationUtils.convertMatrixToString(matrixA) );
		
		int detValue = service.matrixDeterminantOperation(matrixA);
		System.out.println("Matrix A determinent value = " + detValue);
		
	}

	public void performMatrixMultiplication(int rows, int cols) throws IOException {
		int[][] matrixA = generateMatrix(rows, cols);
		int[][] matrixB = generateMatrix(rows, cols);
		Path pathA = MatrixFileOperationUtils.saveMatrixToFile(matrixA, "MatrixA");
		System.out.println("\n\nMatrix A =\n" + MatrixFileOperationUtils.convertMatrixToString(matrixA));
		System.out.println("Matrix A is saved in data folder as --> " + pathA.getFileName());
		
		Path pathB = MatrixFileOperationUtils.saveMatrixToFile(matrixB, "MatrixB");
		System.out.println("\n\nMatrix B =\n" + MatrixFileOperationUtils.convertMatrixToString(matrixB) );
		System.out.println("Matrix B is saved in data folder as --> " + pathB.getFileName());
		
		int[][] matrixC = service.matricesMultipleOperation(matrixA, matrixB);
		Path pathC = MatrixFileOperationUtils.saveMatrixToFile(matrixC, "MatrixC");
		System.out.println("\n\nMatrix Multiplication Resultant C =\n" + MatrixFileOperationUtils.convertMatrixToString(matrixC) );
		System.out.println("Matrix C is saved in data folder as --> " + pathC.getFileName());

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
