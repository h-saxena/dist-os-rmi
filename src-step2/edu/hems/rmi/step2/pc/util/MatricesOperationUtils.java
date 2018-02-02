package edu.hems.rmi.step2.pc.util;

public class MatricesOperationUtils {

	public static int[][] multiply(int[][] a, int[][] b) {
		int m1 = a.length;
		int n1 = a[0].length;
		int m2 = b.length;
		int n2 = b[0].length;
		if (n1 != m2)
			throw new RuntimeException("Illegal matrix dimensions.");
		int[][] c = new int[m1][n2];
		for (int i = 0; i < m1; i++)
			for (int j = 0; j < n2; j++)
				for (int k = 0; k < n1; k++)
					c[i][j] += a[i][k] * b[k][j];
		return c;
	}

	public static int determinant(int[][] pMatrix) {
		int sum = 0;
		int s;
		if (pMatrix.length == 1) { 
			return (pMatrix[0][0]);
		}
		
		// finds determinant using row-by-row expansion
		for (int i = 0; i < pMatrix.length; i++) { 
			// creates smaller matrix- values not in same row, column
			int[][] smaller = new int[pMatrix.length - 1][pMatrix.length - 1]; 
																				
			for (int a = 1; a < pMatrix.length; a++) {
				for (int b = 0; b < pMatrix.length; b++) {
					if (b < i) {
						smaller[a - 1][b] = pMatrix[a][b];
					} else if (b > i) {
						smaller[a - 1][b - 1] = pMatrix[a][b];
					}
				}
			}
			if (i % 2 == 0) { // sign changes based on i
				s = 1;
			} else {
				s = -1;
			}
			// recursive step: determinant of larger determined by smaller
			sum += s * pMatrix[0][i] * (determinant(smaller)); 
		}
		return (sum); 
	}

}
