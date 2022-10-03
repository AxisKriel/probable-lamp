import java.io.ByteArrayInputStream;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import PortableLamp.App;

public class Tests {

    @Test
    public void testGetArgs() {
        var app = new App();

        // a couple test inputs to ensure the app works as intended
        String input1 = "3 ITClinical";                 // n = 3 
        String input2 = "ITClinical";                   // n = 1 (default)
        String input3 = "2 This is a Longer Phrase";    // n = 2
        String input4 = "Multiple Words Without N";     // n = 1

        // first test - n should be 3 and text should be ITClinical
        System.setIn(new ByteArrayInputStream(input1.getBytes()));
        app.getArgs(new String[] {});
        assertEquals(app.n, 3);
        assertEquals(app.text, "ITClinical");
        app.reset();

        // second test - if there is only one parameter, n = 1
        System.setIn(new ByteArrayInputStream(input2.getBytes()));
        app.getArgs(new String[] {});
        assertEquals(app.n, 1);
        assertEquals(app.text, "ITClinical");
        app.reset();

        // third test - check if multiple words are properly addressed
        System.setIn(new ByteArrayInputStream(input3.getBytes()));
        app.getArgs(new String[] {});
        assertEquals(app.n, 2);
        assertEquals(app.text, "This is a Longer Phrase");
        app.reset();

        // fourth test - testing if multiple words still work without an explicit N
        System.setIn(new ByteArrayInputStream(input4.getBytes()));
        app.getArgs(new String[] {});
        assertEquals(app.n, 1);
        assertEquals(app.text, "Multiple Words Without N");
        app.reset();

        // fifth test - check if manual feed of arguments works
        app.getArgs(new String[] { "2", "Multiple", "Words", "with", "N" });
        assertEquals(app.n, 2);
        assertEquals(app.text, "Multiple Words with N");
    }
}
