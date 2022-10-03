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
    }
}
