package model;

import java.util.Optional;

class ActionResult {
    private final Organism organism;
    private Position newPosition;
    private FightResults fightResults;
    private boolean reproductionOccurred;

    ActionResult(Organism organism) {
        this.organism = organism;
        this.reproductionOccurred = true;
    }

    void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    void setFightResults(FightResults fightResults) {
        this.fightResults = fightResults;
    }

    void reproductionOccurred() {
        this.reproductionOccurred = true;
    }

    Optional<Position> getNewPosition() {
        return Optional.ofNullable(newPosition);
    }

    Optional<FightResults> getFightResults() {
        return Optional.ofNullable(fightResults);
    }

    boolean hasReproductionOccurred() {
        return reproductionOccurred;
    }

    Organism getOrganism(){
        return organism;
    }
}
