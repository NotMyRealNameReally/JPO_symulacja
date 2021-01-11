package model;

import java.util.List;
import java.util.Random;

abstract class Animal extends Organism {

    Animal(int strength, int initiative, Position position, World world, String iconName, OrganismType name) {
        super(strength, initiative, position, world, iconName, name);
    }

    protected abstract Animal reproduce(Position position);

    @Override
    ActionResult action() {
        ActionResult actionResult = new ActionResult(this);
        Position prevPosition = getPosition();

        if (move(true)) {
            getWorld().collisionOccurred(this).ifPresent(other -> {
                if (other.getClass() == this.getClass()) {
                    setPosition(prevPosition);
                    actionResult.setReproductionOccurred(handleReproduction());
                } else {
                    actionResult.setFightResults(fight(other));
                }
            });
        }
        setActionPoints(getActionPoints() - 1);
        setAge(getAge() + 1);
        actionResult.setPosition(getPosition());
        return actionResult;
    }

    @Override
    protected FightResults fight(Organism defender) {
        FightResults results;

        if (defender.getStrength() <= this.getStrength()) {
            results = new FightResults(this, defender);
        } else {
            results = new FightResults(defender, this);
        }
        results.addLoserAffliction(Affliction.DEAD);
        return results;
    }

    private boolean handleReproduction() {
        if (!getWorld().getPossibleMovesNoCollision(this).isEmpty()) {
            Animal child = reproduce(getPosition());
            getWorld().addOrganism(child);
            child.move(false);
            return true;
        }
        return false;
    }

    private boolean move(boolean collisionAllowed) {
        List<Position> possiblePositions;
        if (collisionAllowed) {
            possiblePositions = getWorld().getPossibleMovesWithCollision(this);
        } else {
            possiblePositions = getWorld().getPossibleMovesNoCollision(this);
        }
        if (!possiblePositions.isEmpty()) {
            Random r = new Random();
            Position newPos = possiblePositions.get(r.nextInt(possiblePositions.size()));
            setPosition(newPos);
            return true;
        }
        return false;
    }
}