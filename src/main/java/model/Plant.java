package model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

abstract class Plant extends Organism {
    private double reproductionChance;
    private int reproductionTries;

    Plant(int strength, Position position, World world, String iconName, OrganismType name, double reproductionChance, int reproductionTries){
        super(strength, 0, position, world, iconName, name);
        this.reproductionChance = reproductionChance;
        this.reproductionTries = reproductionTries;
    }

    protected abstract Plant reproduce(Position position);

    @Override
    ActionResult action() {
        ActionResult actionResult = new ActionResult(this);
        actionResult.setPosition(getPosition());
        Random random = new Random();
        List<Position> possiblePositions = getWorld().getPossibleMovesNoCollision(this);
        for(int i = 0; i < reproductionTries; i++) {
            if (random.nextInt() < reproductionChance) {
                if (!possiblePositions.isEmpty()) {
                    int index = random.nextInt(possiblePositions.size());
                    Position position = possiblePositions.get(index);
                    possiblePositions.remove(index);
                    Plant other = reproduce(position);
                    getWorld().addOrganism(other);
                } else{
                    break;
                }
            }
        }
        setActionPoints(getActionPoints() - 1);
        return actionResult;
    }

    @Override
    protected FightResults fight(Organism defender) {
        return null;
    }

    @Override
    protected List<Affliction> react() {
        return Collections.emptyList();
    }
}
