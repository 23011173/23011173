/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.personalbudgetplanning;

/**
 *
 * @author misha
 */
import java.text.DecimalFormat;
import java.util.Scanner;

// Superclass LoanCalculator
class LoanCalculator {
    // Method to calculate the interest rate on a property
    public double calcInterestRate(double propertyPrice, double interestRate) {
        return (interestRate / 100) * propertyPrice;
    }

    // Method to calculate loan repayment
    public double calcLoanRepayment(double propertyPrice, double totalPropertyDeposit, double interestRate, int numberOfMonths) {
        double loanAmount = propertyPrice - totalPropertyDeposit;
        double monthlyInterestRate = interestRate / 100 / 12;  // Monthly interest rate
        return (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfMonths));
    }
}

// Subclass PersonalBudgetPlanning extends LoanCalculator
public class PersonalBudgetPlanning extends LoanCalculator {

    // Overriding the calcInterestRate method
    @Override
    public double calcInterestRate(double propertyPrice, double interestRate) {
        // Custom behavior: Adding a fixed handling fee of R500 to the interest
        double baseInterest = super.calcInterestRate(propertyPrice, interestRate);
        return baseInterest + 500;  // Add a fixed fee to the calculated interest rate
    }

    // Overriding the calcLoanRepayment method
    @Override
    public double calcLoanRepayment(double propertyPrice, double totalPropertyDeposit, double interestRate, int numberOfMonths) {
        // Custom behavior: Reducing the monthly repayment by 2.5% if the repayment period is more than 300 months
        double baseRepayment = super.calcLoanRepayment(propertyPrice, totalPropertyDeposit, interestRate, numberOfMonths);
        if (numberOfMonths > 300) {
            return baseRepayment * 0.98;  // 2% discount for longer repayment periods
        } else {
            return baseRepayment;
        }
    }

    public static void main(String[] args) {
        PersonalBudgetPlanning budgetPlanner = new PersonalBudgetPlanning();  // Creating an instance of PersonalBudgetPlanning

        String modelAndMake = "";
        double grossMonthlyIncome = 0, propertyPrice, interestRate = 0, totalCarCost = 0;
        double estimatedMonthlyTax = 0, rentalAmount = 0, loanRepayment = 0, totalPropertyDeposit = 0, insurancePremium = 0, carPrice = 0;
        int numberOfMonths = 0;
        char rentalChoice = '0';
        char convetedRentalChoice = '0';
        char convertedCarChoice = '0';

        // Array to hold expense categories and corresponding costs
        String[] estimatedMonthlyExp = {"Groceries", "WaterAndLights", "Travel cost", "CellPhone and Telephone", "Other expenses"};
        double[] costOfExp = new double[5];

        DecimalFormat df = new DecimalFormat("0.00");
        Scanner sc = new Scanner(System.in);

        System.out.print("PLEASE ENTER YOUR GROSS MONTHLY INCOME: R ");
        grossMonthlyIncome = sc.nextDouble();

        System.out.print("PLEASE ENTER YOUR ESTIMATED MONTHLY TAX: R ");
        estimatedMonthlyTax = sc.nextDouble();

        // Loop through each expense type and get the corresponding cost
        for (int i = 0; i < costOfExp.length; i++) {
            System.out.print(estimatedMonthlyExp[i] + "--" + " R ");
            costOfExp[i] = sc.nextDouble();
        }

        // Ask if renting or buying property
        System.out.println("PLEASE ENTER Y IF YOU ARE RENTING ACCOMMODATION");
        System.out.println("PLEASE ENTER B IF YOU ARE BUYING A PROPERTY");
        System.out.print("YOUR CHOICE: ");
        rentalChoice = sc.next().charAt(0);
        convetedRentalChoice = Character.toUpperCase(rentalChoice);

        if (convetedRentalChoice == 'Y') {
            System.out.print("PLEASE ENTER THE AMOUNT FOR YOUR RENT: R ");
            rentalAmount = sc.nextDouble();
        } else if (convetedRentalChoice == 'B') {
            System.out.print("Enter the purchase price of the property: R ");
            propertyPrice = sc.nextDouble();

            System.out.print("Enter the total deposit: R ");
            totalPropertyDeposit = sc.nextDouble();

            System.out.print("Enter the interest rate in percentage (%): ");
            interestRate = sc.nextDouble();

            // Calculate interest rate and loan repayment using overridden methods
            double totInterestRate = budgetPlanner.calcInterestRate(propertyPrice, interestRate);

            System.out.print("Enter the number of months to repay (between 240 and 360): ");
            numberOfMonths = sc.nextInt();

            loanRepayment = budgetPlanner.calcLoanRepayment(propertyPrice, totalPropertyDeposit, interestRate, numberOfMonths);
            System.out.println("YOUR LOAN REPAYMENT IS: R " + df.format(loanRepayment));

            if (loanRepayment > grossMonthlyIncome) {
                System.out.println("The approval of the home loan is unlikely.");
            }
        } else {
            System.out.println("YOUR CHOICE IS INVALID");
        }

        // Ask if the user wants to buy a car
        System.out.println("Enter the letter C if you want to buy a car");
        System.out.print("YOUR CHOICE FOR BUYING CAR: ");
        char carChoice = sc.next().charAt(0);
        convertedCarChoice = Character.toUpperCase(carChoice);

        if (convertedCarChoice == 'C') {
            System.out.print("Enter the Model and Make of your car: ");
            modelAndMake = sc.next();

            System.out.print("Enter the Purchase price for the car: R ");
            propertyPrice = sc.nextDouble();

            System.out.print("Enter the total deposit: R ");
            totalPropertyDeposit = sc.nextDouble();

            System.out.print("Enter the interest rate: R ");
            interestRate = sc.nextDouble();

            System.out.print("Enter the estimated insurance premium: R ");
            insurancePremium = sc.nextDouble();

            // Calculate car loan repayment using overridden method
            carPrice = budgetPlanner.calcLoanRepayment(propertyPrice, totalPropertyDeposit, interestRate, numberOfMonths);
            totalCarCost = carPrice + insurancePremium;
            System.out.println("The total monthly cost for a car is: R " + df.format(totalCarCost));
        }

        // Display total expenses for user
        double totalExpenses = calculateTotalExpenses(costOfExp);
        System.out.println("Your total estimated monthly expenses are: R " + df.format(totalExpenses));
    }

    // Method to calculate total expenses from the array
    public static double calculateTotalExpenses(double[] expenses) {
        double total = 0;
        for (double expense : expenses) {
            total += expense;
        }
        return total;
    }}
