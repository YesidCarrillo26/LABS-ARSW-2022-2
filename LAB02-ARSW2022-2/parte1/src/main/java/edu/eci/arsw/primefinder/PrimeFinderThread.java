package edu.eci.arsw.primefinder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	private int a,b, countP;
	private final static int seconds = 5000;
	public static long inicio, fin, time;
	public boolean pause=true;
	
	private List<Integer> primes=new LinkedList<Integer>();
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}
	public void run(){
		inicio = System.currentTimeMillis();
		fin = System.currentTimeMillis();
		synchronized(primes) {
			for (int i=a;i<=b;i++){
				if(pause) {
					time= fin-inicio;
				}
				else {
					time=0;
				}
				if (time<=seconds) {
					if (isPrime(i)){
						primes.add(i);
						System.out.println(i);
						fin = System.currentTimeMillis();
					}
				}
				else {
					countP=primes.size();
					System.out.println("El numero de primos encontrado es "+countP);
					try {
						System.out.println("Presione Enter Para Continuar");
						primes.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
		
	}
	
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	public void restaurar() {
		synchronized(primes) {
			pause=false;
			primes.notify();
		}
	}
	
}
