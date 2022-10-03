package PortableLamp;

import java.util.Scanner;

public class App {

    public int n;
    public String text;

    public static void main(String[] args) throws Exception {
        var app = new App();

        // load the params
        app.getArgs(args);

        // print the output
        System.out.println(app.parse());
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
            try (var scanner = new Scanner(System.in)) {
                System.out.println("Enter N (press Enter for 1): ");
                String inputN = scanner.nextLine();
                System.out.println("Enter text: ");
                String inputText = scanner.nextLine();
                args = new String[] { inputN, inputText };
            }
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
        var chars = this.text.toCharArray();

        String output = "";
        // for this feature, we are going to assume that every character that is not a letter fits the criteria
        for (int i = this.n-1; i < chars.length; i+=this.n) {
            // if the Nth char is uppercase, append to the output
            if (Character.isUpperCase(chars[i]) || !Character.isLetter(chars[i]))
            {
                output += chars[i];
            }
        }
        
        return output;
    }
}
