package pl.put.poznan.scenarios;

import java.io.Serializable;
import java.util.ArrayList;

public class ScenarioStep implements Serializable{

    private final static String[] KEYWORDS = {"IF","ELSE","FOR EACH"};
    // every step of scenario contains text but step can also consist of subscenarios
    private String text;
    private ArrayList<Scenario> subScenarios;

    public ScenarioStep(){
        this.subScenarios = new ArrayList<>();
        this.text = "";
    }

    // if subscenarios is null then we initialize it to empty arraylist
    public ScenarioStep(String text, ArrayList<Scenario> subScenarios) {
        this.text = text;
        this.subScenarios = subScenarios != null ? subScenarios : new ArrayList<>();
    }

    public String getText(){
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(text);
        resultBuilder.append("\n");
        for( Scenario subScenario : subScenarios ){
            resultBuilder.append("\n");
            resultBuilder.append(subScenario.getText());
        }
        return resultBuilder.toString();
    }

    public void addSubscenario(Scenario scenario){
        this.subScenarios.add(scenario);
    }

    public int countSteps(){
        // this is a step so it is at least one
        int stepNo = 1;
        for( Scenario scenario : subScenarios ){
            stepNo += scenario.countAllSteps();
        }
        return stepNo;
    }

    /**
     * returns number of steps starting with keywords (in this step and contained within subscenarios)
     */
    public int howManySubStepsStartWithKeywords(){
        int result = 0;
        result += (doesStepTextStartWithKeyword() ? 1 : 0);
        for( Scenario scenario : subScenarios ){
            result += scenario.howManyStepsStartWithKeywords();
        }
        return result;
    }

    /**
     * returns true if text of this step starts with a keyword
     */
    private boolean doesStepTextStartWithKeyword(){
        for( String keyword : KEYWORDS ){
            if( this.text.startsWith(keyword) ){
                return true;
            }
        }
        return false;
    }

    public boolean doesStepStartWithActor(ArrayList<String> actors){
        for( String actor : actors ){
            if( this.text.startsWith(actor) )
                return true;
        }
        return false;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Scenario> getSubScenarios() {
        return subScenarios;
    }

    public void setSubScenarios(ArrayList<Scenario> subScenarios) {
        this.subScenarios = subScenarios;
    }
}
