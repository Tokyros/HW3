import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.*;

import java.util.ArrayList;


public class InventoryDashboard extends BorderPane {
    private final InstrumentDetailsGrid instrumentDetailsGrid = new InstrumentDetailsGrid();
    private ArrayList<MusicalInstrument> instrumentsToShow = new ArrayList<>();
    private ArrayList<MusicalInstrument> allInstruments;
    private AfekaInventory<MusicalInstrument> inventoryManager = new AfekaInventory<>();
    private int currentInstrumentIndex;
    private SimpleStringProperty searchQuery = new SimpleStringProperty();

    public InventoryDashboard(ArrayList<MusicalInstrument> instruments){
        this.allInstruments = instruments;

        setTop(new SearchBar(e -> onSearch(), searchQuery));

        setLeft(new NavigationButtonPane("<", event -> choosePreviousInstrument()));

        setRight(new NavigationButtonPane(">", event -> chooseNextInstrument()));

        setCenter(instrumentDetailsGrid);

        setBottom(new ActionButtonsPane(event -> openAddPanel(), event -> deleteCurrentInstrument(), event -> clearInstruments()));

        filterInstruments();
    }

    private void onSearch() {
        filterInstruments();
    }

    public void deleteCurrentInstrument() {
        if (allInstruments.isEmpty()) return;
        inventoryManager.removeInstrument(allInstruments, getCurrentInstrument());
        filterInstruments();
    }

    private MusicalInstrument getCurrentInstrument() {
        return instrumentsToShow.get(currentInstrumentIndex);
    }

    private void clearInstruments() {
        if (allInstruments.isEmpty()) return;
        inventoryManager.removeAll(allInstruments);
        filterInstruments();
    }

    public void openAddPanel() {
        AddInstrumentStage addInstrumentStage = new AddInstrumentStage();
        addInstrumentStage.setOnAddEvent(ev -> addInstrument(addInstrumentStage));
    }

    public void chooseNextInstrument() {
        if (instrumentsToShow.isEmpty()) return;
        int newIndex = currentInstrumentIndex < instrumentsToShow.size() - 1 ? ++currentInstrumentIndex : (currentInstrumentIndex = 0);
        switchInstrument(newIndex);
    }

    public void choosePreviousInstrument() {
        if (instrumentsToShow.isEmpty()) return;
        int newIndex = currentInstrumentIndex > 0 ? --currentInstrumentIndex : (currentInstrumentIndex = instrumentsToShow.size() - 1);
        switchInstrument(newIndex);
    }

    private void selectFirstInstrument() {
        this.currentInstrumentIndex = 0;
        switchInstrument(currentInstrumentIndex);
    }

    public void filterInstruments() {
        instrumentsToShow.clear();
        if (searchQuery.get() == null) {
            instrumentsToShow.addAll(allInstruments);
            return;
        }
        String lowerCaseQuery = searchQuery.get().toLowerCase();
        for (MusicalInstrument musicalInstrument : this.allInstruments) {
            if (musicalInstrument.toString().toLowerCase().contains(lowerCaseQuery)) this.instrumentsToShow.add(musicalInstrument);
        }

        if (instrumentsToShow.isEmpty()) clearFields();
        else selectFirstInstrument();
    }

    private void switchInstrument(int index) {
        showInstrument(this.instrumentsToShow.get(index));
    }

    private void clearFields() {
        instrumentDetailsGrid.clearFields();
    }

    private void showInstrument(MusicalInstrument instrumentToShow) {
        instrumentDetailsGrid.setDetails(instrumentToShow.getBrand(), instrumentToShow.getClass().getSimpleName(), instrumentToShow.getPrice());
    }

    private void addInstrument(AddInstrumentStage addInstrumentStage){
        MusicalInstrument newInstrument = addInstrumentStage.getNewInstrument();
        if (newInstrument == null) return;
        inventoryManager.addInstrument(allInstruments, newInstrument);
        filterInstruments();
    }
}
