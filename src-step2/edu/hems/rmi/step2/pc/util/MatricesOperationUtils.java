package edu.hems.rmi.step2.pc.util;

public class MatricesOperationUtils {

	public static int[][] multiply(int[][] a, int[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        int[][] c = new int[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
	 }

	public static int determinant(int[][] a) {
		// TODO Auto-generated method stub
		return 0;
	}
}
