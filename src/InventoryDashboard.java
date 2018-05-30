import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;

import java.beans.EventHandler;
import java.util.ArrayList;


/**
 * Created by ps3to_000 on 30-May-18.
 */
public class InventoryDashboard extends BorderPane {
    private AfekaInventory<MusicalInstrument> instruments;
    private ArrayList<MusicalInstrument> actualList;
    private int currentInstrument;

    public InventoryDashboard(AfekaInventory<MusicalInstrument> instruments){
        this.instruments = instruments;
        actualList = this.instruments.getInstrumentsList();

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
        Button addButton = new Button("Add");
        AddInstrumentPanel addInstrumentPanel = new AddInstrumentPanel(instruments);
        addButton.setOnAction(e -> {
            addInstrumentPanel.show();
        });

        addInstrumentPanel.setOnHiding(new javafx.event.EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (addInstrumentPanel.getInstrument() != null){
                    instruments.addInstrument(instruments.getInstrumentsList(), addInstrumentPanel.getInstrument());
                }
            }
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

        goButton.setOnAction(event -> {
            this.actualList = new ArrayList<>();
            for (MusicalInstrument musicalInstrument : this.instruments.getInstrumentsList()) {
                if (musicalInstrument.toString().toLowerCase().contains(searchField.getText().toLowerCase())) this.actualList.add(musicalInstrument);
            }
            this.currentInstrument = 0;
            switchInstrument(typeField, brandField, priceField, this.currentInstrument);
        });

        previousButton.setOnAction(event -> {
            if (currentInstrument > 0) currentInstrument--;
            switchInstrument(typeField, brandField, priceField, currentInstrument);
        });

        nextButton.setOnAction(event -> {
            if (currentInstrument < actualList.size() - 1) currentInstrument++;
            switchInstrument(typeField, brandField, priceField, currentInstrument);
        });

        switchInstrument(typeField, brandField, priceField, currentInstrument);
    }

    private void switchInstrument(TextField typeField, TextField brandField, TextField priceField, int index) {
        if (this.actualList.size() <= index) {
            brandField.clear();
            typeField.clear();
            priceField.clear();
        } else {
            MusicalInstrument result = this.actualList.get(index);
            brandField.setText(result.getBrand());
            typeField.setText(result.getClass().getSimpleName());
            priceField.setText(result.getPrice().toString());
        }
    }
}
