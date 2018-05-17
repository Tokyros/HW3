import java.util.ArrayList;
import java.util.Collections;

public class AfekaInventory<T extends MusicalInstrument> implements InventoryManagement {
    private ArrayList<T> instrumentsList = new ArrayList<>();
    private double totalPrice = 0;
    private boolean isSorted = false;

    public ArrayList<T> getInstrumentsList() {
        return instrumentsList;
    }

    public void setInstrumentsList(ArrayList<T> instrumentsList) {
        this.instrumentsList = instrumentsList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public void setSorted(boolean sorted) {
        isSorted = sorted;
    }

    @Override
    public void addAllStringInstruments(ArrayList<? extends MusicalInstrument> arrFrom, ArrayList<? super MusicalInstrument> arrayTo){
        for (MusicalInstrument musicalInstrument : arrFrom) {
            if (musicalInstrument instanceof StringInstrument) addInstrument(arrayTo, musicalInstrument);
        }
        System.out.println("All String Instruments Added Successfully!");
    }

    @Override
    public void addAllWindInstruments(ArrayList<? extends MusicalInstrument> arrFrom, ArrayList<? super MusicalInstrument> arrayTo){
        for (MusicalInstrument musicalInstrument : arrFrom) {
            if (musicalInstrument instanceof WindInstrument) addInstrument(arrayTo, musicalInstrument);
        }
        System.out.println("All Wind Instruments Added Successfully");
    }

    @Override
    public void sortByBrandAndPrice(ArrayList<? extends MusicalInstrument> arrToSort){
        Collections.sort(arrToSort);
        setSorted(true);
        System.out.println("Instruments Sorted Successfully!");
    }

    @Override
    public int binarySearchByBrandAndPrice(ArrayList<? extends MusicalInstrument> sortedArrToSearch, Number price, String brand) throws NotSortedException{
        if (!isSorted()){
            throw new NotSortedException("Could not binary search when inventory is not sorted");
        }
        int l = 0;
        int r = sortedArrToSearch.size() - 1;
        int m;
        while (l <= r){
            m = (r + l)/2;
            int comparison = sortedArrToSearch.get(m).compareTo(brand, price);
            if (comparison == 0){
                return m;
            } else if (comparison < 0){
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }

    @Override
    public void addInstrument(ArrayList<? super MusicalInstrument> arrToAddTo, MusicalInstrument musicalInstrument){
        boolean added = arrToAddTo.add(musicalInstrument);
        if (added){
            setSorted(false);
            addToTotalPrice(musicalInstrument.getPrice());
        }
    }

    @Override
    public boolean removeInstrument(ArrayList<? extends MusicalInstrument> arrToRemoveFrom, MusicalInstrument instrumentToRemove){
        boolean isRemoved = arrToRemoveFrom.remove(instrumentToRemove);
        if (isRemoved) subtractFromTotalPrice(instrumentToRemove.getPrice());
        return isRemoved;
    }

    @Override
    public boolean removeAll(ArrayList<? extends MusicalInstrument> arrToRemoveFrom){
        if (arrToRemoveFrom == null) return false;
        while (!arrToRemoveFrom.isEmpty()) arrToRemoveFrom.remove(0);
        setTotalPrice(0);
        return true;
    }

    public double add(Number num1, Number num2){
        return num1.doubleValue() + num2.doubleValue();
    }

    public void addToTotalPrice(Number num){
        setTotalPrice(totalPrice + num.doubleValue());
    }

    public void subtractFromTotalPrice(Number num){
        setTotalPrice(totalPrice - num.doubleValue());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (T t : getInstrumentsList()) stringBuilder.append(t).append("\n");

        if (getInstrumentsList().isEmpty()) stringBuilder.append("There Is No Instruments To Show");

        stringBuilder.append(String.format("\nTotal Price: %4.2f Sorted: %s", getTotalPrice(), isSorted()));
        return stringBuilder.toString();
    }
}
