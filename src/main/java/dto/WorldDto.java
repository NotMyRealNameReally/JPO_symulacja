package dto;

import java.util.Collections;
import java.util.List;

import model.Organism;
import model.World;

public class WorldDto {
    private final int turnNumber;
    private final int sizeX;
    private final int sizeY;
    private final List<Organism> organisms;

    public WorldDto(World world){
        this.turnNumber = world.getTurnCounter();
        this.sizeX = world.getSizeX();
        this.sizeY = world.getSizeY();
        this.organisms = Collections.unmodifiableList(world.getOrganisms());
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }
}
