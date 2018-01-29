package edu.hems.rmi.ex1.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import edu.hems.rmi.ex1.compute.Compute;
import edu.hems.rmi.ex1.compute.Task;


public class ComputeAddClient {

    public static void main(String args[]) {
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry(3000);
            Compute comp = (Compute) registry.lookup(name);
            Task<Integer> task = new ComputeAddition(4,5);
            Integer sum = comp.executeTask(task);
            System.out.println(sum);
        } catch (Exception e) {
            System.err.println("ComputeAdd exception:");
            e.printStackTrace();
        }
    }    

}
