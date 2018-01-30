package edu.hems.rmi.step2;

import java.io.IOException;
import java.util.Scanner;

import edu.hems.rmi.step2.io.MatrixIOModule;

public class IOModuleBootup {

	public static void main(String[] args) throws IOException {
		MatrixIOModule ioModule = new MatrixIOModule();
		
		Scanner s = new Scanner(System.in);
	    System.out.print("Enter number of rows: ");
	    int rows = s.nextInt();
	    System.out.print("Enter number of columns: ");
	    int cols = s.nextInt();
	    
	    ioModule.performOperations(rows, cols);
	}
}
