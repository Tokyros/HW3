import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public abstract class BaseNewInstrumentDetails<T extends MusicalInstrument> extends GridPane {
    private Label brandLabel;
    private TextField brandTextField;

    private Label priceLabel;
    private TextField priceTextField;

    private int rowCount;

    public BaseNewInstrumentDetails(){
        setStyles();
        addComponents();
        setPrompts();
    }

    private void setStyles() {
        setAlignment(Pos.CENTER);
        setHgap(StyleConstants.HGAP);
        setVgap(StyleConstants.VGAP);
        setPadding(StyleConstants.PADDING_20);
    }


    protected TextField getBrandTextField() {
        return brandTextField;
    }

    protected TextField getPriceTextField() {
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

    protected void addRow(Node... nodes){
        addRow(++rowCount, nodes);
    }

    public abstract T getInstrumentToAdd();

    protected abstract void setPrompts();

    protected void addComponents(){
        brandLabel = new Label("Brand:");
        brandTextField = new TextField();
        priceLabel = new Label("Price:");
        priceTextField = new TextField();
        addRow(brandLabel, brandTextField);
        addRow(priceLabel, priceTextField);
    }
}
