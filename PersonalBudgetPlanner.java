import java.util.Scanner;

public class PersonalBudgetPlanner {

    public static void main(String[] args) {
        
     Scanner scanner = new Scanner(System.in);
     
     // User inputs
        System.out.print("Enter your gross monthly income: ");
        double grossIncome = scanner.nextDouble();

        System.out.print("Enter estimated monthly tax deducted: ");
        double taxDeducted = scanner.nextDouble();

     double[] expenditures = new double[5];
        String[] expenseNames = {"Groceries", "Water and Lights", "Travel Costs", "Cell Phone and Telephone", "Other Expenses"};

     for (int i = 0; i < expenditures.length; i++) {
            System.out.print("Enter estimated monthly expenditures for " + expenseNames[i] + ": ");
            expenditures[i] = scanner.nextDouble();
      }
     System.out.println("Choose an option: 1. Rent 2. Buy Property");
        int choice = scanner.nextInt();
        
        Expense expense;
        
        if (choice == 1) {
            System.out.print("Enter monthly rental amount: ");
            double rentalAmount = scanner.nextDouble();
            
            expense = new Expense(rentalAmount) {
                @Override
                public double getAmount() {
                    return rentalAmount;
                }

                @Override
                public String getDescription() {
                    return "Monthly Rent";
                }
     };
            } else if (choice == 2) {
            System.out.print("Enter purchase price of the property: ");
            double purchasePrice = scanner.nextDouble();

            System.out.print("Enter total deposit: ");
            double deposit = scanner.nextDouble();

            System.out.print("Enter interest rate (percentage): ");
            double interestRate = scanner.nextDouble();

            System.out.print("Enter number of months to repay (240 to 360): ");
            int months = scanner.nextInt();

            if (months < 240 || months > 360) {
                System.out.println("Invalid number of months. Please enter a value between 240 and 360.");
                return;
            }

            expense = new HomeLoan(purchasePrice, deposit, interestRate, months);
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        // Calculate available monthly money
        double totalExpenditures = 0;
        for (double expenditure : expenditures) {
            totalExpenditures += expenditure;
        }
        double totalExpenses = totalExpenditures + (expense != expense ? expense.getAmount() : 0);
        double availableMoney = grossIncome - (taxDeducted + totalExpenses);

        // Check home loan approval
        if (choice == 2 && expense.getAmount() > grossIncome / 3) {
            System.out.println("Warning: Approval of the home loan is unlikely.");
        }

        // Display results
        System.out.printf("Available monthly money after all deductions: %.2f\n", availableMoney);
        System.out.printf("Monthly %s: %.2f\n", expense.getDescription(), expense.getAmount());
        scanner.close();
    }
}
    

