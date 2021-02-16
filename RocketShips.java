import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class RocketShips {

    public static class Item {
        public String name;
        public int weight;
    }

    // public interface SpaceShip {

    // public boolean launchSuccess();

    // public boolean landSuccess();

    // public boolean canCarry();

    // public int weight();
    // }

    public static class Rocket /* implements SpaceShip */ {

        public boolean launchSucess(boolean launchSuccess) {
            if (launchSuccess) {
                return true;
            } else {
                return false;
            }
        }

        public boolean landSucess(boolean hasLanded) {
            if (hasLanded) {
                return true;
            } else {
                return false;
            }
        }

        public boolean canCarry(Item item, double rocketWeight, double rocketMaxWeight) {
            if (item.weight + rocketWeight <= rocketMaxWeight) {
                return true;
            } else {
                return false;
            }
        }

        public U1 loadItemToU1(Item item, U1 rocket) {
            rocket.weight += item.weight;
            return rocket;
        }

        public U2 loadItemToU2(Item item, U2 rocket) {
            rocket.weight += item.weight;
            return rocket;
        }
    }

    public static class U1 extends Rocket {

        int cost = 100000000;
        double weight = 10000D;
        double maxWeight = 18000D;

        public boolean launchSuccess() {

            double chance = (Math.random() * 100) + 1;

            System.out.println("Launch success number: " + chance);

            if (chance <= 5 * (weight / maxWeight)) {
                return false;
            } else {
                return true;
            }
        }

        public boolean landSuccess() {
            double chance = (Math.random() * 100) + 1;

            System.out.println("Landing success number: " + chance);

            if (chance <= weight / maxWeight) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static class U2 extends Rocket {

        int cost = 120000000;
        double weight = 18000D;
        double maxWeight = 29000D;

        public boolean launchSuccess() {

            double chance = (Math.random() * 100) + 1;

            System.out.println("Launch success number: " + chance);

            if (chance <= 4 * (weight / maxWeight)) {
                return false;
            } else {
                return true;
            }
        }

        public boolean landSuccess() {
            double chance = (Math.random() * 100) + 1;

            System.out.println("Landing success number: " + chance);

            if (chance <= 8 * weight / maxWeight) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static class Simulation {
        public static ArrayList<Item> loaditems() throws Exception {
            File phase1 = new File("Phase-1.txt");
            File phase2 = new File("C:/Users/joelc/Documents/COMP 1501/Phase-2.txt");
            Scanner phase1Scanner = new Scanner(phase1);
            Scanner phase2Scanner = new Scanner(phase2);
            ArrayList<Item> itemList = new ArrayList<Item>();
            String item;

            while (phase1Scanner.hasNextLine()) {
                item = phase1Scanner.nextLine();
                String[] itemParts = item.split("=");
                Item itemObject = new Item();
                itemObject.name = itemParts[0];
                itemObject.weight = Integer.parseInt(itemParts[1]);
                itemList.add(itemObject);
            }
            while (phase2Scanner.hasNextLine()) {
                item = phase2Scanner.nextLine();
                String[] itemParts = item.split("=");
                Item itemObject = new Item();
                itemObject.name = itemParts[0];
                itemObject.weight = Integer.parseInt(itemParts[1]);
                itemList.add(itemObject);
            }
            // System.out.println(itemList.get(0).weight);
            return itemList;
        }

        public static ArrayList<U1> loadU1(ArrayList<Item> warehouse) {
            ArrayList<U1> u1Rockets = new ArrayList<U1>();
            ArrayList<Item> inventory = new ArrayList<Item>();

            while (!warehouse.isEmpty()) {
                U1 rocket = new U1();

                for (int i = 0; i < warehouse.size(); i++) {
                    if (rocket.canCarry(warehouse.get(i), rocket.weight, rocket.maxWeight)) {
                        System.out.println("Rocket has room. Loading " + warehouse.get(i).name + " with weight "
                                + warehouse.get(i).weight);
                        rocket = rocket.loadItemToU1(warehouse.get(i), rocket);
                        System.out.println("New weight: " + rocket.weight);
                        inventory.add(warehouse.get(i));
                        if (rocket.weight == rocket.maxWeight) {
                            System.out.println("ROCKET FULL! Loading new rocket.");
                            break;
                        }
                    } else {
                        System.out.println("No room for item.");
                        if (i == warehouse.size() - 1) {
                            System.out.println(
                                    "No items remaining in warehouse can fit on this rocket. Loading new rocket.");
                        }

                    }
                }
                warehouse.removeAll(inventory);
                u1Rockets.add(rocket);
            }
            System.out.println("Number of U1 rockets loaded: " + u1Rockets.size());
            return u1Rockets;
        }

        public static ArrayList<U2> loadU2(ArrayList<Item> warehouse) {
            ArrayList<U2> u2Rockets = new ArrayList<U2>();
            ArrayList<Item> inventory = new ArrayList<Item>();

            while (!warehouse.isEmpty()) {
                U2 rocket = new U2();

                for (int i = 0; i < warehouse.size(); i++) {
                    if (rocket.canCarry(warehouse.get(i), rocket.weight, rocket.maxWeight)) {
                        System.out.println("Rocket has room. Loading " + warehouse.get(i).name + " with weight "
                                + warehouse.get(i).weight);
                        rocket = rocket.loadItemToU2(warehouse.get(i), rocket);
                        System.out.println("New weight: " + rocket.weight);
                        inventory.add(warehouse.get(i));
                        if (rocket.weight == rocket.maxWeight) {
                            System.out.println("ROCKET FULL! Loading new rocket.");
                            break;
                        }
                    } else {
                        System.out.println("No room for item.");
                        if (i == warehouse.size() - 1) {
                            System.out.println(
                                    "No items remaining in warehouse can fit on this rocket. Loading new rocket.");
                        }

                    }
                }
                warehouse.removeAll(inventory);
                u2Rockets.add(rocket);
            }
            System.out.println("Number of U2 rockets loaded: " + u2Rockets.size());
            return u2Rockets;
        }

        public static int runSimulation1(ArrayList<U1> rockets) {
            int totalCost = 0;
            boolean launch = false;
            boolean land = false;
            int launchCount = 0;
            int launchFails = 0;
            int landCount = 0;
            int landFails = 0;

            while (!rockets.isEmpty()) {
                U1 rocket = rockets.get(0);
                launch = rocket.launchSuccess();
                land = rocket.landSuccess();
                if (launch) {
                    System.out.println("Launch success.");
                    launchCount += 1;
                } else {
                    System.out.println("Launch failure.");
                    launchFails += 1;
                }
                if (land) {
                    System.out.println("Land success.");
                    landCount += 1;
                } else {
                    System.out.println("Land failure.");
                    landFails += 1;
                }
                if (launch && land) {
                    rockets.remove(rocket);
                    launch = false;
                    land = false;
                    totalCost += rocket.cost;
                } else {
                    launch = false;
                    land = false;
                    totalCost += rocket.cost;
                }
            }

            System.out.println("Launch successes: " + launchCount);
            System.out.println("Launch failures: " + launchFails);
            System.out.println("Landing successes: " + landCount);
            System.out.println("Landing failures: " + landFails);

            return totalCost;
        }

        public static int runSimulation2(ArrayList<U2> rockets) {
            int totalCost = 0;
            boolean launch = false;
            boolean land = false;
            int launchCount = 0;
            int launchFails = 0;
            int landCount = 0;
            int landFails = 0;

            while (!rockets.isEmpty()) {
                U2 rocket = rockets.get(0);
                launch = rocket.launchSuccess();
                land = rocket.landSuccess();
                if (launch) {
                    System.out.println("Launch success.");
                    launchCount += 1;
                } else {
                    System.out.println("Launch failure.");
                    launchFails += 1;
                }
                if (land) {
                    System.out.println("Land success.");
                    landCount += 1;
                } else {
                    System.out.println("Land failure.");
                    landFails += 1;
                }
                if (launch && land) {
                    rockets.remove(rocket);
                    launch = false;
                    land = false;
                    totalCost += rocket.cost;
                } else {
                    launch = false;
                    land = false;
                    totalCost += rocket.cost;
                }
            }

            System.out.println("Launch successes: " + launchCount);
            System.out.println("Launch failures: " + launchFails);
            System.out.println("Landing successes: " + landCount);
            System.out.println("Landing failures: " + landFails);

            return totalCost;
        }
    }

    public static void main(String[] args) throws Exception {
        // U1 artemis = new U1();
        // U2 apollo = new U2();

        // if (artemis.launchSuccess()) {
        // System.out.println("Artemis Launch Success!");
        // } else {
        // System.out.println("Artemis Launch Failure. Rocket exploded.");
        // }

        // if (artemis.landSuccess()) {
        // System.out.println("Artemis Landing Success!");
        // } else {
        // System.out.println("Artemis Lander Crashed!");
        // }

        // if (apollo.launchSuccess()) {
        // System.out.println("Apollo Launch Success!");
        // } else {
        // System.out.println("Apollo Launch Failure. Rocket exploded.");
        // }

        // if (apollo.landSuccess()) {
        // System.out.println("Apollo Landing Success!");
        // } else {
        // System.out.println("Apollo Lander Crashed!");
        // }

        ArrayList<Item> warehouse = Simulation.loaditems();
        System.out.println(warehouse.size());
        ArrayList<U1> u1Rockets = Simulation.loadU1(warehouse);
        System.out.println("U1 Rockets loaded: " + u1Rockets.size());
        warehouse = Simulation.loaditems();
        ArrayList<U2> u2Rockets = Simulation.loadU2(warehouse);
        System.out.println("U2 Rockets loaded: " + u2Rockets.size());

        System.out.println("Total cost of U1 Rockets: " + Simulation.runSimulation1(u1Rockets));
        System.out.println("Total cost of U2 Rockets: " + Simulation.runSimulation2(u2Rockets));

    }
}
