package edu.eci.arsw.primefinder;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		PrimeFinderThread pft=new PrimeFinderThread(0, 10000000);
		PrimeFinderThread pft1=new PrimeFinderThread(10000000, 20000000);
		PrimeFinderThread pft2=new PrimeFinderThread(20000000, 30000000);
		pft.start();
		pft1.start();
		pft2.start();
		String a= ".";
		while(!a.equals("")) {
			Scanner in = new Scanner(System.in);
			a= in.nextLine();
		}
		pft.restaurar();
		pft1.restaurar();
		pft2.restaurar();
	}
	
}









