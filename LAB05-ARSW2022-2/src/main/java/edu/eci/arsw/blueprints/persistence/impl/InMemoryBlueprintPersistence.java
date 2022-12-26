/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import  edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */
@Component
@Qualifier("Memory")

public class InMemoryBlueprintPersistence implements BlueprintsPersistence{
	private final int val=3;

    //private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    private final ConcurrentHashMap<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        inicializar();
        
    }  
    public void inicializar() {
    	Random random = new Random();
    	// se crean los puntos
        Point[] points = new Point[10];
        for(int j=0;j<10;j++){
            points[j] = new Point(random.nextInt(100), random.nextInt(100) );
        }
    	//dos prints asociados a un mismo autor
        for(int i = 0;i<2;i++){
            Blueprint newBp = new Blueprint("David leon","Blueprint"+i, points);
            blueprints.put(new Tuple<>(newBp.getAuthor(),newBp.getName()),newBp);
        }
     
        //tres planos por defecto
        Blueprint new1 = new Blueprint("jaun","Blueprint1", points);
        Blueprint new2 = new Blueprint("pedro","Blueprint2", points);
        Blueprint new3 = new Blueprint("jose","Blueprint3", points);
        blueprints.put(new Tuple<>(new1.getAuthor(),new1.getName()),new1);
        blueprints.put(new Tuple<>(new2.getAuthor(),new2.getName()),new2);
        blueprints.put(new Tuple<>(new3.getAuthor(),new3.getName()),new3);
    }
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (this.blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            this.blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }
    @Override
    public Set<Blueprint> getBluePrints() throws BlueprintPersistenceException, BlueprintNotFoundException {
        Set<Blueprint> prints = new HashSet<>();
        for(Tuple<String,String> tuple: this.blueprints.keySet()){
                prints.add(blueprints.get(tuple));
        }
        return prints;
    }
    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> prints = new HashSet<>();
        for(Tuple<String,String> tuple: this.blueprints.keySet()){
            if(tuple.o1.equals(author)){
                prints.add(blueprints.get(tuple));
            }
        }
        if(prints.size() == 0){throw new BlueprintNotFoundException("El author no tiene planos");}
        return prints;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> bluePrinthash = new HashSet<Blueprint>();
        for (Tuple<String, String> blueprint : blueprints.keySet()) {
            if (!blueprint.getElem1().equals("_authorname_")) {
                bluePrinthash.add(blueprints.get(blueprint));
            }
        }
        return bluePrinthash;
    }
	@Override
	public Set<Blueprint> getAll() throws BlueprintNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Blueprint> getSetBlueSprints(String author) throws BlueprintNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


}
