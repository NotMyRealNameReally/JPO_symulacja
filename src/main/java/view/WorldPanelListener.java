package view;

import model.OrganismType;
import model.Position;

public interface WorldPanelListener {
    void addOrganism(Position position, OrganismType type);
}
