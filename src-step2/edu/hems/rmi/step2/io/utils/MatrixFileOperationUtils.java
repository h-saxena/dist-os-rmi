package edu.hems.rmi.step2.io.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MatrixFileOperationUtils {

	
	public static void saveMatrixToFile(int[][] board, String fileName) throws IOException {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < board.length; i++)
		{
		   for(int j = 0; j < board.length; j++)
		   {
		      builder.append(board[i][j]+"");
		      if(j < board.length - 1)
		         builder.append(",");
		   }
		   builder.append("\n");//append new line at the end of the row
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("./data/" + fileName + ".txt"));
		writer.write(builder.toString());//save the string representation of the board
		writer.close();
		
	}
	
	public static int[][] readMatrixToFile(String fileName) throws IOException {
		int totRows = 0;
		int totCols = 0;
		int[][] data = null;
		String fName = "./data/" + fileName + ".txt";
		
		{ // Block to determine the matrix size
			
			BufferedReader reader = new BufferedReader(new FileReader(fName));
			String line = "";
			while((line = reader.readLine()) != null) {
			   String[] cols = line.split(",");
			   totCols = cols.length;
			   totRows++;
			}
			reader.close();
		}

		{ // read content into matrix
			data = new int[totRows][totCols];
			BufferedReader reader = new BufferedReader(new FileReader(fName));
			String line = "";
			int row = 0;
			while((line = reader.readLine()) != null) {
			   String[] cols = line.split(",");
			   int col = 0;
			   for(String  c : cols) {
			      data[row][col] = Integer.parseInt(c.trim());
			      col++;
			   }
			   row++;
			}
			reader.close();
		}
		return data;
	}
}
