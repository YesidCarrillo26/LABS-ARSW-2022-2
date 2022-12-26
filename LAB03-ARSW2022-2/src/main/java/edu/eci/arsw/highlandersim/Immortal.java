package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;
    
    private int health;
    
    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());
    
    private boolean pause=false;


    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue=defaultDamageValue;
    }

    public void run() {

        while (true) {
        	synchronized (this) {
            	while(pause) {
            		try {
    					this.wait();
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            	}
            	game();
			}
        }

    }
    public void game() {
        Immortal im;
    	
        int myIndex = immortalsPopulation.indexOf(this);

        int nextFighterIndex = r.nextInt(immortalsPopulation.size());

        //avoid self-fight
        if (nextFighterIndex == myIndex) {
            nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
        }

        im = immortalsPopulation.get(nextFighterIndex);

        synchronized (immortalsPopulation){
            this.fight(im);
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fight(Immortal i2) {

        if (i2.getHealth() > 0) {
            i2.changeHealth(i2.getHealth() - defaultDamageValue);
            this.health += defaultDamageValue;
            updateCallback.processReport("Fight: " + this + " vs " + i2+"\n");
        } else {
            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
        }

    }
    // Se remueve el inmortal de la lista si su vida es menor a 0 porque esta muerto
    public void changeHealth(int v) {
    	if(this.health > 0) {
    		health = v;
    	}else{
    	    health = v;
    	    immortalsPopulation.remove(this);
    	}
    }

    public int getHealth() {
        return health;
    }
	public void pause() {
		pause=!pause;
	}
	public void resumen() {
		synchronized (this) {
			this.notify();
		}
	}
    @Override
    public String toString() {

        return name + "[" + health + "]";
    }


}