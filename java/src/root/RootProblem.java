package root;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import utils.TerminalPrint;

public class RootProblem {
    
    protected int[] parts = {};
    
    protected String[] readFileLineByLine(Integer problemNumber, Integer partNumber){
        
        BufferedReader reader;
        
        String[] inputArray = {};
        try {
            
            String fileName;
            String rootDir = System.getProperty("user.dir");
            if(partNumber == 0){ // Case if input is the same for both parts
                fileName = rootDir + "/java/src/inputs_2024/problem_" + problemNumber + ".txt";
            }else{
                fileName = rootDir + "/java/src/inputs_2024/problem_" + problemNumber + "_" + partNumber + ".txt";
            }

			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
            List<String> inputList = new ArrayList<String>();
			
            while (line != null) {
				inputList.add(line);
                line = reader.readLine();
			}
			reader.close();
            inputArray = inputList.toArray(new String[0]);

        } catch (IOException e) {
			e.printStackTrace();
		}
        return inputArray;
    }

    public void showSolution(Integer partNumber){
        if(partNumber < 0 || partNumber > 2){
            TerminalPrint.printAbortMsg();
            return;
        }

        if(partNumber == 0){
            solve(1);
            solve(2);
        }else{
            solve(partNumber);
        }

    }


    protected void solve(Integer partNumber){
        System.out.println("Printing message from parent class....");
    }

}
