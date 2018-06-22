import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class InventoryApplication extends Application {
    private final String ANNOUNCMENT_TEXT_TEMPLATE = "%s Afeka Instrument Music Store $$$ ON SALE!!! $$$ Guitars, Basses, Flutes, Saxophones, and more!";
    private Timeline textTimeline;
    private Label announcementLabel = new Label();
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        File instrumentsFile = getFileFromTextDialog();
        ArrayList<MusicalInstrument> instruments = new ArrayList<>();
        AfekaInstruments.loadInstrumentsFromFile(instrumentsFile, instruments);
        InventoryDashboard inventoryDashboard = new InventoryDashboard(instruments);

        VBox container = new VBox(StyleConstants.VGAP, inventoryDashboard, announcementLabel);
        scene = new Scene(container);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case ENTER:
                    inventoryDashboard.filterInstruments();
                    break;
                case DELETE:
                    inventoryDashboard.deleteCurrentInstrument();
                    break;
                case RIGHT:
                    inventoryDashboard.chooseNextInstrument();
                    break;
                case LEFT:
                    inventoryDashboard.choosePreviousInstrument();
                    break;
                case A:
                    inventoryDashboard.openAddPanel();
                    break;
            }
        });

        prepareAnnouncmentText();
        createTextAnimation();
        createClockAnimation();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void prepareAnnouncmentText() {
        updateAnnouncementLabel();
        announcementLabel.setTextFill(Color.RED);
    }

    private void createClockAnimation() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateAnnouncementLabel()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateAnnouncementLabel() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        announcementLabel.setText(String.format(ANNOUNCMENT_TEXT_TEMPLATE, LocalDateTime.now().format(dateTimeFormatter)));
    }

    private void createTextAnimation() {
        textTimeline = new Timeline();
        resetTextAnimation();
        textTimeline.setCycleCount(Timeline.INDEFINITE);
        textTimeline.setAutoReverse(true);
        textTimeline.play();
        announcementLabel.setOnMouseEntered(event -> textTimeline.pause());
        announcementLabel.setOnMouseExited(event -> textTimeline.play());
        scene.widthProperty().addListener(e -> resetTextAnimation());
    }

    private void resetTextAnimation() {
        double sceneWidth = scene.getWidth();
        double textWidth = announcementLabel.getLayoutBounds().getWidth();

        Duration startDuration = Duration.ZERO;
        KeyValue startKeyValue = new KeyValue(announcementLabel.translateXProperty(), -textWidth);
        KeyFrame startKeyFrame = new KeyFrame(startDuration, startKeyValue);
        Duration endDuration = Duration.seconds(10);
        KeyValue endKeyValue = new KeyValue(announcementLabel.translateXProperty(), sceneWidth);
        KeyFrame endKeyFrame = new KeyFrame(endDuration, endKeyValue);
        textTimeline.stop();
        textTimeline.getKeyFrames().clear();
        textTimeline.getKeyFrames().addAll(startKeyFrame, endKeyFrame);
        textTimeline.play();
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
