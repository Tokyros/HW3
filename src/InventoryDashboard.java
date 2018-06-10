import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.InputMismatchException;


/**
 * Created by ps3to_000 on 30-May-18.
 */
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

    private void deleteCurrentInstrument() {
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

    private void openAddPanel() {
        AddInstrumentPanel addInstrumentPanel = new AddInstrumentPanel();
        addInstrumentPanel.setOnAddEvent(ev -> addInstrument(addInstrumentPanel));
    }

    private void chooseNextInstrument() {
        if (instrumentsToShow.isEmpty()) return;
        int newIndex = currentInstrumentIndex < instrumentsToShow.size() - 1 ? ++currentInstrumentIndex : (currentInstrumentIndex = 0);
        switchInstrument(newIndex);
    }

    private void choosePreviousInstrument() {
        if (instrumentsToShow.isEmpty()) return;
        int newIndex = currentInstrumentIndex > 0 ? --currentInstrumentIndex : (currentInstrumentIndex = instrumentsToShow.size() - 1);
        switchInstrument(newIndex);
    }

    private void selectFirstInstrument() {
        this.currentInstrumentIndex = 0;
        switchInstrument(currentInstrumentIndex);
    }

    private void filterInstruments() {
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

    private void addInstrument(AddInstrumentPanel addInstrumentPanel){
        MusicalInstrument newInstrument = addInstrumentPanel.getNewInstrument();
        if (newInstrument == null) return;
        inventoryManager.addInstrument(allInstruments, newInstrument);
        filterInstruments();
    }
}
