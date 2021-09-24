# Project Structure of CloudOrg Simulator

## Project Tree:
```
src:.
├───main
│   ├───resources
│   │   ├───configuration
│   │   ├───experiment
│   │   ├───simulation1
│   │   ├───simulation2
│   │   ├───simulation3
│   │   ├───simulation4
│   │   ├───simulation5
│   │   └───simulation6
│   └───scala
│       ├───Experimentations
│       ├───HelperUtils
│       │   └───ConfigModels
│       └───Simulations
└───test
    ├───resources
    └───scala
        └───Simulations
```

## Descriptions

#### main 
Contains the source code and resources of CloudOrg Simulator

#### main/resources
- Directory contains configuration files that are provided to the CloudOrg Simulator to run simulations.
- [**configuration**](https://github.com/laxmena/CloudOrg-Simulator/tree/main/src/main/resources/configuration) sub-directory contains organization config files like instances.conf (it defines VM configurations, modeled after AWS Instance types), and saasCloudlets (Contains Cloudlet configurations).
- [**experiment**](https://github.com/laxmena/CloudOrg-Simulator/tree/main/src/main/resources/experiment) Contains config files that I used to understand and experiment with CloudSim Plus.
- **Simulation** directories each contain the config files for the simulations.
- **application.conf** is the master config file, and all other config files are imported here.

#### scala
Directory contains Simulation source files. Simulations are invoked from the Scala files in this repository.

#### scala/Experimentations
Scala files in this directory were used to get aquainted with CloudSim Plus library. The configrations were experimented and modified to understand how each input works.

#### scala/HelperUtils
- Helper utils contain classes like **CommonUtil**, **OrgUtils** and **ClientUtils** which hosts function specific to their usecases.
- CreateLogger and ObtainConfigReference helps create Logger and get config getter.
- [**ConfigModels**](https://github.com/laxmena/CloudOrg-Simulator/tree/main/src/main/scala/HelperUtils/ConfigModels) directory contains Case Classes, which defines the structure of the input config files.
- Contains [Simulator](https://github.com/laxmena/CloudOrg-Simulator/tree/main/src/main/scala/HelperUtils/Simulator.scala), a HelperUtil which helps users with simulating the input config files. 

#### test
Contains test suites and test resources

[<< Back to Index](README.md)