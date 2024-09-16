package edu.gatech.seclass;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MyString implements MyStringInterface {
    private String myString;
    private final Map<Character, Integer> charDictionary = createDictionary();
    private final char[] charReverseDictionary = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e',
            'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j',
            'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o',
            'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't',
            'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y',
            'Z', 'z'
    };
    private final String[] digitDictionary = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};

    @Override
    public String getString() {
        return myString;
    }

    @Override
    public void setString(String string) {
        if (string == null || string.equals(MyStringInterface.easterEgg) || string.isEmpty() || !isAlphabeticWord(string)){
            throw new IllegalArgumentException("string must be non-empty and alphabetic and must not be easterEgg");
        }
        myString = string;
    }

    @Override
    public int countAlphabeticWords() {
        if (myString == null){
            throw new NullPointerException("Current String is Null.");
        }
        int count = 0;
        int begin = 0;
        int end = 0; // myString[begin..end) is a word
        while(begin < myString.length()){
            while (end < myString.length() && Character.isLetter(myString.charAt(end))) {
                end++;
            }
            if (begin == end){
                begin++;
                end++;
            } else {
                begin = end;
                count++;
            }
        }
        return count;
    }

    @Override
    public String encrypt(int arg1, int arg2) {
        if (myString == null){
            throw new NullPointerException("Current string is null.");
        }
        if (!isCoprimeTo62(arg1)){
            throw new IllegalArgumentException("First argument should be an integer co-prime to 62 between 0 and 62");
        }
        if (arg2 < 1 || arg2 >= 62){
            throw new IllegalArgumentException("Second argument should be an integer >= 1 and < 62");
        }
        char[] charArray = myString.toCharArray();
        for (int i = 0; i < charArray.length; i++){
            int x = charDictionary.getOrDefault(charArray[i], -1);
            if (x >= 0){
                int newValue = (arg1 * x + arg2)%62;
                charArray[i] = charReverseDictionary[newValue];
            }
        }
        String result = new String(charArray, 0, charArray.length);

        return result;
    }

    @Override
    public void convertDigitsToNamesInSubstring(int firstPosition, int finalPosition) {
        if (myString == null){
            throw new NullPointerException("Current string is null.");
        }
        if (firstPosition < 1 || firstPosition > finalPosition){
            throw new IllegalArgumentException("firstPosition or finalPosition is invalid");
        }
        if (finalPosition > myString.length()){
            throw new MyIndexOutOfBoundsException("finalPosition is out of bound");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < firstPosition - 1; i ++){
            stringBuilder.append(myString.charAt(i));
        }
        for (int i = firstPosition - 1; i < finalPosition; i++){
            if (Character.isDigit(myString.charAt(i))){
                stringBuilder.append(digitDictionary[myString.charAt(i)-'0']);
            } else {
                stringBuilder.append(myString.charAt(i));
            }
        }
        for (int i = finalPosition; i < myString.length(); i++){
            stringBuilder.append(myString.charAt(i));
        }
        myString = stringBuilder.toString();
    }

    private Map<Character, Integer> createDictionary(){
        String customAlphabet = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < customAlphabet.length(); i++) {
            map.put(customAlphabet.charAt(i), i);
        }
        return map;
    }

    private boolean isAlphabeticWord(String string){
        for (int i = 0; i < string.length(); i++){
            if (Character.isLetter(string.charAt(i)) || Character.isDigit(string.charAt(i))){
                return true;
            }
        }
        return false;
    }

    private boolean isCoprimeTo62(int number) {
        if (number <= 0 || number >=62){
            return false;
        }
        BigInteger num = BigInteger.valueOf(number);
        BigInteger sixtyTwo = BigInteger.valueOf(62);
        BigInteger gcd = num.gcd(sixtyTwo);
        return gcd.equals(BigInteger.ONE);
    }


}
