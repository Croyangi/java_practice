import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class bankingSystemAdv {

    //This program is incomplete.

    static boolean systemOn = true;
    static boolean userSigned = false;
    static int numInput;
    static String userInput;
    static String username;
    static String password;
    static ArrayList<String> usernameDatabase = new ArrayList<String>();
    static ArrayList<String> passwordDatabase = new ArrayList<String>();

    public static void printMenu() {
        System.out.println("Welcome, what do you want to do?");
        System.out.println("1: Sign Up");
        System.out.println("2: Log In");
        System.out.print(">>> ");
    }

    public static void menuSelect() {
        //User Menu

        Scanner in = new Scanner(System.in);
        while (!(userSigned)) {

            printMenu();

            try {
                numInput = in.nextInt();
                if (!(numInput > 0 && numInput <= 2)) {
                    System.out.println("Invalid Input, please try again.\n");
                    continue;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid Input, please try again.\n");
                in.nextLine();
                continue;
            }
            userSigned = true;
        }

        switch (numInput) {
            case 1:
            System.out.println("\n\n\n\n\n");
                signUp();
                break;
            case 2:
                System.out.println("Logging in");
                break;
            }
    }

    public static void signUp() {
        boolean compliedUsername = false;
        boolean compliedPassword = false;
        Scanner in = new Scanner(System.in);

        //Accepts Usernames that comply to rules
        while (!(compliedUsername)) {

            System.out.println("Please enter the Username you will log in with.");
            System.out.print(">>> ");

            userInput = in.nextLine();
            int lengthInput = userInput.length();

            if (!(lengthInput > 0 && lengthInput <= 10)) {
                System.out.println("Maximum length username is 10.\n");
                continue;
            }
            compliedUsername = true;
            username = userInput;
        }

        //Accepts Password that comply to rules
        while (!(compliedPassword)) {

            System.out.println("Please enter the Password you will log in with.");
            System.out.print(">>> ");

            userInput = in.nextLine();
            int lengthInput = userInput.length();

            if (!(lengthInput > 0 && lengthInput <= 10)) {
                System.out.println("Maximum length Password is 10.\n");
                continue;
            }
            compliedPassword = true;
            password = userInput;
        }

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        usernameDatabase.add(username);
        passwordDatabase.add(password);
    }


    public static void main(String[] args) {



        menuSelect();
        menuSelect();

        System.out.println(usernameDatabase);
        System.out.println(passwordDatabase);





        
    }
}
