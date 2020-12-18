package model;

import dto.WorldDto;

public interface WorldListener {
    void worldCreated(WorldDto worldDto);
    void worldChanged();
}
