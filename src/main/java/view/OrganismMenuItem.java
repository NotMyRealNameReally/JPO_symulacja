package view;

import model.OrganismType;

import javax.swing.*;

public class OrganismMenuItem extends JMenuItem {
    private final OrganismType organismType;

    public OrganismMenuItem(OrganismType organismType){
        super(switch(organismType){
            case SHEEP -> "owca";
            case WOLF -> "wilk";
            case FOX -> "lis";
            case SLOTH -> "leniwiec";
            case GRASS -> "trawa";
            case GUARANA -> "guarana";
            case DANDELION -> "mlecz";
            case FLAT_EARTHER -> "płaskoziemca";
        });
        this.organismType = organismType;
    }

    public OrganismType getOrganismType() {
        return organismType;
    }
}
