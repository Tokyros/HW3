import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class ActionButtonsPane extends HBox {
    public ActionButtonsPane(EventHandler<ActionEvent> onAdd, EventHandler<ActionEvent> onDelete, EventHandler<ActionEvent> onClear){
        Button addButton = new Button("Add");
        addButton.setOnAction(onAdd);
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(onDelete);
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(onClear);

        setAlignment(Pos.CENTER);
        setSpacing(StyleConstants.HGAP);
        setPadding(StyleConstants.PADDING_20);

        getChildren().addAll(addButton, deleteButton, clearButton);
    }
}
