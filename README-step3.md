# dist-os-rmi

# Go to the root folder of the project
...\dist-os-rmi

### --- Compilation of Step3 ----------------------------------------------------
1) mkdir build-step3

2) javac -d build-step3 src-step3/edu/hems/rmi/step3/*.java src-step3/edu/hems/rmi/step3/io/*.java src-step3/edu/hems/rmi/step3/io/utils/*.java src-step3/edu/hems/rmi/step3/pc/*.java src-step3/edu/hems/rmi/step3/pc/util/*.java src-step3/edu/hems/rmi/step3/service/*.java  src-step3/edu/hems/rmi/step3/model/*.java  src-step3/edu/hems/rmi/step3/worker/*.java


### --- Execution of step3 ------------------------------------------------------

--- If running all the modules on same host -----------------

- Run the PC Module 
...\dist-os-rmi>java -Dport=3000 -cp ./build-step3 edu.hems.rmi.step3.PCModuleBootup

- Then run the Worker Module and follow the prompt to setup number of workers on a given host
- Important to note that all the worker remote objects bind to Registry created in PC Module
...\dist-os-rmi>java -Dhost=localhost -Dport=3000 -cp ./build-step3 edu.hems.rmi.step3.WorkerModuleBootup


- Then run the IO Module and follow the prompt
...\dist-os-rmi>java -Dhost=localhost -Dport=3000 -cp ./build-step3 edu.hems.rmi.step3.IOModuleBootup

Resultant Multiplication of Matrices with be saved in ./data folder

--- If running modules on different hosts --------------------------

- Run the PC Module, let say on PG-03 
...\dist-os-rmi>java -Djava.rmi.server.hostname=PG-03 -Dport=3000 -cp ./build-step3 edu.hems.rmi.step3.PCModuleBootup

- Then run the Worker Module and follow the prompt to setup number of workers on a given host
- Important to note that all the worker remote objects bind to Registry created in PC Module
...\dist-os-rmi>java -Dhost=PG-03 -Dport=3000 -cp ./build-step3 edu.hems.rmi.step3.WorkerModuleBootup


- Then run the IO Module on any other host as follow and follow the prompt:
...\dist-os-rmi>java -Dhost=PG-03 -Dport=3000 -cp ./build-step3 edu.hems.rmi.step3.IOModuleBootup

Auto generated Matrices and Resultant Multiplication Matrix with be saved in ./data folder and will also be output on the console.
Determinant will be output on the console.

