package model;

import java.util.List;
import java.util.Random;

abstract class Animal extends Organism {
    private boolean canReproduce;

    public Animal(int strength, int initiative, Position position, World world, String iconName) {
        super(strength, initiative, position, world, iconName);
    }

    protected abstract Animal reproduce(Position position);

    @Override
    public void action() {
        while (getActionPoints() > 0) {
            Position prevPosition = getPosition();
            move(true);
            getWorld().collisionOccurred(this).ifPresent(other -> {
                if (other.getClass() == this.getClass()) {
                    Animal child = reproduce(prevPosition);
                    getWorld().addOrganism(child);
                    setPosition(prevPosition);
                    child.move(false);
                } else {
                    fight(other);
                }
            });
            setActionPoints(getActionPoints() - 1);
        }
    }

    @Override
    public FightResults fight(Organism defender) {
        FightResults results;

        if (defender.getStrength() < this.getStrength()) {
            results = new FightResults(this, defender);
        } else {
            results = new FightResults(defender, this);
        }
        results.addLoserAffliction(Affliction.DEAD);
        return results;
    }

    void move(boolean collisionAllowed) {
        List<Position> possiblePositions;
        if (collisionAllowed) {
            possiblePositions = getWorld().getPossibleMovesWithCollision(this);
        } else {
            possiblePositions = getWorld().getPossibleMovesNoCollision(this);
        }
        if(!possiblePositions.isEmpty()){
            Random r = new Random();
            Position newPos = possiblePositions.get(r.nextInt(possiblePositions.size()));
            setPosition(newPos);
        }
    }
}