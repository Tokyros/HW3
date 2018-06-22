import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class AddStringInstrumentsPane<T extends StringInstrument> extends BaseNewInstrumentDetails<T> {

    private Label numOfStringLabel;
    private TextField numOfStringTextField;

    public TextField getNumOfStringTextField() {
        return numOfStringTextField;
    }

    protected int getNumOfStrings(){
        if (getNumOfStringTextField().getText().isEmpty()) throw new IllegalArgumentException("Number of strings must not be empty!");
        try {
            return Integer.parseInt(getNumOfStringTextField().getText());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Number of strings must be an integer");
        }
    }

    @Override
    protected void addComponents() {
        super.addComponents();
        numOfStringLabel = new Label("Number of Strings");
        numOfStringTextField = new TextField();
        addRow(numOfStringLabel, numOfStringTextField);
    }
}
