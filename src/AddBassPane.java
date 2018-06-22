import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class AddBassPane extends AddStringInstrumentsPane<Bass> {

    private CheckBox fretless;

    @Override
    public Bass getInstrumentToAdd() {
        return new Bass(getBrand(), getPrice(), getNumOfStrings(), fretless.isSelected());
    }

    @Override
    protected void setPrompts() {
        getBrandTextField().setPromptText("Ex: Fender Jazz");
        getPriceTextField().setPromptText("Ex: 7500");
        getNumOfStringTextField().setPromptText("Ex:4");
    }

    @Override
    protected void addComponents() {
        super.addComponents();
        Label fretlessLabel = new Label("Fretless");
        fretless = new CheckBox();
        addRow(fretlessLabel, fretless);
    }
}
