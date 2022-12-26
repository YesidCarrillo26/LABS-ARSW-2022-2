package edu.eci.arsw.blueprints.controller;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RestController
public class BlueprintAPIController {
	@Autowired
	BlueprintsServices service;
	
	@RequestMapping(value = "/blueprints",method = RequestMethod.GET)
    public ResponseEntity<?> blueprintsServices() {
        try {
            Set<Blueprint> bps= null;
            bps = service.getAllBlueprints();
            service.applyFilter(bps);
            return new ResponseEntity<>(bps, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/blueprints/{author}",method = RequestMethod.GET)
    public ResponseEntity<?> BluePrintsByAuthor(@PathVariable String author) throws BlueprintPersistenceException{
        Set<Blueprint> bps = null;
        try {
        	bps = service.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(bps,HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
        	return new ResponseEntity<>("No se encontro el autor",HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/blueprints/{author}/{bpname}")
    public ResponseEntity<?> GetBluePrint(@PathVariable String author, @PathVariable String bpname){
        Blueprint bp = null;
        try{
            bp = service.getBlueprint(author,bpname);
            return new ResponseEntity<>(bp,HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
        	return new ResponseEntity<>("No existe autor o plano con ese nombre.",HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/blueprints/addBlueprint",method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> manejadorPostRecursoBluePrint(@RequestBody Blueprint bp){
        try {
            service.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException e) {
            return new ResponseEntity<>("EL nombre del plano ya existe",HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping(value = "/blueprints/{author}/{bpname}")
    public ResponseEntity<?> manejadorPutRecursoBluePrint(@PathVariable String author, @PathVariable String bpname, @RequestBody List<Point> points){
        try {
            service.updateBluePrint(author,bpname,points);
            Blueprint bp = service.getBlueprint(author,bpname);
           return new ResponseEntity<>(bp,HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
        	return new ResponseEntity<>("No exite el plano con el nombre dado",HttpStatus.NOT_FOUND);
        }
    }


}