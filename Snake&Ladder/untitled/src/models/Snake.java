package models;

import enums.ObstacleTypes;

public class Snake extends Obstacle{
    public Snake(int src, int dest) {
        super(src, dest);
    }

    @Override
    public ObstacleTypes getObstacleType() {
        return ObstacleTypes.SNAKE;
    }
}
