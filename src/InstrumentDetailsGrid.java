import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InstrumentDetailsGrid extends GridPane {
    private final Label brandLabel = new Label("Brand: ");
    private final TextField brandField = new TextField();

    private final Label typeLabel = new Label("Type: ");
    private final TextField typeField = new TextField();

    private final Label priceLabel = new Label("Price: ");
    private final TextField priceField = new TextField();

    private int totalRows;

    public InstrumentDetailsGrid(){
        setStyles();
        adjustTextFields();
        addRows();
    }

    private void addRows() {
        addRow(brandLabel, brandField);
        addRow(typeLabel, typeField);
        addRow(priceLabel, priceField);
    }

    private void adjustTextFields() {
        adjustTextField(brandField);
        adjustTextField(typeField);
        adjustTextField(priceField);
    }

    private void setStyles() {
        setHgap(StyleConstants.HGAP);
        setVgap(StyleConstants.VGAP);
        setAlignment(Pos.CENTER);
    }

    private void addRow(Node... nodes){
        addRow(totalRows++, nodes);
    }

    private void adjustTextField(TextField textField){
        textField.setEditable(false);
        textField.setPromptText("No Items");
        textField.setFocusTraversable(false);
    }

    public void clearFields() {
        brandField.clear();
        typeField.clear();
        priceField.clear();
    }

    public void setDetails(String brand, String type, Number price) {
        brandField.setText(brand);
        typeField.setText(type);
        priceField.setText(price.toString());
    }
}
