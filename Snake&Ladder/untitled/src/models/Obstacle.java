package models;


import enums.ObstacleTypes;

public abstract class Obstacle {
    private int src;
    private int dest;

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public Obstacle(int src, int dest){
        this.src=src;
        this.dest=dest;
    }
    public abstract ObstacleTypes getObstacleType();
    public int movePlayer(){return dest;};
}
