package edu.eci.arsw.collabpaint.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.collabpaint.model.Point;
import edu.eci.arsw.collabpaint.persistence.CollabPaintException;
import edu.eci.arsw.collabpaint.persistence.ICollabPaintPersistence;

@Controller
public class STOMPMessagesHandler {
	
	@Autowired
	SimpMessagingTemplate msgt;

	@Autowired
	ICollabPaintPersistence collab;
    
	@MessageMapping("/newpoint.{numdibujo}")    
	public void handlePointEvent(Point pt,@DestinationVariable String numdibujo) throws Exception {
		System.out.println("Nuevo punto recibido en el servidor!:"+pt);
		msgt.convertAndSend("/topic/newpoint"+numdibujo, pt);
		try {
			List<Point> puntos = collab.getPolygon(numdibujo, pt);
			System.out.println("Se envio poligono" + puntos);
			msgt.convertAndSend("/topic/newpolygon." + numdibujo, puntos);
		} catch (CollabPaintException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
