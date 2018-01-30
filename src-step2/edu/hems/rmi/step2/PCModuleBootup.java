package edu.hems.rmi.step2;

import edu.hems.rmi.step2.pc.MatrixPCModule;

public class PCModuleBootup {

	public static void main(String[] args) {
		int pcPort = Integer.parseInt(System.getProperty("port"));
		
		MatrixPCModule pcModule = new MatrixPCModule();
		pcModule.init(pcPort);
	}
	
}
