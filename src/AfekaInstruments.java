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
        Scanner consoleScanner = new Scanner(System.in);
        File file = getInstrumentsFileFromUser(consoleScanner);

        loadInstrumentsFromFile(file, allInstruments);

        if(allInstruments.size() == 0) {
            System.out.println("There are no instruments in the store currently");
            consoleScanner.close();
            return;
        }

        printInstruments(allInstruments);

        int different = getNumOfDifferentElements(allInstruments);

        System.out.println("\n\nDifferent Instruments: " + different);

        MusicalInstrument mostExpensive = getMostExpensiveInstrument(allInstruments);

        System.out.println("\n\nMost Expensive Instrument:\n" + mostExpensive);

        startInventoryMenu(allInstruments);
        consoleScanner.close();
    }

    private static void startInventoryMenu(ArrayList<? extends MusicalInstrument> allInstruments){
        Scanner scanner = new Scanner(System.in);
        AfekaInventory afekaInventory = new AfekaInventory();
        while (true){
            System.out.println(MENU_STRING);
            if (!scanner.hasNextInt()) {
                scanner.close();
                return;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
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
                default:
                    scanner.close();
                    return;
            }
        }

    }

    private static void printInventory(AfekaInventory afekaInventory) {
        if (afekaInventory.getInstrumentsList().isEmpty()){
            System.out.println("There Is No Instruments To Show");
        } else {
            printInstruments(afekaInventory.getInstrumentsList());
        }
        System.out.println(String.format("Total Price: %4.2f Sorted: %s", afekaInventory.getTotalPrice(), afekaInventory.isSorted()));
    }

    private static void emptyInventory(Scanner scanner, AfekaInventory afekaInventory) {
        System.out.println("DELETE ALL INSTRUMENTS:");
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

    private static void searchInstrument(Scanner scanner, AfekaInventory afekaInventory) {
        System.out.println("SEARCH INSTRUMENT");
        getMusicalInstrumentFromUserInput(scanner, afekaInventory);
    }

    private static void deleteInstrument(Scanner scanner, AfekaInventory afekaInventory) {
        System.out.println("DELETE INSTRUMENT");
        MusicalInstrument instrumentToDelete = getMusicalInstrumentFromUserInput(scanner, afekaInventory);
        if (instrumentToDelete != null) {
            if (confirm(scanner)){
                boolean success = afekaInventory.removeInstrument(afekaInventory.getInstrumentsList(), instrumentToDelete);
                if (success){
                    System.out.println("Instrument deleted successfully!");
                } else {
                    System.out.println("Could not delete instrument!");
                }
            }
        }
    }

    private static MusicalInstrument getMusicalInstrumentFromUserInput(Scanner scanner, AfekaInventory afekaInventory) {
        double price;

        System.out.println("Enter brand: ");
        String brand = scanner.nextLine();

        System.out.print("Enter price: ");
        while (!scanner.hasNextDouble()){
            System.out.println("Price must be a number!");
            scanner.nextLine();
        }
        price = scanner.nextDouble();
        scanner.nextLine();

        try {
            int index = afekaInventory.binarySearchByBrandAndPrice(afekaInventory.getInstrumentsList(), price, brand);

            if (index == -1) {
                System.out.println("Instrument Not Found!");
                return null;
            }

            MusicalInstrument musicalInstrument = afekaInventory.getInstrumentsList().get(index);
            System.out.println("RESULT:\n" + musicalInstrument.toString());
            return musicalInstrument;
        } catch (NotSortedException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static File getInstrumentsFileFromUser(Scanner consoleScanner){
        boolean stopLoop = true;
        File file;

        do {
            System.out.println("Please enter instruments file name / path:");
            //"C:\\Users\\SBK\\Downloads\\תרגיל בית מס 2\\instruments1b.txt";
            String filepath = "C:\\Users\\ps3to_000\\Desktop\\instruments1b.txt";//consoleScanner.nextLine();
            file = new File(filepath);
            stopLoop = file.exists() && file.canRead();

            if(!stopLoop)
                System.out.println("\nFile Error! Please try again\n\n");
        }while (!stopLoop);

        return file;
    }

    public static void loadInstrumentsFromFile(File file, ArrayList<MusicalInstrument> allInstruments){
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
            if (scanner != null) scanner.close();
        }
        System.out.println("\nInstruments loaded from file successfully!\n");

    }

    public static ArrayList<MusicalInstrument> loadGuitars(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList<MusicalInstrument> guitars = new ArrayList<>(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            guitars.add(new Guitar(scanner));

        return guitars;
    }

    public static ArrayList<MusicalInstrument> loadBassGuitars(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList<MusicalInstrument> bassGuitars = new ArrayList<>(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            bassGuitars.add(new Bass(scanner));

        return bassGuitars;
    }

    public static ArrayList<MusicalInstrument> loadFlutes(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList<MusicalInstrument> flutes = new ArrayList<>(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            flutes.add(new Flute(scanner));


        return flutes;
    }

    public static ArrayList<MusicalInstrument> loadSaxophones(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList<MusicalInstrument> saxophones = new ArrayList<>(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            saxophones.add(new Saxophone(scanner));

        return saxophones;
    }

    public static void addAllInstruments(ArrayList<MusicalInstrument> instruments, ArrayList<MusicalInstrument> moreInstruments){
        for(int i = 0 ; i < moreInstruments.size() ; i++){
            instruments.add(moreInstruments.get(i));
        }
    }

    public static void printInstruments(ArrayList instruments){
        for(int i = 0 ; i < instruments.size() ; i++)
            System.out.println(instruments.get(i));
    }



    public static int getNumOfDifferentElements(ArrayList<MusicalInstrument> instruments){
        int numOfDifferentInstruments;
        ArrayList<MusicalInstrument> differentInstruments = new ArrayList<>();
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
