package edu.eci.arsw.blueprints.Filtro.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
@Component
@Qualifier("Sub")
public class FiltroSubmuestreo implements InterFiltro{
	@Override
    public Blueprint filtrar(Blueprint bp) {
	List<Point> actual = bp.getPoints();
	int longitud=actual.size();
	Point[] nlista=new Point[longitud/2];
	int con=0;
	for (int i =1;i<longitud;i+=2) {
		
		nlista[con]=actual.get(i);
		con++;
    }
	
	Blueprint finala = new Blueprint(bp.getAuthor(), bp.getName(), nlista);
	return finala;
    }

    @Override
    public void filterBlueprint(Blueprint bp) {
        List<Point> points = new ArrayList<Point>(bp.getPoints());
        List<Point> pointsFilter = new ArrayList<Point>();
        int size = points.size();
        for(int i = 0; i<points.size();i++){
            if(i%2 == 1){
                pointsFilter.add(points.get(i));
            }
        }
        bp.setPoints(pointsFilter);
    }
    @Override
    public void filterBlueprints(Set<Blueprint> blueprints){
        for(Blueprint print: blueprints){
            filterBlueprint(print);
        }
    }
}