/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.Filtro.impl.FiltroRedundancia;
import edu.eci.arsw.blueprints.Filtro.impl.FiltroSubmuestreo;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        } catch (BlueprintPersistenceException ex) {

        }
    }

    @Test
    public void getBlueprint() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("yesid", "JEG", pts);
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            ex.printStackTrace();
            System.out.println("No se pudo guardar el BluePrint");
        }
        try {
            Blueprint blueprint = ibpp.getBlueprint("yesid", "JEG");
            assertEquals(blueprint, bp);
        } catch (BlueprintNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("No se pudo encontrar el blueprint");
        }
    }

    @Test
    public void getBlueprintsByAuthor() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("david", "JEG", pts);
        Point[] pts2 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp2 = new Blueprint("yesid", "JEG", pts);
        Point[] pts3 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp3 = new Blueprint("leon", "JEG", pts);

        Set<Blueprint> blueprintSet = new HashSet<Blueprint>();
        blueprintSet.add(bp1);
        blueprintSet.add(bp2);
        blueprintSet.add(bp3);

        //System.out.println(ibpp);
        try {
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            ibpp.saveBlueprint(bp3);
        } catch (BlueprintPersistenceException ex) {
            fail("No se pudo guardar el BluePrint");
        }

        try {
            Set<Blueprint> blueprintSetResult = ibpp.getBlueprintsByAuthor("david");
            assertEquals(blueprintSetResult.iterator().next(), blueprintSet);
            System.out.println(blueprintSet);
            System.out.println(blueprintSetResult.iterator().next());
        } catch (BlueprintNotFoundException ex) {
            fail("No se pudo encontrar el blueprint");
        }
    }

    @Test
    public void testRedundancia() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence persistencia=new InMemoryBlueprintPersistence();
        FiltroRedundancia filtro = new FiltroRedundancia();

        Point[] lpuntos1=new Point[]{new Point(10, 10),new Point(12, 12), new Point(10, 10), new Point(10, 10), new Point(12, 12), new Point(50, 50)};
        Blueprint user1=new Blueprint("David", "leon",lpuntos1);

        Point[] lpuntos2=new Point[]{new Point(10, 10), new Point(50, 50)};//lista de prueba sin puntos repetidos
        Blueprint user2=new Blueprint("Yesid", "Carrillo",lpuntos2);
        persistencia.saveBlueprint(user1);
        persistencia.saveBlueprint(user2);
        Blueprint bpToTest = filtro.filtrar(persistencia.getBlueprint("Pablo", "Perez"));
        assertEquals(user2.getPoints().get(0).getX(),bpToTest.getPoints().get(0).getX()); 
    }

    @Test
    public void testSubmuestreo() throws BlueprintPersistenceException, BlueprintNotFoundException{
    	InMemoryBlueprintPersistence persistencia=new InMemoryBlueprintPersistence();
    	FiltroSubmuestreo filtro = new FiltroSubmuestreo();
    	Point[] lpuntos1=new Point[]{new Point(10, 10),new Point(20, 20), new Point(30, 30), new Point(40, 40), new Point(50, 50), new Point(60, 60)};
        Blueprint user1=new Blueprint("Yesid", "Carrilo",lpuntos1);
        Point[] lpuntos2=new Point[]{new Point(20, 20),new Point(40, 40), new Point(60, 60)};
        Blueprint user2=new Blueprint("David", "Leon",lpuntos2);
        persistencia.saveBlueprint(user1);
        persistencia.saveBlueprint(user2);
        Blueprint bpToTest = filtro.filtrar(persistencia.getBlueprint("Pablo", "Perez"));
        assertEquals(user2.getPoints().get(0).getX(),bpToTest.getPoints().get(0).getX()); 
    }
    

   
}



    
                                                                                                                 
