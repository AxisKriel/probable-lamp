package PortableLamp;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public int n;
    public String text;

    public static void main(String[] args) throws Exception {
        var app = new App();
        app.getArgs(args);
    }

    /**
     * Reset this app's parameters.
     */
    public void reset() {
        this.n = 1;
        this.text = "";
    }

    /**
     * Populates the parameters of the app (n and text) based on the input.
     * @param args Used if not empty, otherwise, the input stream is used.
     */
    public void getArgs(String[] args) {
        // if no args are passed beforehand, we get them from user input
        if (args.length == 0) {
            var input = "";
            try (var scanner = new Scanner(System.in)) {
                while (scanner.hasNext()) {
                    // load the full input
                    input += scanner.nextLine();
                }
            }
            args = input.split(" ");
        }

        String syntaxError = "Invalid syntax. Correct syntax: [n:number] <text...>";
        if (args.length < 1) {
            System.out.println(syntaxError);
            return;
        }

        // this is used to start reading words earlier in case there is no explicit N
        int startIndex = 1;

        // get N from the first parameter
        try {
            this.n = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException exception) {
            // assume N is the default and continue
            this.n = 1;
            startIndex = 0;
        }

        // get text from the remainder
        String textArgs = "";
        for (int i = startIndex; i < args.length; i++) {
            textArgs += args[i];
            if (i < args.length-1) {
                textArgs += " ";
            }
        }
        this.text = textArgs;
    }

    /**
     * Parse this app's loaded parameters.
     * @return The resulting string based on the loaded text and N variables.
     */
    public String parse() {
        // preliminary checks that are easy return cases
        if (this.text == "" || this.n < 1) {
            return "";
        }

        // iterate over the string and store every uppercase char
        var chars = new ArrayList<Character>();
        for (Character ch : this.text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                chars.add(ch);
            }
        }

        String output = "";
        int length = chars.size();
        for (int i = this.n-1; i < length; i+=this.n) {
            output += chars.get(i);
        }
        
        return output;
    }
}
