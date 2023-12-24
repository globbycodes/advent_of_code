package problems;

import utils.TerminalPrint;
import java.util.*;

public class Problem7 extends RootProblem{
    
    int problemNumber = 7;
    int width;
    String[] input;
    Map<String, Integer> rankMap = new HashMap<>();

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
        String[] items;
        String[] cards = new String[input.length];
        int[] bids = new int[input.length];
        int index = 0;
        
        for(String line: input){
            items = line.split("\\s+");
            cards[index] = items[0];
            bids[index] = Integer.parseInt(items[1]);
            index++;
        }

        int[] charMap = new int[86]; // using array instead of hashmap for better performance
        for(String card: cards){
            if(rankMap.get(card) != null) continue;
            resetMap(charMap);
            for(int i = 0; i < 5; i++){
                char curChar = card.charAt(i);
                if(charMap[curChar])
            }
        }
    }

    private void resetMap(int[] charMap){
        for(int i = 0; i < charMap.length; i++){
            charMap[i] = 0;
        }
    }

    private void partTwo(){

    }


}
