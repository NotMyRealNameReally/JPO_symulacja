package model;

import java.util.List;
import java.util.Random;

abstract class Plant extends Organism {
    Plant(int strength, Position position, World world, String iconName, OrganismType name){
        super(strength, 0, position, world, iconName, name);
    }

    protected abstract Plant reproduce(Position position);

    @Override
    ActionResult action() {
        ActionResult actionResult = new ActionResult(this);
        actionResult.setPosition(getPosition());
        Random random = new Random();
        if(random.nextInt() > 0.7){
            List<Position> possiblePositions = getWorld().getPossibleMovesNoCollision(this);
            if(!possiblePositions.isEmpty()) {
                actionResult.setReproductionOccurred(true);
                Position position = possiblePositions.get(random.nextInt(possiblePositions.size()));
                Plant other = reproduce(position);
                getWorld().addOrganism(other);
            }
        }
        setActionPoints(getActionPoints() - 1);
        return actionResult;
    }

    @Override
    protected FightResults fight(Organism defender) {
        return null;
    }
}
