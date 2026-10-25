import java.util.Scanner;
//=================================================================================================
/**
 * Records the teeth of the members of a Floridian family for the Florida Dental Association.
 * Each tooth is an Incisor ('I'), a Bicuspid ('B'), or Missing ('M').
 * The teeth are stored in a three dimensional array of characters: one plane per person,
 * two rows (uppers and lowers), and one column per tooth.
 * @author Ciara Escalante
 * @version 1.0
 */
public class ToothRecords {
//-------------------------------------------------------------------------------------------------
    /** Shared keyboard input for the whole program. */
    private static final Scanner keyboard = new Scanner(System.in);
    /** Maximum number of people in a family. */
    private static final int MAX_PEOPLE = 6;
    /** Maximum number of teeth (including missing teeth) in one layer. */
    private static final int MAX_TEETH = 8;
    /** Number of tooth layers (uppers and lowers). */
    private static final int NUM_LAYERS = 2;
    /** Row index of the upper teeth. */
    private static final int UPPER = 0;
    /** Row index of the lower teeth. */
    private static final int LOWER = 1;
    /** Character for an incisor. */
    private static final char INCISOR = 'I';
    /** Character for a bicuspid. */
    private static final char BICUSPID = 'B';
    /** Character for a missing tooth. */
    private static final char MISSING = 'M';
    /** Width that prompt text is padded to, so that the colons line up. */
    private static final int PROMPT_WIDTH = 44;
//-------------------------------------------------------------------------------------------------
    /**
     * Reads the family's teeth, then offers the menu until the user exits.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {

        int numPeople;
        String[] names;
        char[][][] teeth;
        char option;

//----Display the welcome
        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

//----Input the family's names and teeth
        numPeople = getFamilySize();
        names = new String[numPeople];
        teeth = new char[numPeople][NUM_LAYERS][];
        getFamilyTeeth(names,teeth);

//----Process menu options until the user exits
        do {
            System.out.println();
            option = getMenuOption();
            switch (option) {
                case 'P':
                    System.out.println();
                    printFamilyTeeth(names,teeth);
                    break;
                case 'E':
                    extractTooth(names,teeth);
                    break;
                case 'R':
                    reportRootCanals(teeth);
                    break;
                default:
                    break;
            }
        } while (option != 'X');

//----Say goodbye
        System.out.println();
        System.out.println("Exiting the Floridian Tooth Records :-)");
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Prints a prompt padded so that all the colons line up.
     * @param text The prompt text
     */
    private static void prompt(String text) {

        System.out.printf("%-" + PROMPT_WIDTH + "s: ",text);
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Gets the number of people in the family, re-asking until it is in the range
     * 1 to {@value #MAX_PEOPLE}.
     * @return The number of people in the family
     */
    private static int getFamilySize() {

        int numPeople;

//----Get the number of people, and re-ask until it is valid
        prompt("Please enter number of people in the family");
        numPeople = keyboard.nextInt();
        while (numPeople < 1 || numPeople > MAX_PEOPLE) {
            prompt("Invalid number of people, try again");
            numPeople = keyboard.nextInt();
        }

        return(numPeople);
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Gets the name, uppers, and lowers of every family member.
     * @param names The array to fill with the family members' names
     * @param teeth The 3D array to fill with each person's uppers and lowers
     */
    private static void getFamilyTeeth(String[] names,char[][][] teeth) {

        int person;

        for (person = 0; person < names.length; person++) {
            prompt("Please enter the name for family member " + (person + 1));
            names[person] = keyboard.next();
            teeth[person][UPPER] = getTeethRow("uppers for " + names[person]);
            teeth[person][LOWER] = getTeethRow("lowers for " + names[person]);
        }
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Gets one layer of teeth, re-asking until it contains only valid tooth types and has no
     * more than {@value #MAX_TEETH} teeth.
     * @param description What is being entered, e.g., "uppers for Raul"
     * @return The layer of teeth, in upper case
     */
    private static char[] getTeethRow(String description) {

        String toothString;

//----Get the teeth, and re-ask until they are valid
        prompt("Please enter the " + description);
        toothString = keyboard.next().toUpperCase();
        while (!isValidTeeth(toothString) || toothString.length() > MAX_TEETH) {
            if (!isValidTeeth(toothString)) {
                prompt("Invalid teeth types, try again");
            } else {
                prompt("Too many teeth, try again");
            }
            toothString = keyboard.next().toUpperCase();
        }

        return(toothString.toCharArray());
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Checks that a string is made only of valid tooth type letters.
     * @param toothString The upper case string of tooth types to check
     * @return True if every character is 'I', 'B', or 'M'
     */
    private static boolean isValidTeeth(String toothString) {

        int index;
        char tooth;

//----An empty string has no teeth
        if (toothString.isEmpty()) {
            return(false);
        }

//----Check each character in turn
        for (index = 0; index < toothString.length(); index++) {
            tooth = toothString.charAt(index);
            if (tooth != INCISOR && tooth != BICUSPID && tooth != MISSING) {
                return(false);
            }
        }

        return(true);
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Gets a menu option, re-asking until it is P, E, R, or X (either case).
     * @return The menu option in upper case
     */
    private static char getMenuOption() {

        char option;

//----Get the option, and re-ask until it is valid
        prompt("(P)rint, (E)xtract, (R)oot, e(X)it");
        option = Character.toUpperCase(keyboard.next().charAt(0));
        while (option != 'P' && option != 'E' && option != 'R' && option != 'X') {
            prompt("Invalid menu option, try again");
            option = Character.toUpperCase(keyboard.next().charAt(0));
        }

        return(option);
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Prints every family member's name, uppers, and lowers.
     * @param names The family members' names
     * @param teeth The family's teeth
     */
    private static void printFamilyTeeth(String[] names,char[][][] teeth) {

        int person;

        for (person = 0; person < names.length; person++) {
            System.out.println(names[person]);
            printTeethRow("Uppers",teeth[person][UPPER]);
            printTeethRow("Lowers",teeth[person][LOWER]);
        }
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Prints one layer of teeth, numbering each tooth from 1.
     * @param label The layer's label, "Uppers" or "Lowers"
     * @param row The layer of teeth
     */
    private static void printTeethRow(String label,char[] row) {

        int tooth;

        System.out.print("  " + label + ":");
        for (tooth = 0; tooth < row.length; tooth++) {
            System.out.print("  " + (tooth + 1) + ":" + row[tooth]);
        }
        System.out.println();
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Extracts a tooth by marking it missing. The family member, layer, and tooth number are
     * re-asked until valid, and the tooth must not already be missing.
     * @param names The family members' names
     * @param teeth The family's teeth, updated with the extraction
     */
    private static void extractTooth(String[] names,char[][][] teeth) {

        int person;
        char layer;
        char[] row;
        int toothNumber;

//----Get the family member, and re-ask until they are found
        prompt("Which family member");
        person = findFamilyMember(names,keyboard.next());
        while (person < 0) {
            prompt("Invalid family member, try again");
            person = findFamilyMember(names,keyboard.next());
        }

//----Get the layer, and re-ask until it is valid
        prompt("Which tooth layer (U)pper or (L)ower");
        layer = Character.toUpperCase(keyboard.next().charAt(0));
        while (layer != 'U' && layer != 'L') {
            prompt("Invalid layer, try again");
            layer = Character.toUpperCase(keyboard.next().charAt(0));
        }
        row = teeth[person][layer == 'U' ? UPPER : LOWER];

//----Get the tooth number, and re-ask until it is a tooth that is not missing
        prompt("Which tooth number");
        toothNumber = keyboard.nextInt();
        while (toothNumber < 1 || toothNumber > row.length || row[toothNumber - 1] == MISSING) {
            if (toothNumber < 1 || toothNumber > row.length) {
                prompt("Invalid tooth number, try again");
            } else {
                prompt("Missing tooth, try again");
            }
            toothNumber = keyboard.nextInt();
        }

//----Extract the tooth
        row[toothNumber - 1] = MISSING;
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Finds a family member by name, ignoring case.
     * @param names The family members' names
     * @param name The name to look for
     * @return The family member's index, or -1 if there is no such member
     */
    private static int findFamilyMember(String[] names,String name) {

        int person;

        for (person = 0; person < names.length; person++) {
            if (names[person].equalsIgnoreCase(name)) {
                return(person);
            }
        }

        return(-1);
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Reports the family's root canal indices, the roots of Ix<sup>2</sup> + Bx - M, where I, B,
     * and M are the family's numbers of incisors, bicuspids, and missing teeth. The discriminant
     * B<sup>2</sup> + 4IM is never negative, so the roots are always real.
     * @param teeth The family's teeth
     */
    private static void reportRootCanals(char[][][] teeth) {

        int incisors,bicuspids,missing;
        double discriminant;

//----Count each type of tooth in the family
        incisors = countToothType(teeth,INCISOR);
        bicuspids = countToothType(teeth,BICUSPID);
        missing = countToothType(teeth,MISSING);

//----Compute and display the roots of Ix^2 + Bx - M
        if (incisors == 0) {
            if (bicuspids == 0) {
                System.out.println("No root canals, there are no incisors or bicuspids");
            } else {
                System.out.printf("One root canal at     %.2f\n",(double) missing / bicuspids);
            }
        } else {
            discriminant = Math.pow(bicuspids,2) + 4.0 * incisors * missing;
            System.out.printf("One root canal at     %.2f\n",
                    (-bicuspids + Math.sqrt(discriminant)) / (2.0 * incisors));
            System.out.printf("Another root canal at %.2f\n",
                    (-bicuspids - Math.sqrt(discriminant)) / (2.0 * incisors));
        }
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Counts how many of the family's teeth are of one type.
     * @param teeth The family's teeth
     * @param type The tooth type to count
     * @return The number of teeth of that type
     */
    private static int countToothType(char[][][] teeth,char type) {

        int person,layer,tooth;
        int count;

        count = 0;
        for (person = 0; person < teeth.length; person++) {
            for (layer = 0; layer < teeth[person].length; layer++) {
                for (tooth = 0; tooth < teeth[person][layer].length; tooth++) {
                    if (teeth[person][layer][tooth] == type) {
                        count++;
                    }
                }
            }
        }

        return(count);
    }
//-------------------------------------------------------------------------------------------------
}
//=================================================================================================
