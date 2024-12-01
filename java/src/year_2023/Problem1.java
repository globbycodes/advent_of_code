package year_2023;

import utils.TerminalPrint;
import java.util.*;
import root.RootProblem;

public class Problem1 extends RootProblem{
    
    private int problemNumber = 1;
    Map<String, Character> digitsMap;
    String[] digits = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    int[] wordSizes = {3, 4, 5};

    
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
        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    private void partTwo(String[] input){
        setUpData();
        int sum = 0;
        for(String curLine: input){
            
            char digit1 = '0';
            char digit2 = '0';
            String subString;

            for(int i = 0; i < curLine.length(); i++){
                char curChar = curLine.charAt(i);
                if(curChar >= '1' && curChar <= '9'){
                    digit1 = curChar;
                    break; // found first digit
                }
                boolean foundDigit = false;
                for(int wordSize: wordSizes){
                    if(i + wordSize >= curLine.length()){
                        break;
                    }else{
                        subString = curLine.substring(i, i + wordSize);
                        if(digitsMap.containsKey(subString)){
                            digit1 = digitsMap.get(subString);
                            foundDigit = true;
                            break;
                        }
                    }
                }
                if(foundDigit) break;
            }

            if(digit1 == '0'){
                System.out.println("\nNo digit 1 was found, something is off...");
                return;
            }

            for(int i = curLine.length() - 1; i >= 0; i--){
                
                char curChar = curLine.charAt(i);
                
                if(curChar >= '1' && curChar <= '9'){
                    digit2 = curChar;
                    break; // found last digit
                }
                boolean foundDigit = false;
                for(int wordSize: wordSizes){
                    if(i - wordSize < -1){
                        break;
                    }else{
                        subString = curLine.substring(i - wordSize + 1, i + 1);
                        if(digitsMap.containsKey(subString)){
                            digit2 = digitsMap.get(subString);
                            foundDigit = true;
                            break;
                        }
                    }
                }
                if(foundDigit) break;
            }
            if(digit2 == '0'){
                System.out.println("\nNo digit 2 was found, something is off...");
                return;
            }

            String number = "" + digit1 + digit2;

            sum+= Integer.parseInt(number);
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, sum);
    }

    private void setUpData(){
        this.digitsMap = new HashMap<>();
        for(int i = 0; i < digits.length; i++){
            int value = i + 1;
            Character digitValue = (char)(value + '0');
            digitsMap.put(digits[i], digitValue);
        }
    }





}
