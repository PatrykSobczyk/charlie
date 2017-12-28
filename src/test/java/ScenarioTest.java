import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.put.poznan.scenarios.Scenario;
import pl.put.poznan.scenarios.ScenarioHeader;
import pl.put.poznan.scenarios.ScenarioStep;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ScenarioTest {

    private Scenario scenario;

    private void setUpBasicScenario(){
        ArrayList<String> actors = new ArrayList<>();
        actors.add("Jan"); actors.add("Tomek");
        ScenarioHeader header = new ScenarioHeader("Header Title", actors);
        ScenarioStep step = new ScenarioStep("IF Test step numero uno", null);
        ScenarioStep step2 = new ScenarioStep("Janek step numero secondo", null);
        ArrayList<ScenarioStep> scenarioSteps = new ArrayList<>();
        scenarioSteps.add(step);
        scenarioSteps.add(step2);
        this.scenario = new Scenario(header, scenarioSteps);
    }

    private void setUpAdvancedScenario(){
        ArrayList<String> actors = new ArrayList<>();
        actors.add("Kamil");
        ScenarioHeader header = new ScenarioHeader("Sample title", actors);
        ScenarioStep step = new ScenarioStep("FOR EACH Step one", null);
        ArrayList<ScenarioStep> scenarioSteps = new ArrayList<>();
        scenarioSteps.add(step);
        Scenario subScenario = new Scenario(header, scenarioSteps);

        ArrayList<ScenarioStep> mainSteps = new ArrayList<>();
        ArrayList<Scenario> subscenariosList = new ArrayList<>();
        subscenariosList.add(subScenario);
        ScenarioStep mainStep1 = new ScenarioStep("IF Main Test", subscenariosList);
        mainSteps.add(mainStep1);

        ArrayList<String> mainActors = new ArrayList<>();
        mainActors.add("John");
        ScenarioHeader mainHeader = new ScenarioHeader("Main title", mainActors);
        Scenario mainScenario = new Scenario(mainHeader, mainSteps);
        this.scenario = mainScenario;
    }

    @Test
    public void basicScenarioTextTest(){
        this.setUpBasicScenario();
        System.out.println(this.scenario.getText());
    }

    @Test
    public void scenarioWithSubscenariosTextTest(){
        this.setUpAdvancedScenario();
        System.out.println(this.scenario.getText());
    }

    @Test
    public void countStepsTest1(){
        this.setUpBasicScenario();
        assertEquals(2, this.scenario.countAllSteps());
        this.setUpAdvancedScenario();
        assertEquals(2, this.scenario.countAllSteps());
    }

    @Test
    public void countOnlyMainStepsTest(){
        this.setUpBasicScenario();
        assertEquals(2, this.scenario.countOnlyMainSteps());
        this.setUpAdvancedScenario();
        assertEquals(1, this.scenario.countOnlyMainSteps());
    }

    @Test
    public void startWithKeywordsCountTest(){
        this.setUpBasicScenario();
        assertEquals(1, this.scenario.howManyStepsStartWithKeywords());
        this.setUpAdvancedScenario();
        assertEquals(2, this.scenario.howManyStepsStartWithKeywords());
    }

    @Test
    public void stepsStartingWithActorsTest(){
        this.setUpBasicScenario();
        assertEquals(1, this.scenario.getStepsNotStartingWithActor().size());
        this.setUpAdvancedScenario();
        assertEquals(1, this.scenario.getStepsNotStartingWithActor().size());
    }

    @After
    public void tearDown(){
        this.scenario = null;
    }
}
