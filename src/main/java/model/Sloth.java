package model;

public class Sloth extends Animal {
    public Sloth(Position position, World world){
        super(2, 1, position, world, "sloth.png", OrganismType.SLOTH);
    }

    @Override
    ActionResult action() {
        setActionPoints(getActionPoints() - 1);
        return super.action();
    }

    @Override
    protected Animal reproduce(Position position){
        return new Sloth(position, getWorld());
    }
}
