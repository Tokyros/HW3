import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class AddBassPane extends AddStringInstrumentsPane {

    private CheckBox fretless = new CheckBox();

    public AddBassPane(){
        super();
        getBrandTextField().setPromptText("Ex: Fender Jazz");
        getPriceTextField().setPromptText("Ex: 7500");
        getNumOfStringTextField().setPromptText("Ex:4");
        Label fretlessLabel = new Label("Fretless");
        addRow(3, fretlessLabel, fretless);
    }

    @Override
    public MusicalInstrument getInstrumentToAdd() {
        return new Bass(getBrand(), getPrice(), getNumOfStrings(), fretless.isSelected());
    }

}
