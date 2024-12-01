package year_2024;

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
        Long num1, num2;
        Long[] list1 = new Long[input.length];
        Long[] list2 = new Long[input.length];
        int index = 0;
        for(String line: input){
            String[] row = line.split(" +");
            num1 = Long.parseLong(row[0].trim());
            num2 = Long.parseLong(row[1].trim());
            list1[index] = num1;
            list2[index] = num2;
            index++;
        }
        Arrays.sort(list1);
        Arrays.sort(list2);
        long sum = 0;
        for(int i = 0; i < input.length; i++){
            sum += Math.abs(list1[i] - list2[i]);
        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    private void partTwo(String[] input){
        long sum = 0;
        Long num1, num2;
        Long[] list1 = new Long[input.length];
        // Long[] list2 = new Long[input.length];
        Map<Long, Integer> map = new HashMap<>();
        int index = 0;
        for(String line: input){
            String[] row = line.split(" +");
            num1 = Long.parseLong(row[0].trim());
            num2 = Long.parseLong(row[1].trim());
            map.put(num2, map.getOrDefault(num2, 0) + 1);
            list1[index] = num1;
            index++;
        }
        for(int i = 0; i < input.length; i++){
            sum += list1[i] * (map.getOrDefault(list1[i], 0));
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, sum);
    }
}
