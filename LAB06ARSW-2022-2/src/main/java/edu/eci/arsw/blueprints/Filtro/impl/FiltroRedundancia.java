package edu.eci.arsw.blueprints.Filtro.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Component
@Qualifier("Redundancy")
public class FiltroRedundancia implements InterFiltro{
	@Override
	public Blueprint filtrar(Blueprint bp)
	{
		
		List<Point> lista_actual=bp.getPoints();
		
		List<Point> nueva=new ArrayList();
		
		//int i=0;
        for (int i=0;i<bp.getPoints().size();i++)
        {
        	
        	if (i==(bp.getPoints().size()-1))
        	{
            	Point punto1=lista_actual.get(i);
            	Point punto2=lista_actual.get(i-1);
            	if (punto1.getX()!=punto2.getX() && punto1.getY()!=punto2.getY())
            	{
            		int size_nueva=nueva.size();
            		if (size_nueva!=0 && nueva.get(size_nueva-1).getX()!=punto1.getX()&&nueva.get(size_nueva-1).getY()!=punto1.getY())
            		{
            			nueva.add(punto1);
            		}
            		else if(size_nueva==0) 
            		{
            			nueva.add(punto1);
            		}
            		else {
            			nueva.remove(size_nueva-1);
            		}
            		
            	}
        	}
        	else
        	{
            	Point punto1=lista_actual.get(i);
            	Point punto2=lista_actual.get(i+1);
            	if (punto1.getX()!=punto2.getX() && punto1.getY()!=punto2.getY())
            	{
            		int size_nueva=nueva.size();
            		if (size_nueva!=0 && nueva.get(size_nueva-1).getX()!=punto1.getX()&&nueva.get(size_nueva-1).getY()!=punto1.getY())
            		{
            			nueva.add(punto1);
            		}
            		else if(size_nueva==0) 
            		{
            			nueva.add(punto1);
            		}
            		else {
            			nueva.remove(size_nueva-1);
            		}
            	}
            	else {
            		i+=1;
            	}
            	
        	}

        }
        Point[] nuva_lista=new Point[nueva.size()];
        for (int i=0;i<nueva.size();i++)
        {
        	nuva_lista[i]=nueva.get(i);

        }

        
        Blueprint finala=new Blueprint(bp.getAuthor(),bp.getName(),nuva_lista);
        
		return finala;
	}

	@Override
	public void filterBlueprint(Blueprint bp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void filterBlueprints(Set<Blueprint> blueprints) {
		// TODO Auto-generated method stub
		
	}
}