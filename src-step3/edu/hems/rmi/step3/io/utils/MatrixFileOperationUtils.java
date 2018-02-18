package edu.hems.rmi.step3.io.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MatrixFileOperationUtils {

	public static StringBuilder convertMatrixToString(int[][] matrix) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < matrix.length; i++)
		{
		   for(int j = 0; j < matrix[i].length; j++)
		   {
		      builder.append(matrix[i][j]+"");
		      if(j < matrix[i].length - 1)
		         builder.append(",");
		   }
		   builder.append("\n");
		}
		return builder;
	}
	public static Path saveMatrixToFile(int[][] matrix, String fileName) throws IOException {
		StringBuilder builder = convertMatrixToString(matrix);
		String fPath = "./data/";
		Files.createDirectories(Paths.get(fPath));
		String fName =  fPath + fileName + ".txt";
		Path newFilePath = Paths.get(fName);
		Files.deleteIfExists(newFilePath);
		Files.createFile(newFilePath);
		BufferedWriter writer = new BufferedWriter(new FileWriter(fName));
		writer.write(builder.toString());//save the string representation of the board
		writer.close();
		return newFilePath;
		
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
