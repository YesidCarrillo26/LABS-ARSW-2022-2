package edu.eci.arsw.blueprints.Filtro;

import edu.eci.arsw.blueprints.model.Blueprint;

import java.util.Set;

public interface Filtro {

    public Blueprint filtroBlueprint(Blueprint blueprint);


    public Set<Blueprint> multiFiltroBluePrint(Set<Blueprint> blueprints);
}
