package year_2024;

import utils.TerminalPrint;
import java.util.*;

import root.RootProblem;

public class Problem6 extends RootProblem{
    
    private int problemNumber = 6;
    
    int dir = 0;
    int dim = 0;
    int way = 0;

    int[][] rows;
    int[][] cols;


    String[] grid;
    int len;
    boolean firstTurn = false;
    int counter = 0;


    List<Coord> guardPositions;


    
    protected void solve(Integer partNumber){
        String[] input = readFileLineByLine(problemNumber, 0);

        if(input == null || input.length == 0){
            TerminalPrint.printWrongProblemInputMsg();
            return;
        }

        switch (partNumber) {
            case 1:
                partOne(input);
                break;
            case 2:
                partTwo(input);
                break;
            default:
                return;
        }
    }

    private void partOne(String[] input){
        Set<String> visited = new HashSet<>();
        int[] curPosition = new int[2];
        int gridSize = input.length;
        
        // get starting position
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                if(input[i].charAt(j) == '^'){
                    curPosition[0] = i;
                    curPosition[1] = j;
                }
            }
        }


        visited.add(Arrays.toString(curPosition));

        // guard traversing
        int[] nextPos = curPosition;
        boolean foundEdge = false;
        boolean walking = true;

        int i, j;
        i = nextPos[0];
        j = nextPos[1];
        while(!foundEdge){
            dim = dir % 2 == 0 ? 0 : 1;
            way = (dir == 0 || dir == 3) ? -1 : 1;
            walking = true;
            while(walking){
                if(dim == 0){
                    i = i + way;
                }else{
                    j = j + way;
                }

                nextPos = new int[2];
                
                nextPos[0] = i;
                nextPos[1] = j;

                // System.out.println(i + " : " + j); 
                if(input[i].charAt(j) == '#'){
                    nextPos[dim] -= way;
                    i = nextPos[0];
                    j = nextPos[1];
                    walking = false;
                    turn1();
                }else if(nextPos[dim] == input.length - 1 || nextPos[dim] == 0){
                    foundEdge = true;
                    walking = false;
                }
                visited.add(Arrays.toString(nextPos));
            }
        }
        int steps = visited.size();
        TerminalPrint.printAnswerMsg(problemNumber, 1, steps);
    }

    private void turn1(){
        dir = (dir + 1) % 4;
    }


    private void turn(){
        dir = (dir + 1) % 4;
        dim = dir % 2 == 0 ? 0 : 1;
        way = (dir == 0 || dir == 3) ? -1 : 1;
    }

    private void partTwo(String[] input){
        dir = 0;
        dim = 0;
        way = -1;
        grid = input;
        len = grid.length;

        int[] startPosition = new int[2];
        
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                if(input[i].charAt(j) == '^'){
                    startPosition[0] = i;
                    startPosition[1] = j;
                }
            }
        }
        
        int[] curPosition = new int[2];
        curPosition[0] = startPosition[0];
        curPosition[1] = startPosition[1];
        dir = 0;
        dim = 0;
        way = -1;
        curPosition[0] = startPosition[0];
        curPosition[1] = startPosition[1];
        
        guardPositions = new ArrayList<>();

        loop(curPosition, null, true);
        Set<Path> guardPath = new HashSet<>();
        
        int loops = 0;
        Set<Coord> seenCoords = new HashSet<>();
        for(Coord curCoord: guardPositions){
            if(seenCoords.contains(curCoord))
                continue;
            else
                seenCoords.add(curCoord);
            if(curCoord.x == startPosition[0] && curCoord.y == startPosition[1])
                continue;
            StringBuilder tempLine = new StringBuilder(grid[curCoord.x]);
            String originalLine = grid[curCoord.x];
            tempLine.setCharAt(curCoord.y, '#');
            grid[curCoord.x] = tempLine.toString();
            
            curPosition[0] = startPosition[0];
            curPosition[1] = startPosition[1];
            
            guardPath = new HashSet<>();
            dir = 0;
            dim = 0;
            way = -1;
            boolean isLoop = loop(curPosition, guardPath, false);
            grid[curCoord.x] = originalLine; 
            
            if(isLoop)
                loops++;
        }

        TerminalPrint.printAnswerMsg(problemNumber, 2, loops);
    }

    /**
     * return true if guard ends up looping, otherwise return false
     */
    private boolean loop(int[] position, Set<Path> guardPath, boolean firstPass){
        
        boolean done = false;
        while(!done){
            int[] nextPosition = new int[2];
            if(!firstPass){
                Path curPath = new Path(position[0], position[1], dir);
                if(guardPath.contains(curPath))
                    return true;
            }
            
            if(firstPass){
                Coord curCoord = new Coord(position[0], position[1]);
                guardPositions.add(curCoord);
            }else{
                Path curPath = new Path(position[0], position[1], dir);
                guardPath.add(curPath);
            }

            if(position[dim] + way >= len || position[dim] + way < 0)
                break;
            
            nextPosition[dim] = position[dim] + way;
            nextPosition[(dim + 1) % 2] = position[(dim + 1) % 2];
            int x = nextPosition[0];
            int y = nextPosition[1];

            if(grid[x].charAt(y) == '#'){
                turn();
            }else{
                position = nextPosition;
            }
        }
        return false;
    }


    private class Coord{
        int x;
        int y;

        private Coord(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ":" + y;
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }


        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
        
            if (obj == null || getClass() != obj.getClass()) return false;

            Coord that = (Coord) obj;
            return x == that.x && y == that.y;
        }
    }

    private class Path{
        int x;
        int y;
        int pathDir;

        private Path(int x, int y, int pathDir){
            this.x = x;
            this.y = y;
            this.pathDir = pathDir;
        }

        @Override
        public String toString() {
            return x + ":" + y + "=" + pathDir;
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            
            if (obj == null || getClass() != obj.getClass()) return false;

            Path that = (Path) obj;
            return x == that.x && y == that.y && pathDir == that.pathDir;
        }
    }
}
