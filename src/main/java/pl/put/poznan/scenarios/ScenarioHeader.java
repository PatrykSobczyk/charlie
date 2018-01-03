package pl.put.poznan.scenarios;

import java.io.Serializable;
import java.util.ArrayList;


public class ScenarioHeader implements Serializable {

    // ScenarioHeader consists of its title and authors
    private String title;
    private ArrayList<String> actors;

    public ScenarioHeader(){
        this.title = "";
        this.actors = new ArrayList<>();
    }

    //if actors is null we initialize it to empty arraylist
    public ScenarioHeader(String title, ArrayList<String> actors){
        this.title = title;
        this.actors = actors != null ? actors : new ArrayList<>();
    }

    public ArrayList<String> getActors(){
        return this.actors;
    }

    public boolean isThisActorInHeader(String actor){
        return actors.contains(actor);
    }

    public String getTitle(){
        return this.title;
    }

    public String getText(){
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Title: " + title + "\n");
        resultBuilder.append("Actors: ");
        for( String actor : actors ){
            resultBuilder.append( actor + " ");
        }
        return resultBuilder.toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
