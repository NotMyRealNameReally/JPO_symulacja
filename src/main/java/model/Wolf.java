package model;

class Wolf extends Animal {
    public Wolf(int posX, int posY, World world) {
        super(9, 5, posX, posY, world, "wolf.png");
    }

    @Override
    protected Animal reproduce(int x, int y) {
        return new Wolf(x, y, world);
    }
}
