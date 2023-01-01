package alex.ai01labs.maze;

public class MazeEscapeBacktrack {

    public static void main(String[] args) {
        int[][] map = {
                {1, 1, 1, 1, 1, 1},
                {2, 1, 0, 0, 0, 1},
                {0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 0},
                {0, 1, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0}
        };

        int[][] map2 = {
                {1, 1, 1, 1, 1, 1},
                {2, 1, 1, 1, 0, 1},
                {0, 0, 0, 1, 0, 1},
                {0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 0}
        };
        //solution exists
        new MazeSolver(map,1,0).findWay();

        //solution NOT exists
        new MazeSolver(map2,1,0).findWay();
    }
}
