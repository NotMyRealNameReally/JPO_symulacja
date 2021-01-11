package model;

import java.util.stream.Collectors;

public class WorldLogger {
    private WorldLoggerListener listener;

    public WorldLogger(WorldLoggerListener listener) {
        this.listener = listener;
    }

    void logNewTurn(int turn) {
        listener.log("Zaczyna się tura: " + turn + "\n");
    }

    void logAction(ActionResult actionResult) {
        if (actionResult.getNewPosition().isPresent()) {
            Organism organism = actionResult.getOrganism();
            String log = "Pozycja: " + organism.getPosition() + ": ";
            if (actionResult.getFightResults().isPresent()) {
                log += reportFight(actionResult.getFightResults().get());
                listener.log(log + "\n");
            } else if (actionResult.hasReproductionOccurred()) {
                log += getPlural(organism.getName()) + " rozmnażają się! ";
                listener.log(log + "\n");
            }
        }
    }

    private String reportFight(FightResults fightResults) {
        String result = fightResults.getWinner().getName() + " wiek: " + fightResults.getWinner().getAge() + " ";
        result += fightResults.getLoserAfflictions().stream()
                              .map(this::mapAfflictionToString)
                              .collect(Collectors.joining(", "));
        result += " " + fightResults.getLoser().getName() + " wiek: " + fightResults.getLoser().getAge() + " ";
        return result;
    }

    private String mapAfflictionToString(Affliction affliction) {
        return switch (affliction) {
            case DEAD -> "zabija";
        };
    }

    private String getPlural(String name){
        return switch(name) {
            case "owca" -> "owce";
            case "wilk" -> "wilki";
            default -> "brak";
        };
    }
}
