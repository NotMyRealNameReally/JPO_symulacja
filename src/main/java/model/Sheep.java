package model;

class Sheep extends Animal{
    public Sheep(int posX, int posY, World world) {
        super(4, 4, posX, posY, world, "sheep.png");
    }

    @Override
    protected Animal reproduce(int x, int y) {
        return new Sheep(x, y, world);
    }
}
