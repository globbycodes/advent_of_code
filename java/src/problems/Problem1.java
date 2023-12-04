package problems;

import utils.TerminalPrint;
import java.util.*;

public class Problem1 extends RootProblem{
    
    private int problemNumber = 1;
    String[] digits = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    String[] reversedDigits;
    Map<Integer, char[]> groupedByLengthKeys;

    
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
        int sum = 0;
        for(String line: input){
            char[] lineCharArray = line.toCharArray();
            char digit1 = '0';
            char digit2 = '0';
            for(int i = 0; i < lineCharArray.length; i++){
                char curChar = lineCharArray[i];
                if(curChar >= '1' && curChar <= '9'){
                    digit1 = curChar;
                    break;
                }
            }
            for(int i = lineCharArray.length - 1; i >= 0; i--){
                char curChar = lineCharArray[i];
                if(curChar >= '1' && curChar <= '9'){
                    digit2 = curChar;
                    break;
                }
            }

            if(digit1 != '0' && digit2 != '0'){
                String digit = "" + digit1 + digit2;
                sum+= Integer.parseInt(digit);
            }
        }
        System.out.println("RESULT OF PART ONE: " + sum);
    }

    private void partTwo(String[] input){
        
    }

    // private  slidingWindow()





}
