# Prettifier

## Overview
Prettifier is a robust command-line tool designed to clean and process input text files for readability and formatting. It replaces codes (like airport and municipal codes) with meaningful names, formats dates into human-readable formats, and ensures output files are properly cleaned of excessive blank lines and malformed data.

This tool is particularly useful for processing travel itineraries, logs, or any structured text file requiring formatted replacements and cleanup.

---

## Features
### 1. **Error Handling**
- **Input Validation**: If the input file or the CSV lookup file is missing, the program exits immediately with an error message.
- **CSV Validation**: Detects malformed CSV files and exits without creating or modifying the output file.
- **Safe Output Creation**: Ensures the output file is created or overwritten only if processing is fully successful.

### 2. **Processing Capabilities**
- **Code Replacement**:
  - Supports replacements for:
    - `#<code>`: Replaces with the corresponding airport name.
    - `##<code>`: Replaces with the corresponding municipal name.
    - `*#<code>`: Replaces with alternative mappings if available.
  - Codes not found in the CSV are left unchanged.
- **Date Formatting**:
  - Converts dates and times from ISO 8601 format to a human-readable format (e.g., `2023-11-30T12:00Z` → `30 Nov 2023, 12:00 PM`).
- **Blank Line Cleanup**:
  - Consolidates multiple blank lines into a single blank line.
  - Normalizes vertical whitespace characters (`\v`, `\f`, `\r`) to standard newline characters (`\n`).

### 3. **Output Protection**
- Utilizes a temporary output file during processing.
- Replaces the original output file only if processing is successful.
- Cleans up temporary files in the event of an error.

---

## Usage
### Command Syntax
```bash
java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv
#   F l i g h t E a s e  
 