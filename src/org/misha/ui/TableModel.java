package org.misha.ui;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import org.misha.model.Movie;
import java.util.Collection;

/**
 * Created by mouravievt on 4/6/2017.
 */
public class TableModel {
    private FilteredList<Movie> data = null;

    public TableModel(Collection<Movie> initial){
        data = new FilteredList<Movie>(FXCollections.observableArrayList(initial));
    }
    public FilteredList<Movie> getData(){return data;}

    public void replaceData(Collection<Movie> replacement){
        data = new FilteredList<Movie>(FXCollections.observableArrayList(replacement));
    }
}
