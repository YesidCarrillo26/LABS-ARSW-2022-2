/*
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;


import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.HashSet;
import java.util.LinkedHashMap;
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

	BlueprintsPersistence bpp = null;

	@Autowired
	public BlueprintsServices(BlueprintsPersistence bpp) {
		this.bpp = bpp;
	}
	

	public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
		bpp.saveBlueprint(bp);
	}

	public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
		Set<Blueprint> setresulting = bpp.getAll();
		if (setresulting.size() < 1)
			throw new BlueprintNotFoundException("doesn't exists blueprint");
		return setresulting;
	}

	/**
	 * 
	 * @param author blueprint's author
	 * @param name   blueprint's name
	 * @return the blueprint of the given name created by the given author
	 * @throws BlueprintNotFoundException if there is no such blueprint
	 */
	public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
		Blueprint blueprint = bpp.getBlueprint(author, name);
		if (blueprint == null)
			throw new BlueprintNotFoundException("doesn't exists blueprint");
		return blueprint;
	}

	/**
	 * 
	 * @param author blueprint's author
	 * @return all the blueprints of the given author
	 * @throws BlueprintNotFoundException if the given author doesn't exist
	 */
	public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
		HashSet<Blueprint> blueprints = (HashSet<Blueprint>) bpp.getSetBlueSprints(author);
		if (blueprints.size() < 1)
			throw new BlueprintNotFoundException("doesn't exists blueprint");
		return blueprints;
	}

}
