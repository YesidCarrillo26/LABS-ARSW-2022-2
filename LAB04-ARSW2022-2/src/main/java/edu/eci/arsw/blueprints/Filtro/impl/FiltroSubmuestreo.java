package edu.eci.arsw.blueprints.Filtro.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
@Service
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

}