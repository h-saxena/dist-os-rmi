package edu.hems.rmi.step3;

import java.io.IOException;
import java.util.Scanner;

import edu.hems.rmi.step3.io.MatrixIOModule;

public class IOModuleBootup {

	public static void main(String[] args) throws IOException {
		String pcHost = System.getProperty("host");
		int pcPort = Integer.parseInt(System.getProperty("port"));
		
		int rows = 1;
		int cols = 1;
		String mOperation = "m";
		Scanner s = new Scanner(System.in);

		while (true) {
		    System.out.print("Enter Square matrix size:  ");
		    try {
				cols = rows = s.nextInt();
				if(rows < 1 ||rows > 100)
					throw new RuntimeException("Invalid integer values");
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				System.out.println("Please enter a valid input between 1 and 100");
				continue;
			}
		    break;
		}

		while (true) {
		    System.out.print("Enter Operation to perform - enter 'm' for multiplication or 'd' for determinent:  ");
		    try {
			    mOperation = s.next();
			    
			    if(mOperation != null && !mOperation.isEmpty())
			    	mOperation = mOperation.trim().toLowerCase();
			    
				if(mOperation == null || mOperation.isEmpty() || (!mOperation.equals("m") && !mOperation.equals("d")))
					throw new RuntimeException("Invalid operation: " + mOperation);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				System.out.println("Please enter a valid operation");
				continue;
			}
		    break;
		}
		
		MatrixIOModule ioModule = new MatrixIOModule();
		ioModule.init(pcHost, pcPort);

		switch(mOperation) {
		case "m": ioModule.performMatrixMultiplication(rows, cols);
				  break;
		case "d": ioModule.performMatrixDeterminent(rows, cols);	
				  break;
		}
	}
}
