package year_2024;

import utils.TerminalPrint;
import java.util.*;
import root.RootProblem;

public class Problem7 extends RootProblem{
    
    private int problemNumber = 7;

    char[] math = {'+', '*'};

    char[] math2 = {'+', '*', '|'};

    
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
        for(String line: input){
            String[] eqParts = line.split(":");
            long curSum = Long.parseLong(eqParts[0]);
            String[] stringNumbers = eqParts[1].trim().split(" ");
            List<Long> numbers = new ArrayList<>();
            for(String stringNumber: stringNumbers){
                numbers.add(Long.parseLong(stringNumber));
            }

            int numOfOperators = numbers.size() - 1;
            char[] operators = new char[numOfOperators];
            boolean solved = assemble(curSum, 0, 0, numbers, operators);
            if(!solved){
                solved = assemble(curSum, 0, 1, numbers, operators);
            }
            
            if(solved)
                sum += curSum;
        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    private boolean assemble(long curSum, int operatorIndex, int mathIndex, List<Long> numbers, char[] operators){

        operators[operatorIndex] = math[mathIndex];

        if(operatorIndex == operators.length - 1){
            long sum = solve(numbers, operators);
            if(sum == curSum)
                return true;
            else 
                return false;
        }

        boolean solved = assemble(curSum, operatorIndex + 1, mathIndex, numbers, operators);

        if(!solved){
            solved = assemble(curSum, operatorIndex + 1, (mathIndex + 1) % 2, numbers, operators);
        }

        return solved;
    }

    private Long solve(List<Long> numbers, char[] operators){
        
        long sum = numbers.get(0);
        int operatorIndex = 0;
        for(int i = 1; i < numbers.size(); i++){
            if(operators[operatorIndex] == '+'){
                sum += numbers.get(i);
            }else{
                sum *= numbers.get(i);
            }
            operatorIndex++;
        }

        return sum;
    }

    private void partTwo(String[] input){
        long sum = 0l;
        for(String line: input){
            String[] eqParts = line.split(":");
            long curSum = Long.parseLong(eqParts[0]);
            String[] stringNumbers = eqParts[1].trim().split(" ");
            List<Long> numbers = new ArrayList<>();
            for(String stringNumber: stringNumbers){
                numbers.add(Long.parseLong(stringNumber));
            }

            int numOfOperators = numbers.size() - 1;
            char[] operators = new char[numOfOperators];
            
            boolean solved = false;

            for(int i = 0; i < 3; i++){
                solved = assemble2(curSum, 0, i, numbers, operators);
                if(solved)
                    break;
            }
            
            if(solved)
                sum += curSum;
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, sum);
    }

    private boolean assemble2(long curSum, int operatorIndex, int mathIndex, List<Long> numbers, char[] operators){

        operators[operatorIndex] = math2[mathIndex];

        if(operatorIndex == operators.length - 1){
            long sum = solve2(numbers, operators);
            if(sum == curSum)
                return true;
            else 
                return false;
        }

        boolean solved = false;

        for(int i = 0; i < 3; i++){
            solved = assemble2(curSum, operatorIndex + 1, i, numbers, operators);
            if(solved)
                return true;
        }

        return solved;
    }

    private Long solve2(List<Long> numbers, char[] operators){
        long sum = numbers.get(0);
        int operatorIndex = 0;
        for(int i = 1; i < numbers.size(); i++){
            if(operators[operatorIndex] == '+'){
                sum += numbers.get(i);
            }else if(operators[operatorIndex] == '*'){
                sum *= numbers.get(i);
            }else{
                String newNum = sum + "" + numbers.get(i);
                sum = Long.parseLong(newNum);
            }
            operatorIndex++;
        }

        return sum;
    }
}
