package factory;

import enums.ObstacleTypes;
import models.Ladder;
import models.Obstacle;
import models.Snake;

public class ObstacleFactory {
      public static Obstacle createObstacle(ObstacleTypes type, int up, int down){
          return switch (type){
              case SNAKE -> new Snake(up,down);
              case LADDER -> new Ladder(up,down);
              default -> throw new IllegalArgumentException("Invalid type");
          };

      }
}
