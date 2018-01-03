import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.put.poznan.scenarios.Scenario;
import pl.put.poznan.scenarios.ScenarioHeader;
import pl.put.poznan.scenarios.ScenarioStep;

import java.io.IOException;
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
        this.scenario = new Scenario(mainHeader, mainSteps);
    }

    private void setUpSuperAdvancedScenario(){
        ArrayList<String> actors1 = new ArrayList<>();
        actors1.add("Kamil");
        actors1.add("Konrad");
        ScenarioHeader header1 = new ScenarioHeader("Subscenario 1", actors1);
        ScenarioStep step1 = new ScenarioStep("FOR EACH Step ONE", null);
        ArrayList<ScenarioStep> scenarioSteps1 = new ArrayList<>();
        scenarioSteps1.add(step1);
        Scenario subScenario1 = new Scenario(header1, scenarioSteps1);

        ArrayList<String> actors3 = new ArrayList<>();
        actors3.add("Ania");
        actors3.add("Konrad");
        ScenarioHeader header3 = new ScenarioHeader("Subscenario 3", actors3);
        ScenarioStep step3 = new ScenarioStep("FOR EACH Step THREE", null);
        ArrayList<ScenarioStep> scenarioSteps3 = new ArrayList<>();
        scenarioSteps3.add(step3);
        Scenario subScenario3 = new Scenario(header3, scenarioSteps3);

        ArrayList<String> actors2 = new ArrayList<>();
        actors2.add("Michal");
        actors2.add("Robert");
        ScenarioHeader header2 = new ScenarioHeader("Subscenario 2", actors2);
        ArrayList<Scenario> subscenariosList1 = new ArrayList<>();
        subscenariosList1.add(subScenario3);
        ScenarioStep step2 = new ScenarioStep("FOR EACH Step TWO", subscenariosList1);
        ArrayList<ScenarioStep> scenarioSteps2 = new ArrayList<>();
        scenarioSteps2.add(step2);
        Scenario subScenario2 = new Scenario(header2, scenarioSteps2);

        ArrayList<String> actors5 = new ArrayList<>();
        actors5.add("Wojtek");
        actors5.add("Robert");
        ScenarioHeader header5 = new ScenarioHeader("Subscenario 5", actors5);
        ScenarioStep step5 = new ScenarioStep("FOR EACH Step FIVE", null);
        ArrayList<ScenarioStep> scenarioSteps5 = new ArrayList<>();
        scenarioSteps5.add(step5);
        Scenario subScenario5 = new Scenario(header5, scenarioSteps5);

        ArrayList<String> actors4 = new ArrayList<>();
        actors4.add("Marcin");
        actors4.add("Wiesiek");
        ScenarioHeader header4 = new ScenarioHeader("Subscenario 4", actors4);
        ArrayList<Scenario> subscenariosList2 = new ArrayList<>();
        subscenariosList2.add(subScenario5);
        ScenarioStep step4 = new ScenarioStep("FOR EACH Step FOUR", subscenariosList2);
        ArrayList<ScenarioStep> scenarioSteps4 = new ArrayList<>();
        scenarioSteps4.add(step4);
        Scenario subScenario4 = new Scenario(header4, scenarioSteps4);

        ArrayList<ScenarioStep> mainSteps = new ArrayList<>();
        ArrayList<Scenario> subscenariosList3 = new ArrayList<>();
        subscenariosList3.add(subScenario1);
        subscenariosList3.add(subScenario2);
        subscenariosList3.add(subScenario4);
        ScenarioStep mainStep1 = new ScenarioStep("IF Main Test", subscenariosList3);
        mainSteps.add(mainStep1);

        ArrayList<String> mainActors = new ArrayList<>();
        mainActors.add("John");
        ScenarioHeader mainHeader = new ScenarioHeader("Main title", mainActors);
        this.scenario = new Scenario(mainHeader, mainSteps);
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

    @Test
    public void makeDeepCopyOfScenarioTest() throws Exception {
        this.setUpBasicScenario();
        Scenario temp = this.scenario.makeDeepCopyOfScenario(this.scenario);
        temp.getHeader().setTitle("BLABLABLA");
        Assert.assertTrue(!temp.getHeader().getTitle().equals(this.scenario.getHeader().getTitle()));
        System.out.println(temp.getHeader().getTitle());
        System.out.println(this.scenario.getHeader().getTitle());
    }

    @Test//(expected = IllegalArgumentException.class)
    public void getStepsToCertainLevelTest() throws IllegalArgumentException, IOException, ClassNotFoundException {
        this.setUpSuperAdvancedScenario();
        Scenario result = this.scenario.getStepsToCertainLevel(this.scenario, 3, true);
        System.out.println(result.getText());
    }


    @After
    public void tearDown(){
        this.scenario = null;
    }
}
