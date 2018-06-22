import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class ActionButtonsPane extends HBox {
    public ActionButtonsPane(EventHandler<ActionEvent> onAdd, EventHandler<ActionEvent> onDelete, EventHandler<ActionEvent> onClear){
        setAlignment(Pos.CENTER);
        setSpacing(StyleConstants.HGAP);
        setPadding(StyleConstants.PADDING_20);

        getChildren().addAll(
                createActionButton(onAdd, "Add"),
                createActionButton(onDelete, "Delete"),
                createActionButton(onClear, "Clear"));
    }

    private Button createActionButton(EventHandler<ActionEvent> onAction, String text) {
        Button button = new Button(text);
        button.setOnAction(onAction);
        return button;
    }
}
