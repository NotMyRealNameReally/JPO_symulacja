package model;

import java.util.function.Predicate;

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
        Predicate<Position> predicate;
        if (collisionAllowed) {
            predicate = getWorld()::isValidPosition;
        } else {
            predicate = getWorld()::isValidEmptyPosition;
        }
        int dx;
        int dy;
        Position newPos;
        do {
            dx = (int) (1 - Math.round(Math.random() * 2));
            dy = (int) (1 - Math.round(Math.random() * 2));
            newPos = getPosition().translate(dx, dy);
        } while (!predicate.test(newPos));
        setPosition(newPos);
    }

    private void getPossibleMoves(){

    }
}
