package model;

import java.util.function.BiPredicate;

abstract class Animal extends Organism {

    public Animal(int strength, int initiative, int posX, int posY, World world, String iconName) {
        super(strength, initiative, posX, posY, world, iconName);
    }

    protected abstract Animal reproduce(int x, int y);

    @Override
    public void action() {
        while (actionPoints > 0) {
            int prevX = getPosX();
            int prevY = getPosY();
            move(true);
            world.collisionOccurred(this).ifPresent(other -> {
                if(other.getClass() == this.getClass()){
                    Animal child = reproduce(prevX, prevY);
                    world.addOrganism(child);
                    posX = prevX;
                    posY = prevY;
                    child.move(false);
                } else{
                    fight(other);
                }
            });
            actionPoints--;
        }
    }

    @Override
    public FightResults fight(Organism defender) {
        FightResults results;

        if (defender.strength < this.strength) {
            results = new FightResults(this, defender);
        } else {
            results = new FightResults(defender, this);
        }
        results.addLoserAffliction(Affliction.DEAD);
        return results;
    }

    void move(boolean collisionAllowed) {
        BiPredicate<Integer, Integer> predicate;
        if(collisionAllowed){
            predicate = world::isValidPosition;
        } else{
            predicate = world::isValidEmptyPosition;
        }
        int dx;
        int dy;
        do
        {
            dx = (int) (1 - Math.round(Math.random() * 2));
            dy = (int) (1 - Math.round(Math.random() * 2));
        } while (!predicate.test(posX + dx, posY + dy));
        posX += dx;
        posY += dy;
    }
}
