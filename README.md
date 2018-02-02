# dist-os-rmi

# Go to the root folder of the project
...\dist-os-rmi

### --- Compilation of Step2 ----------------------------------------------------
1) mkdir build-step2

2) javac -d build-step2 src-step2/edu/hems/rmi/step2/*.java src-step2/edu/hems/rmi/step2/io/*.java src-step2/edu/hems/rmi/step2/io/utils/*.java src-step2/edu/hems/rmi/step2/pc/*.java src-step2/edu/hems/rmi/step2/pc/util/*.java src-step2/edu/hems/rmi/step2/service/*.java


### --- Execution of Step2 ------------------------------------------------------

--- If running both the modules on same host -----------------

- Run the PC Module 
...\dist-os-rmi>java -Dport=3000 -cp ./build-step2 edu.hems.rmi.step2.PCModuleBootup

- Then run the IO Module and follow the prompt
...\dist-os-rmi>java -Dhost=localhost -Dport=3000 -cp ./build-step2 edu.hems.rmi.step2.IOModuleBootup

Resultant Multiplication of Matrices with be saved in ./data folder

--- If running modules on same host --------------------------

- Run the PC Module, let say on PG-03 
...\dist-os-rmi>java -Djava.rmi.server.hostname=PG-03 -Dport=3000 -cp ./build-step2 edu.hems.rmi.step2.PCModuleBootup

- Then run the IO Module on any other host as follow and follow the prompt:
...\dist-os-rmi>java -Dhost=PG-03 -Dport=3000 -cp ./build-step2 edu.hems.rmi.step2.IOModuleBootup

Auto generated Matrices and Resultant Multiplication Matrix with be saved in ./data folder and will also be output on the console.
Determinant will be output on the console.

