package org.misha.ui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import org.misha.model.Movie;
import org.misha.model.Storage;

import java.util.*;

public class MainAppViewController {
    private static final String BY_YEAR = "By the Year";
    private static final String BY_GENRE = "By Genre";
    private static final String BY_ACTOR = "By Actor";

    @FXML
    TreeView treeView;
    @FXML
    TreeItem<String> treeRoot;
    @FXML
    TableView tableView;
    @FXML
    TableColumn yearColumn;
    @FXML
    TableColumn genreColumn;
    @FXML
    TableColumn titleColumn;
    @FXML
    TableColumn plotColumn;
    @FXML
    WebView webView;

    @FXML
    private void findByUrlPressed(){
        FinalMovieProApp.getPopupStage().show();
    }

    public void initTreeView(Storage storage){
        treeRoot.getChildren().removeAll(treeRoot.getChildren());

        TreeItem<String> byYear = new TreeItem<>(BY_YEAR);
        TreeItem<String> byGenre = new TreeItem<>(BY_GENRE);
        TreeItem<String> byActor = new TreeItem<>(BY_ACTOR);

        for (Integer ii : storage.getYearSet()) {
            byYear.getChildren().add(new TreeItem<String>(ii.toString()));
        }

        for(String str : storage.getGenreSet()){
            byGenre.getChildren().add(new TreeItem<String>(str));
        }

        List<String> temp = Arrays.asList(storage.getActorSet().toArray(new String[0]));
        Collections.sort(temp);
        String firstLetter = "0";
        TreeItem<String> firstLetterTreeItem = null;
        for(String str : temp){
            if(str.startsWith(firstLetter)){
                firstLetterTreeItem.getChildren().add(new TreeItem<String>(str));
            } else {
                firstLetter = str.substring(0,1);
                firstLetterTreeItem = new TreeItem<String>(firstLetter);
                byActor.getChildren().add(firstLetterTreeItem);
                firstLetterTreeItem.getChildren().add(new TreeItem<String>(str));
            }
        }
        treeRoot.getChildren().addAll(byYear, byGenre, byActor);

        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        treeView.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                TreeItem<String> selected = (TreeItem<String>) treeView.getSelectionModel().getSelectedItems().get(0);
                TreeItem<String> parent = null;

                if(selected == treeRoot || null == selected.getParent() || null == selected.getParent().getValue()){
                    filterTableData(null, null);
                    return;
                }

                if((parent = selected.getParent()).getValue().length() == 1){
                    parent = parent.getParent();
                }

                filterTableData(parent.getValue(), selected.getValue());
            }
        });
    }


    private String initialWebContent(){
        return "<html><body>Select the movie from the table above</body></html>";
    }

    public void initTableViewSelectionMode(){
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.getSelectionModel().setCellSelectionEnabled(false);
        webView.getEngine().loadContent(initialWebContent());
        tableView.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>()
        {
            @Override
            public void onChanged(Change<? extends Integer> change)
            {
                if (change.getList().size() > 0)
                {
                    webView.getEngine().loadContent(((Movie)tableView.getSelectionModel().getSelectedItem()).generateWebContent());
                }
                else
                {
                    webView.getEngine().loadContent(initialWebContent());
                }
            }

        });
    }

    public void initTableColumns(){
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        genreColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>() {
            public ReadOnlyObjectWrapper call(TableColumn.CellDataFeatures<Movie, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getGenresString());
            }
        });
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        plotColumn.setCellValueFactory(new PropertyValueFactory<>("plotSummary"));
    }

    public void replaceTableData(ObservableList<Movie> list){
        tableView.setItems(list);
    }

    public void filterTableData(String category, String filterValue){
        tableView.getSelectionModel().clearSelection();
//        System.out.println("Filtering table by " + filterValue + " in category " + category);
        ObservableList<Movie> initial = FXCollections.observableArrayList(FinalMovieProApp.getStorageAccess().getAllMovies());
        if(null != category && null != filterValue) {
            Collection<Movie> forRemoval = new ArrayList<>();
            for (Movie movie : initial) {
                if ((BY_YEAR.equals(category) && movie.getReleaseYear().toString().equals(filterValue)) ||
                        (BY_GENRE.equals(category) && movie.getGenres().contains(filterValue)) ||
                        (BY_ACTOR.equals(category) && movie.getCast().contains(filterValue))) {
                    continue;
                } else {
                    forRemoval.add(movie);
                }
            }
            initial.removeAll(forRemoval);
        } else {
            initTreeView(FinalMovieProApp.getStorageAccess().getStorage());
        }
        replaceTableData(initial);
    }
}
