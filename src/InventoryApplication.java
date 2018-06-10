import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class InventoryApplication extends Application {
    private final String ANNOUNCMENT_TEXT_TEMPLATE = "Something Something Something %s Something Something Something Something Something Something Something Something ";

    @Override
    public void start(Stage primaryStage) throws Exception {
        File instrumentsFile = getFileFromTextDialog();
        ArrayList<MusicalInstrument> instruments = new ArrayList<>();
        AfekaInstruments.loadInstrumentsFromFile(instrumentsFile, instruments);

        InventoryDashboard inventoryDashboard = new InventoryDashboard(instruments);
        Label announcement = new Label(String.format(ANNOUNCMENT_TEXT_TEMPLATE, new Date().toString()));
        announcement.setAlignment(Pos.BASELINE_LEFT);

        VBox container = new VBox(StyleConstants.VGAP, inventoryDashboard, announcement);

        Scene scene = new Scene(container);
        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            announcement.setText(String.format(ANNOUNCMENT_TEXT_TEMPLATE, new Date().toString()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Line line = new Line(0, 0, 150, 0);
        PathTransition pathTransition = new PathTransition(Duration.millis(1500), line, announcement);
        inventoryDashboard.widthProperty().addListener(e -> {
            double newWidth = inventoryDashboard.widthProperty().doubleValue();
            line.setEndX(newWidth);
            pathTransition.setDuration(Duration.millis(newWidth*5));
            pathTransition.stop();
            pathTransition.play();
        });
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.play();
    }

    private static File getFileFromTextDialog(){
        boolean stopLoop = false;
        File file = null;

        TextInputDialog textInputDialog = new TextInputDialog("C:\\Users\\SBK\\IdeaProjects\\HW3\\out\\production\\HW3\\instruments1b.txt");
        textInputDialog.setHeaderText("Load Instruments From File");
        textInputDialog.setContentText("Please enter file name:");

        do {

            Optional<String> result = textInputDialog.showAndWait();
            if (result.isPresent()){
                String filepath = result.get();
                file = new File(filepath);
                stopLoop = file.exists() && file.canRead();
            } else {
                System.exit(0);
            }

            if(!stopLoop) {
                Alert fileErrorDialog = new Alert(Alert.AlertType.ERROR);
                fileErrorDialog.setContentText("Cannot read from file, please try again");
                fileErrorDialog.setHeaderText("File Error!");
                fileErrorDialog.showAndWait();
            }
        }while (!stopLoop);

        return file;
    }
}
