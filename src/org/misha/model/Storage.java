package org.misha.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mouravievt on 4/2/2017.
 */
@XmlRootElement(name = "storage")
public class Storage {
    @XmlElement(name = "year")
    @XmlElementWrapper(name = "years")
    private Set<Integer> yearSet = new HashSet<>();

    @XmlElement(name = "genre")
    @XmlElementWrapper(name = "genres")
    private Set<String> genreSet = new HashSet<>();

    @XmlElement(name = "actor")
    @XmlElementWrapper(name = "actors")
    private Set<String> actorSet = new HashSet<>();

    @XmlElement
    @XmlElementWrapper(name = "movies")
    private Collection<Movie> movie = new ArrayList<>();

    public Collection<Movie> getAllMovies(){
        return movie;
    }

    public void addMovie(Movie movie){
        this.movie.add(movie);
    }

    public void addYear(Integer year){this.yearSet.add(year);}

    public void addGenre(String genre){this.genreSet.add(genre);}

    public void addActor(String actor){this.actorSet.add(actor);}

    public Set<Integer> getYearSet(){
        return yearSet;
    }

    public Set<String> getGenreSet() {
        return genreSet;
    }

    public Set<String> getActorSet() {
        return actorSet;
    }
}
