package year_2023;

import utils.TerminalPrint;
import java.util.Map;
import java.util.HashMap;
import root.RootProblem;

public class Problem4 extends RootProblem{
    
    private int problemNumber = 4;

    String[] input;

    int indexOfColumn;
    int indexOfBar;
    int width;
    int partTwoSum = 0;

    protected void solve(Integer partNumber){
        String[] input = readFileLineByLine(problemNumber, 0);
        this.input = input;
        this.width = input[0].length();
        char[] firstLine = input[0].toCharArray();
        int index = 0;
        for(char curChar: firstLine){
            if(curChar == ':'){
                this.indexOfColumn = index;
            }

            if(curChar == '|'){
                this.indexOfBar = index;
                break;
            }

            index++;
        }
        


        if(input == null || input.length == 0){
            TerminalPrint.printWrongProblemInputMsg();
            return;
        }

        switch (partNumber) {
            case 1:
                partOne();
                break;
            case 2:
                partTwo();
                break;
            default:
                return;
        }
    }

    private void partOne(){         
        int[] map = new int[101]; // by quickly looking at the input, the range of numbers is from 0 to 99. Instead of hashmap I can just use an int array
        int sum = 0;
        int curScore = 0;

        for(String line: input){
            resetMap(map);
            
            String[] winNumbers = line.substring(indexOfColumn + 1, indexOfBar).trim().split("\\s+");
            
            populateMap(map, winNumbers);

            String[] playerNumbers = line.substring(indexOfBar + 1, width).trim().split("\\s+");
            
            curScore = 0;
            for(String playerNumberAsString: playerNumbers){
                int playerNumber = Integer.parseInt(playerNumberAsString);
                if(map[playerNumber] > 0){
                    if(curScore == 0){
                        curScore = 1;
                    }else{
                        curScore = curScore << 1; // multiply by 2
                    }
                }
            }
            sum += curScore;
        };

        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    // Instead of creating new int array, just clear up the old one and reuse (saving on memory?)
    private void resetMap(int[] map){
        for(int i = 0; i < map.length; i++){
            map[i] = 0;
        }
    }

    private void populateMap(int[] map, String[] winNumbers){
        for(String winNumber: winNumbers){
            int number = Integer.parseInt(winNumber);
            map[number] = 1;
        }
    }

    private void partTwo(){
        Map <Integer, Integer> memoization = new HashMap<>();
        for(int i = 0; i < input.length; i++){
            partTwoSum++;
            recursiveFunction(i, memoization);
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, partTwoSum);
    }

    private void recursiveFunction(int lineNumber, Map<Integer, Integer> memoization){
        
        if(memoization.get(lineNumber) != null){
            partTwoSum += memoization.get(lineNumber);
            return;
        }
        
        if(lineNumber >= input.length){
            System.out.println("end of input"); 
            return;
        }

        int[] map = new int[101];
        String line = input[lineNumber];
        
        String[] winNumbers = line.substring(indexOfColumn + 1, indexOfBar).trim().split("\\s+");
        
        populateMap(map, winNumbers);

        String[] playerNumbers = line.substring(indexOfBar + 1, width).trim().split("\\s+");
        
        int curScore = 0;
        for(String playerNumberAsString: playerNumbers){
            int playerNumber = Integer.parseInt(playerNumberAsString);
            if(map[playerNumber] > 0){
                curScore++;
            }
        }

        if(curScore == 0)
            return;
        
        
        int oldPartTwoSum = partTwoSum;
        partTwoSum += curScore;

        for(int i = 1; i < curScore + 1; i++){
            recursiveFunction(lineNumber + i, memoization);
        }

        int curCardScore = partTwoSum - oldPartTwoSum;

        if(memoization.get(lineNumber) == null){
            memoization.put(lineNumber, curCardScore);
        }

    }

}
