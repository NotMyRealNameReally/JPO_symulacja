package model;

class Sheep extends Animal{
    public Sheep(Position position, World world) {
        super(4, 4, position, world, "sheep.png", OrganismType.SHEEP);
    }

    @Override
    protected Animal reproduce(Position position) {
        return new Sheep(position, getWorld());
    }
}
