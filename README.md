# Change Instepector Java
This project is on inspecting change types from commits.
![](/figures/ChangeInspectorJava.png)

## Running PyDriller for change collection
For PyDriller you need Python 3.x. It is recommended to use Anaconda for installing dependecies.

To run Pydriller, you can simply use the command below:

`python find_changed_codes.py`

## Running Change Inspectore Java
To run Change Inspector Java (CIJ), make sure you have collected the changed files extracted from PyDriller. You will need two folders; one for `source codes before change` and one for `source codes after change`. These two folders should have the Java source codes for parse tree generation.

### Setting Parameters for Change Inspector Java
Running CIJ is very simple and very few parameters needs to be set. All the required parameters should be set in the ``Main`` class given below.
```java
cij.Main.java
```
There are two variables that need to be set: 
* **Set path to source files:** To set the path to the source files you need to use modify the varible given below in the main function, which is currently set to `commons-csv_data` in the source code by default.  
```java 
String dataFileName = "commons-csv_bug_fix_data";
``` 
This path should contain data obtained from PyDriller and the folder hierarchy should follow the example given below: 

`python/[project_bug_fix_data]/[commit-hash-value]/before/[set of .java files]`

`python/[project_bug_fix_data]/[commit-hash-value]/after/[set of .java files]` 

For example for project `commons-csv` and for hash value `1a7c6140825bd7b3abe73c5dd732b090acc84b61` the folder structure would look like the path given below:
 
`python/commons-csv_bug_fix_data/1a7c6140825bd7b3abe73c5dd732b090acc84b61/before/*.java` 

and 

`python/commons-csv_bug_fix_data/1a7c6140825bd7b3abe73c5dd732b090acc84b61/after/*.java` 

* **Set report file name and/or directory:** Currently the change type report will create a file called `changeReport.txt`. If you want to change the file report name and directory, you will have to modify the code below from the main function: 
```java
BufferedWriter bw = new BufferedWriter(new FileWriter("changeReport.txt"));
```

## Dataset
Dataset is organized as the following:

```
change_reports
├── changeReport_commons-csv.txt
└── ...
python
├── find_changed_codes.py
├── bug_and_fix_data
|   ├── commons-csv
|   |   └── active-bugs.csv
|   └── ...
├── commons-csv_bug_fix_data
|   ├── 1282503fb97d621b4225bd031757adbfada66181
|   |   ├── after
|   |   |   ├── CSVFormat.java
|   |   |   ├── CSVPrinter.java
|   |   |   └── CSVPrinterTest.java
|   |   └── before
|   |       ├── CSVFormat.java
|   |       ├── CSVPrinter.java
|   |       └── CSVPrinterTest.java
|   └── ...
└── ...
```
