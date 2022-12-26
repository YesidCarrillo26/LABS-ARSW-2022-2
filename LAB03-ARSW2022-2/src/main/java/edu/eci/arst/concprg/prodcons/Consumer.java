/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;

import java.util.Queue;

/**
 *
 * @author hcadavid
 */
public class Consumer extends Thread{
    
    private Queue<Integer> queue;
    
    
    public Consumer(Queue<Integer> queue){
        this.queue=queue;        
    }
    
    @Override
    public void run() {
        while (true) {
        	consumir();
        }
    }
    public void consumir() {
    	synchronized (queue) {
        	while(queue.size() <= 0) {
        		try {
					queue.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
	        int elem=queue.poll();
	        System.out.println("Consumer consumes "+elem);   
	        try {
	            this.sleep(1000);
	        } catch (InterruptedException e) {
	            throw new RuntimeException(e);
	        }
	        queue.notifyAll();
    	}
    }
}
