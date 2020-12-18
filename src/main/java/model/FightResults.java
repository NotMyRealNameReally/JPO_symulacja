package model;

import java.util.ArrayList;
import java.util.List;

class FightResults {
    private final Organism winner;
    private final Organism loser;
    private final List<Affliction> winnerAfflictions;
    private final List<Affliction> loserAfflictions;

    FightResults(Organism winner, Organism loser) {
        this.winner = winner;
        this.loser = loser;
        this.winnerAfflictions = new ArrayList<>();
        this.loserAfflictions = new ArrayList<>();
    }

    void addWinnerAffliction(Affliction affliction){
        winnerAfflictions.add(affliction);
    }

    void addLoserAffliction(Affliction affliction){
        loserAfflictions.add(affliction);
    }

    public Organism getWinner() {
        return winner;
    }

    public Organism getLoser() {
        return loser;
    }

    public List<Affliction> getWinnerAfflictions() {
        return winnerAfflictions;
    }

    public List<Affliction> getLoserAfflictions() {
        return loserAfflictions;
    }
}
