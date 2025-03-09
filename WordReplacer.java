import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class WordReplacer {

    public static void processInput(String inputPath, String outputPath, HashMap<String, String> airportNames, HashMap<String, String> municipalNames) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            String line;
            boolean isBlank = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim(); 

                if (line.isEmpty()) {
                   
                    if (isBlank) {
                        continue; 
                    }
                    writer.write("\n");
                    isBlank = true; 
                } else {
                    isBlank = false; 
                    line = line.replace("\u000B", "\n").replace("\u000C", "\n").replace("\r", "\n");

                 
                    String[] words = line.split("\\s+");
                    StringBuilder updatedLine = new StringBuilder();

                    for (String word : words) {
                        char lastChar = word.charAt(word.length() - 1);
                        boolean hasPunctuation = !Character.isLetterOrDigit(lastChar);
                        String wordCore = hasPunctuation ? word.substring(0, word.length() - 1) : word;

                        String category = getCategory(wordCore);

                        switch (category) {
                            case "AIRPORT1":
                                String code = wordCore.substring(1);
                                updatedLine.append(airportNames.getOrDefault(code, wordCore));
                                break;
                            case "MUNICIPAL1":
                                code = wordCore.substring(2);
                                updatedLine.append(municipalNames.getOrDefault(code, wordCore));
                                break;
                            case "AIRPORT2":
                                code = wordCore.substring(2);
                                updatedLine.append(airportNames.getOrDefault(code, wordCore));
                                break;
                            default:
                                updatedLine.append(wordCore);
                                break;
                        }

                        if (hasPunctuation) {
                            updatedLine.append(lastChar);
                        }
                        updatedLine.append(" ");
                    }

                   
                    writer.write(updatedLine.toString().trim());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR in WordReplacer: File operation failed.");
            e.printStackTrace();
        }
    }


    private static String getCategory(String word) {
        if (word.startsWith("#") && !word.startsWith("##")) {
            return "AIRPORT1"; 
        } else if (word.startsWith("*#")) {
            return "MUNICIPAL1"; 
        } else if (word.startsWith("##")) {
            return "AIRPORT2"; 
        }
        return "NOPREFIX"; 
    }
}
