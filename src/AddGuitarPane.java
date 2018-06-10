import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class AddGuitarPane extends AddStringInstrumentsPane {

    private Label guitarTypeLabel = new Label("Guitar Type:");
    private ComboBox<String> guitarTypeCombo = new ComboBox<>(FXCollections.observableArrayList(Guitar.GUITAR_TYPE));

    public AddGuitarPane(){
        super();
        getBrandTextField().setPromptText("Ex: Gibson");
        getPriceTextField().setPromptText("Ex: 7500");
        getNumOfStringTextField().setPromptText("Ex:6");
        guitarTypeCombo.setPromptText("Type");
        addRow(3, guitarTypeLabel, guitarTypeCombo);
    }

    @Override
    public MusicalInstrument getInstrumentToAdd() {
        return new Guitar(getBrand(), getPrice(), getNumOfStrings(), guitarTypeCombo.getValue());
    }
}
