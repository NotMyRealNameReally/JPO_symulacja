package model;

public class Fox extends Animal {
    public Fox(Position position, World world) {
        super(3, 7, position, world, "fox.png", OrganismType.FOX);
    }

    @Override
    protected Animal reproduce(Position position) {
        return new Fox(position, getWorld());
    }

    @Override
    protected FightResults fight(Organism defender) {
        FightResults results = new FightResults(this, defender);

        if (defender.getStrength() <= this.getStrength()) {
            results.addLoserAffliction(Affliction.DEAD);
        } else {
            results.addLoserAffliction(Affliction.EVADED);
            move(false);
        }
        return results;
    }
}
