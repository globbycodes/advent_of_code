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
    Map<Integer, List<Long[]>> dictMap = new HashMap<>();

    Long minLocation = Long.MAX_VALUE;

    
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
        dictMap.put(1, seedToSoil);
        dictMap.put(2, soilToFert);
        dictMap.put(3, fertToWater);
        dictMap.put(4, waterToLight);
        dictMap.put(5, lightToTemp);
        dictMap.put(6, tempToHumid);
        dictMap.put(7, humidToLoc);
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
        setUp();
        sortMaps();
        
        Long[][] seedArray = new Long[this.seeds.length / 2][2];
        int index = 0;
        for(int i = 0; i < seedArray.length; i++){
            seedArray[i][0] = this.seeds[index];
            seedArray[i][1] = this.seeds[index] + (this.seeds[index + 1] - 1L);
            index = index + 2;
        }
        for(Long[] seedRange: seedArray){
            processRange(1, seedRange[0], seedRange[1]);
        }
        TerminalPrint.printAnswerMsg(problemNumber, 2, minLocation);
    }

    private void processRange(int dictIndex, Long min, Long max){
        List<Long[]> dict = dictMap.get(dictIndex);
        List<Long[]> newRanges = new ArrayList<>();
        List<Long[]> rangesToProcess = new ArrayList<>();

        Long[] initialOldRange = new Long[] {min, max};
        rangesToProcess.add(initialOldRange);
        
        Long[] newRange;
        Long[] newRangeToProcess;

        while(rangesToProcess.size() > 0){
            min = rangesToProcess.get(0)[0];
            max = rangesToProcess.get(0)[1];
            boolean reset = true;
            for(Long[] range: dict){
                Long minRange = range[2];
                Long maxRange = range[3];
                Long minDestRange = range[0];
                Long maxDestRange = range[1];
                if(min >= minRange && min <= maxRange){
                    // this whole block can be compressed, but leaving it as is for now for.
                    if(min.equals(minRange)){
                        if(max > maxRange){
                            newRange = new Long[] {minDestRange, maxDestRange};
                            newRanges.add(newRange);
                            newRangeToProcess = new Long[] {maxRange + 1L, max};
                            rangesToProcess.remove(0);
                            rangesToProcess.add(newRangeToProcess);
                            reset = false;
                        }else{ // max <= maxRange
                            Long newDestMax = minDestRange + (max - min);
                            newRange = new Long[] {minDestRange, newDestMax};
                            newRanges.add(newRange);
                            rangesToProcess.remove(0);
                            reset = false;
                        }
                    }else{ // min > minRange
                        if(max > maxRange){
                            Long newDestMin = minDestRange + (min - minRange);
                            newRange = new Long[] {newDestMin, maxDestRange};
                            newRanges.add(newRange);
                            newRangeToProcess = new Long[] {maxRange + 1L, max};
                            rangesToProcess.remove(0);
                            rangesToProcess.add(newRangeToProcess);
                            reset = false;
                        }else{ // max <= maxRange
                            Long newDestMin = minDestRange + (min - minRange);
                            Long newDestMax = minDestRange + (max - minRange);
                            newRange = new Long[] {newDestMin, newDestMax};
                            rangesToProcess.remove(0);
                            newRanges.add(newRange);
                            reset = false;
                        }
                    }
                }
            }
            if(reset){
                // no range found
                newRange = new Long[] {min, max};
                newRanges.add(newRange);
                rangesToProcess.remove(0);
            }
        }
        
        // base case
        // update the location number (min) and terminate
        if(dictIndex == 7){
            newRanges.forEach(range -> {
                minLocation = Math.min(minLocation, range[0]);
            });
            return;
        }

        newRanges.forEach(range -> {
            processRange(dictIndex + 1, range[0], range[1]);
        });
    }

    private void sortMaps(){
        for(int i = 1; i <= 7; i++){
            sortSingleMap(i);
        }
    }

    private void sortSingleMap(int dictIndex){
        List<Long[]> toSort = dictMap.get(dictIndex);
        toSort.sort((o1, o2) -> o1[2].compareTo(o2[2]));
    }
}
