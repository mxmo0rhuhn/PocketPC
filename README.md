# PocketPC

This is a project for the Zurich university of applied sciences course 'Computer Science III'

It provides a simple (graphical) implementation of general purpose computer that can parse a given assembler code.
Since the course is held in German all labels are german, too. Sorry for the inconvenience.

## Functionality:
A file with ASM code as well as the input data has to be provided. 
The input is assumed to be located at word 500 of the virtual RAM. The commands will be read into the virtual RAM beginning at wort 500.

While processing the ASM the following values are displayed:
- Program counter
- Current command (+ / - 10 next commands)
- Akkumulator
- Register 1 - 3
- Carrr-Bit
- Content of the filled virtual RAM from word 500 on

All registers are 16 bit long and beeing displayed binary and as decimal value.

The processing of the ASM can happen in 3 modes:
* STEP:
Every command has to be processed with an individual click.
* SLOW:
The commands are beeing processed automaticly (can be paused).
* FAST:
No output of the registers etc. until the computation is terminated.

## Usage:
You can provide a parameter `nogui` for GUI or Commandline interface. The default is false (Application is started with GUI).
If started without GUI the following parameters should be provided:
* asm = Asembler file to parse
* BLOCKS_PER_LINE = Number of ASM commands displayed per line (default = 5)
* mode = One of the three modes described above

## Example:
### Without GUI:
Sample call:
```
java -Dmode=STEP -Dasm=asmfiles/multiplication.asm -Dnogui=true -DBLOCKS_PER_LINE=5 -jar PocketPC-0.1-SNAPSHOT-jar-with-dependencies.jar 
```
The current step beeing displayed on the command line (to run the next Step in STEP mode just press Enter).

![alt text](https://raw.githubusercontent.com/mxmo0rhuhn/PocketPc/master/doc/Sample_computation_no_GUI.png "Computation without GUI")

### With GUI:
In the initial screen of the GUI application you can select "Optonen" and afterwards "Datei laden" to load a file in the format described above. Running the GUI you may select:
* "Nächster Schritt" for the computation of the next ASM command
* "Langsam" for the automatic computation in SLOW mode
* "Schnell" for the automatic computation in FAST mode
* "Pause" to pause a computation in SLOW mode
* "Stop" to cancel a computation

![alt text](https://raw.githubusercontent.com/mxmo0rhuhn/PocketPc/master/doc/Sample_computation_GUI.png "Computation with GUI")

Selecting the "Alle Befehle" tab you can edit the ASM and memory to see what happens in other configurations.

![alt text](https://raw.githubusercontent.com/mxmo0rhuhn/PocketPc/master/doc/edit_asm.png "Edit ASM with GUI")

If you like the new version of the code you may also store this file by selecting "Optionen" and then "Datei speichern"


## License:
This project is free software: You can redistribute it and/or modify it under the terms of the European Union Public Licence (EUPL v.1.1) or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence"); You may not use this work except in compliance with the Licence.

The project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the Licence for the specific language governing permissions and limitations under the Licence.
