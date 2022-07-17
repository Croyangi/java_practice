import java.util.Scanner;
import java.text.NumberFormat;
import java.util.*;

public class morgageCalculator {

    static double numInput;

    static double getMonthlyRate(double x) {
        x = x / 100 / 12;
        return x;
    }

    static double getNumberOfPayments(double x, double y) {
        x = y * 12;
        return x;
    }

    static double userInputLoop(double x) {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.print(">>> ");

            try {
                numInput = in.nextDouble();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid Input, please try again.\n");
                in.nextLine();
                continue;
            }
            break;
        }
        x = numInput;
        return x;
    }

    public static void main(String[] args) {

        double principal;
        double rate;
        double totalPayments;
        double paymentOwed;

        System.out.println("Please enter Loan amount.");
        principal = userInputLoop(numInput);

        
        System.out.println("Please enter Annual Interest Rate amount.");
        rate = userInputLoop(numInput);
        rate = getMonthlyRate(rate);


        System.out.println("Please enter the Term in Years.");
        totalPayments = userInputLoop(numInput);
        totalPayments = getNumberOfPayments(totalPayments, numInput);

        
        System.out.println(principal);
        System.out.println(rate);
        System.out.println(totalPayments);
        

        paymentOwed = principal * 
            (rate * (Math.pow(1 + rate, totalPayments))) /
            ((Math.pow(1 + rate, totalPayments)) -1);

        System.out.println("\nYour morgage payment is: " + NumberFormat.getCurrencyInstance().format(paymentOwed));

        

    }
}
