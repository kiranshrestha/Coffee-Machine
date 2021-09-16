package machine;

import java.util.Scanner;

public class CoffeeMachine {


    int totalWater = 400, totalMilk = 540, totalCoffeeBean = 120, totalCups, amountCollected = 550, disposableCups = 9;
    final int requiredWater = 200, requiredMilk = 50, requiredCoffeeBean = 15;
    Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        //step1();
        //step2();
        step3();
    }

    private static void step3() {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.actOnCoffeeMachine();
    }

    private void actOnCoffeeMachine() {
        boolean askAgain = true;
        while (askAgain) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String act = s.nextLine();
            switch (act) {
                case "buy": {
                    buyCoffee();
                    break;
                }
                case "fill": {
                    fillCoffeeMachine();
                    break;
                }
                case "take": {
                    System.out.printf("I gave you $%d\n", amountCollected);
                    amountCollected = 0;
                    break;
                }
                case "remaining": {
                    showCoffeeMachineStatus();
                    break;
                }
                case "exit": {
                    askAgain = false;
                }
            }
        }
    }

    private void fillCoffeeMachine() {
        System.out.println("Write how many ml of water you want to add:");
        totalWater += s.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        totalMilk += s.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        totalCoffeeBean += s.nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add:");
        disposableCups += s.nextInt();
    }

    private void buyCoffee() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        if(s.hasNextInt()){
            int coffeeType = s.nextInt();
            Coffee coffee;
            switch (coffeeType){
                case 1: coffee = new EspressoCoffee();
                    break;
                case 2: coffee = new LatteCoffee();
                    break;
                case 3: coffee = new CappuccinoCoffee();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + coffeeType);
            }
            if(canMakeCoffee(coffee))
            {
                totalWater -= coffee.requiredWater;
                totalMilk -= coffee.requiredMilk;
                totalCoffeeBean -= coffee.requiredCoffeeBean;
                amountCollected += coffee.cost;
                disposableCups--;
                System.out.println("I have enough resources, making you a coffee!");
            }
        }else {
            s.nextLine();
        }

    }

    private boolean canMakeCoffee(Coffee coffee) {
         if(totalWater < coffee.requiredWater) {
             System.out.println("Sorry, not enough water!");
             return false;
         }
         if(totalMilk < coffee.requiredMilk) {
             System.out.println("Sorry, not enough milk!");
             return false;
         }
         if(totalCoffeeBean < coffee.requiredCoffeeBean){
             System.out.println("Sorry, not enough coffee bean!");
             return false;
         }
         if(disposableCups == 0){
             System.out.println("Sorry, not enough disposable cups!");
             return false;
         }
         return true;
    }

    private void step2() {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        System.out.println("Write how many ml of water the coffee machine has:");
        coffeeMachine.totalWater = s.nextInt();
        System.out.println("Write how many ml of milk the coffee machine has:");
        coffeeMachine.totalMilk = s.nextInt();
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        coffeeMachine.totalCoffeeBean = s.nextInt();

        System.out.println("Write how many cups of coffee you will need:");
        coffeeMachine.totalCups = s.nextInt();

        coffeeMachine.requestForCupsOfCoffees();
    }

    private void step1() {
        System.out.println("Write how many cups of coffee you will need:");
        int cups = s.nextInt();
        int water = cups * 200, milk = cups * 50, coffeeBeans = cups * 15;

        System.out.printf("For %d cups of coffee you will need:\n" +
                        "%d ml of water\n" +
                        "%d ml of milk\n" +
                        "%d g of coffee beans",
                cups, water, milk , coffeeBeans);
    }

    private void requestForCupsOfCoffees() {
        int coffeeCount = 0;
        while (canMakeMoreCoffee()){
            coffeeCount++;
        }

        if(coffeeCount == totalCups){
            System.out.println("Yes, I can make that amount of coffee");
        }else if(coffeeCount > totalCups){
            final int more = coffeeCount - totalCups;
            System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)",more);
        }else {
            System.out.printf("No, I can make only %d cup(s) of coffee\n", coffeeCount);
        }

    }

    private boolean canMakeMoreCoffee(){
         if(totalWater >= requiredWater && totalMilk >= requiredMilk && totalCoffeeBean >= requiredCoffeeBean){
             totalWater-=requiredWater;
             totalMilk-=requiredMilk;
             totalCoffeeBean-=requiredCoffeeBean;
             return true;
         }else {
             return false;
         }
    }

    private void showCoffeeMachineStatus(){
        System.out.printf("The coffee machine has:\n" +
                "%d ml of water\n" +
                "%d ml of milk\n" +
                "%d g of coffee beans\n" +
                "%d disposable cups\n" +
                "$%d of money\n\n", totalWater, totalMilk, totalCoffeeBean, disposableCups, amountCollected);
    }

}
class Coffee {
    String name;
    int requiredWater, requiredMilk, requiredCoffeeBean, cost;
}

class EspressoCoffee extends Coffee {
    public EspressoCoffee() {
        name = "EspressoCoffee";
        requiredWater = 250;
        requiredCoffeeBean = 16;
        cost = 4;
    }
}

class LatteCoffee extends Coffee {
    public LatteCoffee() {
        name = "Latte";
        requiredWater = 350;
        requiredMilk = 75;
        requiredCoffeeBean = 20;
        cost = 7;
    }
}

class CappuccinoCoffee extends Coffee {
    public CappuccinoCoffee() {
        name = "Cappuccino";
        requiredWater = 200;
        requiredMilk = 100;
        requiredCoffeeBean = 12;
        cost = 6;
    }
}
