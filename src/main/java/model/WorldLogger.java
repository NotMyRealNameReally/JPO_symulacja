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
        String log = "Pozycja " + organism.getPosition() + ": ";
        boolean meaningfulAction = false;

        if (actionResult.getFightResults().isPresent()) {
            log += reportFight(actionResult.getFightResults().get());
            meaningfulAction = true;
        } else if (actionResult.hasReproductionOccurred()) {
            log += getNamePlural(organism) + " rozmnażają się! ";
            meaningfulAction = true;
        }
        if(meaningfulAction){
            listener.log(new LogMessage(log, LogLevel.NORMAL));
        }
    }

    private String reportFight(FightResults fightResults) {
        Organism winner = fightResults.getWinner();
        Organism loser = fightResults.getLoser();
        String result = getNameSingular(winner) + " wiek: " + winner.getAge() + " ";
        result += fightResults.getLoserAfflictions().stream()
                              .map(this::afflictionToString)
                              .collect(Collectors.joining(", "));
        result += " " + getNameSingular(loser) + " wiek: " + loser.getAge() + " ";
        return result;
    }

    private String afflictionToString(Affliction affliction) {
        return switch (affliction) {
            case DEAD -> "zabija";
            case EVADED -> "unika";
        };
    }

    private String getNamePlural(Organism organism) {
        return switch (organism.getName()) {
            case SHEEP -> "owce";
            case WOLF -> "wilki";
            case FOX -> "lisy";
            case SLOTH -> "leniwce";
            case FLAT_EARTHER -> "płaskoziemcy";
        };
    }

    private String getNameSingular(Organism organism) {
        return switch (organism.getName()) {
            case SHEEP -> "owca";
            case WOLF -> "wilk";
            case FOX -> "lis";
            case SLOTH -> "leniwiec";
            case FLAT_EARTHER -> "płaskoziemca";
        };
    }
}
