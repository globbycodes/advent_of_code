package year_2024;

import utils.TerminalPrint;
import java.util.*;

import root.RootProblem;

public class Problem5 extends RootProblem{
    
    private int problemNumber = 5;

    
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
        Long sum = 0l;
        Map<String, HashSet<String>> queueMap = new HashMap<>();
        boolean queueStage = true;
        for(String line: input){
            if(line.length() == 0){
                queueStage = false;
                continue;
            }

            if(queueStage){
                String[] numbers = line.split("\\|");
                if(queueMap.containsKey(numbers[0])){
                    queueMap.get(numbers[0]).add(numbers[1]);
                }else{
                    queueMap.put(numbers[0], new HashSet<>());
                    queueMap.get(numbers[0]).add(numbers[1]);
                }
            }else{
                String[] page = line.split("\\,");
                boolean validPage = true;
                for(int i = 0; i < page.length; i++){
                    HashSet<String> set = queueMap.get(page[i]);
                    for(int j = i + 1; j < page.length; j++){
                        if(set == null || !set.contains(page[j])){
                            validPage = false;
                            break;
                        }
                    }
                    if(!validPage)
                        break;
                }
                if(validPage){
                    int mid = page.length / 2;
                    int x_num = Integer.parseInt(page[mid]);
                    sum += x_num;
                }
            }
        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    private void partTwo(String[] input){
        Long sum = 0l;
        Map<String, HashSet<String>> queueMap = new HashMap<>();
        boolean queueStage = true;
        for(String line: input){
            if(line.length() == 0){
                queueStage = false;
                continue;
            }

            if(queueStage){
                String[] numbers = line.split("\\|");
                if(queueMap.containsKey(numbers[1])){
                    queueMap.get(numbers[1]).add(numbers[0]);
                }else{
                    queueMap.put(numbers[1], new HashSet<>());
                    queueMap.get(numbers[1]).add(numbers[0]);
                }
            }else{
                String[] page = line.split("\\,");
                boolean validPage = true;
                for(int i = page.length - 1; i >= 0; i--){
                    HashSet<String> set = queueMap.get(page[i]);
                    for(int j = i - 1; j >= 0; j--){
                        if(set == null || !set.contains(page[j])){
                            validPage = false;
                            break;
                        }
                    }
                }

                if(!validPage){
                    Arrays.sort(page, new Comparator<String>() {
                        public int compare(String o1, String o2) {
                            // Intentional: Reverse order for this demo
                            if(queueMap.containsKey(o1)){
                                if(queueMap.get(o1).contains(o2)){
                                    return 1;
                                }else{
                                    return -1;
                                }
                            }else if(queueMap.containsKey(o2)){
                                if(queueMap.get(o2).contains(o1)){
                                    return -1;
                                }else{
                                    return 1;
                                }
                            }
                            
                            return 0;
                        }
                    });
                    // gabagool
                    int mid = page.length / 2;
                    int x_num = Integer.parseInt(page[mid]);
                    sum += x_num;
                }
            }
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, sum);
    }
}
