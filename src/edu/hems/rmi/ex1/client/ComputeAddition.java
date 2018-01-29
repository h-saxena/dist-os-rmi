package edu.hems.rmi.ex1.client;

import java.io.Serializable;

import edu.hems.rmi.ex1.compute.Task;

public class ComputeAddition implements Task<Integer>, Serializable {
	Integer a = 0;
	Integer b = 0;
	
	public ComputeAddition(Integer a, Integer b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public Integer execute() {
		return a + b;
	}

}
