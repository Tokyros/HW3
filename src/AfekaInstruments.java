import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class AfekaInstruments {

    private static final String MENU_STRING = "-------------------------------------------------------------------------\n" +
            "AFEKA MUSICAL INSTRUMENT INVENTORY MENU\n" +
            "-------------------------------------------------------------------------\n" +
            "1. Copy All String Instruments To Inventory\n" +
            "2. Copy All Wind Instruments To Inventory\n" +
            "3. Sort Instruments By Brand And Price\n" +
            "4. Search Instrument By Brand And Price\n" +
            "5. Delete Instrument\n" +
            "6. Delete all Instruments\n" +
            "7. Print Inventory Instruments\n" +
            "Choose your option or any other key to EXIT";

    public static void main(String[] args) {
        ArrayList<MusicalInstrument> allInstruments = new ArrayList<>();
        File file = getInstrumentsFileFromUser();

        loadInstrumentsFromFile(file, allInstruments);

        if(allInstruments.size() == 0) {
            System.out.println("There are no instruments in the store currently");
            return;
        }

        printInstruments(allInstruments);

        int different = getNumOfDifferentElements(allInstruments);

        System.out.println("\n\nDifferent Instruments: " + different);

        MusicalInstrument mostExpensive = getMostExpensiveInstrument(allInstruments);

        System.out.println("\n\nMost Expensive Instrument:\n" + mostExpensive);

        startInventoryMenu(allInstruments);
    }

    public static void startInventoryMenu(ArrayList<? extends MusicalInstrument> allInstruments){
        Scanner scanner = new Scanner(System.in);
        AfekaInventory<MusicalInstrument> afekaInventory = new AfekaInventory<>();
        while (true){
            System.out.println(MENU_STRING);
            if (!scanner.hasNextInt()) return;
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    afekaInventory.addAllStringInstruments(allInstruments, afekaInventory.getInstrumentsList());
                    break;
                case 2:
                    afekaInventory.addAllWindInstruments(allInstruments, afekaInventory.getInstrumentsList());
                    break;
                case 3:
                    afekaInventory.sortByBrandAndPrice(afekaInventory.getInstrumentsList());
                    break;
                case 4:
                    searchInstrument(scanner, afekaInventory);
                    break;
                case 5:
                    deleteInstrument(scanner, afekaInventory);
                    break;
                case 6:
                    emptyInventory(scanner, afekaInventory);
                    break;
                case 7:
                    printInventory(afekaInventory);
                    break;
            }
        }

    }

    private static void printInventory(AfekaInventory<MusicalInstrument> afekaInventory) {
        if (afekaInventory.getInstrumentsList().isEmpty()){
            System.out.println("There Is No Instruments To Show");
        } else {
            printInstruments(afekaInventory.getInstrumentsList());
        }
        System.out.println(String.format("Total Price: %4.2f Sorted: %s", afekaInventory.getTotalPrice(), afekaInventory.isSorted()));
    }

    private static void emptyInventory(Scanner scanner, AfekaInventory<MusicalInstrument> afekaInventory) {
        System.out.print("Delete all items, ");
        if (confirm(scanner)) afekaInventory.removeAll(afekaInventory.getInstrumentsList());
    }

    private static boolean confirm(Scanner scanner){
        while (true){
            System.out.println("Are you sure? Y/N");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("Y")){
                return true;
            } else if (answer.equalsIgnoreCase("N")){
                return false;
            } else {
                System.out.println("Answer should be Y/N");
            }
        }
    }

    private static void searchInstrument(Scanner scanner, AfekaInventory<MusicalInstrument> afekaInventory) {
        MusicalInstrument instrumentFromSearch = getMusicalInstrumentFromUserInput(scanner, afekaInventory);
        if (instrumentFromSearch != null) System.out.println("Result:\n" + instrumentFromSearch);
    }

    private static void deleteInstrument(Scanner scanner, AfekaInventory<MusicalInstrument> afekaInventory) {
        MusicalInstrument instrumentToDelete = getMusicalInstrumentFromUserInput(scanner, afekaInventory);
        if (instrumentToDelete != null) {
            boolean confirm = confirm(scanner);
            if (confirm){
                boolean success = afekaInventory.removeInstrument(afekaInventory.getInstrumentsList(), instrumentToDelete);
                if (success){
                    System.out.println("Instrument deleted successfully:\n" + instrumentToDelete);
                } else {
                    System.out.println("Could not delete instrument:\n" + instrumentToDelete);
                }
            }
        }
    }

    private static MusicalInstrument getMusicalInstrumentFromUserInput(Scanner scanner, AfekaInventory<MusicalInstrument> afekaInventory) {
        double price;

        System.out.print("Enter price: ");
        if (scanner.hasNextDouble()) price = scanner.nextDouble();
        else price = 0;
        scanner.nextLine();

        System.out.println("Enter brand: ");
        String brand = scanner.nextLine();

        try {
            int index = afekaInventory.binarySearchByBrandAndPrice(afekaInventory.getInstrumentsList(), price, brand);

            if (index == -1) {
                System.out.println("Could not find instrument");
                return null;
            }

            return afekaInventory.getInstrumentsList().get(index);
        } catch (NotSortedException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static File getInstrumentsFileFromUser(){
        boolean stopLoop = true;
        File file;
        Scanner consoleScanner = new Scanner(System.in);

        do {
            System.out.println("Please enter instruments file name / path:");
            //"C:\\Users\\SBK\\Downloads\\תרגיל בית מס 2\\instruments1b.txt";
            String filepath = consoleScanner.nextLine();
            file = new File(filepath);
            stopLoop = file.exists() && file.canRead();

            if(!stopLoop)
                System.out.println("\nFile Error! Please try again\n\n");
        }while (!stopLoop);

        return file;
    }

    public static void loadInstrumentsFromFile(File file, ArrayList allInstruments){
        Scanner scanner = null;

        try {

            scanner = new Scanner(file);

            addAllInstruments(allInstruments ,loadGuitars(scanner));

            addAllInstruments(allInstruments ,loadBassGuitars(scanner));

            addAllInstruments(allInstruments ,loadFlutes(scanner));

            addAllInstruments(allInstruments ,loadSaxophones(scanner));

        }catch (InputMismatchException | IllegalArgumentException ex){
            System.err.println("\n"+ ex.getMessage());
            System.exit(1);
        }catch (FileNotFoundException ex){
            System.err.println("\nFile Error! File was not found");
            System.exit(2);
        } finally {
            scanner.close();
        }
        System.out.println("\nInstruments loaded from file successfully!\n");

    }

    public static ArrayList loadGuitars(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList guitars = new ArrayList(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            guitars.add(new Guitar(scanner));

        return guitars;
    }

    public static ArrayList loadBassGuitars(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList bassGuitars = new ArrayList(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            bassGuitars.add(new Bass(scanner));

        return bassGuitars;
    }

    public static ArrayList loadFlutes(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList flutes = new ArrayList(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            flutes.add(new Flute(scanner));


        return flutes;
    }

    public static ArrayList loadSaxophones(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList saxophones = new ArrayList(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            saxophones.add(new Saxophone(scanner));

        return saxophones;
    }

    public static void addAllInstruments(ArrayList instruments, ArrayList moreInstruments){
        for(int i = 0 ; i < moreInstruments.size() ; i++){
            instruments.add(moreInstruments.get(i));
        }
    }

    public static void printInstruments(ArrayList instruments){
        for(int i = 0 ; i < instruments.size() ; i++)
            System.out.println(instruments.get(i));
    }



    public static int getNumOfDifferentElements(ArrayList instruments){
        int numOfDifferentInstruments;
        ArrayList differentInstruments = new ArrayList();
        System.out.println();

        for(int i = 0 ; i < instruments.size() ; i++){
            if(!differentInstruments.contains((instruments.get(i)))){
                differentInstruments.add(instruments.get(i));
            }
        }

        if(differentInstruments.size() == 1)
            numOfDifferentInstruments = 0;

        else
            numOfDifferentInstruments = differentInstruments.size();


        return numOfDifferentInstruments;
    }

    public static MusicalInstrument getMostExpensiveInstrument(ArrayList instruments){
        double maxPrice = 0;
        MusicalInstrument mostExpensive = (MusicalInstrument) instruments.get(0);

        for(int i = 0 ; i < instruments.size() ; i++){
            MusicalInstrument temp = (MusicalInstrument)instruments.get(i);

            if(temp.getPrice().doubleValue() > maxPrice){
                maxPrice = temp.getPrice().doubleValue();
                mostExpensive = temp;
            }
        }

        return mostExpensive;
    }

}
