package alex.ai01labs.maze;

/**
 * 0 - way
 * 1 - wall
 * target - find way from start cell to bottom left corner
 */
public class MazeSolver {
    private final int startCol;
    private final int startRow;
    private final int[][] maze;
    private final boolean[][] visited;

    public MazeSolver(int[][] maze, int startRow, int startColumn) {
        this.maze = maze;
        visited = new boolean[maze.length][maze.length];
        this.startCol = startColumn;
        this.startRow = startRow;
    }

    public void findWay() {
        if (dfs(startRow, startCol)) {
            System.out.println("\nSolution Exists...\n");
        } else {
            System.out.println("\nSolution NOT Exists...\n");
        }

    }

    private boolean isFeasible(int x, int y) {
        if (x < 0 || x > maze.length - 1) {
            return false;
        }
        if (y < 0 || y > maze.length - 1) {
            return false;
        }
        if (visited[x][y]) {
            return false;
        }
        return maze[x][y] != 1;
    }

    private boolean dfs(int x, int y) {
        if (x == maze.length - 1 && y == maze.length - 1) {
            return true;
        }
        if(isFeasible(x,y)){
            System.out.printf("{%d:%d} = %d,\t",x,y, maze[x][y]);
            visited[x][y]=true;
            //step-in in the all 4 possible directions
            if(dfs(x+1,y)){
                return true;
            }
            if(dfs(x-1,y)){
                return true;
            }
            if(dfs(x,y+1)){
                return true;
            }
            if(dfs(x,y-1)){
                return true;
            }
            //BACKTRACK
            visited[x][y]=false;
        }
        return false;
    }
}
