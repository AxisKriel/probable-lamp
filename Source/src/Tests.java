import java.io.ByteArrayInputStream;
import java.util.Dictionary;
import java.util.Hashtable;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import PortableLamp.App;

public class Tests {

    /**
     * Test the getArgs() method using various strings.
     */
    @Test
    public void testGetArgs() {
        var app = new App();

        // a couple test inputs to ensure the app works as intended
        String input1 = "3\nITClinical";                 // n = 3 
        String input2 = "ITClinical";                   // n = 1 (default)
        String input3 = "2\nThis is a Longer Phrase";    // n = 2
        String input4 = "Multiple Words Without N";     // n = 1

        // first test - n should be 3 and text should be ITClinical
        System.setIn(new ByteArrayInputStream(input1.getBytes()));
        app.getArgs(new String[] {});
        assertEquals(app.n, 3);
        assertEquals(app.text, "ITClinical");
        app.reset();

        // second test - if there is only one parameter, n = 1
        app.getArgs(new String[] { input2 });
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
        app.getArgs(new String[] { input4 });
        assertEquals(app.n, 1);
        assertEquals(app.text, "Multiple Words Without N");
        app.reset();

        // fifth test - check if manual feed of arguments works
        app.getArgs(new String[] { "2", "Multiple", "Words", "with", "N" });
        assertEquals(app.n, 2);
        assertEquals(app.text, "Multiple Words with N");
    }

    /**
     * Test the parse method. Given a text, it should return every Nth char that is uppercase. 
     */
    @Test(dependsOnMethods = {"testGetArgs"})
    public void testParse() {
        var app = new App();

        // test params in this order: N, text, output
        String[] test1 = { "1", "ITCLiNicAl", "ITCLNA" };
        String[] test2 = { "2", "ITCLiNicAl", "TLN" };
        String[] test3 = { "3", "ITCLiNicAl", "CNA" };
        // special case one - if N > number of uppercase letters, return ""
        String[] test4 = { "100", "ITCLiNicAl", "" };
        // special case two - if N < 1, return ""
        String[] test5 = { "-1", "ITCLiNicAl", "" };

        app.getArgs(new String[] { test1[0], test1[1] });
        assertEquals(app.parse(), test1[2]);
        app.reset();

        app.getArgs(new String[] { test2[0], test2[1] });
        assertEquals(app.parse(), test2[2]);
        app.reset();

        app.getArgs(new String[] { test3[0], test3[1] });
        assertEquals(app.parse(), test3[2]);
        app.reset();

        app.getArgs(new String[] { test4[0], test4[1] });
        assertEquals(app.parse(), test4[2]);
        app.reset();

        app.getArgs(new String[] { test5[0], test5[1] });
        assertEquals(app.parse(), test5[2]);
    }

    /**
     * Test the optional task #1 addon - now should also return numbers and special characters.
     */
    @Test(dependsOnMethods = { "testParse" })
    public void testOptionalOne()
    {
        var app = new App();

        String text = "!tCL1Nical";
        String n = "1";
        String expected = "!CL1N";
        app.getArgs(new String[] { n, text });
        assertEquals(app.parse(), expected);
    }

    @Test(dependsOnMethods = { "testParse" })
    public void testOptionalTwo()
    {
        var app = new App();

        String text = "ItCLINiCAL";
        String n = "1";
        String expected = "ICLINCAL";

        app.getArgs(new String[] { n, text });

        // check if the output matches
        assertEquals(app.parse(), expected);

        // check if the letter counts match
        assertEquals(app.counter.get('I'), 2);
        assertEquals(app.counter.get('C'), 2);
        assertEquals(app.counter.get('L'), 2);
        assertEquals(app.counter.get('N'), 1);
        assertEquals(app.counter.get('A'), 1);
    }
}
