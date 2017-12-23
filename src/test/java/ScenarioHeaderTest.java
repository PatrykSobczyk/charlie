import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.put.poznan.scenarios.ScenarioHeader;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ScenarioHeaderTest {

    private ScenarioHeader header;

    @Before
    public void setUp(){
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Marek");
        authors.add("Janek");
        this.header = new ScenarioHeader("Test Title", authors);
    }

    @Test
    public void testIsAuthorInHeader(){
        assertEquals(header.isThisActorInHeader("Janek"), true);
        assertEquals(header.isThisActorInHeader("Marek"), true);
        assertEquals(header.isThisActorInHeader("Stasio"), false);
    }

    @Test
    public void printHeaderText(){
        System.out.println(header.getText());
    }

    @After
    public void tearDown(){
        this.header = null;
    }
}
