package edu.hems.rmi.step2.pc.util;

public class MatricesOperationUtils {

	public static int[][] multiply(int[][] a, int[][] b) {
	    int rowsInA = a.length;
	       int columnsInA = a[0].length; // same as rows in B
	       int columnsInB = b[0].length;
	       int[][] c = new int[rowsInA][columnsInB];
	       for (int i = 0; i < rowsInA; i++) {
	           for (int j = 0; j < columnsInB; j++) {
	               for (int k = 0; k < columnsInA; k++) {
	                   c[i][j] = c[i][j] + a[i][k] * b[k][j];
	               }
	           }
	       }
	       return c;	
	 }

	public static int determinant(int[][] a) {
		// TODO Auto-generated method stub
		return 0;
	}
}
