import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;


/**
 * Created by ps3to_000 on 30-May-18.
 */
public class AddInstrumentPanel extends Stage {
    public static final ObservableList<String> INSTRUMENT_TYPES = FXCollections.observableArrayList("Guitar", "Saxophone", "Bass", "Flute");

    private ComboBox<String> instrumentTypeCombo = new ComboBox<>(INSTRUMENT_TYPES);

    private Label brandLabel = new Label("Brand:");
    private TextField brandTextField = new TextField();

    private Label priceLabel = new Label("Price:");
    private TextField priceTextField = new TextField();

    private Label numOfStringLabel = new Label("Number of Strings:");
    private TextField numOfStringTextField = new TextField();

    private Label fluteMaterialLabel = new Label("Material");
    private ComboBox<String> fluteMaterialCombo = new ComboBox<>(FXCollections.observableArrayList(WindInstrument.WIND_INSTRUMENT_MATERIAL));

    private Label guitarTypeLabel = new Label("Guitar Type:");
    private ComboBox<String> guitarTypeCombo = new ComboBox<>(FXCollections.observableArrayList(Guitar.GUITAR_TYPE));

    private Label fluteTypeLabel = new Label("Flute Type:");
    private ComboBox<String> fluteTypeComboBox = new ComboBox<>(FXCollections.observableArrayList(Flute.FLUET_TYPE));

    private Label fretlessLabel = new Label("Fretless");
    private CheckBox fretless = new CheckBox();

    private Button addButton = new Button("Add");

    private GridPane gridPane = new GridPane();
    private final VBox layoutContainer;

    public AddInstrumentPanel(AfekaInventory<MusicalInstrument> instruments){
        setTitle("Add an instrument");
        setMinHeight(500);
        setMinWidth(500);

//        priceTextField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
//        numOfStringTextField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));

        instrumentTypeCombo.setMinWidth(200);
        instrumentTypeCombo.setPromptText("Choose Instrument Type Here");
        instrumentTypeCombo.setOnAction(e -> {
            switchLayout(instrumentTypeCombo.getValue());
        });

        guitarTypeCombo.setPromptText("Type");
        fluteMaterialCombo.setPromptText("Material");
        fluteTypeComboBox.setPromptText("Type");

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10));

        addButton.setVisible(false);
        layoutContainer = new VBox(instrumentTypeCombo, gridPane, addButton);
        layoutContainer.setFillWidth(true);
        layoutContainer.setAlignment(Pos.CENTER);
        setScene(new Scene(layoutContainer));
    }

    private void switchLayout(String layoutType){
        resetGridPane();
        addButton.setVisible(true);
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

    private void switchFlueLayout() {
        brandTextField.setPromptText("Ex: Levit");
        priceTextField.setPromptText("Ex: 300");
        gridPane.addRow(2, fluteMaterialLabel, fluteMaterialCombo);
        gridPane.addRow(3, fluteTypeLabel, fluteTypeComboBox);
    }

    private void switchBassLayout() {
        setStringInstrumentGridPane();
        brandTextField.setPromptText("Ex: Fender Jazz");
        priceTextField.setPromptText("Ex: 7500");
        numOfStringTextField.setPromptText("Ex:4");
        gridPane.addRow(3, fretlessLabel, fretless);
    }

    private void switchGuitarLayout() {
        setStringInstrumentGridPane();
        brandTextField.setPromptText("Ex: Gibson");
        priceTextField.setPromptText("Ex: 7500");
        numOfStringTextField.setPromptText("Ex:6");
        gridPane.addRow(3, guitarTypeLabel, guitarTypeCombo);
    }


    private void setStringInstrumentGridPane() {
        gridPane.addRow(2, numOfStringLabel, numOfStringTextField);
    }

    private void switchSaxhophoneLayout() {
        brandTextField.setPromptText(null);
        priceTextField.setPromptText(null);
    }

    private void resetGridPane() {
        gridPane.getChildren().clear();
        gridPane.addRow(0, brandLabel, brandTextField);
        gridPane.addRow(1, priceLabel, priceTextField);
    }

    public String getBrand(){
        return brandTextField.getText();
    }

    public Number getPrice(){
        return Double.parseDouble(priceTextField.getText());
    }

    public String getMaterial(){
        return fluteMaterialCombo.getValue();
    }

    public String getFluteType(){
        return fluteTypeComboBox.getValue();
    }

    public String getGuitarType(){
        return guitarTypeCombo.getValue();
    }

    public int getNumberOfStrings(){
        return Integer.parseInt(numOfStringTextField.getText());
    }

    public boolean getFretless(){
        return fretless.isSelected();
    }

    public Button getAddButton() {
        return addButton;
    }

    public String getInstrumentType(){
        return instrumentTypeCombo.getValue();
    }
}
