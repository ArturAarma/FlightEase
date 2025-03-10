# ✈️ FlightEase – Itinerary Formatter

FlightEase is a **command-line tool** designed to **prettify** flight itineraries by converting raw administrator-formatted data into a **clean, customer-friendly format**. It simplifies **airport codes**, formats **dates/times**, and enhances readability.

---

## 🚀 Features
✅ Reads and processes **raw flight itinerary data**  
✅ Converts **IATA/ICAO airport codes** into full airport names  
✅ Formats **dates and times** into a readable format  
✅ Trims **excess whitespace** and cleans up output  
✅ Provides **error handling** for missing/invalid input  

---

## 🛠 Installation & Setup
### **Prerequisites**
- **Java 17+** installed (`java -version` to check)
- Terminal/Command Line access

### **Clone the Repository**
```sh
git clone https://github.com/ArturAarma/FlightEase.git
cd FlightEase
```

---

## 🎯 Usage
Run the program from the command line using:
```sh
java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv
```

### **Arguments**
| Argument | Description |
|----------|------------|
| `./input.txt` | Path to the raw itinerary file |
| `./output.txt` | Path where the formatted itinerary will be saved |
| `./airport-lookup.csv` | CSV file containing airport codes & names |

### **Help Command**
Display usage instructions:
```sh
java Prettifier.java -h
```

---

## 📄 Input Format
The input file contains **unformatted flight details** generated by back-office systems. Example:
```
Passenger: John Doe  
Flight: #LAX → ##EGLL  
Date: D(2023-06-15T14:45-07:00)  
Departure Time: T12(2023-06-15T14:45-07:00)  
Arrival Time: T24(2023-06-16T06:30+01:00)  
```

---

## ✅ Output Format
After processing, FlightEase produces a **customer-friendly itinerary**:
```
Passenger: John Doe  
Flight: Los Angeles International Airport → London Heathrow Airport  
Date: 15-Jun-2023  
Departure Time: 2:45PM (-07:00)  
Arrival Time: 06:30 (+01:00)  
```

---

## ⚠️ Error Handling
| Scenario | Behavior |
|----------|------------|
| Missing arguments | Displays usage instructions |
| Missing `input.txt` | Shows `Input not found` |
| Missing `airport-lookup.csv` | Shows `Airport lookup not found` |
| Malformed airport data | Shows `Airport lookup malformed` |

---

## 🚀 Future Enhancements
- 🔹 Implement some UI 
 

---

## 🤝 Contributing
Want to improve **FlightEase**? Contributions are welcome!  
1. **Fork** the repo  
2. **Create** a feature branch (`git checkout -b feature-xyz`)  
3. **Commit** your changes (`git commit -m "Added feature XYZ"`)  
4. **Push** to your fork (`git push origin feature-xyz`)  
5. Open a **Pull Request** 🚀  



