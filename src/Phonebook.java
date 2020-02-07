import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A phonebook application.
 * First, it fills parallel arrays with last names, first names, and phone numbers.
 * Then, it prompts the user to choose what to do - lookup, reverse-lookup, add, and quit.
 * Depending on what the user chooses, they enter the credentials asked and gives a response.
 * It keeps doing this until the user indicates they want to stop with the entry of "q".
 * Lastly, it prints out the number of times the user preforms the action.
 *
 * @author James Rupa
 */

public class Phonebook {
    /**
     * Sets up the arrays and calls the read method; then calls the lookup method for as long as the user wishes.
     *
     * @param args The array of command-line arguments
     */
    public static ArrayList<String> firstNames = new ArrayList<>();
    public static ArrayList<String> lastNames = new ArrayList<>();
    public static ArrayList<String> phoneNumbers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        File file = new File("phonebook.txt");
        Scanner keyboard = new Scanner(System.in);
        Scanner inputFile = new Scanner(file);
        FileWriter filewriter = new FileWriter(file, true);
        BufferedWriter output = new BufferedWriter(filewriter);
        int numberOfEntries = 0;
        read(inputFile, firstNames, lastNames, phoneNumbers);
        System.out.print("lookup, reverse-lookup, add, or quit (l/r/a/q)?");
        lookup(output, keyboard, numberOfEntries, firstNames, lastNames, phoneNumbers);
    }

    /**
     * Reads last names, first names, and phone numbers from a file into three seperate arrays.
     * Keeps count of how many records have been read in.
     *
     * @param inputFile The Scanner object that reads from a file
     * @param firstName The array of first names
     * @param lastName The array of last names
     * @param phoneNumber The array of phone numbers
     *
     * @return The number of records that were read into the arrays from the file
     */
    public static int read(Scanner inputFile, ArrayList firstNames, ArrayList lastNames, ArrayList phoneNumbers) {
        int counter = 0;
        while (inputFile.hasNext()) {
            lastNames.add(inputFile.next());
            firstNames.add(inputFile.next());
            phoneNumbers.add(inputFile.next());
            counter++;
        }
        inputFile.close();
        return counter;
    }

    /**
     * Given an option from the user's input, this method will preform various lookups
     * whether it is using a last and first name to find a phone number. Or looking up
     * the phone number to find the first and last names. It can also add an additional
     * contact into the phonebook. This continously happens until the user enters "q" to quit the program.
     *
     * @param output The BufferedWriter object that appends to a file
     * @param keyboard The Scanner object that reads input from keyboard
     * @param numberOfEntries The integer/number of entries read into the arrays from the file
     * @param firstName The array of first names
     * @param lastName The array of last names
     * @param phoneNumber The array of phone numbers
     */
    public static void lookup(BufferedWriter output, Scanner keyboard, int numberOfEntries, ArrayList firstNames,
                              ArrayList lastNames, ArrayList phoneNumbers) throws IOException {
        int numLookups = 0, numRevLookups = 0;
        String numPhone = "", FN = "", LN = "";
        String inputNumPhone, inputFN, inputLN;
        boolean found = false;
        while (true) {
            String userInput = keyboard.next();
            if (userInput.equals("q"))
                break;
            else if (userInput.equals("l")) {
                System.out.print("last name? ");
                inputLN = keyboard.next();
                System.out.print("first name? ");
                inputFN = keyboard.next();
                for (int i = 0; i < numberOfEntries; i++) {
                    if (firstNames.get(i).equals(inputFN) && lastNames.get(i).equals(inputLN)) {
                        numPhone = (String) phoneNumbers.get(i);
                        inputFN = (String) firstNames.get(i);
                        inputLN = (String) lastNames.get(i);
                        found = true;
                        break;
                    }
                }
                System.out.print(numberOfEntries+"\n");
                if (found) {
                    System.out.printf("%s %s%s %s%n%n", inputFN, inputLN, "'s phone number is", numPhone);
                } else {
                    System.out.print("-- Name not found\n\n");
                }
                found = false;
                numLookups++;
            } else if (userInput.equals("r")) {
                System.out.print("phone number (nnn-nnn-nnnn)? ");
                inputNumPhone = keyboard.next();
                for (int i = 0; i < numberOfEntries; i++) {
                    if (inputNumPhone.equals(phoneNumbers.get(i))) {
                        inputNumPhone = (String) phoneNumbers.get(i);
                        LN = (String) lastNames.get(i);
                        FN = (String) firstNames.get(i);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    System.out.printf("%s %s %s, %s%n%n", inputNumPhone, "belongs to", LN, FN);
                } else {
                    System.out.print("-- Phone number not found\n\n");
                }
                found = false;
                numRevLookups++;
            } else if (userInput.equals("a")) {
                add(output, keyboard, firstNames, lastNames, phoneNumbers);
                numberOfEntries++;
            } else {
                System.out.print("INVALID ENTRY\n");
                System.out.print("Please enter a new response\n\n");
            }
            System.out.print("lookup, reverse-lookup, add, or quit (l/r/a/q)?");
        }
        System.out.printf("%n%d%s%n%d%s", numLookups, " lookups performed", numRevLookups, " reverse lookups performed");
        keyboard.close();
    }

    /**
     * Adds an additional contact onto the phonebook. Adding their first name,
     * last name, and phone number. It then appends into the original file to
     * update the contact list.
     *
     * @param output The BufferedWriter object that appends to a file
     * @param keyboard The Scanner object that reads input from keyboard
     * @param firstName The array of first names
     * @param lastName The array of last names
     * @param phoneNumber The array of phone numbers
     */
    public static void add(BufferedWriter output, Scanner keyboard, ArrayList firstNames,
                           ArrayList lastNames, ArrayList phoneNumbers) throws IOException {
        System.out.print("enter last name: ");
        lastNames.add(keyboard.next());
        System.out.print("enter first name: ");
        firstNames.add(keyboard.next());
        System.out.print("enter phone number (nnn-nnn-nnnn): ");
        phoneNumbers.add(keyboard.next());
        int element = lastNames.size() - 1;
        output.write(lastNames.get(element)+ "       " + firstNames.get(element) + "    " + phoneNumbers.get(element));
        System.out.printf("%s, %s has been added to your contact list%n%n",lastNames.get(element), firstNames.get(element));
    }
}