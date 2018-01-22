package org.misha.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.misha.utils.Parser;
import org.misha.utils.StorageAccess;

import java.io.IOException;

public class FinalMovieProApp extends Application {
    private static final String APP_TITLE = "Final Movie Pro";
    public static final String STORAGE_FILENAME = "C:\\Misha\\FinalMoviePro\\storage.xml";
    public static final StorageAccess storageAccess = new StorageAccess();
    public static final Parser parser = new Parser();
    private static FXMLLoader loader = null;

    private static Stage primaryStage = null;
    private static Stage popupStage = null;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        FinalMovieProApp.primaryStage = primaryStage;
    }

    public static Stage getPopupStage() {
        return popupStage;
    }

    public static void setPopupStage(Stage popupStage) {
        FinalMovieProApp.popupStage = popupStage;
    }

    public static FXMLLoader getLoader() {return loader;}
    public static void setLoader(FXMLLoader loader) {FinalMovieProApp.loader = loader;}
    private static TableModel tableModel = null;
    private static MainAppViewController controller = null;

    public static MainAppViewController getMainController(){return controller;}

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Set stages
        FinalMovieProApp.setPrimaryStage(primaryStage);
        FinalMovieProApp.setPopupStage(initURLPopup());

        // Read Storage
        getStorageAccess().unmarshall();
        setLoader(new FXMLLoader(getClass().getResource("mainAppView.fxml")));
        Parent root = loader.load();

        controller = (MainAppViewController)loader.getController();
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(new Scene(root, 1500, 800));

        controller.initTreeView(storageAccess.getStorage());
        tableModel = new TableModel(storageAccess.getAllMovies());
        controller.initTableViewSelectionMode();
        controller.initTableColumns();
        controller.replaceTableData(tableModel.getData());
        primaryStage.show();
    }

    private Stage initURLPopup() throws IOException {
        Stage stage = new Stage();
        Parent popupRoot = loader.load(getClass().getResource("popup.fxml"));

        stage.setTitle("Enter URL");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(popupRoot, 200, 60));
        return stage;
    }

    public static StorageAccess getStorageAccess(){
        return storageAccess;
    }

    public static Parser getParser(){
        return parser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
