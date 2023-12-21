package problems;

import java.util.*;
import utils.TerminalPrint;

public class Problem5  extends RootProblem{

    int problemNumber = 5;
    int width;
    String[] input;
    boolean calledSetup = false;
    Long[] seeds;
    List<Long[]> seedToSoil = new ArrayList<>();
    List<Long[]> soilToFert = new ArrayList<>();
    List<Long[]> fertToWater = new ArrayList<>();
    List<Long[]> waterToLight = new ArrayList<>();
    List<Long[]> lightToTemp = new ArrayList<>();
    List<Long[]> tempToHumid = new ArrayList<>();
    List<Long[]> humidToLoc = new ArrayList<>();

    
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
        setUp();
        Long minLocation = Long.MAX_VALUE;
        for(Long seed: seeds){
            Long soil = findDestination(seedToSoil, seed);
            Long fert = findDestination(soilToFert, soil);
            Long water = findDestination(fertToWater, fert);
            Long light = findDestination(waterToLight, water);
            Long temp = findDestination(lightToTemp, light);
            Long humid = findDestination(tempToHumid, temp);
            Long location = findDestination(humidToLoc, humid);
            minLocation = Math.min(minLocation, location);
        }
        TerminalPrint.printAnswerMsg(problemNumber, 1, minLocation);
    }

    private Long findDestination(List<Long[]> map, Long input){
        Long dest = input;
        for(Long[] ranges: map){
            if(input >= ranges[2] && input <= ranges[3]){
                Long diff = input - ranges[2];
                dest = ranges[0] + diff;
                return dest;
            }
        }
        return dest;
    }

    private void setUp(){
        if(calledSetup){
            return;
        }

        calledSetup = true;
        // extract seeds into Long array
        String seedsLine = input[0];
        String[] seedNumbersAsStrings = seedsLine.substring(6, seedsLine.length()).trim().split("\\s+");
        Long[] seeds = new Long[seedNumbersAsStrings.length];
        for(int i = 0; i < seedNumbersAsStrings.length; i++){
            seeds[i] = Long.parseLong(seedNumbersAsStrings[i]);
        }
        this.seeds = seeds;
        
        String line;
        // set up other maps
        for(int i = 1; i < input.length; i++){
            line = input[i];
            
            if(line == "") continue;

            if(line.length() > 0 && isNumber(line.charAt(0))){
                System.out.println(line);
                System.out.println(i);  
                TerminalPrint.somethingIsWrong();
                return;
            }
            
            if(line.contains("seed-to-soil"))
                i = populateMap(1, i);
            else if(line.contains("soil-to-fertilizer"))
                i = populateMap(2, i);
            else if(line.contains("fertilizer-to-water"))
                i = populateMap(3, i);
            else if(line.contains("water-to-light"))
                i = populateMap(4, i);
            else if(line.contains("light-to-temperature"))
                i = populateMap(5, i);
            else if(line.contains("temperature-to-humidity"))
                i = populateMap(6, i);
            else if(line.contains("humidity-to-location"))
                i = populateMap(7, i);
        }
    }

    private int populateMap(int key, int index){
        int newIndex = 0;
        switch(key){
            case 1:
                newIndex = fillInTable(seedToSoil, index);
                break;
            case 2:
                newIndex = fillInTable(soilToFert, index);
                break;
            case 3:
                newIndex = fillInTable(fertToWater, index);
                break;
            case 4:
                newIndex = fillInTable(waterToLight, index);
                break;
            case 5:
                newIndex = fillInTable(lightToTemp, index);
                break;
            case 6:
                newIndex = fillInTable(tempToHumid, index);
                break;
            case 7:
                newIndex = fillInTable(humidToLoc, index);
                break;
            default:
                return -1;
        }
        return newIndex;
    }

    private int fillInTable(List<Long[]> map, int index){
        String line;
        int i;
        
        for(i = index + 1; i < input.length; i++){
            
            line = input[i];
            if(line.equals("")){
                return i - 1;
            }

            String[] numbersAsStrings = line.trim().split("\\s+");
            Long[] numbers = new Long[4];
            
            numbers[0] = Long.parseLong(numbersAsStrings[0]);
            numbers[1] = Long.parseLong(numbersAsStrings[0]) + Long.parseLong(numbersAsStrings[2]) - 1L;

            numbers[2] = Long.parseLong(numbersAsStrings[1]);
            numbers[3] = Long.parseLong(numbersAsStrings[1]) + Long.parseLong(numbersAsStrings[2]) - 1L;
            map.add(numbers);
        }
        
        return i;
    }

    private boolean isNumber(char curChar){
        if(curChar >= '0' && curChar <= '9'){
            return true;
        }
        return false;
    }

    private void partTwo(){
        
    }
}
