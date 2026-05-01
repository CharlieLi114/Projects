/**
 * CSC 112 Lab Week10 (Unique Linked Inventory) 
 * This program reads inventory and update files and creates a database (list)
 * of unique ID numbers. Statistics about the database and file contents
 * are then displayed. The file names are read from the user.
 */ 
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Inventory2Linked {
    public static void main(String[] args) {
        String inFileName;      // inventory file name
        String upFileName;      // update file name
        Scanner keyboard = new Scanner(System.in); // keyboard
        Scanner inFile = null;  // inventory file scanner
        Scanner upFile = null;  // update file scanner

        // read inFileName and upFileName from the user
        System.out.print("Enter inventory file name and update file name  -> ");
        inFileName = keyboard.next();
        upFileName = keyboard.next();

        inFile = openFileScanner(inFileName);
        upFile = openFileScanner(upFileName);

        var list = new LinkedList<Integer>();  // the database

        // process inventory file
        System.out.println("processing: " + inFileName);
        readInventoryFile(inFile, list);
        System.out.println(list);

        // process the update file
        System.out.println("processing: " + upFileName);
        readUpdateFile(upFile, list);
        System.out.println(list);
    }


    /**
     * Opens a Scanner for the file called fileName, if unable the  
     * method will exit the program.
     *
     * @param fileName String is the file name
     * @return Scanner object for the file fileName
     */
    private static Scanner openFileScanner(String fileName) {
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File(fileName));
        } 
        catch(FileNotFoundException e) {
            System.out.println("Error in opening " + fileName);
            System.exit(0);
        }
        return fileScanner;

    }


    /**
     * Reads the contents of inFile and stores the unique IDs in
     * list. Maintains statistics about the items read.
     *
     * @param inFile Scanner for the inventory file 
     * @param list LinkedList of unique IDs
     */
    private static void readInventoryFile(Scanner inFile, LinkedList<Integer>list) {
        int id;         // id read from the file
        int loc = 0;    // location of id in the list
        int lines = 0;  // number of lines read from file

        while(inFile.hasNext()) {
            try {
                id = inFile.nextInt();
                if(id >= 0) {
                    loc = list.indexOf(id);
                    if(loc < 0) {
                        list.addFirst(id);  
                    }
                }
            }
            catch(InputMismatchException e) {
                inFile.next();
            }
            lines++;
        }
        System.out.println("lines: " + lines + ", unique: " + list.size());
        inFile.close();
    }


    /**
     * Reads the contents of upFile and stores the unique IDs in
     * list. Maintains statistics about the items read.
     *
     * @param upFile Scanner for the update file name
     * @param list LinkedList of unique IDs
     */
    private static void readUpdateFile(Scanner upFile, LinkedList<Integer> list) {
        char action;    // action code from file
        int id;         // id from file
        int loc = 0;    // location of id in the list
        int lines = 0;  // number of lines read from file

        while(upFile.hasNext()) {
            try {
                action = upFile.next().charAt(0);
                id = upFile.nextInt();
                if(Character.isLetter(action) && id >= 0) {
                    if(id >= 0) {
                        action = Character.toLowerCase(action);
                        loc = list.indexOf(id);
                        if(action == 'a' && loc < 0) {
                            list.addFirst(id);
                        }
                        else if(action == 'd' && loc >= 0) {
                            list.remove(loc);  
                        }
                    }
                }
            }
            catch(InputMismatchException e) {
                upFile.next();
            }
            lines++;
        }
        System.out.println("lines: " + lines + ", unique: " + list.size());
        upFile.close();
    }

}

