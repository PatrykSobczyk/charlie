package pl.put.poznan.scenarios;

import java.util.ArrayList;

public class Scenario {


    private ScenarioHeader header;
    private ArrayList<ScenarioStep> scenarioSteps;

    public Scenario(){
        this.scenarioSteps = new ArrayList<>();
    }

    public Scenario(ScenarioHeader header, ArrayList<ScenarioStep> scenarioSteps) {
        this.header = header;
        this.scenarioSteps = scenarioSteps;
    }

    public void addScenarioStep(ScenarioStep scenarioStep){
        this.scenarioSteps.add(scenarioStep);
    }

    public String getText(){
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(header.getText() + "\n");
        int stepNo = 1;
        for( ScenarioStep scenarioStep : scenarioSteps ){
            //resultBuilder.append( "#" + stepNo + ". " );
            resultBuilder.append(scenarioStep.getText()) ;
            stepNo++;
        }
        return resultBuilder.toString();
    }

    /**
     * Counts all steps, that is also those contained in subscenarios of counted steps
     */
    public int countAllSteps(){
        int stepNo = 0;
        for( ScenarioStep scenarioStep : scenarioSteps ){
            stepNo += scenarioStep.countSteps();
        }
        return stepNo;
    }

    /**
     * Counts only main steps, without any regard for substeps that may be contained in
     * subscenarios of counted steps
     */
    public int countOnlyMainSteps(){
        return this.scenarioSteps.size();
    }

    public int howManyStepsStartWithKeywords(){
        int result = 0;
        for( ScenarioStep step : scenarioSteps ){
            result += step.howManySubStepsStartWithKeywords();
        }
        return result;
    }
}
