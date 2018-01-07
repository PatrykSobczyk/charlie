package pl.put.poznan.scenarios;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class responsibility for create scenario step
 */

public class ScenarioStep implements Serializable{
    /**
     * private keywords
     * private text
     * private subScenarios
     */
    private final static String[] KEYWORDS = {"IF","ELSE","FOR EACH"};
    // every step of scenario contains text but step can also consist of subscenarios
    private String text;
    private ArrayList<Scenario> subScenarios;

    /**
     * Constructor without parameters
     */
    public ScenarioStep(){
        this.subScenarios = new ArrayList<>();
        this.text = "";
    }

    /**
     * Constructor with two parameters
     * @param text to display
     * @param subScenarios subscenario
     */
    public ScenarioStep(String text, ArrayList<Scenario> subScenarios) {
        this.text = text;
        this.subScenarios = subScenarios != null ? subScenarios : new ArrayList<>();
    }

    /**
     * Return text of scenario step
     * @return string
     */
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

    /**
     * add subscenario
     * @param scenario
     */
    public void addSubscenario(Scenario scenario){
        this.subScenarios.add(scenario);
    }

    /**
     * count scenario step
     * @return stepNo
     */
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
     * @return result
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
     * @return true or false
     */
    private boolean doesStepTextStartWithKeyword(){
        for( String keyword : KEYWORDS ){
            if( this.text.startsWith(keyword) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if step start with actors
     * @param actors
     * @return true or false
     */

    public boolean doesStepStartWithActor(ArrayList<String> actors){
        for( String actor : actors ){
            if( this.text.startsWith(actor) )
                return true;
        }
        return false;
    }

    /**
     * set scenario text
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * return sub scenarios
     * @return subScenarios
     */
    public ArrayList<Scenario> getSubScenarios() {
        return subScenarios;
    }

    /**
     * set subscenarios
     * @param subScenarios
     */
    public void setSubScenarios(ArrayList<Scenario> subScenarios) {
        this.subScenarios = subScenarios;
    }
}
