package model;

import dto.WorldDto;

import java.io.Serializable;
import java.util.ArrayList;
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
        organisms = new ArrayList<>();
        organismsToAdd = new ArrayList<>();


        organisms.add(new Wolf(new Position(2, 7), this));
        organisms.add(new Wolf(new Position(6, 9), this));
        organisms.add(new Wolf(new Position(7, 3), this));

        organisms.add(new Sheep(new Position(2, 3), this));
        organisms.add(new Sheep(new Position(4, 5), this));
        organisms.add(new Sheep(new Position(6, 3), this));

        organisms.add(new Fox(new Position(2, 6), this));
        organisms.add(new Fox(new Position(6, 7), this));
        organisms.add(new Fox(new Position(3, 6), this));

        organisms.add(new Sloth(new Position(3, 5), this));
        organisms.add(new Sloth(new Position(3, 2), this));
        organisms.add(new Sloth(new Position(7, 5), this));

        organisms.add(new FlatEarther(new Position(7, 7), this));
        organisms.add(new FlatEarther(new Position(1, 8), this));
        organisms.add(new FlatEarther(new Position(8, 5), this));

        organisms.add(new Grass(new Position(9, 3), this));
        organisms.add(new Grass(new Position(10, 9), this));
        organisms.add(new Grass(new Position(8, 2), this));

        organisms.add(new Guarana(new Position(8, 13), this));
        organisms.add(new Guarana(new Position(11, 6), this));
        organisms.add(new Guarana(new Position(11, 10), this));

        organisms.add(new Dandelion(new Position(4, 16), this));
        organisms.add(new Dandelion(new Position(18, 12), this));
        organisms.add(new Dandelion(new Position(13, 10), this));


        turnCounter = 0;

        this.listener = listener;
        listener.worldCreated(new WorldDto(this));
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
