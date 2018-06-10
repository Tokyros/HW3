import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class AddFlutePane extends AddWindInstrumentPane {
    private Label fluteTypeLabel = new Label("Flute Type:");
    private ComboBox<String> fluteTypeComboBox = new ComboBox<>(FXCollections.observableArrayList(Flute.FLUET_TYPE));

    public AddFlutePane() {
        super();
        fluteTypeComboBox.setPromptText("Type");
        getBrandTextField().setPromptText("Ex: Levit");
        getBrandTextField().setPromptText("Ex: 300");
        addRow(3, fluteTypeLabel, fluteTypeComboBox);
    }

    @Override
    public MusicalInstrument getInstrumentToAdd() {
        return new Flute(getBrand(), getPrice(), getWindMaterialCombo().getValue(), getFluteTypeComboBox().getValue());
    }

    public ComboBox<String> getFluteTypeComboBox() {
        return fluteTypeComboBox;
    }
}
