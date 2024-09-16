package edu.gatech.seclass.sdpencryptor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText textInput;
    private EditText multiplierInput;
    private EditText adderInput;
    private TextView textEncrypted;
    private Button encryptButton;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInput = findViewById(R.id.inputTextID);
        multiplierInput = findViewById(R.id.multiplierInputID);
        adderInput = findViewById(R.id.adderInputID);
        textEncrypted =  findViewById(R.id.resultTextID);
        encryptButton = findViewById(R.id.cipherButtonID);

        multiplierInput.setText("1");
        adderInput.setText("1");

        encryptButton.setOnClickListener(v -> handleEncryptButtonClick());
    }

    public void handleEncryptButtonClick() {

        boolean noTextToEncrypt = false;

        if (inputTextIsInvalid()){
            textInput.setError("Invalid Input Text");
            noTextToEncrypt = true;
        }

        if (multiplierInputIsInvalid()){
            multiplierInput.setError("Invalid Multiplier Input");
            noTextToEncrypt = true;
        }

        if (adderInputIsInvalid()){
            adderInput.setError("Invalid Adder Input");
            noTextToEncrypt = true;
        }

        if (noTextToEncrypt){
            return;
        }

        String inputString = textInput.getText().toString();
        Integer multiplier = Integer.parseInt(multiplierInput.getText().toString());
        Integer adder = Integer.parseInt(adderInput.getText().toString());

        String result = encrypt(inputString, multiplier, adder);

        textEncrypted.setText(result);
    }

    private boolean inputTextIsInvalid(){
        String inputString = textInput.getText().toString();
        return inputString.isBlank() || inputString.isEmpty() || !isAlphabeticWord(inputString);
    }

    private boolean isAlphabeticWord(String string){
        for (int i = 0; i < string.length(); i++){
            if (Character.isLetter(string.charAt(i)) || Character.isDigit(string.charAt(i))){
                return true;
            }
        }
        return false;
    }

    private boolean multiplierInputIsInvalid(){

        String multiplierString = multiplierInput.getText().toString();
        if (multiplierString.isBlank() || multiplierString.isEmpty()){
            return true;
        }
        Integer number = null;

        try{
            number = Integer.parseInt(multiplierString);

        } catch (NumberFormatException e){
            return true;
        }

        return !isCoprimeTo62(number);
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

    private boolean adderInputIsInvalid(){
        String adderString = adderInput.getText().toString();
        if (adderString.isBlank() || adderString.isEmpty()){
            return true;
        }
        Integer number = null;

        try{
            number = Integer.parseInt(adderString);

        } catch (NumberFormatException e){
            return true;
        }

        return number < 1 || number >= 62;
    }

    private String encrypt(String myString, int arg1, int arg2){

        char[] charArray = myString.toCharArray();
        for (int i = 0; i < charArray.length; i++){
            int x = charDictionary.getOrDefault(charArray[i], -1);
            if (x >= 0){
                int newValue = (arg1 * x + arg2)%62;
                charArray[i] = charReverseDictionary[newValue];
            }
        }
        return new String(charArray, 0, charArray.length);


    }

    private Map<Character, Integer> createDictionary(){
        String customAlphabet = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < customAlphabet.length(); i++) {
            map.put(customAlphabet.charAt(i), i);
        }
        return map;
    }
}