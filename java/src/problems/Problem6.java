package problems;

import java.util.*;
import utils.TerminalPrint;

public class Problem6 extends RootProblem{
    int problemNumber = 6;
    int width;
    String[] input;
    int[] times;
    int[] dists;
    Long time;
    Long dist;

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
        setUp(1);
        int sum = 1;
        for(int i = 0; i < times.length; i++){
            int curTime = times[i];
            int curDest = dists[i];
            int leftBound = -1;
            int rightBound = -1;
            // should be binary search, but too lazy....
            for(int j = 0; j < curTime; j++){
                int rem = curTime - j;
                int dist = j * rem;
                if(dist > curDest){
                    leftBound = j;
                    break;
                }
            }

            for(int j = curTime - 2; j >= 0; j--){
                int rem = curTime - j;
                int dist = j * rem;
                if(dist > curDest){
                    rightBound = j;
                    break;
                }
            }

            if(leftBound < 0 || rightBound < 0){
                TerminalPrint.somethingIsWrong();
                return;
            }

            sum *= (rightBound - leftBound) + 1;
        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    private void setUp(int part){
        String timeLine = input[0];
        String[] timesAsStrings = timeLine.substring(timeLine.indexOf(':') + 1, timeLine.length()).trim().split("\\s+");
        int[] times = new int[timesAsStrings.length];
        for(int i = 0; i < timesAsStrings.length; i++){
            times[i] = Integer.parseInt(timesAsStrings[i]);
        }

        String destLine = input[1];
        String[] distsAsStrings = destLine.substring(destLine.indexOf(':') + 1, destLine.length()).trim().split("\\s+");
        int[] dists = new int[distsAsStrings.length];
        for(int i = 0; i < distsAsStrings.length; i++){
            dists[i] = Integer.parseInt(distsAsStrings[i]);
        }
        this.times = times;
        this.dists = dists;
        StringBuilder timeBuilder = new StringBuilder();
        StringBuilder distBuilder = new StringBuilder();
        if(part == 2){
            for(int i = 0; i < timesAsStrings.length; i++){
                timeBuilder.append(timesAsStrings[i]);
                distBuilder.append(distsAsStrings[i]);
            }
            this.time = Long.parseLong(timeBuilder.toString());
            this.dist = Long.parseLong(distBuilder.toString());
        }
    }

    private void partTwo(){
        setUp(2);
        
        
        // I guess I would have to do the binary search anyway hah

        


        System.out.println(time);
        System.out.println(dist);
    }
}
