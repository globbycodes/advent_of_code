package problems;

import java.util.*;

import utils.TerminalPrint;

public class Problem3 extends RootProblem{
    
    private int problemNumber = 3;
    String[] input;
    int width;


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
        StringBuilder numberString = new StringBuilder();
        List<int[]> numberIndices = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        
        for(int i = 0; i < input.length; i++){

            numberIndices.clear();
            numbers.clear();
            
            String line = input[i];

            numberString.setLength(0);
            int startIndex = -1, endIndex = -1;
            // System.out.println("gggg"+line);
            for(int j = 0; j < width; j++){
                
                char curChar = line.charAt(j);
                int [] indices = {startIndex, endIndex};
                if(endIndex >= 0 && (!isNumber(curChar) || j == width - 1)){
                    // System.out.println("the char: " + curChar);
                    numberIndices.add(indices);
                    numbers.add(Integer.parseInt(numberString.toString()));
                    numberString.setLength(0);
                    startIndex = -1;
                    endIndex = -1;
                }

                if(isNumber(curChar)){
                    if(startIndex < 0){
                        startIndex = j;
                    }
                    endIndex = j;
                    numberString.append(curChar);
                    if(j == width - 1){
                        numberIndices.add(indices);
                        numbers.add(Integer.parseInt(numberString.toString()));
                    }
                }

            }

            sum+= countPartNumbers(numberIndices, numbers, i);

        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    private int countPartNumbers(List<int[]> indices, List<Integer> numbers, int lineNumber){
        System.out.println(numbers);
        int sum = 0;
        String line = input[lineNumber];
        String upperLine = lineNumber > 0 ? input[lineNumber - 1] : null;
        String lowerLine = lineNumber < input.length - 1 ? input[lineNumber + 1] : null;

        int startIndex;
        int endIndex;

        // check current row
        for(int i = 0; i < indices.size(); i++){
            
            System.out.println(numbers.get(i));
            startIndex = indices.get(i)[0];
            endIndex = indices.get(i)[1];
            
            if(startIndex > 0 && line.charAt(startIndex - 1) != '.'){
                System.out.println("left neighbor");
                sum+= numbers.get(i);
                continue;
            }

            if(endIndex < width - 1 && line.charAt(endIndex + 1) != '.'){
                System.out.println("right neighbor");
                sum+= numbers.get(i);
                continue;
            }

            if(upperLine != null){
                if(checkSubString(upperLine, startIndex, endIndex)) {
                    System.out.println("upperLine");
                    sum+= numbers.get(i);
                    continue;
                }
            }

            if(lowerLine != null){
                if(checkSubString(lowerLine, startIndex, endIndex)){
                    System.out.println("lowerLine");
                    sum+= numbers.get(i);
                    continue;
                }
            }

        }

        return sum;
    }

    private boolean checkSubString(String line, int startIndex, int endIndex){
        char curChar;
        if(startIndex > 0){
            // System.out.println(startIndex);
            curChar = line.charAt(startIndex - 1);
            if(!isNumber(curChar) && curChar != '.'){
                System.out.println("diag" + startIndex);
                return true;
            }
        }

        if(endIndex < width - 1){
            curChar = line.charAt(endIndex + 1);
            if(!isNumber(curChar) && curChar != '.'){
                System.out.println("diag" + endIndex);
                return true;
            }
        }

        for(int i = startIndex; i <= endIndex; i++){
            curChar = line.charAt(i);
            if(!isNumber(curChar) && curChar != '.'){
                System.out.println("vert" + endIndex);
                return true;
            }
        }
        return false;
    }

    private boolean isNumber(char curChar){
        if(curChar >= '0' && curChar <= '9'){
            return true;
        }
        return false;
    }

    private void partTwo(String[] input){

    }
}
