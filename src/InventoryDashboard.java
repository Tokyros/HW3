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
    private final Label typeLabel = new Label("Type: ");
    private final Label brandLabel = new Label("Brand: ");
    private final Label priceLabel = new Label("Price: ");
    private AfekaInventory<MusicalInstrument> inventory;
    private ArrayList<MusicalInstrument> actualList;
    private int currentInstrumentIndex;
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

    public InventoryDashboard(AfekaInventory<MusicalInstrument> inventory){
        this.inventory = inventory;
        actualList = this.inventory.getInstrumentsList();

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
            AddInstrumentPanel addInstrumentPanel = new AddInstrumentPanel(this.inventory);
            addInstrumentPanel.show();
            addInstrumentPanel.getAddButton().setOnAction(ev -> {
                addInstrument(addInstrumentPanel);
            });
        });

        goButton.setOnAction(event -> {
            filterBySearchAndSelectFirst();
        });

        previousButton.setOnAction(event -> {
            choosePreviousInstrument();
        });

        nextButton.setOnAction(event -> {
            chooseNextInstrument();
        });
    }

    private void chooseNextInstrument() {
        if (currentInstrumentIndex < actualList.size() - 1) currentInstrumentIndex++;
        switchInstrument(currentInstrumentIndex);
    }

    private void choosePreviousInstrument() {
        int newIndex = currentInstrumentIndex > 0 ? currentInstrumentIndex-- : (currentInstrumentIndex = actualList.size());
        switchInstrument(newIndex);
    }

    private void filterBySearchAndSelectFirst() {
        filterInstrumentsBySearch();
        this.currentInstrumentIndex = 0;
        switchInstrument(currentInstrumentIndex);
    }

    private void filterInstrumentsBySearch() {
        this.actualList = new ArrayList<>();
        for (MusicalInstrument musicalInstrument : this.inventory.getInstrumentsList()) {
            if (musicalInstrument.toString().toLowerCase().contains(searchField.getText().toLowerCase())) this.actualList.add(musicalInstrument);
        }
    }

    private void switchInstrument(int index) {
        if (this.actualList.size() <= index) {
            clearFields(typeField, brandField, priceField);
        } else {
            MusicalInstrument instrumentToShow = this.actualList.get(index);
            showInstrument(typeField, brandField, priceField, instrumentToShow);
        }
    }

    private void clearFields(TextField typeField, TextField brandField, TextField priceField) {
        brandField.clear();
        typeField.clear();
        priceField.clear();
    }

    private void showInstrument(TextField typeField, TextField brandField, TextField priceField, MusicalInstrument instrumentToShow) {
        brandField.setText(instrumentToShow.getBrand());
        typeField.setText(instrumentToShow.getClass().getSimpleName());
        priceField.setText(instrumentToShow.getPrice().toString());
    }

    private void addInstrument(AddInstrumentPanel addInstrumentPanel){
        try {
            switch (addInstrumentPanel.getInstrumentType()){
                case "Guitar":
                    Guitar guitar = addGuitar(addInstrumentPanel.getBrand(), addInstrumentPanel.getPrice(), addInstrumentPanel.getNumberOfStrings(), addInstrumentPanel.getGuitarType());
                    inventory.addInstrument(inventory.getInstrumentsList(), guitar);
                    break;
                case "Flute":
                    Flute flute = addFlute(addInstrumentPanel.getBrand(), addInstrumentPanel.getPrice(), addInstrumentPanel.getMaterial(), addInstrumentPanel.getFluteType());
                    inventory.addInstrument(inventory.getInstrumentsList(), flute);
                    break;
                case "Saxophone":
                    Saxophone saxophone = addSaxophone(addInstrumentPanel.getBrand(), addInstrumentPanel.getPrice());
                    inventory.addInstrument(inventory.getInstrumentsList(), saxophone);
                    break;
                case "Bass":
                    Bass bass = addBass(addInstrumentPanel.getBrand(), addInstrumentPanel.getPrice(), addInstrumentPanel.getNumberOfStrings(), addInstrumentPanel.getFretless());
                    inventory.addInstrument(inventory.getInstrumentsList(), bass);
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
