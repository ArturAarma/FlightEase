import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CsvParser {

    public static List<HashMap<String, String>> airportNameParser(String csvFilePath) throws IllegalArgumentException {
        HashMap<String, String> airportNames = new HashMap<>();
        HashMap<String, String> municipalNames = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String header = reader.readLine();
           

            if (header == null) {
                throw new IllegalArgumentException("CSV file is empty or missing a header.");
            }

            String[] columns = header.split(",");
            int nameIndex = -1;
            int municipalityIndex = -1;
            int icaoIndex = -1;
            int iataIndex = -1;

            for (int i = 0; i < columns.length; i++) {
                switch (columns[i].trim().toLowerCase()) {
                    case "name":
                        nameIndex = i;
                        break;
                    case "municipality":
                        municipalityIndex = i;
                        break;
                    case "icao_code":
                        icaoIndex = i;
                        break;
                    case "iata_code":
                        iataIndex = i;
                        break;
                }
            }

            

            if (nameIndex == -1 || municipalityIndex == -1 || icaoIndex == -1 || iataIndex == -1) {
                throw new IllegalArgumentException("CSV file is malformed. Required columns are missing.");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",", -1);
                if (row.length <= Math.max(nameIndex, Math.max(municipalityIndex, Math.max(icaoIndex, iataIndex)))) {
                    continue;
                }

                String airportName = row[nameIndex].trim();
                String municipality = row[municipalityIndex].trim();
                String icao = row[icaoIndex].trim();
                String iata = row[iataIndex].trim();

                if (!icao.isEmpty()) airportNames.put(icao, airportName);
                if (!iata.isEmpty()) airportNames.put(iata, airportName);
                if (!icao.isEmpty()) municipalNames.put(icao, municipality);
                if (!iata.isEmpty()) municipalNames.put(iata, municipality);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read CSV file: " + e.getMessage());
        }

        List<HashMap<String, String>> maps = new ArrayList<>();
        maps.add(airportNames);
        maps.add(municipalNames);
        return maps;
    }
}
