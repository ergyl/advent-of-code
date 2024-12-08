Taking part in the annual Advent of Code (https://www.adventofcode.com).

This repository contains my solutions to the Advent of Code challenges. 
The program allows you to set up and solve the daily tasks. 

## Requirements 
- Java 17+
- Maven

## Build the project
1. Navigate to the project root directory and run `mvn clean install`

## Run the project
After the project has been successfully built, you can run the program in two ways, either

### Without arguments  
To setup and run challenge of the current day), run: `mvn exec:java`  
This will:
    • Run the `setupDay()` method.  
    • Download the input data (if needed).  
    • Set up the skeleton solution files for the current day.  

### With argument  
If you want to solve a specific day's challenge, pass the day number as an argument.  
For example, to run Day 1 ( which is 1st of December): `mvn exec:java "-Dexec.args=1"`  
This will:  
    • Run the `solveForDay(1)` method for Day 1.
    • Use the corresponding input file `input01.txt` and solve the problem for Day 1.  
Make sure you have the necessary `inputXX.txt` files in the `src/main/resources/input` folder. If they do not exist, the program will try to download them (requires a valid session ID and email in the `config.txt` file).  

** Note!! ** Must add the new classfile to SOLVER_MAP in `DaySolverFactory` before you can run the solution.

## Automation guidelines
This repo follows the automation guidelines on the /r/adventofcode community wiki ([https://www.reddit.com/r/adventofcode/wiki/faqs/automation]). Specifically:
- Outbound calls are throttled to every 5 minutes in `downloadInput()`
- Once inputs are downloaded, they are cached locally (`inputExists()` returns `true`)
- If you suspect your input is corrupted, you can manually request a fresh copy by deleting the corresponding .txt file in `src/main/resources/input`, e.g. `day04.txt` is the input file for `Day04.java`
- The User-Agent header in `getInput()` is dynamically set by pulling email address and session Id from `config.txt`

# Please note
**Note!** The `config.txt` file is not included in this repo so that must be added manually.
