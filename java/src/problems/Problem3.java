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
            for(int j = 0; j < width; j++){
                
                char curChar = line.charAt(j);
                int[] indices = {startIndex, endIndex};
                if(endIndex >= 0 && (!isNumber(curChar))){
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
                    indices[0] = startIndex;
                    indices[1] = endIndex;
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
        int sum = 0;
        String line = input[lineNumber];
        String upperLine = lineNumber > 0 ? input[lineNumber - 1] : null;
        String lowerLine = lineNumber < input.length - 1 ? input[lineNumber + 1] : null;

        int startIndex;
        int endIndex;

        // check current row
        for(int i = 0; i < indices.size(); i++){
            
            startIndex = indices.get(i)[0];
            endIndex = indices.get(i)[1];
            
            if(startIndex > 0 && line.charAt(startIndex - 1) != '.'){
                sum+= numbers.get(i);
                continue;
            }

            if(endIndex < width - 1 && line.charAt(endIndex + 1) != '.'){
                sum+= numbers.get(i);
                continue;
            }

            if(upperLine != null){
                if(checkSubString(upperLine, startIndex, endIndex)) {
                    sum+= numbers.get(i);
                    continue;
                }
            }

            if(lowerLine != null){
                if(checkSubString(lowerLine, startIndex, endIndex)){
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
            curChar = line.charAt(startIndex - 1);
            if(!isNumber(curChar) && curChar != '.'){
                return true;
            }
        }

        if(endIndex < width - 1){
            curChar = line.charAt(endIndex + 1);
            if(!isNumber(curChar) && curChar != '.'){
                return true;
            }
        }

        for(int i = startIndex; i <= endIndex; i++){
            curChar = line.charAt(i);
            if(!isNumber(curChar) && curChar != '.'){
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

    private void partTwo(){
        int sum = 0;
        String curLine;
        for(int i = 0; i < input.length; i++){
            curLine = input[i];
            
            for(int j = 0; j < width; j++){
                char curChar = curLine.charAt(j);

                if(curChar == '*'){
                    sum += searchAroundGear(i,j);
                }
            }
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, sum);
    }

    private int searchAroundGear(int lineNumber, int gearIndex){
        int sum = 0;

        int num1 = -1, num2 = -1;

        int numberCount = 0;
        int[] numbers = new int[] {-1, -1, -1, -1, -1, -1};
        int index;
        
        char symbol;
        StringBuilder number = new StringBuilder();
        String numberToConvert;
        
        // Check left from the gear
        if(gearIndex > 0){
            index = gearIndex - 1; // one position to the left of the gear
            symbol = input[lineNumber].charAt(index);

            number = buildNumber(input[lineNumber], index, number, false);
            if(number.length() > 0){
                numberToConvert = number.toString();
                numbers[0] = Integer.parseInt(numberToConvert);;
                number.setLength(0);
                numberCount++;
            }
        }

        // Check right from the gear
        if(gearIndex < width - 1){
            index = gearIndex + 1; // one position to the right of the gear
            symbol = input[lineNumber].charAt(index);
            number = buildNumber(input[lineNumber], index, number, true);
            if(number.length() > 0){
                numberToConvert = number.toString();
                if(numberCount == 0){
                    numbers[0] = Integer.parseInt(numberToConvert);
                }else{
                    numbers[1] = Integer.parseInt(numberToConvert);;
                }
                numberCount++;
            }
        }

        
        // Check above and below the gear if possible
        
        int[] aboveNumbers = new int[] {-1, -1};
        if(lineNumber > 0){
            aboveNumbers = checkLine(lineNumber - 1, gearIndex);
            if(aboveNumbers[0] > 0){
                numberCount++;
                numbers[2] = aboveNumbers[0];
            }
            if(aboveNumbers[1] > 0){
                numberCount++;
                numbers[3] = aboveNumbers[1];
            }
        }

        int[] belowNumbers = new int[] {-1, -1};
        if(lineNumber < input.length - 1){
            belowNumbers = checkLine(lineNumber + 1, gearIndex);
            if(belowNumbers[0] > 0){
                numberCount++;
                numbers[4] = belowNumbers[0];
            }
            if(belowNumbers[1] > 0){
                numberCount++;
                numbers[5] = belowNumbers[1];
            }
        }

        if(numberCount != 2) return 0;

        for(int curNumber: numbers){
            if(curNumber >= 0){
                if(num1 < 0){
                    num1 = curNumber;
                }else{
                    num2 = curNumber;
                }
            }
        }

        if(num1 < 0 || num2 < 0){
            TerminalPrint.somethingIsWrong();
        } 

        return num1 * num2;
    }

    private int[] checkLine(int lineNumber, int gearIndex){
        String curLine = input[lineNumber];
        int[] localResult = new int[] {-1, -1};
        int startIndex = gearIndex - 1 > 0 ? gearIndex - 1 : gearIndex; // if not at the beginning of the line, go one position to the left
        int endIndex = gearIndex + 1 < width - 1 ? gearIndex + 1 : gearIndex;
        StringBuilder number = new StringBuilder();
        
        for(int i = startIndex; i <= endIndex; i++){
            if(isNumber(curLine.charAt(i))){
                int curIndex = i;
                if(curIndex == gearIndex - 1){
                    // duplicated while loops -> extract into separate methods
                    number = buildNumber(curLine, curIndex, number, false);
                }else{
                    if(number.length() > 0 && curIndex == gearIndex || number.length() == 0){
                        // Only one number is above/below the gear symbol
                        number = buildNumber(curLine, curIndex, number, true);
                        localResult[0] = Integer.parseInt(number.toString());
                        return localResult;
                    }else if(number.length() > 0){
                        // Found a second number above the gear symbol
                        localResult[0] = Integer.parseInt(number.toString());
                        number.setLength(0);
                        number = buildNumber(curLine, curIndex, number, true);
                        localResult[1] = Integer.parseInt(number.toString());
                        return localResult;
                    }
                }
            }else{
                continue;
            }
        }
        
        if(number.length() > 0){
            localResult[0] = Integer.parseInt(number.toString());
        }

        return localResult;
    }
    
    private StringBuilder buildNumber(String curLine, int curIndex, StringBuilder number, boolean dir){
        
        int increment = dir ? 1 : -1; // if left then decrement by 1
        char curSymbol = curLine.charAt(curIndex);
        while(bound(curIndex, dir) && isNumber(curSymbol)){
            number.append(curSymbol);
            curIndex += increment;
            if(bound(curIndex, dir)){
                curSymbol = curLine.charAt(curIndex);
            }else{
                break;
            }
        }

        if(!dir) number = number.reverse();

        return number;
    }

    private boolean bound(int curIndex, boolean dir){
        boolean result;
        if(!dir){
            result = curIndex >= 0;
        }else{
            result = curIndex <= width - 1;
        }
        return result;
    }
}
