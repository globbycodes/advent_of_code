package year_2024;

import utils.TerminalPrint;

import java.util.Arrays;

import root.RootProblem;

public class Problem4 extends RootProblem{
    
    private int problemNumber = 4;

    private String[] matrix;
    private char[] xmas = {'M', 'A', 'S'};
    private char[] samx = {'A', 'M', 'X'};

    private int[] down = {1, 0};
    private int[] right = {0, 1};
    private int[] diagUp = {-1, 1};
    private int[] diagDown = {1, 1};

    private int[] diagUpRight = {-1, 1};
    private int[] diagUpLeft = {-1, -1};
    private int[] diagDownRight = {1, 1};
    private int[] diagDownLeft = {1, -1};

    private int[][] dirs = {down, right, diagUp, diagDown};
    private int[][] dirs2 = {diagUpRight, diagDownLeft, diagDownRight, diagUpLeft};

    
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
        Long sum = 0l;

        matrix = input;

        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input.length; j++){
                char curChar = matrix[i].charAt(j);
                if(curChar == 'X' || curChar == 'S'){
                    sum += findMatch(i, j, curChar);
                }
            }
        }

        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    private int findMatch(int i, int j, char curChar){
        char[] matchingLetters = curChar == 'X' ? xmas : samx;
        int matches = 0;
        
        for(int[] dir: dirs){
            // check if within boundaries
            int newI = i + dir[0] * 3;
            int newJ = j + dir[1] * 3;

            if(newI < 0 || newI >= matrix.length || newJ >= matrix.length){
                continue;
            }
            boolean foundMatch = true;
            
            for(int index = 0; index < 3; index++){
                newI = i + (dir[0] * (index + 1)); 
                newJ = j + (dir[1] * (index + 1));
                if(matrix[newI].charAt(newJ) != matchingLetters[index]){
                    foundMatch = false;
                }
            }
            if(foundMatch)
                matches++;

        }
        
        return matches;
    }

    private void partTwo(String[] input){
        Long sum = 0l;
        matrix = input;

        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input.length; j++){
                char curChar = matrix[i].charAt(j);
                if(curChar == 'A'){
                    if(findxmas(i, j)){
                        sum += 1l;
                    }
                }
            }
        }

        TerminalPrint.printAnswerMsg(problemNumber, 2, sum);
    }

    private boolean findxmas(int i, int j){
        int s_count = 0;
        int m_count = 0;
        
        char[] firstDiag = new char[2];
        char[] secondDiag = new char[2];

        // private int[][] dirs2 = {diagUpRight, diagDownLeft, diagDownRight, diagUpLeft};
        int index = 0;
        for(int[] dir: dirs2){
            int newI = i + dir[0];
            int newJ = j + dir[1];

            if(newI < 0 || newI >= matrix.length ||  newJ < 0 || newJ >= matrix.length){
                continue;
            }

            if(matrix[newI].charAt(newJ) == 'M'){
                m_count++;
            }else if(matrix[newI].charAt(newJ) == 'S'){
                s_count++;
            }

            if(index < 2){
                firstDiag[index % 2] = matrix[newI].charAt(newJ);
            }else{
                secondDiag[index % 2] = matrix[newI].charAt(newJ);
            }
            index++;
        }
        if(firstDiag[0] == firstDiag[1] || secondDiag[0] == secondDiag[1])
            return false;
        return (m_count == 2 && s_count == 2) ? true : false;
    }
}
