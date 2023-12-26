package problems;

import utils.TerminalPrint;
import java.util.*;

public class Problem8 extends RootProblem{
    int problemNumber = 8;
    int width;
    String[] input;
    Map<String, String[]> dirMap = new HashMap<>();
    char[] dirArray;

    protected void solve(Integer partNumber){
        String[] input = readFileLineByLine(problemNumber, 0);
        this.input = input;
        this.width = input[0].length();

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

    }

    private void setUpP1(){
        dirArray = input[0].toCharArray();
        String line;
        for(int i = 2; i < input.length; i++){
            line = input[i];
            String[] seedNumbersAsStrings = line.substring(6, line.length()).trim().split("\\s+");

        }
    }

    private void partTwo(){

    }
}
