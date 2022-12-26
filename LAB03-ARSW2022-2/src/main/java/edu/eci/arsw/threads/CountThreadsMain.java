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
public class CountThreadsMain {
		static CountThread obj0;
		static CountThread obj1;
		static CountThread obj2;
	   public static void main(String[] args) {
		   obj0 = new CountThread(0,99,"1");
		   obj1 = new CountThread(99,199,"2");
		   obj2 = new CountThread(200,299,"3");
		   run();
		}
	   public static void run() {
		     obj0.run();
		     obj1.run();
		     obj2.run();
	   }
	   public static void start() {
		     obj0.start();
		     obj1.start();
		     obj2.start();
	   }
    
}
