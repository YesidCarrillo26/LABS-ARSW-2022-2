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
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */
@Component
@Qualifier("Memory")

public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bluePrintHash = new HashSet<Blueprint>();
        for(Tuple<String,String> blueprint:blueprints.keySet()){
            if(blueprint.getElem1().equals(author)){
                bluePrintHash.add(blueprints.get(blueprint));
            }
        }
        if(bluePrintHash.size()!=0){
            return bluePrintHash;
        }
        throw new BlueprintNotFoundException("No se pudieron encontrar blueprints");

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
