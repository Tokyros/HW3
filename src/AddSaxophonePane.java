import javafx.collections.FXCollections;

/**
 * Created by SBK on 6/10/2018.
 */
public class AddSaxophonePane extends AddWindInstrumentPane {

    public AddSaxophonePane() {
        super();
        getWindMaterialCombo().setItems(FXCollections.observableArrayList(WindInstrument.WIND_INSTRUMENT_MATERIAL[Saxophone.METAL]));
        getWindMaterialCombo().setValue(WindInstrument.WIND_INSTRUMENT_MATERIAL[Saxophone.METAL]);
        getWindMaterialCombo().setDisable(true);
    }

    @Override
    public MusicalInstrument getInstrumentToAdd() {
        return new Saxophone(getBrand(), getPrice());
    }
}
