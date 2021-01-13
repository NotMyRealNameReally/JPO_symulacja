package model;

class Grass extends Plant {
    Grass(Position position, World world){
        super(0, position, world, "grass.png", OrganismType.GRASS);
    }

    @Override
    protected Plant reproduce(Position position) {
        return new Grass(position, getWorld());
    }
}
