package org.misha.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by mouravievt on 4/2/2017.
 */
@XmlRootElement()
public class Movie {
    String imdbUrl;
    String title;
    Integer releaseYear;
    Collection<String> actor = new HashSet<>();
    String plotSummary;
    Collection<String> genres = new HashSet<>();
    String imageUrl;

    @XmlElement(name = "url")
    public String getImdbUrl() {
        return imdbUrl;
    }

    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
    }
    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "releaseYear")
    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @XmlElement(name = "genre")
    @XmlElementWrapper(name = "genres")
    public Collection<String> getGenres() {
        return genres;
    }

    public void setGenres(Collection<String> genres) {
        this.genres = genres;
    }

    public void addGenre(String genre){
        genres.add(genre);
    }

    public String getGenresString(){
        StringBuffer buffer = new StringBuffer("");

        for (String ss : genres){
            if(buffer.toString().isEmpty()){
                buffer.append(ss);
            } else {
                buffer.append(", " + ss);
            }
        }
        return buffer.toString();
    }

    @XmlElement(name = "actor")
    @XmlElementWrapper(name = "cast")
    public Collection<String> getCast() {
        return actor;
    }

    public void setCast(Collection<String> cast) {
        this.actor = cast;
    }

    public void addNameToCast(String name){
        actor.add(name);
    }

    @XmlElement(name = "plotSummary")
    public String getPlotSummary() {
        return plotSummary;
    }

    public void setPlotSummary(String plotSummary) {
        this.plotSummary = plotSummary;
    }

    @XmlElement(name = "imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String generateWebContent(){
        StringBuffer buffer = new StringBuffer("<html><body>");

        buffer.append("<div style=\"float:left\">");
        buffer.append("<img src=\"");
        buffer.append(getImageUrl());
        buffer.append("\" style=\"width:300px;height:500px\"");
        buffer.append("</div>");

        buffer.append("<div style=\"float:right;margin:50px;width:400px\"");
        buffer.append("<p>");
        buffer.append("<h1>");
        buffer.append("Title: " + getTitle());
        buffer.append("</h1>");
        buffer.append("<h2>");
        buffer.append("Released: " + getReleaseYear());
        buffer.append("</h2>");
        buffer.append("<h3>Plot Summary:</h3>");
        buffer.append("</p>");
        buffer.append(getPlotSummary());
        buffer.append("<p>");
        buffer.append("</p>");
        buffer.append("</div>");

        buffer.append("</body></html>");
        return buffer.toString();
    }
}
