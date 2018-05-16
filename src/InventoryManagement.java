import java.util.ArrayList;

public interface InventoryManagement {
    void addAllStringInstruments(ArrayList<? extends MusicalInstrument> arrFrom, ArrayList<? super MusicalInstrument> arrayTo);

    void addAllWindInstruments(ArrayList<? extends MusicalInstrument> arrFrom, ArrayList<? super MusicalInstrument> arrayTo);

    void sortByBrandAndPrice(ArrayList<? extends MusicalInstrument> arrToSort);

    int binarySearchByBrandAndPrice(ArrayList<? extends MusicalInstrument> sortedArrToSearch, Number price, String brand);

    void addInstrument(ArrayList<? super MusicalInstrument> arrToAddTo, MusicalInstrument musicalInstrument);

    boolean removeInstrument(ArrayList<? extends MusicalInstrument> arrToRemoveFrom, MusicalInstrument instrumentToRemove);

    boolean removeAll(ArrayList<? extends MusicalInstrument> arrToRemoveFrom);
}
