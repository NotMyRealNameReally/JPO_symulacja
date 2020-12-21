package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import dto.WorldDto;

public class World {
    private int turnCounter;
    private final int sizeX;
    private final int sizeY;
    private final List<Organism> organisms;
    private final List<Organism> organismsToAdd;
    private final WorldLogger logger;
    private WorldListener listener;

    public World(WorldLogger logger, WorldListener listener) {
        sizeX = 20;
        sizeY = 20;
        organisms = new ArrayList<>();
        organismsToAdd = new ArrayList<>();
        organisms.add(new Wolf(2, 2, this));
        organisms.add(new Wolf(6, 9, this));
        organisms.add(new Wolf(7, 3, this));
        organisms.add(new Sheep(2, 3, this));
        organisms.add(new Sheep(4, 5, this));
        organisms.add(new Sheep(6, 3, this));
        turnCounter = 0;

        this.logger = logger;
        this.listener = listener;
        listener.worldCreated(new WorldDto(this));
    }

    public void newTurn() {
        //logger.logNewTurn(turnCounter);
        organisms.stream().sorted()
                 .forEach(organism -> {
                     if (!organism.isDead()) {
                         organism.action();
                         organism.addActionPoints(1);
                     }
                 });
        organisms.removeIf(Organism::isDead);
        organisms.addAll(organismsToAdd);
        organismsToAdd.clear();
        turnCounter++;
        listener.worldChanged();
    }

    void addOrganism(Organism organism){
        organismsToAdd.add(organism);
    }

    boolean isValidPosition(int posX, int posY) {
        return posX >= 0 &&
                posX < sizeX &&
                posY >= 0 &&
                posY < sizeY;
    }

    boolean isValidEmptyPosition(int posX, int posY){
        return isValidPosition(posX, posY) &&
                Stream.concat(organisms.stream(), organismsToAdd.stream())
                         .noneMatch(organism -> posX == organism.getPosX() && posY == organism.getPosY());
    }

    Optional<Organism> collisionOccurred(Organism organism) {
        return organisms.stream()
                        .filter(o -> o.posX == organism.posX &&
                                o.posY == organism.posY &&
                                o != organism)
                        .findFirst();
    }

    private void applyAffliction(Organism organism, Affliction affliction) {
        switch (affliction) {
            case DEAD -> organism.tagAsDead();
        }
    }

    public int getTurnCounter() {
        return turnCounter;
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
