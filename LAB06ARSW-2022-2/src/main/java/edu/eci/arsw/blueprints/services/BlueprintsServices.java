/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.types.filterType;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
@Component
//@Qualifier("Service")
public class BlueprintsServices {
   
    @Autowired
    @Qualifier("Memory")
    BlueprintsPersistence bpp;

    @Autowired
    @Qualifier("Redundancy")
    filterType filter;
    
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }
    
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException, BlueprintPersistenceException {
        return bpp.getBluePrints();
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
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

    /**
     * Aplica el filtro a el blueprint
     * @param bp Blueprint a aplicar el filtro
     * @throws BlueprintNotFoundException
     */
    public void applyFilter(Blueprint bp) throws BlueprintNotFoundException {
        filter.filterBlueprint(bp);
    }

    /**
     * Aplica el filtro a el set de Blueprints
     * @param bps Set de Blueprints a aplicar el filtro
     * @throws BlueprintNotFoundException
     * @throws BlueprintPersistenceException
     */
    public void applyFilter(Set<Blueprint> bps) throws BlueprintNotFoundException, BlueprintPersistenceException {
        filter.filterBlueprints(bps);
    }

    /**
     * Aplica el filtro por autor a un set de BluePrints
     * @param author Nombre del autor del plano a los que se hara filtro
     * @param bps Set de planos a los que se hara el filtro por autor
     * @throws BlueprintNotFoundException
     * @throws BlueprintPersistenceException
     */
    public void applyFilerByAuthor(String author,Set<Blueprint> bps) throws BlueprintNotFoundException, BlueprintPersistenceException {
        filter.filterPrintsByAuthor(author,bps);
    }

    public void updateBluePrint(String author, String bpname, List<Point> points) throws BlueprintNotFoundException {
        Blueprint bp = getBlueprint(author,bpname);
        bp.setPoints(points);
    }

    public void deleteService(String author, String bpname) throws BlueprintNotFoundException, BlueprintPersistenceException {
        bpp.deleteBlueprint(author,bpname);
    }

    
}
