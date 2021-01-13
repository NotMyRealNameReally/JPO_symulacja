package model;

class Dandelion extends Plant {
    Dandelion(Position position, World world){
        super(0, position, world, "dandelion.png", OrganismType.DANDELION);
    }

    @Override
    ActionResult action() {
        return super.action();
    }

    @Override
    protected Plant reproduce(Position position) {
        return new Dandelion(position, getWorld());
    }
}
