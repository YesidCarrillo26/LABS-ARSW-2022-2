/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;

import java.util.Set;

/**
 *
 * @author hcadavid
 */
public interface BlueprintsPersistence {
    
    /**
     * Funcion generada para guardar el blueprint en la memoria
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;
    
    /**
     * Funcion generada para retornar el blueprint de acuerdo al nombre del autor y del blueprint
     * el cual nos lo da el usuario
     * @param author blueprint's author
     * @param bprintname blueprint's author
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String bprintname) throws BlueprintNotFoundException;

    /**
     * Funcion generada para retornar todos los blueprints que se han ingresado al sistema
     * @return
     * @throws BlueprintPersistenceException
     * @throws BlueprintNotFoundException
     */
    public Set<Blueprint> getBluePrints() throws BlueprintPersistenceException, BlueprintNotFoundException;

    /**
     * Funcion generada para buscar todos los blueprints creados por el autor que nos de el usuario
     * @param author blueprint's author
     * @return Arreglo de blueprints que ha hecho el autor
     * @throws BlueprintNotFoundException en caso que el autor no exista
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;

    public void deleteBlueprint(String author,String name) throws BlueprintPersistenceException,BlueprintNotFoundException;
    
}
