import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public abstract class AddWindInstrumentPane extends BaseNewInstrumentDetails {
    private Label windMaterialLabel = new Label("Material");
    private ComboBox<String> windMaterialCombo = new ComboBox<>(FXCollections.observableArrayList(WindInstrument.WIND_INSTRUMENT_MATERIAL));

    public AddWindInstrumentPane(){
        super();
        windMaterialCombo.setPromptText("Material");
        addRow(2, windMaterialLabel, windMaterialCombo);
    }

    public ComboBox<String> getWindMaterialCombo() {
        return windMaterialCombo;
    }
}
