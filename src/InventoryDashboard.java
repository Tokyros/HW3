import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.InputMismatchException;


/**
 * Created by ps3to_000 on 30-May-18.
 */
public class InventoryDashboard extends BorderPane {
    private AfekaInventory<MusicalInstrument> inventory;
    private ArrayList<MusicalInstrument> actualList;
    private int currentInstrument;

    public InventoryDashboard(AfekaInventory<MusicalInstrument> inventory){
        this.inventory = inventory;
        actualList = this.inventory.getInstrumentsList();

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");

        Button goButton = new Button("Go!");

        FlowPane searchBar = new FlowPane();
        searchBar.setHgap(20);
        searchBar.setPadding(new Insets(20));
        searchBar.setOrientation(Orientation.HORIZONTAL);
        searchBar.getChildren().addAll(searchField, goButton);
        setTop(searchBar);

        Button previousButton = new Button("<");
        previousButton.setAlignment(Pos.CENTER);
        setLeft(previousButton);

        Button nextButton = new Button(">");
        nextButton.setAlignment(Pos.CENTER);
        setRight(nextButton);

        GridPane instrumentDetailsGrid = new GridPane();
        instrumentDetailsGrid.add(new Label("Type: "), 0, 0);
        instrumentDetailsGrid.add(new Label("Brand: "), 0, 1);
        instrumentDetailsGrid.add(new Label("Price: "), 0, 2);
        TextField typeField = new TextField();
        typeField.setEditable(false);
        typeField.setPromptText("No Items");
        instrumentDetailsGrid.add(typeField, 1, 0, 2, 1);
        TextField brandField = new TextField();
        brandField.setEditable(false);
        brandField.setPromptText("No Items");
        instrumentDetailsGrid.add(brandField, 1, 1, 2, 1);
        TextField priceField = new TextField();
        priceField.setEditable(false);
        priceField.setPromptText("No Items");
        instrumentDetailsGrid.add(priceField, 1, 2, 2, 1);
        instrumentDetailsGrid.setHgap(20);
        instrumentDetailsGrid.setVgap(20);
        instrumentDetailsGrid.setAlignment(Pos.CENTER);
        setCenter(instrumentDetailsGrid);

        FlowPane actionButtons = new FlowPane();

        addActionButtons(inventory, actionButtons);

        goButton.setOnAction(event -> {
            this.actualList = new ArrayList<>();
            for (MusicalInstrument musicalInstrument : this.inventory.getInstrumentsList()) {
                if (musicalInstrument.toString().toLowerCase().contains(searchField.getText().toLowerCase())) this.actualList.add(musicalInstrument);
            }
            this.currentInstrument = 0;
            switchInstrument(typeField, brandField, priceField, this.currentInstrument);
        });

        previousButton.setOnAction(event -> {
            int newIndex = currentInstrument > 0 ? currentInstrument-- : (currentInstrument = actualList.size());
            switchInstrument(typeField, brandField, priceField, newIndex);
        });

        nextButton.setOnAction(event -> {
            if (currentInstrument < actualList.size() - 1) currentInstrument++;
            switchInstrument(typeField, brandField, priceField, currentInstrument);
        });

        switchInstrument(typeField, brandField, priceField, currentInstrument);
    }

    private void addActionButtons(AfekaInventory<MusicalInstrument> inventory, FlowPane actionButtons) {
        Button addButton = new Button("Add");
        AddInstrumentPanel addInstrumentPanel = new AddInstrumentPanel(inventory);
        addButton.setOnAction(e -> {
            addInstrumentPanel.show();
        });

        addInstrumentPanel.getAddButton().setOnAction(e -> {
            addInstrument(addInstrumentPanel);
        });

        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");

        actionButtons.getChildren().addAll(addButton, deleteButton, clearButton);
        actionButtons.setOrientation(Orientation.HORIZONTAL);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setHgap(20);
        actionButtons.setVgap(20);
        actionButtons.setPadding(new Insets(20));

        setBottom(actionButtons);
    }

    private void switchInstrument(TextField typeField, TextField brandField, TextField priceField, int index) {
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
