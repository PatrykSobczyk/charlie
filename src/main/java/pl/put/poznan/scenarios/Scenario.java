package pl.put.poznan.scenarios;

import java.io.*;
import java.util.ArrayList;

/**
 * Class responsibility for create of scenario
 * @version 0.1
 */
public class Scenario implements Serializable {

    /**
     * private header
     * private scenarioSteps
     */
    private ScenarioHeader header;
    private ArrayList<ScenarioStep> scenarioSteps;

    /**
     * Public constructor without parameters
     */
    public Scenario() {
        this.header = new ScenarioHeader();
        this.scenarioSteps = new ArrayList<>();
    }
    /**
     * Public constructor with two parameters
     * @param header
     * @param scenarioSteps
     */
    public Scenario(ScenarioHeader header, ArrayList<ScenarioStep> scenarioSteps) {
        this.header = header;
        this.scenarioSteps = scenarioSteps;
    }
    /**
     * Add scenario step
     * @param  scenarioStep
     */
    public void addScenarioStep(ScenarioStep scenarioStep) {
        this.scenarioSteps.add(scenarioStep);
    }

    /**
     * Return scenario text
     * @return resultBuilder.toString()
     */
    public String getText() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(header.getText() + "\n");
        int stepNo = 1;
        for (ScenarioStep scenarioStep : scenarioSteps) {
            //resultBuilder.append( "#" + stepNo + ". " );
            resultBuilder.append(scenarioStep.getText());
            stepNo++;
        }
        return resultBuilder.toString();
    }


    /**
     * Counts all steps, that is also those contained in subscenarios of counted steps
     * @return stemNo
     */
    public int countAllSteps() {
        int stepNo = 0;
        for (ScenarioStep scenarioStep : scenarioSteps) {
            stepNo += scenarioStep.countSteps();
        }
        return stepNo;
    }

    /**
     * Counts only main steps, without any regard for substeps that may be contained in
     * subscenarios of counted steps
     * @return scenarioSteps
     */
    public int countOnlyMainSteps() {
        return this.scenarioSteps.size();
    }

    /**
     * Counts how many steps stated with keywords
     * @return result
     */

    public int howManyStepsStartWithKeywords() {
        int result = 0;
        for (ScenarioStep step : scenarioSteps) {
            result += step.howManySubStepsStartWithKeywords();
        }
        return result;
    }

    /**
     * Return step with not starting with actor
     * @return result
     */


    public ArrayList<ScenarioStep> getStepsNotStartingWithActor() {
        ArrayList<ScenarioStep> result = new ArrayList<>();
        for (ScenarioStep step : scenarioSteps) {
            if (!step.doesStepStartWithActor(header.getActors())) {
                result.add(step);
            }
        }
        return result;
    }

    /**
     * Copy scenario
     * @param object scenario
     * @return result
     */

    public Scenario makeDeepCopyOfScenario(Scenario object) {
        try {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        oos.close();
        bos.close();
        byte[] byteData = bos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
        Scenario result;
        result = (Scenario) new ObjectInputStream(bais).readObject();

        return result;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Input is scenario and number of level
     * if level is 1 then return scenario of the highest level
     * if level is over 1 then return scenario of this level
     * if level is under 1 then return scenaio to this level
     * @param scenario
     * @param level
     * @param shallIMakeACopy if true then you must make copy, if false then you not have to
     * @return temp

    */
    public Scenario getStepsToCertainLevel(Scenario scenario, int level, boolean shallIMakeACopy) throws IOException, ClassNotFoundException {

        Scenario temp;
        if (shallIMakeACopy) {
            temp = makeDeepCopyOfScenario(scenario);
        } else {
            temp = scenario;
        }
        if (level < 1){
            throw new IllegalArgumentException("Poziom musi być liczbą naturalną ( > 0 )!");
        }
        else if (level == 1) {
            ArrayList<ScenarioStep> scenarioSteps = temp.getScenarioSteps();
            for (ScenarioStep scenarioStep : scenarioSteps) {
                scenarioStep.setSubScenarios(new ArrayList<>());
            }
        } else {
            ArrayList<ScenarioStep> scenarioSteps = temp.getScenarioSteps();
            for (ScenarioStep scenarioStep : scenarioSteps) {
                ArrayList<Scenario> subScenarios = scenarioStep.getSubScenarios();
                for (int j = 0; j < subScenarios.size(); j++) {
                    subScenarios.set(j, getStepsToCertainLevel(subScenarios.get(j), level - 1, false));
                }
            }
        }
        return temp;

    }
    /**
     * Return header of scenario
     * @return header
     */

    public ScenarioHeader getHeader() {
        return header;
    }

    /**
     * Set header
     * @param header
     */


    public void setHeader(ScenarioHeader header) {
        this.header = header;
    }

    /**
     * Return scenario step
     * @return scenarioSteps
     */

    public ArrayList<ScenarioStep> getScenarioSteps() {
        return scenarioSteps;
    }

    /**
     * Set scenario steps
     * @param scenarioSteps
     */

    public void setScenarioSteps(ArrayList<ScenarioStep> scenarioSteps) {
        this.scenarioSteps = scenarioSteps;
    }
}
