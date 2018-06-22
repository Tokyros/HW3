import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class AddGuitarPane extends AddStringInstrumentsPane<Guitar> {

    private Label guitarTypeLabel;
    private ComboBox<String> guitarTypeCombo;

    @Override
    public Guitar getInstrumentToAdd() {
        return new Guitar(getBrand(), getPrice(), getNumOfStrings(), guitarTypeCombo.getValue());
    }

    @Override
    protected void setPrompts() {
        getBrandTextField().setPromptText("Ex: Gibson");
        getPriceTextField().setPromptText("Ex: 7500");
        getNumOfStringTextField().setPromptText("Ex:6");
        guitarTypeCombo.setPromptText("Type");
    }

    @Override
    protected void addComponents() {
        super.addComponents();
        guitarTypeLabel = new Label("Guitar Type:");
        guitarTypeCombo = new ComboBox<>(FXCollections.observableArrayList(Guitar.GUITAR_TYPE));
        addRow(guitarTypeLabel, guitarTypeCombo);
    }
}
