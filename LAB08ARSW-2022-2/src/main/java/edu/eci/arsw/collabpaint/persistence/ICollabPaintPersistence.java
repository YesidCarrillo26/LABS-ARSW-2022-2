package edu.eci.arsw.collabpaint.persistence;

import java.util.List;

import edu.eci.arsw.collabpaint.model.Point;

public interface ICollabPaintPersistence {
    public List<Point> getPolygon(String numero, Point pt) throws CollabPaintException;

	public void putPointBoard(String numero, Point pt);
}
