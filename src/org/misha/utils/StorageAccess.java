package org.misha.utils;

import org.misha.model.Movie;
import org.misha.model.Storage;
import org.misha.ui.FinalMovieProApp;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collection;

/**
 * Created by mouravievt on 4/2/2017.
 */
public class StorageAccess {
    private Storage storage = new Storage();

    public void addMovie(Movie movie){
        if(null != movie){
            storage.addMovie(movie);
            marshall();
        }
    }

    public void addYear(Integer year){
        if(null != year){
            storage.addYear(year);
            marshall();
        }
    }

    public void addGenre(String genre){
        if(null != genre){
            storage.addGenre(genre);
            marshall();
        }
    }

    public void addActor(String actor){
        if(null != actor){
            storage.addActor(actor);
            marshall();
        }
    }

    public Collection<Movie> getAllMovies(){
        return storage.getAllMovies();
    }

    public Storage getStorage(){return storage;}

   public void unmarshall(){
        try {
            String storageFilename = FinalMovieProApp.STORAGE_FILENAME;
            if(null != storageFilename) {
                File file = new File(storageFilename);
                JAXBContext context = JAXBContext.newInstance(Storage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                storage = (Storage) unmarshaller.unmarshal(file);
            }
        } catch (JAXBException ee) {
            System.err.println("Cannot read from Storage file" + ee);
        }
    }

    private void marshall(){
        try {
            String storageFilename = FinalMovieProApp.STORAGE_FILENAME;
            if(null != storageFilename) {
                File file = new File(storageFilename);
                JAXBContext context = JAXBContext.newInstance(Storage.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.marshal(storage, file);
            }
        } catch (JAXBException ee) {
            System.err.println("Cannot write to Storage file" + ee);
        }
    }
}
