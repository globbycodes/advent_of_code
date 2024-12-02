package year_2024;

import utils.TerminalPrint;
import java.util.*;
import root.RootProblem;

public class Problem2 extends RootProblem{
    
    private int problemNumber = 2;

    
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
        int validReports = 0;
        for(String line: input){

            String[] row = line.split(" ");
            int[] report = new int[row.length];
            for(int i = 0; i < row.length; i++){
                report[i] = Integer.parseInt(row[i]);
            }

            boolean asc = true;
            if(report[1] < report[0])
                asc = false;
            
            boolean isValid = true;
            for(int i = 1; i < row.length; i++){
                if(asc){
                    if(report[i] <= report[i - 1]){
                        isValid = false;
                        break;
                    }
                }else{
                    if(report[i] >= report[i - 1]){
                        isValid = false;
                        break;
                    }
                }

                if(Math.abs(report[i] - report[i - 1]) > 3){
                    isValid = false;
                    break;
                }
            }
            if(isValid)
                validReports++;

        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, validReports);
    }

    private void partTwo(String[] input){
        int validReports = 0;
        for(String line: input){
            String[] row = line.split(" ");
            int[] report = new int[row.length];
            for(int i = 0; i < row.length; i++){
                report[i] = Integer.parseInt(row[i]);
            }
            boolean any = false;
            
            int[] copy = new int[row.length - 1];

            for(int i = 0; i < row.length; i++){
                int index = 0;
                for(int j = 0; j < row.length; j++){
                    if(j == i){
                        continue;
                    }
                    copy[index] = report[j];
                    index++;
                }
                if(safe(copy, row.length - 1)){
                    any = true;
                }
            }

            if(any)
                validReports++;
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, validReports);
    }

    private boolean safe(int[] report, int length){
        boolean asc = true;
        if(report[1] < report[0])
            asc = false;
        
        boolean isValid = true;
        for(int i = 1; i < length; i++){
            if(asc){
                if(report[i] <= report[i - 1]){
                    isValid = false;
                    break;
                }
            }else{
                if(report[i] >= report[i - 1]){
                    isValid = false;
                    break;
                }
            }

            if(Math.abs(report[i] - report[i - 1]) > 3){
                isValid = false;
                break;
            }
        }
        return isValid;  
    }
}
