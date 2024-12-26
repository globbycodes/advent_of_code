package year_2024;

import utils.TerminalPrint;
import java.util.*;
import root.RootProblem;

public class Problem8 extends RootProblem{
    
    private int problemNumber = 8;

    
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
        long sum = 0l;
        Map<Character, List<int[]>> antennas = new HashMap<>();
        Set<String> antinodes = new HashSet<>();
        int index = 0;
        int height = input.length;
        int width = input[0].length();
        for(String line: input){
            for(int i = 0; i < line.length(); i++){
                char curChar = line.charAt(i);
                if(Character.isLetterOrDigit(curChar)){
                    List<int[]> coords = antennas.getOrDefault(curChar, new ArrayList<>());
                    coords.add(new int[] {index, i});
                    antennas.putIfAbsent(curChar, coords);
                }
            }
            index++;
        }

        for(Character key: antennas.keySet()){
            List<int[]> coords = antennas.get(key);
            
            for(int i = 0; i < coords.size() - 1; i++){
                int[] curCoord = coords.get(i);
                for(int j = i + 1; j < coords.size(); j++){
                    
                    int[] otherCoord = coords.get(j);

                    int[] dist = new int[2];
                    
                    dist[0] = Math.abs(curCoord[0] - otherCoord[0]);
                    dist[1] = Math.abs(curCoord[1] - otherCoord[1]);
                    
                    boolean left = curCoord[1] < otherCoord[1];
                    
                    int row = curCoord[0] - dist[0];
                    int col = left ? curCoord[1] - dist[1] : curCoord[1] + dist[1];

                    if(row >= 0 && col >= 0 && row < height && col < width){
                        antinodes.add(row + "|" + col);
                    }

                    row = otherCoord[0] + dist[0];
                    col = left ? otherCoord[1] + dist[1] : otherCoord[1] - dist[1];

                    if(row < height && col < width && row >= 0 && col >= 0){
                        antinodes.add(row + "|" + col);
                    }
                }
            }
        }

        TerminalPrint.printAnswerMsg(problemNumber, 1, antinodes.size());
    }


    private void partTwo(String[] input){
        long sum = 0l;
        Map<Character, List<int[]>> antennas = new HashMap<>();
        Set<String> antinodes = new HashSet<>();
        int index = 0;
        int height = input.length;
        int width = input[0].length();
        for(String line: input){
            for(int i = 0; i < line.length(); i++){
                char curChar = line.charAt(i);
                if(Character.isLetterOrDigit(curChar)){
                    List<int[]> coords = antennas.getOrDefault(curChar, new ArrayList<>());
                    coords.add(new int[] {index, i});
                    antennas.putIfAbsent(curChar, coords);
                }
            }
            index++;
        }
        int x = 0;
        for(Character key: antennas.keySet()){
            List<int[]> coords = antennas.get(key);
            
            for(int i = 0; i < coords.size() - 1; i++){
                int[] curCoord = coords.get(i);
                for(int j = i + 1; j < coords.size(); j++){
                    
                    int[] otherCoord = coords.get(j);

                    int[] dist = new int[2];
                    
                    dist[0] = Math.abs(curCoord[0] - otherCoord[0]);
                    dist[1] = Math.abs(curCoord[1] - otherCoord[1]);
                    
                    int[] firstCounter = new int[2];
                    int[] secondCounter = new int[2];
                    firstCounter[0] = curCoord[0];
                    firstCounter[1] = curCoord[1];

                    secondCounter[0] = otherCoord[0];
                    secondCounter[1] = otherCoord[1];
                    boolean left = curCoord[1] < otherCoord[1];
                    int row, col;
                    boolean first = false;
                    boolean second = false;

                    antinodes.add(curCoord[0] + "|" + curCoord[1]);
                    antinodes.add(otherCoord[0] + "|" + otherCoord[1]);
                    while(true){
                        if(first && second)
                            break;

                        left = firstCounter[1] < secondCounter[1];
                        
                        row = firstCounter[0] - dist[0];
                        col = left ? firstCounter[1] - dist[1] : firstCounter[1] + dist[1];
                        if(row >= 0 && col >= 0 && row < height && col < width){
                            antinodes.add(row + "|" + col);
                            firstCounter[0] = row;
                            firstCounter[1] = col;
                        }else{
                            first = true;
                        }
    
                        row = secondCounter[0] + dist[0];
                        col = left ? secondCounter[1] + dist[1] : secondCounter[1] - dist[1];
    
                        if(row < height && col < width && row >= 0 && col >= 0){
                            antinodes.add(row + "|" + col);
                            secondCounter[0] = row;
                            secondCounter[1] = col;
                        }else{
                            second = true;
                        }
                    }
                }
            }
            
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, antinodes.size() + x);
    }
}
