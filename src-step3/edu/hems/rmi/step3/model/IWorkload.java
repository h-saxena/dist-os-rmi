package edu.hems.rmi.step3.model;

import java.io.Serializable;

public interface IWorkload extends Serializable {
	public IWorkload execute();
}
