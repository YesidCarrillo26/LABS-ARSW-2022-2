package edu.eci.arsw.blueprints.filter.types.impl;

import edu.eci.arsw.blueprints.filter.types.filterType;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Qualifier("Redundancy")
public class filterRedundancy implements filterType {
    /**
     * Funcion generada para revisar en toda la lista de puntos cual está
     * repetido para eliminarlo. Como los elimina todos, luego se vuelve
     * a poner el punto. Se le deja al blueprint con la nueva lista de puntos
     * @param bp
     * @param point
     */
    public void review(Blueprint bp, Point point){
        List<Point> points = new ArrayList<Point>(bp.getPoints());
        for(int i = 0; i<=points.size()-1;i++){
            if(point.equals(points.get(i))){
                points.remove(i);
            }
        }
        points.add(point);
        bp.setPoints(points);
    }

    /**
     * Funcion sobreescrita para que filtre los puntos de un blueprint por
     * redundancia. Eso significa que se revisa la lista en busca de puntos
     * que esten repetidos para eliminarlos
     * @param bp
     * @throws BlueprintNotFoundException
     */
    @Override
    public void filterBlueprint(Blueprint bp) throws BlueprintNotFoundException {
        for(Point point:bp.getPoints()){
            review(bp,point);
        }
    }

    /**
     * Funcion sobreescrita para filtrar por redundancia (eliminar puntos
     * repetidos) un arreglo de blueprints
     * @param blueprints
     * @throws BlueprintPersistenceException
     * @throws BlueprintNotFoundException
     */
    @Override
    public void filterBlueprints(Set<Blueprint> blueprints) throws BlueprintPersistenceException, BlueprintNotFoundException {
        for(Blueprint print: blueprints){
            filterBlueprint(print);
        }
    }

    /**
     * Funcion sobreescrita para filtrar por redundancia (eliminar puntos
     * repetidos) un arreglo de blueprints pero solo del autor escogido
     * @param author
     * @param blueprints
     * @throws BlueprintNotFoundException
     */
    @Override
    public void filterPrintsByAuthor(String author,Set<Blueprint> blueprints) throws BlueprintNotFoundException {
        for(Blueprint print: blueprints){
            if(print.getAuthor().equals(author)){
                filterBlueprint(print);
            }
        }
    }
}
