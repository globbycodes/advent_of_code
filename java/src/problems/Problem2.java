package problems;

import utils.TerminalPrint;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem2 extends RootProblem{
    
    private int problemNumber = 2;

    Map<String, Integer> cubeLimit;

    protected void solve(Integer partNumber){
        String[] input = readFileLineByLine(problemNumber, 0);
        if(input == null || input.length == 0){
            TerminalPrint.printWrongProblemInputMsg();
            return;
        }


        cubeLimit = new HashMap<>();
        cubeLimit.put("r", 12);
        cubeLimit.put("g", 13);
        cubeLimit.put("b", 14);

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
 
        // String[] cubeSets;
        
        int gameNumber = 1;
        int sum = 0;
        Integer digit;
        String color;

        String regEx = "(\\d+)\\s(r|g|b)"; // (\\d+) is a first group. Then (r|g|b) is a second group
        Pattern pattern = Pattern.compile(regEx);
        for(String line: input){
            // cubeSets = line.split(";\\s"); // redundant :)
            boolean isPossible = true;
            // for(String cubSet: cubeSets){
                Matcher matcher = pattern.matcher(line);
                while(matcher.find()){
                    digit = Integer.parseInt(matcher.group(1));
                    color = matcher.group(2);
                    if(digit > cubeLimit.get(color)){
                        isPossible = false;
                    }
                }
            // }
            if(isPossible){
                sum+= gameNumber;
            }
            gameNumber++;
        }

        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    private void partTwo(String[] input){
        
        int sum = 0;
        Integer digit;
        String color;

        String regEx = "(\\d+)\\s(r|g|b)"; // (\\d+) is a first group. Then (r|g|b) is a second group
        Pattern pattern = Pattern.compile(regEx);

        for(String line: input){
            
            cubeLimit.put("r", 1);
            cubeLimit.put("g", 1);
            cubeLimit.put("b", 1);


            Matcher matcher = pattern.matcher(line);
            
            while(matcher.find()){
                digit = Integer.parseInt(matcher.group(1));
                color = matcher.group(2);
                if(cubeLimit.get(color) < digit){
                    cubeLimit.put(color, digit);
                }
            }
            
            Integer product = cubeLimit.get("r") * cubeLimit.get("g") * cubeLimit.get("b");
            sum+= product;
        }

        TerminalPrint.printAnswerMsg(problemNumber, 2, sum);
    }

}
