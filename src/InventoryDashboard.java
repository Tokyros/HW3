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
    private ArrayList<MusicalInstrument> instrumentsToShow;
    private AfekaInventory<MusicalInstrument> inventoryManager = new AfekaInventory<>();
    private ArrayList<MusicalInstrument> allInstruments;
    private int currentInstrumentIndex;

    private final Label typeLabel = new Label("Type: ");
    private final Label brandLabel = new Label("Brand: ");
    private final Label priceLabel = new Label("Price: ");
    private final Button goButton = new Button("Go!");
    private final HBox searchBar = new HBox();
    private final TextField searchField = new TextField();
    private final Button previousButton = new Button("<");
    private final Button nextButton = new Button(">");
    private final GridPane instrumentDetailsGrid = new GridPane();
    private final TextField typeField = new TextField();
    private final TextField brandField = new TextField();
    private final TextField priceField = new TextField();
    private final FlowPane actionButtons = new FlowPane();
    private final Button addButton = new Button("Add");
    private final Button deleteButton = new Button("Delete");
    private final Button clearButton = new Button("Clear");

    public InventoryDashboard(ArrayList<MusicalInstrument> instruments){
        this.allInstruments = instruments;
        this.instrumentsToShow = instruments;

        searchField.setPromptText("Search...");

        searchBar.setPadding(new Insets(20));
        searchBar.setSpacing(20);
        searchBar.getChildren().addAll(searchField, goButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);
        setTop(searchBar);

        VBox leftBox = new VBox(previousButton);
        leftBox.setAlignment(Pos.CENTER);
        setLeft(leftBox);

        VBox rightVBox = new VBox(nextButton);
        rightVBox.setAlignment(Pos.CENTER);
        setRight(rightVBox);

        adjustTextField(typeField);
        instrumentDetailsGrid.addRow(0, typeLabel, typeField);

        adjustTextField(brandField);
        instrumentDetailsGrid.addRow(1, brandLabel, brandField);

        adjustTextField(priceField);
        instrumentDetailsGrid.addRow(2, priceLabel, priceField);

        instrumentDetailsGrid.setHgap(20);
        instrumentDetailsGrid.setVgap(20);
        instrumentDetailsGrid.setAlignment(Pos.CENTER);
        setCenter(instrumentDetailsGrid);

        addActionButtons();



        switchInstrument(currentInstrumentIndex);
    }

    private void adjustTextField(TextField textField){
        textField.setEditable(false);
        textField.setPromptText("No Items");
    }

    private void addActionButtons() {
        actionButtons.getChildren().addAll(addButton, deleteButton, clearButton);
        setButtonsStyle();
        addButtonEvents();
        setBottom(actionButtons);
    }

    private void setButtonsStyle() {
        actionButtons.setOrientation(Orientation.HORIZONTAL);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setHgap(20);
        actionButtons.setVgap(20);
        actionButtons.setPadding(new Insets(20));
    }

    private void addButtonEvents() {
        addButton.setOnAction(e -> {
            openAddPanel();
        });

        goButton.setOnAction(event -> {
            if (allInstruments.isEmpty()) return;
            filterBySearchAndSelectFirst();
        });

        previousButton.setOnAction(event -> {
            if (allInstruments.isEmpty()) return;
            choosePreviousInstrument();
        });

        nextButton.setOnAction(event -> {
            if (allInstruments.isEmpty()) return;
            chooseNextInstrument();
        });

        clearButton.setOnAction(e -> {
            if (allInstruments.isEmpty()) return;
            inventoryManager.removeAll(allInstruments);
            inventoryManager.removeAll(instrumentsToShow);
            chooseNextInstrument();
        });

        deleteButton.setOnAction(e -> {
            if (instrumentsToShow.isEmpty()) return;
            MusicalInstrument musicalInstrument = instrumentsToShow.get(currentInstrumentIndex);
            inventoryManager.removeInstrument(instrumentsToShow, musicalInstrument);
            inventoryManager.removeInstrument(allInstruments, musicalInstrument);
            choosePreviousInstrument();
        });
    }

    private void openAddPanel() {
        AddInstrumentPanel addInstrumentPanel = new AddInstrumentPanel();
        addInstrumentPanel.show();
        addInstrumentPanel.getAddButton().setOnAction(ev -> {
            addInstrument(addInstrumentPanel);
        });
    }

    private void chooseNextInstrument() {
        int newIndex = currentInstrumentIndex < allInstruments.size() - 1 ? ++currentInstrumentIndex : (currentInstrumentIndex = 0);
        switchInstrument(newIndex);
    }

    private void choosePreviousInstrument() {
        int newIndex = currentInstrumentIndex > 0 ? --currentInstrumentIndex : (currentInstrumentIndex = allInstruments.size() - 1);
        switchInstrument(newIndex);
    }

    private void filterBySearchAndSelectFirst() {
        filterInstrumentsBySearch();
        this.currentInstrumentIndex = 0;
        switchInstrument(currentInstrumentIndex);
    }

    private void filterInstrumentsBySearch() {
        this.instrumentsToShow = new ArrayList<>();
        for (MusicalInstrument musicalInstrument : this.allInstruments) {
            if (musicalInstrument.toString().toLowerCase().contains(searchField.getText().toLowerCase())) this.instrumentsToShow.add(musicalInstrument);
        }
    }

    private void switchInstrument(int index) {
        if (this.allInstruments.size() <= index) {
            clearFields();
        } else {
            MusicalInstrument instrumentToShow = this.instrumentsToShow.get(index);
            showInstrument(instrumentToShow);
        }
    }

    private void clearFields() {
        brandField.clear();
        typeField.clear();
        priceField.clear();
    }

    private void showInstrument(MusicalInstrument instrumentToShow) {
        brandField.setText(instrumentToShow.getBrand());
        typeField.setText(instrumentToShow.getClass().getSimpleName());
        priceField.setText(instrumentToShow.getPrice().toString());
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
        } catch (InputMismatchException | IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        addInstrumentPanel.close();
        //In case the added instrument should be included in the search results
        filterInstrumentsBySearch();
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
