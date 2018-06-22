import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class AddFlutePane extends AddWindInstrumentPane<Flute> {
    private Label fluteTypeLabel;
    private ComboBox<String> fluteTypeComboBox;

    @Override
    public Flute getInstrumentToAdd() {
        return new Flute(getBrand(), getPrice(), getWindMaterialCombo().getValue(), fluteTypeComboBox.getValue());
    }

    @Override
    protected void setPrompts() {
        super.setPrompts();
        fluteTypeComboBox.setPromptText("Type");
        getBrandTextField().setPromptText("Ex: Levit");
        getPriceTextField().setPromptText("Ex: 300");
    }

    @Override
    protected void addComponents() {
        super.addComponents();
        fluteTypeLabel = new Label("Flute Type:");
        fluteTypeComboBox = new ComboBox<>(FXCollections.observableArrayList(Flute.FLUET_TYPE));
        addRow(fluteTypeLabel, fluteTypeComboBox);
    }
}
