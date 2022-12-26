/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread {
	private int var_1;
	private int var_2;
	private String hilo=null;
	public CountThread(int a, int b, String hilo) {
		this.var_1=a;
		this.var_2=b;
		this.hilo=hilo;
	}
	public void run(){
		for(int i=var_1+1; i<var_2; i++) {
		System.out.println(i+" hilo "+hilo);  
		}
	}

}
