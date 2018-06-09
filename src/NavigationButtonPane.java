import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class NavigationButtonPane extends VBox {
    public NavigationButtonPane(String label, EventHandler<ActionEvent> onAction){
        setAlignment(Pos.CENTER);
        Button button = new Button(label);
        button.setOnAction(onAction);
        getChildren().add(button);
    }
}
