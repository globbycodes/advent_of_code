package problems;

import utils.TerminalPrint;
import java.util.*;
import root.RootProblem;

public class Problem7 extends RootProblem{
    
    int problemNumber = 7;
    int width;
    String[] input;
    Map<String, Integer> rankMap = new HashMap<>();
    Map<String, Long> bidMap = new HashMap<>();
    Map<Character, Integer> charOrderMap = new HashMap<>();

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
        charOrderSetup();
        String[] items;
        String[] cards = new String[input.length];
        // int[] bids = new int[input.length];
        int index = 0;
        
        for(String line: input){
            items = line.split("\\s+");
            cards[index] = items[0];
            // bids[index] = Integer.parseInt(items[1]);
            
            // Won't work if input will have duplicated cards with different bids! If that is the case then don't split cards and bids just sort as is and ignore the bid substring during sort.
            bidMap.put(items[0], Long.parseLong(items[1]));
            index++;
        }

        int[] charMap = new int[86]; // using array instead of hashmap for better performance
        int[] counterMap = new int[5];

        int cardValue;
        // calculate ranks
        for(String card: cards){
            if(rankMap.get(card) != null) continue;
            resetMap(charMap);
            resetMap(counterMap);
            
            for(int i = 0; i < 5; i++){
                char curChar = card.charAt(i);
                charMap[curChar]++;
            }

            for(int i = 0; i < charMap.length; i++){
                counterMap[(charMap[i] - 1) < 0 ? 0 : (charMap[i] - 1)]++;
            }

            if(counterMap[4] == 1)
                cardValue = 1;
            else if(counterMap[3] == 1)
                cardValue = 2;
            else if(counterMap[2] == 1 && counterMap[1] == 1)
                cardValue = 3;
            else if(counterMap[2] == 1)
                cardValue = 4;
            else if(counterMap[1] == 2)
                cardValue = 5;
            else if(counterMap[1] == 1)
                cardValue = 6;
            else
                cardValue = 7;

            rankMap.put(card, cardValue);
        }
        // Sort cards
        Arrays.sort(cards, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int length = o1.length();
                int firstRank = rankMap.get(o1);
                int secondRank = rankMap.get(o2);
                if(firstRank < secondRank)
                    return 1;
                else if(firstRank > secondRank)
                    return -1;
                
                for(int i = 0; i < length; i++) {
                   int firstLetterIndex = charOrderMap.get(o1.charAt(i));
                   int secondLetterIndex = charOrderMap.get(o2.charAt(i));
        
                   if(firstLetterIndex == secondLetterIndex) continue;
        
                   if(firstLetterIndex < secondLetterIndex) return 1;
                   else return -1;
                }
        
                return 0;
            }
        });
        Long cardRank = 1L;
        Long total = 0L;
        for(String card: cards){
            Long bid = bidMap.get(card);
            total += cardRank * bid;
            cardRank++;
        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, total);
    }

    private void charOrderSetup(){
        char[] charOrder = new char[] {'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
        for(int i = 0; i < charOrder.length; i++){
            charOrderMap.put(charOrder[i], i);
        }
    }

    private void resetMap(int[] charMap){
        for(int i = 0; i < charMap.length; i++){
            charMap[i] = 0;
        }
    }

    private void partTwo(){
        rankMap = new HashMap<>();
        bidMap = new HashMap<>();
        charOrderMap = new HashMap<>();
        charOrderSetupP2();
        String[] items;
        String[] cards = new String[input.length];
        // int[] bids = new int[input.length];
        int index = 0;
        
        for(String line: input){
            items = line.split("\\s+");
            cards[index] = items[0];
            // bids[index] = Integer.parseInt(items[1]);
            
            // Won't work if input will have duplicated cards with different bids! If that is the case then don't split cards and bids just sort as is and ignore the bid substring during sort.
            bidMap.put(items[0], Long.parseLong(items[1]));
            index++;
        }

        int[] charMap = new int[86]; // using array instead of hashmap for better performance
        int[] counterMap = new int[5];

        int cardValue;
        // calculate ranks
        for(String card: cards){
            if(rankMap.get(card) != null) continue;
            resetMap(charMap);
            resetMap(counterMap);
            int j_counter = 0;
            for(int i = 0; i < 5; i++){
                char curChar = card.charAt(i);
                if(curChar == 'J'){
                    j_counter++;
                }else{
                    charMap[curChar]++;
                }
            }

            for(int i = 0; i < charMap.length; i++){
                counterMap[(charMap[i] - 1) < 0 ? 0 : (charMap[i] - 1)]++;
            }
            int adj_number = 0;

            if(counterMap[4] == 1 || j_counter == 5){
                cardValue = 1;
            }
            else if(counterMap[3] == 1){
                cardValue = 2 - j_counter;
            }
            else if(counterMap[2] == 1 && counterMap[1] == 1){
                cardValue = 3;
            }
            else if(counterMap[2] == 1){
                if(j_counter > 0)
                    adj_number = 1;
                cardValue = 4 - (j_counter + adj_number);
            }
            else if(counterMap[1] == 2){
                if(j_counter > 0)
                    adj_number = 1;
                cardValue = 5 - (j_counter + adj_number);
            }
            else if(counterMap[1] == 1){
                cardValue = 6;
                if(j_counter >= 2)
                    cardValue = cardValue - (j_counter + 2);
                else if(j_counter == 1)
                    cardValue = cardValue - (j_counter + 1);
            }
            else{
                cardValue = 7;
                if(j_counter >= 3)
                    cardValue = cardValue - (j_counter + 2);
                else if(j_counter > 1)
                    cardValue = cardValue - (j_counter + 1);
                else if(j_counter == 1)
                    cardValue = cardValue - j_counter;
            }
            rankMap.put(card, cardValue);
        }
        // Sort cards
        Arrays.sort(cards, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int length = o1.length();
                int firstRank = rankMap.get(o1);
                int secondRank = rankMap.get(o2);
                if(firstRank < secondRank)
                    return 1;
                else if(firstRank > secondRank)
                    return -1;
                
                for(int i = 0; i < length; i++) {
                   int firstLetterIndex = charOrderMap.get(o1.charAt(i));
                   int secondLetterIndex = charOrderMap.get(o2.charAt(i));
        
                   if(firstLetterIndex == secondLetterIndex) continue;
        
                   if(firstLetterIndex < secondLetterIndex) return 1;
                   else return -1;
                }
        
                return 0;
            }
        });
        Long cardRank = 1L;
        Long total = 0L;
        for(String card: cards){
            Long bid = bidMap.get(card);
            total += cardRank * bid;
            cardRank++;
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, total);
    }

    private void charOrderSetupP2(){
        char[] charOrder = new char[] {'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'};
        for(int i = 0; i < charOrder.length; i++){
            charOrderMap.put(charOrder[i], i);
        }
    }


}
