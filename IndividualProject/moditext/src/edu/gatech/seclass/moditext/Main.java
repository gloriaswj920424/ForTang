package edu.gatech.seclass.moditext;

public class Main {
    // Empty Main class for compiling Individual Project
    // During Deliverable 1 and Deliverable 2, DO NOT ALTER THIS CLASS or implement it

    public static void main(String[] args) {
        WeijingModitext wj = new WeijingModitext();
        String result = wj.moditext(args);
        System.out.print(result);
    }

    private static void usage() {
        System.err.println("Usage: moditext [ -k substring | -p ch num | -t num | -g | -f style substring | -r ] FILE");
    }
}
