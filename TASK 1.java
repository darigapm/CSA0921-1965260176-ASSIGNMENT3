import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

public class ATMWithdrawal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double balance = 10000.00;
        File cardFile = new File("card_reader.txt");

        try {
            if (!cardFile.exists()) {
                throw new FileNotFoundException("Card-reader configuration file missing.");
            }
            System.out.print("Enter Account Number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter Withdrawal Amount: ");
            double amount = scanner.nextDouble();

            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
            }
            if (amount > balance) {
                throw new InsufficientBalanceException("Requested amount exceeds balance of Rs." + balance);
            }

            balance -= amount;
            System.out.println("Dispensing Rs." + amount + " for Account " + accountNumber);
            System.out.println("Remaining Balance: Rs." + balance);
        } catch (FileNotFoundException e) {
            System.out.println("[Hardware Error] " + e.getMessage());
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("[Input Error] Invalid entry: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("[Transaction Declined] " + e.getMessage());
        } finally {
            System.out.println("Session closed. Releasing ATM resources and ejecting card.");
            scanner.close();
        }
    }
}
