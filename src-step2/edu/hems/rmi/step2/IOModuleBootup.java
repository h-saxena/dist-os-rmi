package edu.hems.rmi.step2;

import java.io.IOException;
import java.util.Scanner;

import edu.hems.rmi.step2.io.MatrixIOModule;

public class IOModuleBootup {

	public static void main(String[] args) throws IOException {
		String pcHost = System.getProperty("host");
		int pcPort = Integer.parseInt(System.getProperty("port"));
		
		
		Scanner s = new Scanner(System.in);
	    System.out.print("Enter number of rows: ");
	    int rows = s.nextInt();
	    System.out.print("Enter number of columns: ");
	    int cols = s.nextInt();

		MatrixIOModule ioModule = new MatrixIOModule();
		ioModule.init(pcHost, pcPort);

	    ioModule.performMatrixOperations(rows, cols);
	}
}
