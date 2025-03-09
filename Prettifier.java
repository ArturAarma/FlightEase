import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

public class Prettifier {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("-h")) {
            printUsage();
            return;
        }

        if (args.length != 3) {
            System.out.println("\u001B[31m" + "Error: Incorrect number of arguments." + "\u001B[37m");
            printUsage();
            System.exit(1);
        }

        String inputPath = args[0];
        String outputPath = args[1];
        String tempOutputPath = outputPath + ".tmp";
        String csvFilePath = args[2];

        validateFile(inputPath, "\u001B[31m" + "Error: Input file not found." + "\u001B[37m");
        validateFile(csvFilePath, "\u001B[31m" + "Error: Airport lookup file not found." + "\u001B[37m");

        List<HashMap<String, String>> maps = parseCsvFile(csvFilePath);
        if (maps == null) {
            System.out.println("\u001B[31m" + "Airport lookup file is malformed." + "\u001B[37m");
            System.exit(1);
        }

        HashMap<String, String> airportNames = maps.get(0);
        HashMap<String, String> municipalNames = maps.get(1);

        
        if (airportNames.isEmpty() || municipalNames.isEmpty()) {
            System.out.println("WARNING: Parsed maps are empty. Check CSV file.");
        }

        try {
            WordReplacer.processInput(inputPath, tempOutputPath, airportNames, municipalNames);
        } catch (Exception e) {
            System.out.println("\u001B[31m" + "Error: Failed to process input file." + "\u001B[37m");
            deleteFile(tempOutputPath);
            System.exit(1);
        }

        try {
            FixDate.parseDate(tempOutputPath, tempOutputPath);
        } catch (Exception e) {
            System.out.println("\u001B[31m" + "Error: Failed to parse dates in output file." + "\u001B[37m");
            deleteFile(tempOutputPath);
            System.exit(1);
        }

        if (filesAreIdentical(new File(inputPath), new File(tempOutputPath))) {
            System.out.println("\u001B[33m" + "No changes were made. Output file was not created." + "\u001B[37m");
            deleteFile(tempOutputPath);
        } else {
            if (replaceFile(outputPath, tempOutputPath)) {
                System.out.println("\u001B[32m" + "Processing complete. Check the output file." + "\u001B[37m");
            } else {
                System.out.println("\u001B[31m" + "Error: Failed to create output file." + "\u001B[37m");
                deleteFile(tempOutputPath);
                System.exit(1);
            }
        }
    }

    private static void validateFile(String filePath, String errorMessage) {
        if (!new File(filePath).exists()) {
            System.out.println(errorMessage);
            System.exit(1);
        }
    }

    private static List<HashMap<String, String>> parseCsvFile(String csvFilePath) {
        try {
            return CsvParser.airportNameParser(csvFilePath);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static boolean replaceFile(String originalFilePath, String tempFilePath) {
        try {
            Files.move(new File(tempFilePath).toPath(), new File(originalFilePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.out.println("\u001B[31m" + "Error: Could not replace file." + "\u001B[37m");
            return false;
        }
    }

    private static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && !file.delete()) {
            System.out.println("\u001B[31m" + "Error: Failed to delete temporary file." + "\u001B[37m");
        }
    }

    private static boolean filesAreIdentical(File file1, File file2) {
        try {
            return Files.mismatch(file1.toPath(), file2.toPath()) == -1;
        } catch (IOException e) {
            System.out.println("\u001B[31m" + "Error: Could not compare files." + "\u001B[37m");
            return false;
        }
    }

    private static void printUsage() {
        System.out.println("\u001B[33m" + "Usage:" + "\u001B[37m");
        System.out.println("\u001B[33m" + "java Prettifier <inputPath> <outputPath> <csvFilePath>" + "\u001B[37m");
        System.out.println("\u001B[33m" + "Example:" + "\u001B[37m");
        System.out.println("\u001B[33m" + "java Prettifier ./input.txt ./output.txt ./airport-lookup.csv" + "\u001B[37m");
    }
}
