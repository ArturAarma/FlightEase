import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FixDate {

    public static void parseDate(String inputPath, String outputPath) {
        String tempFilePath = outputPath + ".tmp"; 
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath))) {

            String line;

            
            while ((line = reader.readLine()) != null) {
                
                DateTimeFormatter inputFormatterZ = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX"); 
                DateTimeFormatter inputFormatterOffset = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmXXX"); 
                DateTimeFormatter simpleFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy"); 
                DateTimeFormatter amFormatter = DateTimeFormatter.ofPattern("hh:mma (XXX)"); 
                DateTimeFormatter H24Formatter = DateTimeFormatter.ofPattern("HH:mm (XXX)"); 

                StringBuilder updatedLine = new StringBuilder();

               
                String[] words = line.split("\\s+");


                

                for (String word : words) {
                    try {
                        if (word.startsWith("D(") && word.endsWith(")")) {
                          
                            String cleanedWord = word.substring(2, word.length() - 1);
                            ZonedDateTime dateTime = ZonedDateTime.parse(cleanedWord, inputFormatterZ);
                            String formattedDate = dateTime.format(simpleFormatter);
                            updatedLine.append(formattedDate).append(" ");
                        } else if (word.startsWith("T12(") && word.endsWith(")") && word.contains("Z")) {
                           
                            String cleanedWord = word.substring(4, word.length() - 1);
                            ZonedDateTime dateTime = ZonedDateTime.parse(cleanedWord, inputFormatterZ);
                            String formattedDate = dateTime.format(amFormatter).replace("Z", "+00:00").toUpperCase();
                            updatedLine.append(formattedDate).append(" ");
                        } else if (word.startsWith("T12(") && word.endsWith(")")) {
                           
                            String cleanedWord = word.substring(4, word.length() - 1);
                            ZonedDateTime dateTime = ZonedDateTime.parse(cleanedWord, inputFormatterOffset);
                            String formattedDate = dateTime.format(amFormatter).toUpperCase();
                            updatedLine.append(formattedDate).append(" ");
                        } else if (word.startsWith("T24(") && word.endsWith(")") && word.contains("Z")) {
                           
                            String cleanedWord = word.substring(4, word.length() - 1);
                            ZonedDateTime dateTime = ZonedDateTime.parse(cleanedWord, inputFormatterZ);
                            String formattedDate = dateTime.format(H24Formatter).replace("Z", "+00:00");
                            updatedLine.append(formattedDate).append(" ");
                        } else if (word.startsWith("T24(") && word.endsWith(")")) {
                           
                            String cleanedWord = word.substring(4, word.length() - 1);
                            ZonedDateTime dateTime = ZonedDateTime.parse(cleanedWord, inputFormatterOffset);
                            String formattedDate = dateTime.format(H24Formatter);
                            updatedLine.append(formattedDate).append(" ");
                        } else {
                           
                            updatedLine.append(word).append(" ");
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Failed to parse: " + word);
                        updatedLine.append(word).append(" ");
                    }
                }

                
                writer.write(updatedLine.toString().trim());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("ERROR: File operation failed.");
            e.printStackTrace();
            return;
        }

       
        replaceFile(outputPath, tempFilePath);
    }

   
    private static void replaceFile(String originalFilePath, String tempFilePath) {
        File originalFile = new File(originalFilePath);
        File tempFile = new File(tempFilePath);

        if (originalFile.delete()) {
            if (!tempFile.renameTo(originalFile)) {
                System.out.println("ERROR: Failed to rename temporary file to original.");
            }
        } else {
            System.out.println("ERROR: Failed to delete the original file.");
        }
    }
}
