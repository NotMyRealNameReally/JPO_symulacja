package model;

import java.util.Optional;

class ActionResult {
    private final Organism organism;
    private Position position;
    private FightResults fightResults;
    private boolean reproductionOccurred;

    ActionResult(Organism organism) {
        this.organism = organism;
        this.reproductionOccurred = false;
    }

    void setPosition(Position position) {
        this.position = position;
    }

    void setFightResults(FightResults fightResults) {
        this.fightResults = fightResults;
    }

    void reproductionOccurred() {
        this.reproductionOccurred = true;
    }

    Position getPosition() {
        return position;
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
