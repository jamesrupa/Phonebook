import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Phonebook {

    public static ArrayList<String> firstNames = new ArrayList<>();
    public static ArrayList<String> lastNames = new ArrayList<>();
    public static ArrayList<String> phoneNumbers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        FileWriter filewriter = new FileWriter("phonebook.txt", true);
        PrintWriter output = new PrintWriter(filewriter);
        int numberOfEntries = 0;
        System.out.print("lookup, reverse-lookup, add, or quit (l/r/a/q)?");
        lookup(output, keyboard, numberOfEntries, firstNames, lastNames, phoneNumbers);
    }

    public static int read(ArrayList firstNames, ArrayList lastNames, ArrayList phoneNumbers) throws FileNotFoundException {
        Scanner file = new Scanner(new File("phonebook.txt"));
        int counter = 0;
        while (file.hasNext()) {
            lastNames.add(file.next());
            firstNames.add(file.next());
            phoneNumbers.add(file.next());
            counter++;
        }
        file.close();
        return counter;
    }

    public static void lookup(PrintWriter output, Scanner keyboard, int numberOfEntries, ArrayList firstNames,
                              ArrayList lastNames, ArrayList phoneNumbers) {
        int numLookups = 0, numRevLookups = 0;
        String numPhone = "", FN = "", LN = "";
        String inputNumPhone, inputFN, inputLN;
        boolean found = false;
        while (true) {
            String userInput = keyboard.next();
            if (userInput.equals("q"))
                break;
            else if (userInput.equals("l")) {
                System.out.print(numberOfEntries+"\n");
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
            } else if (userInput.equals("d")) {

            } else {
                System.out.print("INVALID ENTRY\n");
                System.out.print("Please enter a new response\n\n");
            }
            System.out.print("lookup, reverse-lookup, add, or quit (l/r/a/q)?");
        }
        System.out.printf("%n%d%s%n%d%s", numLookups, " lookups performed", numRevLookups, " reverse lookups performed");
        keyboard.close();
    }

    public static void add(PrintWriter output, Scanner keyboard, ArrayList firstNames,
                           ArrayList lastNames, ArrayList phoneNumbers) {
        System.out.print("enter last name: ");
        lastNames.add(keyboard.next());
        System.out.print("enter first name: ");
        firstNames.add(keyboard.next());
        System.out.print("enter phone number (nnn-nnn-nnnn): ");
        phoneNumbers.add(keyboard.next());
        int element = lastNames.size() - 1;
        output.println(lastNames.get(element)+ "       " + firstNames.get(element) + "    " + phoneNumbers.get(element));
        System.out.printf("%s, %s has been added to your contact list%n%n",lastNames.get(element), firstNames.get(element));
    }
}