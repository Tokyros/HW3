import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class AddStringInstrumentsPane extends BaseNewInstrumentDetails {

    private Label numOfStringLabel = new Label("Number of Strings:");
    private TextField numOfStringTextField = new TextField();

    public AddStringInstrumentsPane(){
        super();
        addRow(2, numOfStringLabel, numOfStringTextField);
    }

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
}
