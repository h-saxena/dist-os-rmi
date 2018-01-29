package edu.hems.rmi.step2.io;

public class MatrixIOModule {

	
	 // create and return a random M-by-N matrix 
	private static int[][] generateMatrix(int M, int N) {
		int[][] data = new int[M][N];

        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                data[i][j] = (int) Math.random();

		return data;
	}
}
