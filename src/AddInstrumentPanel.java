import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.InputMismatchException;


public class AddInstrumentPanel extends Stage {
    private static final ObservableList<String> INSTRUMENT_TYPES = FXCollections.observableArrayList("Guitar", "Saxophone", "Bass", "Flute");

    private ComboBox<String> instrumentTypeCombo = new ComboBox<>(INSTRUMENT_TYPES);

    private Button addButton = new Button("Add");

    private final VBox layoutContainer;

    public AddInstrumentPanel(){
        setTitle("Add an instrument");
        setMinHeight(500);
        setMinWidth(500);

        instrumentTypeCombo.setMinWidth(200);
        instrumentTypeCombo.setPromptText("Choose Instrument Type Here");
        instrumentTypeCombo.setOnAction(e -> switchLayout(instrumentTypeCombo.getValue()));

        layoutContainer = new VBox(instrumentTypeCombo);
        layoutContainer.setFillWidth(true);
        layoutContainer.setAlignment(Pos.CENTER);
        setScene(new Scene(layoutContainer));
        show();
    }

    private void switchLayout(String layoutType){
        switch (layoutType){
            case "Guitar":
                switchGuitarLayout();
                break;
            case "Saxophone":
                switchSaxhophoneLayout();
                break;
            case "Bass":
                switchBassLayout();
                break;
            case "Flute":
                switchFlueLayout();
                break;
        }
    }

    private void switchGuitarLayout(){
        setGridPane(new AddGuitarPane());
    }

    private void switchFlueLayout() {
        setGridPane(new AddFlutePane());
    }

    private void switchBassLayout() {
        setGridPane(new AddBassPane());
    }

    private void switchSaxhophoneLayout() {
        setGridPane(new AddSaxophonePane());
    }

    public void setOnAddEvent(EventHandler<ActionEvent> onAddEvent) {
        addButton.setOnAction(onAddEvent);
    }

    private void setGridPane(BaseNewInstrumentDetails addPane){
        if (layoutContainer.getChildren().size() == 1){
            layoutContainer.getChildren().addAll(addPane, addButton);
        } else {
            layoutContainer.getChildren().set(1, addPane);
        }
    }

    public MusicalInstrument getNewInstrument(){
        try {
            return ((BaseNewInstrumentDetails) layoutContainer.getChildren().get(1)).getInstrumentToAdd();
        } catch (InputMismatchException | IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return null;
        }
    }
}
