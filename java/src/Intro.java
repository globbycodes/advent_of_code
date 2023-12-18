import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import utils.*;
import problems.*;

public class Intro {
    public static void main(String[] args) throws Exception {
        welcome(0);
    }

    private static void welcome(Integer counter){


        TerminalPrint.printIntroLogo();
        
        while(true){
            TerminalPrint.printIntroMsg();
            
            BufferedReader terminal = new BufferedReader(new InputStreamReader(System.in)); 
            
            String[] terminalInput;
            try {
                terminalInput = terminal.readLine().split(",\\s");
            } catch(Exception e) {
                e.printStackTrace();
                TerminalPrint.printAbortMsg();
                return;
            }
            
            if(terminalInput.length < 1 || terminalInput.length > 2){
                // TerminalPrint.printAbortMsg();
                return;
            }

            String problemNumberInput = terminalInput[0];
            if(problemNumberInput.equals("")){
                return;
            }
            String problemPartInput;
            
            if(terminalInput.length == 1){ 
                TerminalPrint.printAllPartsSelecteMsg();
                problemPartInput = "0"; // no part # was selected
            }else{
                problemPartInput = terminalInput[1];
            }

            Integer problemNumber = convertToNum(problemNumberInput);
            Integer problemPart = convertToNum(problemPartInput);

            if(problemNumber < 0 || problemPart < 0){
                TerminalPrint.printAbortMsg();
                return;
            }

            try {
                Class<?> problemClass = Class.forName("problems.Problem" + problemNumber);
                Object problemObject = problemClass.getDeclaredConstructor().newInstance();
                Method showSolutionMethod = problemObject.getClass().getMethod("showSolution", Integer.class);
                showSolutionMethod.invoke(problemObject, problemPart);
                // welcome(counter + 1);
            } catch (Exception e) {
                e.printStackTrace();
                TerminalPrint.printWrongClassOrMethodMsg();
                return;
            }
        }
    }

    private static Integer convertToNum(String arg){
        if(arg == null) return -1;
        
        Integer num;
        
        try {
            num = Integer.parseInt(arg);
        } catch (NumberFormatException e){
            e.printStackTrace();
            return -1;
        }

        return num;
    }
}