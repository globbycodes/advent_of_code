package problems;

import utils.TerminalPrint;
import root.RootProblem;

public class Problem6 extends RootProblem{
    int problemNumber = 6;
    int width;
    String[] input;
    int[] times;
    int[] dists;
    Long time;
    Long dist;
    Long leftBound;
    Long rightBound;

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
        Long mid = time / 2L;
        Long left = findBoundary(0L, mid, false, -1L);
        Long right = findBoundary(mid + 1, time, true, -1L);

        TerminalPrint.printAnswerMsg(problemNumber, 2, right - left + 1);
    }

    private Long findBoundary(Long left, Long right, boolean dir, Long higherDist){
        
        if(left <= time && left <= right){
            Long mid = left + (right - left) / 2; 
            Long rem = time - mid;
            Long newDist = mid * rem;

            higherDist = newDist > dist ? mid : higherDist;
            
            if(newDist.equals(dist)){
                if(!dir){
                    return mid + 1;
                }else{
                    return mid - 1;
                }
            }else if(left.equals(right)){
                if(!dir)
                    return higherDist;
                else
                    return higherDist;
            }

            if(newDist > dist){
                if(!dir){
                    return findBoundary(left, mid, dir, higherDist);
                }else{
                    return findBoundary(mid + 1, right, dir, higherDist);
                }
            }else{
                if(!dir){
                    return findBoundary(mid + 1, right, dir, higherDist);
                }else{
                    return findBoundary(left, mid, dir, higherDist);
                }
            }
        }
        return -1L;
    }
}
