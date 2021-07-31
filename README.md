# private-investigator

This application receives an input file describing the notes of a private investigator following his target.
The file has many sentences, ordered by time, one in a row, using consistent phrasing to describe what is going on.
<br>**Example:**
```
01-01-2012 19:45:00 Naomi is getting into the car
01-01-2012 20:12:39 Naomi is eating at a restaurant
02-01-2012 09:13:15 George is getting into the car
```
The output of the application is groups of similar sentences (sentences where only a single word differ between them) and extracts the changes, then outputs them to a file **in the following format:** 
```
01-01-2012 19:45:00 Naomi is getting into the car
02-01-2012 09:13:15 George is getting into the car
The changing word was: [Naomi, George]

Following sentences had no similar sentences:
01-01-2012 20:12:39 Naomi is eating at a restaurant
```

## Overview of the solution: 

The application reads the input file line by line, and creates and populate the following Maps: 
1. **sentencesMap** - using the time-stamp as key and the entire row as the value
2. **permutationsMap** - sentence permutation, with one removed word, as key and all similar sentences as values (with additional metha data for each similar sentence)

so, for example, for the input line: 
```
01-01-2012 19:45:00 Naomi is getting into the car
```
sentencesMap will be populated with the following item: 
```
{01-01-2012 19:45:00=01-01-2012 19:45:00 Naomi is getting into the car}
```
permutationsMap will be populated with the following items:
```
{isgettingintothecar=[…timestamp=01-01-2012 19:45:00, … removedWord=Naomi…], 
naomigettingintothecar=[… timestamp=01-01-2012 19:45:00, …  removedWord=is…], 
naomiisgettingthecar=[… timestamp=01-01-2012 19:45:00, … removedWord=into…], 
naomiisgettingintocar=[… timestamp=01-01-2012 19:45:00, … removedWord=the…], 
naomiisintothecar=[… timestamp=01-01-2012 19:45:00, … removedWord=getting…], 
naomiisgettingintothe=[… timestamp=01-01-2012 19:45:00, … removedWord=car…]}
```

when analyzing the next line, e.g. ```02-01-2012 09:13:15 George is getting into the car```, the permutations map will be updated with all its permutations - new items will be created, but for the permutation  **isgettingintothecar** - the item will be updated with additional row:
```
{isgettingintothecar=[[…timestamp=01-01-2012 19:45:00, … removedWord=Naomi…], […timestamp=02-01-2012 09:13:15, … removedWord=George…]]....}
```

once finished reading the input, the app iterates over the permutationsMap, and for each permutation item that contains more then one value - meaning similar sentences were found - it will output its information, including the list of changing words (e.g. [Naomi, George]). In addition, the these sentences will be deleted from sentencesMap, and at the end it will only contain sentences with no similarities, so they will be groupd togeter in output.

## How to run?

1. download the code and build it using Maven - cd to the **/private-investigator** directory, where the pom.xml file resides, and run the maven command:
```  mvn clean install ```
- alternatively, download the **private_investigator.jar** from within this repository: [omachlev/private-investigator/JAR](https://github.com/omachlev/private-investigator/tree/master/JAR)
2. after build finishes successfuly, cd to **/target** directory, where the **private_investigator.jar** file was created
3. run the app with the following command, without providing the path to the input file, to use the default input.txt file loacted in input directory:
```
java -jar private_investigator.jar
```
or
run the app with the following command, providing the absolute or relative path to the input file:
```
java -jar private_investigator.jar /Users/<USER>/Documents/eclipse-workspace/private-investigator/input/input.txt
```
```
java -jar private_investigator.jar ../input/input.txt
```

#### the console output should look like:

```
Input file will be analyzed: ../input/input.txt

*** Application Private-Investigator started ***
- Start reading information from input file: ../input/input.txt
- Finished reading information from input file: ../input/input.txt
- Starting to analyze received input...
- Finishid anlyzing Input file - Number of rows analyzed: 8
- Starting to write results to output file
- Finished writing results to output file: ./output_1627766035154.txt
*** Application Private-Investigator finished ***
```

#### and the output file (e.g. output_1627766035154.txt) will be created at the directory where the app was run, e.g. /private-investigator/target


### Code Complexity
1. What can you say about the complexity of your code? 


### Scaling
2. How will your algorithm scale?

### Better implementation/s
3. If you had two weeks to do this task, what would you have done differently? What would be better?


