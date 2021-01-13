package model;

class Guarana extends Plant {
    Guarana(Position position, World world){
        super(0, position, world, "guarana.png", OrganismType.GUARANA);
    }

    @Override
    protected Plant reproduce(Position position) {
        return new Guarana(position, getWorld());
    }
}
