import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public abstract class BaseNewInstrumentDetails extends GridPane {
    private Label brandLabel = new Label("Brand:");
    private TextField brandTextField = new TextField();

    private Label priceLabel = new Label("Price:");
    private TextField priceTextField = new TextField();

    public BaseNewInstrumentDetails(){
        setAlignment(Pos.CENTER);
        setHgap(StyleConstants.HGAP);
        setVgap(StyleConstants.VGAP);
        setPadding(StyleConstants.PADDING_20);
        addRow(0, brandLabel, brandTextField);
        addRow(1, priceLabel, priceTextField);
    }


    public TextField getBrandTextField() {
        return brandTextField;
    }

    public TextField getPriceTextField() {
        return priceTextField;
    }

    protected String getBrand(){
        if (getBrandTextField().getText().isEmpty()) throw new IllegalArgumentException("Brand must not be empty!");
        return getBrandTextField().getText();
    }

    protected Double getPrice(){
        if (getPriceTextField().getText().isEmpty()) throw new IllegalArgumentException("Price must not be empty!");
        try {
            return Double.parseDouble(getPriceTextField().getText());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Price must be a number");
        }
    }

    public abstract MusicalInstrument getInstrumentToAdd();
}
