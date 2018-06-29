import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class InventoryApplication extends Application {
    private Scene scene;
    private AnimatedAnnouncement animatedAnnouncement;

    @Override
    public void start(Stage primaryStage) throws Exception {
        File instrumentsFile = getFileFromTextDialog();
        ArrayList<MusicalInstrument> instruments = new ArrayList<>();
        AfekaInstruments.loadInstrumentsFromFile(instrumentsFile, instruments);
        prepareScene(instruments);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> animatedAnnouncement.stop());
        primaryStage.show();
    }

    private void prepareScene(ArrayList<MusicalInstrument> instruments) {
        InventoryDashboard inventoryDashboard = new InventoryDashboard(instruments);
        VBox container = new VBox(StyleConstants.VGAP);
        scene = new Scene(container);
        animatedAnnouncement = new AnimatedAnnouncement(scene);
        container.getChildren().addAll(inventoryDashboard, animatedAnnouncement);
        setOnKeyPressed(inventoryDashboard);
    }

    private void setOnKeyPressed(InventoryDashboard inventoryDashboard) {
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
