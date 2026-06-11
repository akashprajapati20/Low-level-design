package models;

import enums.ObstacleTypes;

public class Ladder extends Obstacle{
    public Ladder(int top, int bottom) {
        super(bottom, top);
    }

    @Override
    public ObstacleTypes getObstacleType() {
        return ObstacleTypes.LADDER;
    }
}
