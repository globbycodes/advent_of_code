package utils;

public class TerminalPrint {
    public static void printIntroLogo(){
        System.out.println("              ___      _            _     _____  __   _____           _        _ ");
        System.out.println("             / _ \\    | |          | |   |  _  |/ _| /  __ \\         | |      | |");
        System.out.println("            / /_\\ \\ __| | ___ _ __ | |_  | | | | |_  | /  \\/ ___   __| | ___  | |");
        System.out.println("            |  _  |/ _` |/ _ \\ '_ \\| __| | | | |  _| | |    / _ \\ / _` |/ _ \\ | |");
        System.out.println("            | | | | (_| |  __/ | | | |_  \\ \\_/ / |   | \\__/\\ (_) | (_| |  __/ |_|");
        System.out.println("            \\_| |_/\\__,_|\\___|_| |_|\\__|  \\___/|_|    \\____/\\___/ \\__,_|\\___| (_)");
    }

    public static void printAllPartsSelecteMsg(){
        System.out.println("\n\n\n No part number was selected. Printing results for all parts");
    }

    public static void printIntroMsg(){
        System.out.println("\n\n\nEnter the problem number, followed by the part number:");
    }

    public static void printAbortMsg(){
        System.out.println("Wrong input, aborting");
    }

    public static void printWrongClassOrMethodMsg(){
        System.out.println("Something is wrong with calling a class or method:");
    }


    public static void printWrongProblemInputMsg(){
        System.out.println("Something is off with file input");
    }
}
