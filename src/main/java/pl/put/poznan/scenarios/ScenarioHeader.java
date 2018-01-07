package pl.put.poznan.scenarios;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class responsibility for create header of scenario
 */
public class ScenarioHeader implements Serializable {
    /**
     * ScenarioHeader consists of its title and authors
     * private title
     * private actors
     */

    private String title;
    private ArrayList<String> actors;

    /**
     * Construction without parameters
     */
    public ScenarioHeader(){
        this.title = "";
        this.actors = new ArrayList<>();
    }

    /**
     * Constructor with two parameters
     * @param title
     * @param actors
     */
    public ScenarioHeader(String title, ArrayList<String> actors){
        this.title = title;
        this.actors = actors != null ? actors : new ArrayList<>();
    }

    /**
     * Return list of actors
     * @return actors
     */
    public ArrayList<String> getActors(){
        return this.actors;
    }
    /**
     * Check if this actor is in the header
     * @param actor
     * @return true or false
     */
    public boolean isThisActorInHeader(String actor){
        return actors.contains(actor);
    }
    /**
     * Return title of scenario
     * @return title
     */
    public String getTitle(){
        return this.title;
    }
    /**
     * Return text of scenario header
     * @return text
     */
    public String getText(){
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Title: " + title + "\n");
        resultBuilder.append("Actors: ");
        for( String actor : actors ){
            resultBuilder.append( actor + " ");
        }
        return resultBuilder.toString();
    }
    /**
     * Set title of scenario
     * @param  title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
