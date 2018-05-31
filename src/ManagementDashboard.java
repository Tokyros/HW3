import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * Created by ps3to_000 on 30-May-18.
 */
public class ManagementDashboard extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        File fileFromTextDialog = getFileFromTextDialog();
        ArrayList<MusicalInstrument> afekaInstruments = new ArrayList<>();
        AfekaInstruments.loadInstrumentsFromFile(fileFromTextDialog, afekaInstruments);
        AfekaInventory<MusicalInstrument> afekaInventory = new AfekaInventory<>();
        afekaInventory.addAllStringInstruments(afekaInstruments, afekaInventory.getInstrumentsList());
        afekaInventory.addAllWindInstruments(afekaInstruments, afekaInventory.getInstrumentsList());
        afekaInventory.sortByBrandAndPrice(afekaInventory.getInstrumentsList());

//        InventoryDashboard inventoryDashboard = new InventoryDashboard();

//        Scene scene = new Scene(inventoryDashboard);
//        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static File getFileFromTextDialog(){
        boolean stopLoop = false;
        File file = null;

        TextInputDialog textInputDialog = new TextInputDialog("C:\\Users\\ps3to_000\\IdeaProjects\\HW3\\src\\instruments1b.txt");
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
