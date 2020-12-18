package model;

class Sheep extends Animal{
    public Sheep(int posX, int posY, World world) {
        super(4, 4, posX, posY, world, "sheep.png");
    }

    @Override
    public void draw() {

    }

    @Override
    public Organism reproduce() {
        return new Sheep(posX, posY, world);
    }
}
