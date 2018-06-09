import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


public class SearchBar extends HBox {

    private static final String SEARCH_PROMPT = "Search...";
    private static final String GO_BUTTON_TEXT = "Go!";

    public SearchBar(EventHandler<ActionEvent> onSearch, SimpleStringProperty propertyToBind){
        setPadding(StyleConstants.PADDING_20);
        setSpacing(StyleConstants.HGAP);

        Button goButton = new Button(GO_BUTTON_TEXT);
        goButton.setOnAction(onSearch);

        TextField searchField = new TextField();
        searchField.setPromptText(SEARCH_PROMPT);
        propertyToBind.bind(searchField.textProperty());

        getChildren().addAll(searchField, goButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);
    }
}
