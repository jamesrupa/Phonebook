import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Phonebook{
    public static void main(String[] args) throws FileNotFoundException {
        Scanner keyboard = new Scanner(System.in);
        final int MAX_SIZE = 100;
        String[] firstName = new String[MAX_SIZE];
        String[] lastName = new String[MAX_SIZE];
        String[] phoneNumber = new String[MAX_SIZE];
        int numberOfEntries = read(firstName, lastName, phoneNumber);
        System.out.print("lookup, reverse-lookup, quit (l/r/q)? ");
        lookup(keyboard, numberOfEntries, firstName, lastName, phoneNumber);
    }

    public static int read(String[] firstName, String[] lastName, String[] phoneNumber) throws FileNotFoundException {
        Scanner file = new Scanner(new File("phonebook.txt"));
        int counter = 0;
        while (file.hasNext()) {
            lastName[counter] = file.next();
            firstName[counter] = file.next();
            phoneNumber[counter] = file.next();
            counter++;
        }
        file.close();
        return counter;
    }

    public static void lookup(Scanner keyboard, int numberOfEntries, String[] firstName, String[] lastName, String[] phoneNumber) {
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
                    if (firstName[i].equals(inputFN) && lastName[i].equals(inputLN)) {
                        numPhone = phoneNumber[i];
                        inputFN = firstName[i];
                        inputLN = lastName[i];
                        found = true;
                        break;
                    }
                }
                if (found) {
                    System.out.printf("%s %s%s %s%n%n", inputFN, inputLN, "'s phone number is", numPhone);
                } else {
                    System.out.print("-- Name not found\n\n");
                }
                found = false;
                numLookups++;
            } else {
                System.out.print("phone number (nnn-nnn-nnnn)? ");
                inputNumPhone = keyboard.next();
                for (int i = 0; i < numberOfEntries; i++) {
                    if (inputNumPhone.equals(phoneNumber[i])) {
                        inputNumPhone = phoneNumber[i];
                        LN = lastName[i];
                        FN = firstName[i];
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
            }
            System.out.print("lookup, reverse-lookup, quit (l/r/q)? ");
        }
        System.out.printf("%n%d%s%n%d%s", numLookups, " lookups performed", numRevLookups, " reverse lookups performed");
        keyboard.close();
    }
}
