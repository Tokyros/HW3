import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public abstract class AddWindInstrumentPane<T extends WindInstrument> extends BaseNewInstrumentDetails<T> {
    private Label windMaterialLabel;
    private ComboBox<String> windMaterialCombo;

    public ComboBox<String> getWindMaterialCombo() {
        return windMaterialCombo;
    }

    @Override
    protected void setPrompts() {
        windMaterialCombo.setPromptText("Material");
    }

    @Override
    protected void addComponents() {
        super.addComponents();
        windMaterialLabel = new Label("Material");
        windMaterialCombo = new ComboBox<>(FXCollections.observableArrayList(WindInstrument.WIND_INSTRUMENT_MATERIAL));
        addRow(windMaterialLabel, windMaterialCombo);
    }
}
