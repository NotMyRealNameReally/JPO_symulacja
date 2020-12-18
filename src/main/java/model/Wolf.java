package model;

class Wolf extends Animal {
    public Wolf(int posX, int posY, World world) {
        super(9, 5, posX, posY, world, "wolf.png");
    }

    @Override
    public void draw() {

    }

    @Override
    public Organism reproduce() {
        return new Wolf(posX, posY, world);
    }
}
