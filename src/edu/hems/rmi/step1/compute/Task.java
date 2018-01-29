package edu.hems.rmi.step1.compute;

public interface Task<T> {
    T execute();
}