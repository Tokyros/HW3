import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by ps3to_000 on 30-May-18.
 */
public class InventoryApplication extends Application {
    private final AfekaInventory<MusicalInstrument> inventory = new AfekaInventory<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        File instrumentsFile = getFileFromTextDialog();
        ArrayList<MusicalInstrument> instruments = new ArrayList<>();
        AfekaInstruments.loadInstrumentsFromFile(instrumentsFile, instruments);

        InventoryDashboard inventoryDashboard = new InventoryDashboard(instruments);

        Scene scene = new Scene(inventoryDashboard);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static File getFileFromTextDialog(){
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
