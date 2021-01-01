package model;

class Wolf extends Animal {
    public Wolf(Position position, World world) {
        super(9, 5, position, world, "wolf.png");
    }

    @Override
    protected Animal reproduce(Position position) {
        return new Wolf(position, getWorld());
    }
}
