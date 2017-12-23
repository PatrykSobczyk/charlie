import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.put.poznan.scenarios.ScenarioStep;

public class ScenarioStepTest {

    private ScenarioStep scenarioStep;

    @Test
    public void emptySubscenariosTextTest(){
        this.scenarioStep = new ScenarioStep("Sample text", null);
        System.out.println(scenarioStep.getText());
    }


    @After
    public void tearDown(){
        this.scenarioStep = null;
    }

}
