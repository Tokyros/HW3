import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Created by ps3to_000 on 30-May-18.
 */
public class AddInstrumentPanel extends Stage {
    private Guitar instrument;

    public AddInstrumentPanel(AfekaInventory<MusicalInstrument> instruments){
        setTitle("Add an instrument");

        GridPane gridPane = new GridPane();
        ComboBox<String> child = new ComboBox<>(FXCollections.observableArrayList("Guitar", "Saxophone", "Bass", "Flute"));
        child.setPromptText("Choose Instrument Type here");
        child.setOnAction(event -> {
            String value = child.getValue();
            System.out.println(value);
        });
        gridPane.add(child, 0, 0);
        Button add = new Button("Add");
        add.setOnAction(event -> {
            this.instrument = new Guitar("ShaharHa", 2005, 6, "Electric");
            this.close();
        });
        gridPane.add(add, 1, 0);
        setScene(new Scene(gridPane));
    }

    public Guitar getInstrument() {
        return instrument;
    }

    public void setInstrument(Guitar instrument) {
        this.instrument = instrument;
    }
}
