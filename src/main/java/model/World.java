package model;

import dto.WorldDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class World implements Serializable {
    private int turnCounter;
    private final int sizeX;
    private final int sizeY;
    private final List<Organism> organisms;
    private final List<Organism> organismsToAdd;
    private static transient WorldLogger logger;
    private transient WorldListener listener;

    public World(int sizeX, int sizeY, WorldListener listener) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        turnCounter = 0;
        organisms = new ArrayList<>();
        organismsToAdd = new ArrayList<>();

        List<Position> availablePositions = new ArrayList<>(sizeX * sizeY);
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                availablePositions.add(new Position(i, j));
            }
        }
        Collections.shuffle(availablePositions);
        int positionsToFill = (int) (sizeX * sizeY * 0.6);

        for(int i = 0; i < (positionsToFill * 0.05); i++) {
            organisms.add(new Wolf(availablePositions.remove(availablePositions.size() - 1), this));
        }
        for(int i = 0; i < (positionsToFill * 0.15); i++){
            organisms.add(new Sheep(availablePositions.remove(availablePositions.size() - 1), this));
        }
        for(int i = 0; i < (positionsToFill * 0.2); i++){
            organisms.add(new Fox(availablePositions.remove(availablePositions.size() - 1), this));
        }
        for(int i = 0; i < (positionsToFill * 0.15); i++){
            organisms.add(new Sloth(availablePositions.remove(availablePositions.size() - 1), this));
        }
        for(int i = 0; i < (positionsToFill * 0.05); i++){
            organisms.add(new FlatEarther(availablePositions.remove(availablePositions.size() - 1), this));
        }
        for(int i = 0; i < (positionsToFill * 0.3); i++){
            organisms.add(new Grass(availablePositions.remove(availablePositions.size() - 1), this));
        }
        for(int i = 0; i < (positionsToFill * 0.08); i++){
            organisms.add(new Guarana(availablePositions.remove(availablePositions.size() - 1), this));
        }
        for(int i = 0; i < (positionsToFill * 0.02); i++){
            organisms.add(new Dandelion(availablePositions.remove(availablePositions.size() - 1), this));
        }

        setListener(listener);
    }

    public void newTurn() {
        logger.logNewTurn(turnCounter);
        organisms.stream().sorted()
                .forEach(organism -> {
                    if (!organism.isDead()) {
                        while (organism.getActionPoints() > 0) {
                            ActionResult result = organism.action();
                            result.getFightResults().ifPresent(this::resolveFight);
                            logger.logAction(result);
                        }
                        organism.addActionPoints(1);
                    }
                });
        organisms.removeIf(Organism::isDead);
        organismsToAdd.removeIf(Organism::isDead);
        organisms.addAll(organismsToAdd);
        organismsToAdd.clear();
        turnCounter++;
        listener.worldChanged();
    }

    public void forceAddOrganism(Position position, OrganismType type){
        organisms.removeIf(organism -> organism.getPosition().equals(position));
        organisms.add(switch(type){
            case SHEEP -> new Sheep(position, this);
            case WOLF -> new Wolf(position, this);
            case FOX -> new Fox(position, this);
            case SLOTH -> new Sloth(position, this);
            case GRASS -> new Grass(position, this);
            case GUARANA -> new Guarana(position, this);
            case DANDELION -> new Dandelion(position, this);
            case FLAT_EARTHER -> new FlatEarther(position, this);
        });
        listener.worldChanged();
    }

    void addOrganism(Organism organism) {
        organismsToAdd.add(organism);
    }

    List<Position> getPossibleMovesWithCollision(Organism organism) {
        List<Position> positions = new ArrayList<>();
        Position current = organism.getPosition();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                Position position = current.translate(dx, dy);
                if (isValidPosition(position)) {
                    positions.add(position);
                }
            }
        }
        positions.remove(current);
        return positions;
    }

    List<Position> getPossibleMovesNoCollision(Organism organism) {
        return getPossibleMovesWithCollision(organism)
                .stream()
                .filter(this::isEmptyPosition)
                .collect(Collectors.toList());
    }

    Optional<Organism> collisionOccurred(Organism organism) {
        return Stream.concat(organisms.stream(), organismsToAdd.stream())
                .filter(other -> other.getPosition().equals(organism.getPosition()) &&
                        other != organism)
                .findFirst();
    }

    private boolean isValidPosition(Position pos) {
        return pos.getX() >= 0 &&
                pos.getX() < sizeX &&
                pos.getY() >= 0 &&
                pos.getY() < sizeY;
    }

    private boolean isEmptyPosition(Position pos) {
        return Stream.concat(organisms.stream(), organismsToAdd.stream())
                .noneMatch(organism -> organism.getPosition().equals(pos));
    }

    private void resolveFight(FightResults fightResults) {
        fightResults.getWinnerAfflictions().forEach(affliction -> applyAffliction(fightResults.getWinner(), affliction));
        fightResults.getLoserAfflictions().forEach(affliction -> applyAffliction(fightResults.getLoser(), affliction));
    }

    private void applyAffliction(Organism organism, Affliction affliction) {
        switch (affliction) {
            case DEAD, EATEN -> organism.tagAsDead();
            case STRONGER -> organism.setStrength(organism.getStrength() + 3);
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

    public void setListener(WorldListener listener) {
        this.listener = listener;
        listener.worldCreated(new WorldDto(this));
    }

    public static void setLogger(WorldLogger logger) {
        World.logger = logger;
    }
}
