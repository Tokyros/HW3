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


public class AddInstrumentStage extends Stage {
    private final int INITIAL_HEIGHT = 500;
    private final int INITIAL_WIDTH = 500;

    private final String[] POSSIBLE_INSTRUMENT_TYPES = {"Guitar", "Saxophone", "Bass", "Flute"};
    private final ObservableList<String> INSTRUMENT_TYPES = FXCollections.observableArrayList(POSSIBLE_INSTRUMENT_TYPES);
    private final ComboBox<String> instrumentTypeCombo = new ComboBox<>(INSTRUMENT_TYPES);

    private BaseNewInstrumentDetails<? extends MusicalInstrument> addInstrumentPane;

    private Button addButton = new Button("Add");

    private VBox layoutContainer;

    public AddInstrumentStage(){
        setTitle("Add an instrument");
        setHeight(INITIAL_HEIGHT);
        setWidth(INITIAL_WIDTH);

        setupInstrumentTypeCombo();

        setupScene();
        show();
    }

    private void setupScene() {
        layoutContainer = new VBox(instrumentTypeCombo);
        layoutContainer.setFillWidth(true);
        layoutContainer.setAlignment(Pos.CENTER);
        setScene(new Scene(layoutContainer));
    }

    private void setupInstrumentTypeCombo() {
        instrumentTypeCombo.setPromptText("Choose Instrument Type Here");
        instrumentTypeCombo.setOnAction(e -> switchLayout(instrumentTypeCombo.getValue()));
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

    private void setGridPane(BaseNewInstrumentDetails<? extends MusicalInstrument> addPane){
        if (layoutContainer.getChildren().size() <= 1){
            layoutContainer.getChildren().addAll(addPane, addButton);
        } else {
            layoutContainer.getChildren().set(1, addPane);
        }
        addInstrumentPane = addPane;
    }

    public MusicalInstrument getNewInstrument(){
        try {
            return getAddInstrumentPane().getInstrumentToAdd();
        } catch (InputMismatchException | IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return null;
        }
    }

    private BaseNewInstrumentDetails getAddInstrumentPane() {
        return addInstrumentPane;
    }
}
