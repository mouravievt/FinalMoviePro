package org.misha.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.misha.model.Movie;
import org.misha.utils.Parser;

public class PopupController {
    @FXML
    private void onEnterURL(Event event){
        String url = null;
        GridPane pane = (GridPane)((Button)event.getSource()).getParent();
        MainAppViewController mainAppViewController = (MainAppViewController) FinalMovieProApp.getLoader().getController();

        for (Node node : pane.getChildren()){
            if(null != node.getId() && node.getId().equals("enterUrlTextField")){
                url = ((TextField)node).getText();
                break;
            }
        }

        if(null != url && !url.isEmpty()) {
            Movie movie = new Movie();
            Parser parser = FinalMovieProApp.getParser();

            parser.init(url);
            movie.setImdbUrl(url);
            String rawTitle = parser.getTitle();
            Integer year = getReleaseYear(rawTitle);

            movie.setReleaseYear(year);
            FinalMovieProApp.getStorageAccess().addYear(year);

            movie.setTitle(getCleanTitle(rawTitle));
            movie.setPlotSummary(parser.getPlot());
            for (String ss : parser.getCast()) {
                movie.addNameToCast(ss);
                FinalMovieProApp.getStorageAccess().addActor(ss);
            }
            for (String ss : parser.getGenres()) {
                movie.addGenre(ss);
                FinalMovieProApp.getStorageAccess().addGenre(ss);
            }
            movie.setImageUrl(parser.getImage());

            FinalMovieProApp.getStorageAccess().addMovie(movie);
        }
        FinalMovieProApp.getPopupStage().hide();
        FinalMovieProApp.getMainController().filterTableData(null, null);
    }

    private Integer getReleaseYear(String title){
        Integer year = null;

        if(title.matches(".*\\(\\d{4}\\).*")){
            int openingIdx = title.indexOf("(");
            year = new Integer(title.substring(openingIdx+1, openingIdx+5));
        }
        return year;
    }

    private String getCleanTitle(String title){
        String clean = null;

        if(title.matches(".*\\(\\d{4}\\).*")){
            int openingIdx = title.indexOf("(");
            clean = title.substring(0, openingIdx).trim();
        }
        return clean;
    }
}
