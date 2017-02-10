package com.maangata.l.omdbapi;

/**
 * Created by l on 8/2/17.
 */

/**
 * This class is created to simplify working with the info retrieved from the database.
 * Thus, a Movie object is created and the getter and setters it contains make it easier.
 */
public class Movie {

    public String title, year, rated, released, runtime, genre, director, writer, actor, plot, language, country, awards, poster, metascore, imdbrating, imdbvotes, imdbid, type, response;

    public void Movie() {

    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String str) {
        title = str;
    }
    
    public String getYear() {
        return year;
    }
    public void setYear(String str) {
        year = str;
    }
    
    public String getRated() {
        return rated;
    }
    public void setRated(String str) {
        rated = str;
    }
    
    public String getReleased() {
        return released;
    }
    public void setReleased(String str) {
        released = str;
    }
    
    public String getRuntime() {
        return runtime;
    }
    public void setRuntime(String str) {
        runtime = str;
    }
    
    public String getGenre() {
        return genre;
    }
    public void setGenre(String str) {
        genre = str;
    }
    
    public String getDirector() {
        return director;
    }
    public void setDirector(String str) {
        director = str;
    }
    
    public String getWriter() {
        return writer;
    }
    public void setWriter(String str) {
        writer = str;
    }
    
    public String getActor() {
        return actor;
    }
    public void setActor(String str) {
       actor = str;
    }
    
    public String getPlot() {
        return plot;
    }
    public void setPlot(String str) {
        plot = str;
    }
    
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String str) {
        language = str;
    }
    
    public String getCountry() {
        return country;
    }
    public void setCountry(String str) {
        country = str;
    }
    
    public String getAwards() {
        return awards;
    }
    public void setAwards(String str) {
        awards = str;
    }
    
    public String getPoster() {
        return poster;
    }
    public void setPoster(String str) {
        poster = str;
    }
    
    public String getMetascore() {
        return metascore;
    }
    public void setMetascore(String str) {
        metascore = str;
    }
    
    public String getImdbrating() {
        return imdbrating;
    }
    public void setImdbrating(String str) {
        imdbrating = str;
    }
    
    public String getImdbvotes() {
        return imdbvotes;
    }
    public void setImdbvotes(String str) {
        imdbvotes = str;
    }
    
    public String getImdbid() {
        return imdbid;
    }
    public void setImdbid(String str) {
        imdbid = str;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String str) {
        type = str;
    }
    
    public String getResponse() {
        return response;
    }
    public void setResponse(String str) {
        response = str;
    }

}
