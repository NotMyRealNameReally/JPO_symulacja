package model;

abstract class Animal extends Organism {

    public Animal(int strength, int initiative, int posX, int posY, World world, String iconName) {
        super(strength, initiative, posX, posY, world, iconName);
    }

    @Override
    public void action() {
        while (actionPoints > 0) {
            move();
            actionPoints--;
        }
    }

    @Override
    public FightResults fight(Organism attacker) {
        FightResults results;

        if (attacker.strength >= this.strength) {
            results = new FightResults(attacker, this);
        } else {
            results = new FightResults(this, attacker);
        }
        results.addLoserAffliction(Affliction.DEAD);
        return results;
    }

    private void move() {
        int dx;
        int dy;
        do
        {
            dx = (int) (1 - Math.round(Math.random()) * 2);
            dy = (int) (1 - Math.round(Math.random()) * 2);
        } while (!world.isValidPosition(posX + dx, posY + dy));
        posX += dx;
        posY += dy;
    }
}
