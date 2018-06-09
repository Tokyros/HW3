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
        selectFirstInstrument();
    }

    private void onSearch() {
        filterInstruments();
        selectFirstInstrument();
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
        addInstrumentPanel.show();
        addInstrumentPanel.getAddButton().setOnAction(ev -> addInstrument(addInstrumentPanel));
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
        for (MusicalInstrument musicalInstrument : this.allInstruments) {
            if (musicalInstrument.toString().toLowerCase().contains(searchQuery.get().toLowerCase())) this.instrumentsToShow.add(musicalInstrument);
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
        try {
            switch (addInstrumentPanel.getInstrumentType()){
                case "Guitar":
                    Guitar guitar = addGuitar(addInstrumentPanel.getBrand(), addInstrumentPanel.getPrice(), addInstrumentPanel.getNumberOfStrings(), addInstrumentPanel.getGuitarType());
                    inventoryManager.addInstrument(allInstruments, guitar);
                    break;
                case "Flute":
                    Flute flute = addFlute(addInstrumentPanel.getBrand(), addInstrumentPanel.getPrice(), addInstrumentPanel.getMaterial(), addInstrumentPanel.getFluteType());
                    inventoryManager.addInstrument(allInstruments, flute);
                    break;
                case "Saxophone":
                    Saxophone saxophone = addSaxophone(addInstrumentPanel.getBrand(), addInstrumentPanel.getPrice());
                    inventoryManager.addInstrument(allInstruments, saxophone);
                    break;
                case "Bass":
                    Bass bass = addBass(addInstrumentPanel.getBrand(), addInstrumentPanel.getPrice(), addInstrumentPanel.getNumberOfStrings(), addInstrumentPanel.getFretless());
                    inventoryManager.addInstrument(allInstruments, bass);
                    break;
            }
//            addInstrumentPanel.close();
            filterInstruments();
        } catch (InputMismatchException | IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private Saxophone addSaxophone(String brand, Number price) {
        return new Saxophone(brand, price);
    }

    private Flute addFlute(String brand, Number price, String material, String fluteType) {
        return new Flute(brand, price, material, fluteType);
    }

    private Bass addBass(String brand, Number price, int numberOfStrings, boolean fretless) {
        return new Bass(brand, price, numberOfStrings, fretless);
    }

    private Guitar addGuitar(String brand, Number price, int numberOfStrings, String guitarType) {
        return new Guitar(brand, price, numberOfStrings, guitarType);
    }
}
