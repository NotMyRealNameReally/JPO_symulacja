package model;

import java.util.List;

class Guarana extends Plant {
    Guarana(Position position, World world){
        super(0, position, world, "guarana.png", OrganismType.GUARANA, 0.3, 1);
    }

    @Override
    protected Plant reproduce(Position position) {
        return new Guarana(position, getWorld());
    }

    @Override
    protected List<Affliction> react() {
        return List.of(Affliction.STRONGER);
    }
}
