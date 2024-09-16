package edu.gatech.seclass.moditext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Timeout(value = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class MyMainTest {
    // Place all of your tests in this class, optionally using MainTest.java as an example
    private final String usageStr =
        "Usage: moditext [ -k substring | -p ch num | -t num | -g | -f style substring | -r ] FILE"
            + System.lineSeparator();

    @TempDir
    Path tempDirectory;

    @RegisterExtension
    OutputCapture capture = new OutputCapture();

    /* ----------------------------- Test Utilities ----------------------------- */

    /**
     * Returns path of a new "input.txt" file with specified contents written
     * into it. The file will be created using {@link TempDir TempDir}, so it
     * is automatically deleted after test execution.
     * 
     * @param contents the text to include in the file
     * @return a Path to the newly written file, or null if there was an
     *         issue creating the file
     */
    private Path createFile(String contents) {
        return createFile(contents, "input.txt");
    }

    /**
     * Returns path to newly created file with specified contents written into
     * it. The file will be created using {@link TempDir TempDir}, so it is
     * automatically deleted after test execution.
     * 
     * @param contents the text to include in the file
     * @param fileName the desired name for the file to be created
     * @return a Path to the newly written file, or null if there was an
     *         issue creating the file
     */
    private Path createFile(String contents, String fileName) {
        Path file = tempDirectory.resolve(fileName);
        try {
            Files.writeString(file, contents);
        } catch (IOException e) {
            return null;
        }

        return file;
    }

    /**
     * Takes the path to some file and returns the contents within.
     * 
     * @param file the path to some file
     * @return the contents of the file as a String, or null if there was an
     *         issue reading the file
     */
    private String getFileContent(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* ------------------------------- Test Cases ------------------------------- */

    @Test
    public void moditextTest1(){

        String input = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();

        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p", "1", "5", "-k", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    public void moditextTest2(){
        //Frame #:2
        String input = "";


        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void moditextTest3(){
        //Frame #:3
        Path file = createFile("test2.csv");

        String[] args = {"-r", file.toString()};
        Main.main(args);


        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest4(){
        //Frame #:4
        String input = "abc123" + System.lineSeparator();
        String output = "abc123" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(capture.stdout(), output);
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void moditextTest5(){
        //Frame #:5
        String input = "abc123";


        Path inputFile = createFile(input);
        String[] args = {"-p", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest6() {
        //Frame #: 6
        String input = "abc123" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p","1", "10", "-t", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest7() {
        //Frame #: 7
        String input = "abc123" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p","1", "10", "-t", "1", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest8() {
        //Frame #: 8
        String input = "abc123";
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-k", "321", "-p","1", "10", "-t", "1", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest9() {
        //Frame #: 9
        String input = "abc"+ System.lineSeparator()
                + System.lineSeparator()
                + "How are you?" + System.lineSeparator();
        String expected = "abc"+ System.lineSeparator()
                +"aa" + System.lineSeparator()
                + "How are you?" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "a", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest10() {
        //Frame #: 10
        String input = "abc"+ System.lineSeparator()
                + System.lineSeparator()
                + "How are you?" + System.lineSeparator();
        String expected = "How are you?"+ System.lineSeparator()
                +"aa" + System.lineSeparator()
                + "abc" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "a", "2", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest11() {
        //Frame #: 11
        String input = "abc"+ System.lineSeparator()
                + System.lineSeparator()
                + "How are you?" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-k", "321", "-p", "a", "2", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest12() {
        //Frame #: 12
        String input = "abc"+ System.lineSeparator()
                + "How are you?" + System.lineSeparator();
        String expected = "a"+ System.lineSeparator()
                + "H" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest13() {
        //Frame #: 13
        String input = "abc"+ System.lineSeparator()
                + "How are you?" + System.lineSeparator();
        String expected = "H" + System.lineSeparator()
                +"a"+ System.lineSeparator();


        Path inputFile = createFile(input);
        String[] args = {"-t", "1", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest14() {
        //Frame #: 14
        String input = "abc"+ System.lineSeparator()
                + "How are you?" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-k", "3", "-t", "1", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest15() {
        //Frame #: 15
        String input = "a";
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest16() {
        //Frame #: 16
        String input = "ao" + System.lineSeparator()
                + System.lineSeparator()
                + "I love you" + System.lineSeparator();
        String expected = "I love you" + System.lineSeparator()
                + "ao" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-k", "o", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest17() {
        //Frame #: 17
        String input = "ao" + System.lineSeparator()
                + System.lineSeparator()
                + "I love you" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p", "o", "2", "-t", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest18() {
        //Frame #: 18
        String input = "ao" + System.lineSeparator()
                + System.lineSeparator()
                + "I love you" + System.lineSeparator();
        String expected = "ao" + System.lineSeparator()
                +"oo" + System.lineSeparator()
                + "I love you" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "o", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest19() {
        //Frame #: 19
        String input = "ao" + System.lineSeparator()
                + System.lineSeparator()
                + "I love you" + System.lineSeparator();
        String expected = "ao" + System.lineSeparator()
                + System.lineSeparator()
                + "I " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest20() {
        //Frame #: 20
        String input = "Check" + System.lineSeparator()
                + "all" + System.lineSeparator()
                + "chars" + System.lineSeparator();
        String expected = "*Check*" + System.lineSeparator()
                + "all" + System.lineSeparator()
                + "chars" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-f", "italic", "Check", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void moditextTest21() {
        //Frame #: 21
        String input = "!@#$%";
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p","l", "10", "-t", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest22() {
        //Frame #: 22
        String input = "!@#$%";
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p","l", "10", "-t", "1", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest23() {
        //Frame #: 23
        String input = "!@#$%";
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-k","c","-p","l", "10", "-t", "1", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest24() {
        //Frame #:24

        String input = "****" + System.lineSeparator();
        String expected = "cccccc****" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "c", "10",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());

        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest25() {
        //Frame #:25
        String input = "!@#$%" + System.lineSeparator();
        String expected = "lllll!@#$%"+ System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "l", "10", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest26() {
        //Frame #:26
        String input = "!@#$%" + System.lineSeparator();
        String expected = "";;

        Path inputFile = createFile(input);
        String[] args = {"-k","1","-p", "1", "10", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest27() {
        //Frame #:27
        String input = "!@#$%" + System.lineSeparator();;
        String expected = "!@#$%" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t", "10", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest28() {
        //Frame #:28
        String input = "!@#$%" + System.lineSeparator();
        String expected = "!@#$%" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t", "10", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest29() {
        //Frame #:29
        String input = "!@#$%" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-t", "10", "-r", "-k", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest30() {
        //Frame #:30
        String input = "!@#$%" + System.lineSeparator();
        String expected = "!@#$%" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest31() {
        //Frame #:31
        String input = "!@#$%" + System.lineSeparator();
        String expected = "!@#$%" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-r","-k","!", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest32() {
        //Frame #:32
        String input = "!@#$%" + System.lineSeparator()
                + "$$$$$" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p","!","10","-t","5", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest33() {
        //Frame #:33
        String input = "!@#$%" + System.lineSeparator()
                + "$$$$$" + System.lineSeparator();
        String expected = "!!!!!!@#$%" + System.lineSeparator()
                + "!!!!!$$$$$" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p","!","10", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest34() {
        //Frame #:34
        String input = "!@#$%" + System.lineSeparator()
                + "$$$$$" + System.lineSeparator();
        String expected = "!@" + System.lineSeparator()
                + "$$" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t","2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest35() {
        //Frame #:35
        String input = "!@#$%" + System.lineSeparator()
                + "$$$$$" + System.lineSeparator();
        String expected = "!@#**$**%" + System.lineSeparator()
                + "**$**$$$$" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-f","bold", "$", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest36() {
        //Frame #: 36
        String input = "abc123#" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p","1", "10", "-t", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest37() {
        //Frame #: 37
        String input = "abc123#" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p","1", "10", "-t", "1", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest38() {
        //Frame #: 38
        String input = "abc123#" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-k","12", "-p","1", "10", "-t", "1", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest39() {
        //Frame #: 39
        String input = "abc123#" + System.lineSeparator();
        String expected = "111abc123#" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p","1", "10", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest40() {
        //Frame #: 40
        String input = "abc123#" + System.lineSeparator();
        String expected = "111abc123#" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p","1", "10", "-k", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest41() {
        //Frame #: 41
        String input = "abc123#" + System.lineSeparator();
        String expected = "**1****1****1**abc**1**23#" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p","1", "10", "-g", "-f", "bold", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest42() {
        //Frame #: 42
        String input = "abc123#" + System.lineSeparator();
        String expected = "a" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t","1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest43() {
        //Frame #: 43
        String input = "abc123#" + System.lineSeparator();
        String expected = "a" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t","1", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest44() {
        //Frame #: 44
        String input = "abc123#" + System.lineSeparator();
        String expected = "a" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t","1","-k", "b", "-f", "italic", "b", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest45() {
        //Frame #: 45
        String input = "abc123#" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-k","!", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest46() {
        //Frame #: 46
        String input = "abc123#";
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-k","!", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest47() {
        //Frame #: 47
        String input = "abc"+ System.lineSeparator()
                + "How are you#" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p", "1", "2", "-t", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void moditextTest48() {
        //Frame #: 48
        String input = "abc"+ System.lineSeparator()
                + "How are you#" + System.lineSeparator();
        String expected = "11111111111111111abc"+ System.lineSeparator()
                + "11111111How are you#" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "1", "20", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest49() {
        //Frame #: 49
        String input = "abc"+ System.lineSeparator()
                + "How are you#" + System.lineSeparator();
        String expected = "abc"+ System.lineSeparator()
                + "How are yo" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-t", "10", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void moditextTest50() {
        //Frame #: 50
        String input = "abc"+ System.lineSeparator()
                + "How are you#" + System.lineSeparator();
        String expected = "How are you#"+ System.lineSeparator()
                + "abc" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = { "-r", "-k", "a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void additionalTest1(){
        String input = "abc123" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p", "1", "10.5", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest2(){
        String input = "abc123" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p", "1", "-1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest3(){
        String input = "abc123" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p", "aaa", "-1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest4(){
        String input = "abc123" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"p", "aaa", "-1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest5(){
        String input = "ab";
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-p", "a", "10", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest6(){
        String input = "ab" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-t", "10.5", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest7(){
        String input = "ab" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-t", "-1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest8(){
        String input = "ab" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-t",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest9(){
        String input = "ab" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-g",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest10(){
        String input = "ab" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-f","bold","",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest11(){
        String input = "ab" + System.lineSeparator();
        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-f","Arial","a",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest12(){
        String input = "ab" + System.lineSeparator();
        String expected = "`a`b" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-f","code","a",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void additionalTest13(){
        String input = "ab" + System.lineSeparator();
        String expected = "ab" + System.lineSeparator();;

        Path inputFile = createFile(input);
        String[] args = {"-k","",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
    }

    @Test
    public void additionalTest14(){
        String input = "ab" + System.lineSeparator();
        String expected = "";;

        Path inputFile = createFile(input);
        String[] args = {"-k",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(input, getFileContent(inputFile));
        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
    }

    @Test
    public void additionalTest15() {
        String input = System.lineSeparator();
        String expected = "aa" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-k", "", "-p", "a", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest16() {
        String input = "Okay, here is how this is going to work." + System.lineSeparator()
                + "No shouting!" + System.lineSeparator()
                + "Does that make sense?" + System.lineSeparator()
                + "Alright, good meeting." + System.lineSeparator();
        String expected = "Okay, here is how this is going to work." + System.lineSeparator()
                + "Alright, good meeting." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-k", ".", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest17() {
        String input = "Hey, mind rotating this for me?" + System.lineSeparator()
                + "*" + System.lineSeparator()
                + "**" + System.lineSeparator()
                + "***" + System.lineSeparator()
                + "****" + System.lineSeparator()
                + "*****" + System.lineSeparator()
                + "Thanks!" + System.lineSeparator();

        String expected = "Hey, mind rotating this for me?" + System.lineSeparator()
                + "----*" + System.lineSeparator()
                + "---**" + System.lineSeparator()
                + "--***" + System.lineSeparator()
                + "-****" + System.lineSeparator()
                + "*****" + System.lineSeparator()
                + "Thanks!" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "-", "5", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest18() {
        String input = "The vibrant red roses bloomed in the garden" + System.lineSeparator()
                + "She wore a beautiful blue dress to the party" + System.lineSeparator()
                + "The sky turned into a brilliant shade of blue" + System.lineSeparator()
                + "His favorite color is red, her favorite is blue" + System.lineSeparator();
        String expected = "The" + System.lineSeparator()
                + "She" + System.lineSeparator()
                + "The" + System.lineSeparator()
                + "His" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t", "2", "-t", "6", "-t", "3", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest19() {
        String input = "Integers in Java are written using the keyword int." + System.lineSeparator()
                + "An int is 32-bits in most programming languages." + System.lineSeparator()
                + "Java is no exception." + System.lineSeparator()
                + "C++ however has uint, which is an int holding positive numbers." + System.lineSeparator();
        String expected = "Integers in Java are written using the keyword `int`." + System.lineSeparator()
                + "An `int` is 32-bits in most programming languages." + System.lineSeparator()
                + "Java is no exception." + System.lineSeparator()
                + "C++ however has u`int`, which is an int holding positive numbers." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-f", "code", "int", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest20() {
        String input = "Write your name." + System.lineSeparator()
                + "Write the date." + System.lineSeparator()
                + "Answer questions 1-4." + System.lineSeparator()
                + "Ignore all other instructions and turn this in as-is." + System.lineSeparator();
        String expected = "Ignore all other instructions and turn this in as-is." + System.lineSeparator()
                + "Answer questions 1-4." + System.lineSeparator()
                + "Write the date." + System.lineSeparator()
                + "Write your name." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest21() {
        String input = "- has everyone packed? Check." + System.lineSeparator()
                + "- Does the car contain enough gas? Check." + System.lineSeparator()
                + "- Fun will be had? Check." + System.lineSeparator();
        String expected = "- has everyone packed? *Check*." + System.lineSeparator()
                + "- Fun will be had? *Check*." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-f", "italic", "Check", "-k", "contain", "-k", "ha", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest22() {
        String input = "-red paint goes well with purple paint." + System.lineSeparator()
                + "-teal is a type of blue and green." + System.lineSeparator()
                + "-roses are either red or purple." + System.lineSeparator();
        String expected = "-red paint goes well with purple paint." + System.lineSeparator()
                + "-roses are either red or purple." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-k", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest23() {
        String input = "Once upon a time, here was a hen." + System.lineSeparator()
                + "When this hen left the den, it roamed all of the land." + System.lineSeparator()
                + "All of it, until the hen got to the end." + System.lineSeparator();
        String expected = "--All of it, until the **hen** got to the end." + System.lineSeparator()
                + "W**hen** this **hen** left the den, it roamed all of the land." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-k", "the", "-p", "-", "42", "-g", "-f", "bold", "hen", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest24() {
        String input = "Once upon a time, here was a hen." + System.lineSeparator()
                + "When this hen left the den, it roamed all of the land." + System.lineSeparator()
                + "All of it, until the hen got to the end." + System.lineSeparator();
        String expected = "All of it, until the hen got to the end." + System.lineSeparator()
                + "When this hen left the den, it roamed all of the land." + System.lineSeparator()
                + "Once upon a time, here was a hen." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-r", "-r", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest25() {
        String input = "Once" + System.lineSeparator();

        String expected = "111111Once" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "o", "5", "-p", "1", "10", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest26() {
        String input = "Once" + System.lineSeparator()
        +"upon" + System.lineSeparator();

        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-g", "-g", "f", "italic", "n", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertEquals(capture.stderr().isEmpty(), false);
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest27() {
        String input = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();

        String expected = "O**n**ce" + System.lineSeparator()
                +"upo**n**" + System.lineSeparator();;

        Path inputFile = createFile(input);
        String[] args = {"-g", "-f", "italic", "n", "-g", "-f", "bold", "n", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest28() {
        String input = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();

        String expected = "On" + System.lineSeparator()
                +"up" + System.lineSeparator();;

        Path inputFile = createFile(input);
        String[] args = {"-t", "1", "-t", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest29() {
        String input = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();

        String expected = "O" + System.lineSeparator()
                +"u" + System.lineSeparator();;

        Path inputFile = createFile(input);
        String[] args = {"-t", "2", "-t", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest30() {
        String input = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();

        String expected = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();;

        Path inputFile = createFile(input);
        String[] args = {"-k", "2", "-k", "n", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest31() {
        String input = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();

        String expected = "";

        Path inputFile = createFile(input);
        String[] args = {"-k", "n", "-k", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void additionalTest32() {
        String input = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();

        String expected = "O*n*" + System.lineSeparator()
                +"up" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-g", "-f", "italic", "n", "-t", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void d3AdditionalTest33() {
        String input = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();

        String expected = "O*n*ce" + System.lineSeparator()
                +"upo*n*" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-g", "-g", "-f", "italic", "n", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    public void d3AdditionalTest34() {
        //add a test case on "-t" "0"
        String input = "Once" + System.lineSeparator()
                +"upon" + System.lineSeparator();

        String expected = "" + System.lineSeparator()
                + "" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-t", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    public void d3AdditionalTest35() {
        //add a test case on doubleNewLines with empty content
        String input = "" + System.lineSeparator()
                +"" + System.lineSeparator();

        String expected = "cc" + System.lineSeparator()
                + "cc" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "c", "2", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }





















}
