package edu.hems.rmi.step3.model;

public class Matrix {

	int[][] matrix;
	
	public int[][] getMatrix() {
		return matrix;
	}
	
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	public int getRowsCount() {
		return matrix.length;
	}
	
	public int getColumnCount() {
		return matrix[0].length;
	}
	
}
