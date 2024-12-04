package year_2024;

import utils.TerminalPrint;
import root.RootProblem;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem3 extends RootProblem{
    
    private int problemNumber = 3;

    
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
        
        String regEx = "mul\\(\\d+,\\d+\\)";
        Pattern pattern = Pattern.compile(regEx);

        String regexDigit = "\\d+";
        Pattern patternDigit = Pattern.compile(regexDigit);
        
        
        Matcher matcher;
        Matcher matcherDigit;
        String mulLine;
        for(String line: input){
            matcher = pattern.matcher(line);
            while(matcher.find()){
                mulLine = matcher.group(0);
                matcherDigit = patternDigit.matcher(mulLine);
                Long mul = 1l;
                while(matcherDigit.find()){
                    mul *= Long.parseLong(matcherDigit.group(0));
                }
                sum += mul;
            }
        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, sum);
    }

    private void partTwo(String[] input){
        long sum = 0;

        String regEx = "mul\\(\\d+,\\d+\\)";
        Pattern pattern = Pattern.compile(regEx);

        String regexDigit = "\\d+";
        Pattern patternDigit = Pattern.compile(regexDigit);

        String regexDont = "don't\\(\\).*?mul\\(\\d+,\\d+\\)(.*?)(?=do\\(\\)|$)";
        Pattern patternDont = Pattern.compile(regexDont);
        
        
        Matcher matcher;
        Matcher matcherDigit;
        Matcher matchDont;
        StringBuilder newString = new StringBuilder();
        String mulLine;
        
        for(String line: input){
            newString.append(line);
        }
        
        matchDont = patternDont.matcher(newString);
        int index = 0;
        int[] startIndices = new int[20];
        int[] endIndices = new int[20];
        while(matchDont.find()){
            startIndices[index] = matchDont.start(0);
            endIndices[index] = matchDont.end(0);
            index++;
        }

        for(int i = 0; i < index; i++){
            for(int j = startIndices[i]; j < endIndices[i]; j++){
                newString.setCharAt(j, '|');
            }
        }


        matcher = pattern.matcher(newString);
        while(matcher.find()){
            mulLine = matcher.group(0);
            matcherDigit = patternDigit.matcher(mulLine);
            Long mul = 1l;
            while(matcherDigit.find()){
                mul *= Long.parseLong(matcherDigit.group(0));
            }
            sum += mul;
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, sum);
    }
}
