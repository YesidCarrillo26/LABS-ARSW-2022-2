package edu.eci.arsw.blueprints.filter.types.impl;

import edu.eci.arsw.blueprints.filter.types.filterType;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Component
@Qualifier("Sub")
public class FilterSub implements filterType {
    /**
     * Funcion sobreescrita para que filtre los puntos de un blueprint por
     * submuestreo. Eso significa que se revisa la lista e intercalado
     * @param bp
     * @throws BlueprintNotFoundException
     */
    @Override
    public void filterBlueprint(Blueprint bp) throws BlueprintNotFoundException {
        List<Point> points = new ArrayList<Point>(bp.getPoints());
        List<Point> pointsFilter = new ArrayList<Point>();
        int size = points.size();
        for(int i = 0; i<points.size();i++){
            //System.out.println("Index :"+i);
            if(i%2 == 1){
                //System.out.println("Size: "+points.size()+"Indice: "+i);
                pointsFilter.add(points.get(i));
                //System.out.println(pointsFilter);
            }
        }
        bp.setPoints(pointsFilter);
    }

    @Override
    public void filterBlueprints(Set<Blueprint> blueprints) throws BlueprintPersistenceException, BlueprintNotFoundException {
        //System.out.println("Antes----------------------------:"+blueprints.toString());
        for(Blueprint print: blueprints){
            filterBlueprint(print);
        }
        //System.out.println("Despues------------------:"+blueprints.toString());
    }

    @Override
    public void filterPrintsByAuthor(String author,Set<Blueprint> blueprints) throws BlueprintNotFoundException {
        for(Blueprint print: blueprints){
            if(print.getAuthor().equals(author)){
                filterBlueprint(print);
            }
        }
    }
}
