import java.util.Scanner;
import java.lang.reflect.*;
import utils.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to to the ADVENT OF CODE!!!");
        Scanner terminal = new Scanner(System.in);
        terminal.useDelimiter(",");
        // terminal.setDelimiter();
        System.out.println("Please enter problmem number");
        int num1 = terminal.nextInt();
        int num2 = terminal.nextInt();

        System.out.println("Please enter problmem number " + num1 + " " + num2);
        // String problemNumber = terminal.next1Line();  // Read user input

        Challenges.hi();
        Class<?> testClass = Class.forName("utils.Challenges");
        Method setNameMethod = testClass.getMethod("hi");
        setNameMethod.invoke(testClass);
        // testClass.hi();
    }
}