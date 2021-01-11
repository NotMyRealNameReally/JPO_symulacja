package model;

import java.util.stream.Collectors;

public class WorldLogger {
    private WorldLoggerListener listener;

    public WorldLogger(WorldLoggerListener listener) {
        this.listener = listener;
    }

    void logNewTurn(int turn) {
        listener.log(new LogMessage("Zaczyna się tura " + turn, LogLevel.ESSENTIAL));
    }

    void logAction(ActionResult actionResult) {
        Organism organism = actionResult.getOrganism();
        String log = "Pozycja: " + organism.getPosition() + ": ";
        boolean meaningfulAction = false;

        if (actionResult.getFightResults().isPresent()) {
            log += reportFight(actionResult.getFightResults().get());
            meaningfulAction = true;
        } else if (actionResult.hasReproductionOccurred()) {
            log += getPlural(organism.getName()) + " rozmnażają się! ";
            meaningfulAction = true;
        }
        if(meaningfulAction){
            listener.log(new LogMessage(log, LogLevel.NORMAL));
        }
    }

    private String reportFight(FightResults fightResults) {
        String result = fightResults.getWinner().getName() + " wiek: " + fightResults.getWinner().getAge() + " ";
        result += fightResults.getLoserAfflictions().stream()
                              .map(this::afflictionToString)
                              .collect(Collectors.joining(", "));
        result += " " + fightResults.getLoser().getName() + " wiek: " + fightResults.getLoser().getAge() + " ";
        return result;
    }

    private String afflictionToString(Affliction affliction) {
        return switch (affliction) {
            case DEAD -> "zabija";
        };
    }

    private String getPlural(String name) {
        return switch (name) {
            case "owca" -> "owce";
            case "wilk" -> "wilki";
            default -> "brak";
        };
    }
}
