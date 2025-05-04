package CoffeeVendingMachine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CoffeeMachine machine = new CoffeeMachine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            machine.displayMenu();

            System.out.print("Enter the coffee you want (or 'exit' to quit): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("exit")) {
                System.out.println("Thank you! Goodbye!");
                break;
            }

            Coffee selected = null;
            for (Coffee coffee : machine.coffeeMenu) {
                if (coffee.getName().equalsIgnoreCase(choice)) {
                    selected = coffee;
                    break;
                }
            }

            if (selected == null) {
                System.out.println("Invalid coffee selection. Please try again.");
                continue;
            }

            System.out.print("Enter payment amount: ");
            Double amount;
            try {
                amount = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
                continue;
            }

            machine.makePayment(selected, amount);
        }
        scanner.close();
    }
}
