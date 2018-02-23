package edu.hems.rmi.step3.model;

import java.io.Serializable;

/*
 * is a unit of workload passed to a worker module to work/execute on a given time.
 */
public interface IWorkload extends Serializable {
	public IWorkload execute();
}
