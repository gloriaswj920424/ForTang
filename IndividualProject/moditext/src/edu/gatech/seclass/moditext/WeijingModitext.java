package edu.gatech.seclass.moditext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WeijingModitext {
    public String moditext(String[] args){
        if (args == null || args.length == 0){
            throwError();
            return "";
        }
        if (!isValidFile(args[args.length - 1])){
            throwError();
            return "";
        }
        Path file = Paths.get(args[args.length - 1]);
        String input = getFileContent(file);
        if (input != null && input.isEmpty()){
            return "";
        }
        if (!endWithLineSeperator(input)){
            throwError();
            return "";
        }
        String[] options = new String[args.length - 1];
        System.arraycopy(args, 0, options, 0, args.length - 1);
        List<String[]> validOptionList = getValidOptionsList(options);
        if (validOptionList == null){
            throwError();
            return "";
        }
        String result = executeOptions(validOptionList, input);
        return result;
    }

    private boolean isValidFile(String filename){
        return filename.endsWith(".txt");
    }

    private boolean endWithLineSeperator(String input){
        if (input == null){
            return false;
        }
        return input.endsWith(System.lineSeparator());
    }

    private void throwError(){
        System.err.println("Usage: moditext [ -k substring | -p ch num | -t num | -g | -f style substring | -r ] FILE");
    }

    private String getFileContent(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String[]> getValidOptionsList(String[] options){
        List<String[]> optionsList = Arrays.asList(null, null, null, null, null);
        int pos = 0;
        boolean hasTOption = false;
        boolean hasPOption = false;
        while (pos < options.length){
            String curOption = options[pos];
            switch (curOption){
                case "-k":
                    if (!isValidKOptions(pos, options)){
                        return null;
                    }
                    optionsList.set(0, new String[]{options[pos], options[pos + 1]});
                    pos += 2;
                    break;
                case "-p":
                    if (hasTOption || !isValidPOptions(pos, options)){
                        return null;
                    }
                    optionsList.set(1, new String[]{options[pos], options[pos + 1], options[pos + 2]});
                    hasPOption = true;
                    pos += 3;
                    break;
                case "-t":
                    if (hasPOption || !isValidTOptions(pos, options)){
                        return null;
                    }
                    optionsList.set(1, new String[]{options[pos], options[pos + 1]});
                    hasTOption = true;
                    pos += 2;
                    break;
                case "-g":
                    optionsList.set(2, new String[]{options[pos]});
                    pos ++;
                    break;
                case "-f":
                    if (!isValidFOptions(pos, options)){
                        return null;
                    }
                    optionsList.set(3, new String[]{options[pos], options[pos + 1], options[pos + 2]});
                    pos += 3;
                    break;
                case "-r":
                    optionsList.set(4, new String[]{options[pos]});
                    pos ++;
                    break;
                default:
                    return null;
            }
        }
        if (gProvidedWithoutF(optionsList)){
            return null;
        }
        return optionsList;
    }

    private boolean isValidKOptions(int pos, String[] options){
        return pos + 2 <= options.length;
    }

    private boolean isValidPOptions(int pos, String[] options){
        if (pos + 3 > options.length){
            return false;
        }
        String ch = options[pos + 1];
        String num = options[pos + 2];
        if (ch.length() != 1){
            return false;
        }
        try{
            int number = Integer.parseInt(num);
            if (number < 1 || number > 100){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean isValidTOptions(int pos, String[] options){
        if (pos + 2 > options.length){
            return false;
        }
        String num = options[pos + 1];
        try{
            int number = Integer.parseInt(num);
            if (number < 0 || number > 100){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean isValidFOptions(int pos, String[] options){
        if (pos + 3 > options.length){
            return false;
        }
        String style = options[pos + 1];
        String substring = options[pos + 2];
        if (notValidStyle(style)){
            return false;
        }
        return substring != null && !substring.isEmpty();
    }

    private boolean notValidStyle(String style){
        return style == null || style.isEmpty() || !(style.equals("bold") || style.equals("italic") || style.equals("code"));
    }

    private boolean gProvidedWithoutF(List<String[]> optionsList){
        return optionsList.get(2) != null && optionsList.get(3) == null;
    }

    private String executeOptions(List<String[]> validOptionList, String input){
        String output = "";
        output = applyKOption(validOptionList.get(0), input);
        output = applyPOrTOption(validOptionList.get(1), output);
        boolean isGlobal = validOptionList.get(2) != null;
        output = applyFOption(validOptionList.get(3), output, isGlobal);
        output = applyROption(validOptionList.get(4), output);
        return output;
    }

    private String applyKOption(String[] option, String input){
        if (option == null || input.isEmpty() || input.equals(System.lineSeparator())){
            return input;
        }
        String[] oldlines = input.split(System.lineSeparator(), -1);
        String[] lines = new String[oldlines.length - 1];
        System.arraycopy(oldlines, 0, lines, 0, lines.length);
        StringBuilder filteredLines = new StringBuilder();
        for (String line : lines){
            if (line.contains(option[1])){
                filteredLines.append(line).append(System.lineSeparator());
            }
        }
        return filteredLines.toString();
    }

    private String applyPOrTOption(String[] option, String input){
        if (option == null || input.isEmpty()){
            return input;
        }
        if (option.length == 3){
            return applyPOption(option, input);
        } else {
            return applyTOption(option, input);
        }
    }

    private String applyPOption(String[] option, String input){
        String[] oldlines = input.split(System.lineSeparator(), -1);
        String[] lines = new String[oldlines.length - 1];
        System.arraycopy(oldlines, 0, lines, 0, lines.length);
        StringBuilder filteredLines = new StringBuilder();
        char ch = option[1].charAt(0);
        int num = Integer.parseInt(option[2]);
        for (String line : lines){
            int diff = line.length() - num;
            String padding = diff >= 0 ? "" : String.valueOf(ch).repeat(Math.max(0, num - line.length()));
            String newLine = padding + line;
            filteredLines.append(newLine).append(System.lineSeparator());
        }
        return filteredLines.toString();
    }

    private String applyTOption(String[] option, String input){
        String[] oldlines = input.split(System.lineSeparator(), -1);
        String[] lines = new String[oldlines.length - 1];
        System.arraycopy(oldlines, 0, lines, 0, lines.length);
        StringBuilder filteredLines = new StringBuilder();
        int num = Integer.parseInt(option[1]);
        for (String line : lines){
            String newLine = line.substring(0, Math.min(num, line.length()));
            filteredLines.append(newLine).append(System.lineSeparator());
        }
        return filteredLines.toString();
    }

    private String applyFOption(String[] option, String output, boolean isGlobal){
        if (option == null || output.isEmpty() || output.equals(System.lineSeparator())){
            return output;
        }
        String[] oldlines = output.split(System.lineSeparator(), -1);
        String[] lines = new String[oldlines.length - 1];
        System.arraycopy(oldlines, 0, lines, 0, lines.length);
        StringBuilder filteredLines = new StringBuilder();
        String style = option[1];
        String substring = option[2];
        String newSubstring = applyStyleToSubstring(style, substring);
        for (String line : lines){
            String newline = line;
            newline = isGlobal ? line.replace(substring, newSubstring) : replaceFirst(substring, line, newSubstring);
            filteredLines.append(newline).append(System.lineSeparator());
        }
        return filteredLines.toString();
    }

    private String replaceFirst(String substring, String line, String newSubstring){
        if (substring.length() > line.length()){
            return line;
        }
        int index = line.indexOf(substring);
        if (index != -1){
            return line.substring(0, index) + newSubstring + line.substring(index + substring.length());
        }
        return line;
    }

    private String applyStyleToSubstring(String style, String substring){
        return switch (style) {
            case "bold" -> "**" + substring + "**";
            case "italic" -> "*" + substring + "*";
            case "code" -> "`" + substring + "`";
            default -> substring;
        };
    }

    private String applyROption(String[] option, String output){
        if (option == null || output.isEmpty() || output.equals(System.lineSeparator())){
            return output;
        }
        String[] oldlines = output.split(System.lineSeparator(), -1);
        String[] lines = new String[oldlines.length - 1];
        System.arraycopy(oldlines, 0, lines, 0, lines.length);
        Collections.reverse(Arrays.asList(lines));
        return String.join(System.lineSeparator(), lines) + System.lineSeparator();
    }
}
