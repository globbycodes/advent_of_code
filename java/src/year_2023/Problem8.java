package year_2023;

import utils.TerminalPrint;
import java.util.*;
import root.RootProblem;

public class Problem8 extends RootProblem{
    int problemNumber = 8;
    int width;
    String[] input;
    Map<String, String[]> dirMap = new HashMap<>();
    char[] dirArray;
    boolean setUp = false;

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
        setUpP1();

        String currentPoint = "AAA";
        int index = 0;
        char curChar;
        int counter = 0;
        while(!currentPoint.equals("ZZZ")){
            curChar = dirArray[index];
            index = (index + 1) % dirArray.length;
            if(curChar == 'L')
                currentPoint = dirMap.get(currentPoint)[0];
            else
                currentPoint = dirMap.get(currentPoint)[1];
            counter++;
        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, counter);
    }

    private void setUpP1(){
        if(!setUp)
            setUp = true;
        else
            return;
        dirArray = input[0].toCharArray();
        String line;
        for(int i = 2; i < input.length; i++){
            line = input[i];
            String source = line.substring(0, 3);
            String[] leftRight = line.substring(7, 15).trim().split(", ");
            dirMap.put(source, leftRight);
        }
    }

    private void partTwo(){
        setUpP1();
        List<String> entrances = new ArrayList<>();
        for(String entrance: dirMap.keySet()){
            if(entrance.charAt(2) == 'A')
                entrances.add(entrance);
        }
        Long[] numbers = new Long[entrances.size()];
        int i = 0;
        for(String entrance: entrances){
            int index = 0;
            int dir = dirArray[index] == 'L' ? 0 : 1;
            String nextDir = entrance;
            Long counter = 0L;
            boolean looped = false;
            while(true){
                if(looped)
                    counter++;
                dir = dirArray[index] == 'L' ? 0 : 1;
                index = (index + 1) % dirArray.length;
                nextDir = dirMap.get(nextDir)[dir];
                if(nextDir.charAt(2) == 'Z'){
                    if(looped)
                        break;
                    else
                        looped = true;
                }
            }
            numbers[i++] = counter;
        }
        Long lcm = numbers[0];
        for(int j = 1; j < numbers.length; j++){
            lcm = findLCM(lcm, numbers[j]);
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, lcm);
    }


    private Long findLCM(Long a, Long b){
        return (a * b) / gcd(a, b);
    }
    
    // find greatest common denominator using Euclidean algorithm
    private Long gcd(Long a, Long b) {
        if(b.equals(0L)){
            return a;
        }
        return gcd(b, a % b);
    }
}
