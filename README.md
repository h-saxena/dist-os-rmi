# dist-os-rmi

# Go to the root folder of the project
...\dist-os-rmi

## ---------- If running both the modules on same host -----------------

# Run the PC Module 
...\dist-os-rmi>java -Dport=3000 -cp ./build-step2 edu.hems.rmi.step2.PCModuleBootup

# Then run the IO Module
...\dist-os-rmi>java -Dhost=localhost -Dport=3000 -cp ./build-step2 edu.hems.rmi.step2.IOModuleBootup

Resultant Multiplication of Matrices with be saved in ./data folder

## ---------- If running modules on same host -----------------

# Run the PC Module, let say on PG-03 
...\dist-os-rmi>java -Djava.rmi.server.hostname=PG-03 -Dport=3000 -cp ./build-step2 edu.hems.rmi.step2.PCModuleBootup

# Then run the IO Module on any other host as follow:
...\dist-os-rmi>java -Dhost=PG-03 -Dport=3000 -cp ./build-step2 edu.hems.rmi.step2.IOModuleBootup

Resultant Multiplication of Matrices with be saved in ./data folder

