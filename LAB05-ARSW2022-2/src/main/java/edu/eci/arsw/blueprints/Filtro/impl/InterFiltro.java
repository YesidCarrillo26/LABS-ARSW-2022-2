package edu.eci.arsw.blueprints.Filtro.impl;

import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;

public interface InterFiltro {
	public Blueprint filtrar(Blueprint bp);
	public void filterBlueprint(Blueprint bp);
	public void filterBlueprints(Set<Blueprint> blueprints);
}