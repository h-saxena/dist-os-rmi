package edu.hems.rmi.step3;

import java.io.IOException;
import java.util.Scanner;

import edu.hems.rmi.step3.io.MatrixIOModule;
import edu.hems.rmi.step3.worker.MatrixWorkerModule;

public class WorkerModuleBootup {

	public static void main(String[] args) throws IOException {
		String pcHost = System.getProperty("host");
		int pcPort = Integer.parseInt(System.getProperty("port"));
		int workersCount = 1;
		
		Scanner s = new Scanner(System.in);
		

		while (true) {
		    System.out.print("Number of workers on this host:  ");
		    try {
				workersCount = s.nextInt();
				if(workersCount < 1 ||workersCount > 100)
					throw new RuntimeException("Invalid workers count");
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				System.out.println("Please enter a valid input between 1 and 100");
				continue;
			}
		    break;
		}

		MatrixWorkerModule.init(pcHost, pcPort, workersCount);

	}
}
