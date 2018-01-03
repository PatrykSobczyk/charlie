package pl.put.poznan.scenarios;

import java.io.*;
import java.util.ArrayList;

public class Scenario implements Serializable {


    private ScenarioHeader header;
    private ArrayList<ScenarioStep> scenarioSteps;

    public Scenario() {
        this.header = new ScenarioHeader();
        this.scenarioSteps = new ArrayList<>();
    }

    public Scenario(ScenarioHeader header, ArrayList<ScenarioStep> scenarioSteps) {
        this.header = header;
        this.scenarioSteps = scenarioSteps;
    }

    public void addScenarioStep(ScenarioStep scenarioStep) {
        this.scenarioSteps.add(scenarioStep);
    }

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
     */
    public int countOnlyMainSteps() {
        return this.scenarioSteps.size();
    }

    public int howManyStepsStartWithKeywords() {
        int result = 0;
        for (ScenarioStep step : scenarioSteps) {
            result += step.howManySubStepsStartWithKeywords();
        }
        return result;
    }

    public ArrayList<ScenarioStep> getStepsNotStartingWithActor() {
        ArrayList<ScenarioStep> result = new ArrayList<>();
        for (ScenarioStep step : scenarioSteps) {
            if (!step.doesStepStartWithActor(header.getActors())) {
                result.add(step);
            }
        }
        return result;
    }

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
    Wejście stanowi struktura scenariusza oraz liczba określająca poziom
    Przy poziomie = 1 zwracany jest tylko scenariusz najwyższego poziomu
    Przy poziomach > 1 zwracane są scenariusze do danego poziomu włącznie
    (np. poziom = 2 oznacza scenariusz na najwyższym poziomie oraz jego bezpośrednie pod-scenariusze)

     parametr shallIMakeACopy:
     jeśli true to zwraca kopię scenariusza, na którym wywołana jest metoda,
     jeśli false, to obcina scenariusz, na którym wywołana jest metoda do określonego poziomu (grozi utratą danych)
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

    public ScenarioHeader getHeader() {
        return header;
    }

    public void setHeader(ScenarioHeader header) {
        this.header = header;
    }

    public ArrayList<ScenarioStep> getScenarioSteps() {
        return scenarioSteps;
    }

    public void setScenarioSteps(ArrayList<ScenarioStep> scenarioSteps) {
        this.scenarioSteps = scenarioSteps;
    }
}
