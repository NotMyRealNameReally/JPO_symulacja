package model;

import java.util.*;
import java.util.stream.Collectors;
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
        organisms.add(new Wolf(new Position(2, 7), this));
        organisms.add(new Wolf(new Position(6, 9), this));
        organisms.add(new Wolf(new Position(7, 3), this));
        organisms.add(new Sheep(new Position(2, 3), this));
        organisms.add(new Sheep(new Position(4, 5), this));
        organisms.add(new Sheep(new Position(6, 3), this));
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

    boolean isValidPosition(Position pos) {
        return pos.getX() >= 0 &&
                pos.getX() < sizeX &&
                pos.getY() >= 0 &&
                pos.getY() < sizeY;
    }

    boolean isEmptyPosition(Position pos){
        return Stream.concat(organisms.stream(), organismsToAdd.stream())
                         .noneMatch(organism -> organism.getPosition().equals(pos));
    }

    Optional<Organism> collisionOccurred(Organism organism) {
        return organisms.stream()
                        .filter(o -> o.getPosition().equals(organism.getPosition()) &&
                                o != organism)
                        .findFirst();
    }

    private void applyAffliction(Organism organism, Affliction affliction) {
        switch (affliction) {
            case DEAD -> organism.tagAsDead();
        }
    }

    List<Position> getPossibleMovesWithCollision(Organism organism){
        List<Position> positions = new ArrayList<>();
        Position current = organism.getPosition();

        for(int dx = -1; dx <= 1; dx++){
           for(int dy = -1; dy <= 1; dy++){
               Position position = current.translate(dx, dy);
               if(isValidPosition(position)){
                   positions.add(position);
               }
           }
        }
        positions.remove(current);
        return positions;
    }

    List<Position> getPossibleMovesNoCollision(Organism organism){
        return getPossibleMovesWithCollision(organism).stream()
                .filter(this::isEmptyPosition)
                .collect(Collectors.toList());
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
