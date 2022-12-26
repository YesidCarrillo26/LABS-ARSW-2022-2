/*
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;


import edu.eci.arsw.blueprints.Filtro.impl.InterFiltro;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    @Qualifier("Memory")

	BlueprintsPersistence bpp;
    
    @Autowired
    @Qualifier("Sub")
    InterFiltro filter;

	@Autowired
	public BlueprintsServices(BlueprintsPersistence bpp) {
		this.bpp = bpp;
	}
	

	public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
		bpp.saveBlueprint(bp);
	}

    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException, BlueprintPersistenceException {
        return bpp.getBluePrints();
    }

	/**
	 * 
	 * @param author blueprint's author
	 * @param name   blueprint's name
	 * @return the blueprint of the given name created by the given author
	 * @throws BlueprintNotFoundException if there is no such blueprint
	 */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException {
        return bpp.getBlueprint(author, name);
    }

	/**
	 * 
	 * @param author blueprint's author
	 * @return all the blueprints of the given author
	 * @throws BlueprintNotFoundException if the given author doesn't exist
	 */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException, BlueprintPersistenceException{

        return bpp.getBlueprintsByAuthor(author);
    }
    public void applyFilter(Blueprint bp) throws BlueprintNotFoundException {
        filter.filterBlueprint(bp);
    }
    public void applyFilter(Set<Blueprint> bps) throws BlueprintNotFoundException, BlueprintPersistenceException {
        filter.filterBlueprints(bps);
    }
    public void updateBluePrint(String author, String bpname, List<Point> points) throws BlueprintNotFoundException {
        Blueprint bp = getBlueprint(author,bpname);
        bp.setPoints(points);
    }

}
