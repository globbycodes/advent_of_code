package problems;

import utils.TerminalPrint;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem4 extends RootProblem{
    
    private int problemNumber = 4;

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
        
    }

    private void partTwo(String[] input){
        
    }

}
