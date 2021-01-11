package model;

public class FlatEarther extends Animal {
    public FlatEarther(Position position, World world){
        super(9, 1, position, world, "flat_earther.png", OrganismType.FLAT_EARTHER);
    }

    @Override
    protected Animal reproduce(Position position) {
        return new FlatEarther(position, getWorld());
    }
}
