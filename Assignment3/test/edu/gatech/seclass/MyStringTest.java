package edu.gatech.seclass;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Timeout.ThreadMode;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 * <p>
 * This class is provided to interpret your grades via junit tests
 * and as a reminder, should NOT be posted in any public repositories,
 * even after the class has ended.
 */

@Timeout(value = 1, unit = TimeUnit.SECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
public class MyStringTest {

    private MyStringInterface myString;

    @BeforeEach
    public void setUp() {
        myString = new MyString();
    }

    @AfterEach
    public void tearDown() {
        myString = null;
    }

    @Test
    // Description: First count number example in the interface documentation
    public void testCountAlphabeticWords1() {
        myString.setString("My numbers are 11, 96, and thirteen");
        assertEquals(5, myString.countAlphabeticWords());
    }

    @Test
    // Description: count null string
    public void testCountAlphabeticWords2() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> myString.countAlphabeticWords(),
                "Expect countAlphabeticWords() method throws exception"
        );
        assertEquals("Current String is Null.", exception.getMessage());
    }

    @Test
    // Description: count a string of numbers seperated by spaces
    public void testCountAlphabeticWords3() {
        myString.setString("0 1 2 3 4 5 6 7 8 9");
        assertEquals(0, myString.countAlphabeticWords());
    }

    @Test
    // Description: count a string of words with mix of numbers
    public void testCountAlphabeticWords4() {
        myString.setString("   I l0ve 7ou       ");
        assertEquals(4, myString.countAlphabeticWords());
    }

    @Test
    // Description: set my current string as an empty string
    public void testSetString1() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> myString.setString(""),
                "Expect setString() method throws exception"
        );
        assertEquals("string must be non-empty and alphabetic and must not be easterEgg", exception.getMessage());
    }

    @Test
    // Description: Sample encryption 1
    public void testEncrypt1() {
        myString.setString("Cat & 5 DogS");
        assertEquals("aY0 & J fBXs", myString.encrypt(5, 3));
    }

    @Test
    // Description: Encrypt a null string
    public void testEncrypt2() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> myString.encrypt(1, 1),
                "Expect encrypt() method throws exception"
        );
        assertEquals("Current string is null.", exception.getMessage());
    }

    @Test
    // Description: first argument of encrypt method is not a coprime to 62
    public void testEncrypt3() {
        myString.setString("123");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> myString.encrypt(2, 1),
                "Expect encrypt() method throws exception"
        );
        assertEquals("First argument should be an integer co-prime to 62 between 0 and 62", exception.getMessage());
    }

    @Test
    // Description: second argument of encrypt method is less than 1
    public void testEncrypt4() {
        myString.setString("123");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> myString.encrypt(3, 0),
                "Expect encrypt() method throws exception"
        );
        assertEquals("Second argument should be an integer >= 1 and < 62", exception.getMessage());
    }

    @Test
    // Description: encrypt a string that only contains one number with legal arguments
    public void testEncrypt5() {
        myString.setString("01");
        assertEquals("z0", myString.encrypt(1, 61));
    }

    @Test
    // Description: encrypt a string that contains repeated characters with legal arguments
    public void testEncrypt6() {
        myString.setString("aaa");
        assertEquals("BBB", myString.encrypt(1,1));
    }

    @Test
    // Description: First convert digits example in the interface documentation
    public void testConvertDigitsToNamesInSubstring1() {
        myString.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        myString.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", myString.getString());
    }

    @Test
    // Description: convert a null string
    public void testConvertDigitsToNamesInSubstring2() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> myString.convertDigitsToNamesInSubstring(1, 2),
                "Expect convertDigitsToNamesInSubstring() method throws exception"
        );
        assertEquals("Current string is null.", exception.getMessage());
    }

    @Test
    // Description: firstPosition is bigger than finalPosition as in arguments
    public void testConvertDigitsToNamesInSubstring3() {
        myString.setString("123456");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> myString.convertDigitsToNamesInSubstring(2, 1),
                "Expect convertDigitsToNamesInSubstring() method throws exception"
        );
        assertEquals("firstPosition or finalPosition is invalid", exception.getMessage());

    }

    @Test
    // Description: finalPosition is greater than the length of my string
    public void testConvertDigitsToNamesInSubstring4() {
        myString.setString("123456");
        MyIndexOutOfBoundsException exception = assertThrows(
                MyIndexOutOfBoundsException.class,
                () -> myString.convertDigitsToNamesInSubstring(2, 7),
                "Expect convertDigitsToNamesInSubstring() method throws exception"
        );
        assertEquals("finalPosition is out of bound", exception.getMessage());
    }

    @Test
    // Description: convert a string with mixture of letters and digits
    public void testConvertDigitsToNamesInSubstring5() {
        myString.setString("IL0ve7ou");
        myString.convertDigitsToNamesInSubstring(3,3);
        assertEquals("ILZerove7ou", myString.getString());
    }
}
