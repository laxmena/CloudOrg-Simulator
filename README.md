# CloudOrg Simulator

CloudOrg Simulator is a Cloud Simulation and analysis Project built using CloudSim Plus Library and Scala 3.0.2 

This project is also designed to be a primitive CloudSimulation Framework. This project can help save significant overhead time in writing the code part. Users can just specify the Configuration files, and the redundant process of loading and running the simulation is taken care by this framework.

**Author**: Lakshmanan Meiyappan

**Email**: lmeiya2@uic.edu

## Dependencies

- Scala 3.0.2
- SBT 1.5.2
- CloudSim Plus 6.5.0
- PureConfig 1.6.0

## How to Run CloudOrg Simulator?

### Step 1: Clone the Project
```shell
git clone https://github.com/laxmena/CloudOrg-Simulator.git
cd CloudOrg-Simulator
```

### Step 2: Setting-up the Project 
#### Option 1: IntelliJ
1. Open the Project in IntelliJ
2. Wait for the IDE to index and install dependencies
3. Navigate to the Simulation<x>.scala files in the `src\main\scala\` directory
4. Run the Simulation file

#### Option 2: Command Line and SBT 
1. Download and Install sbt v1.5.2 from [SBT Downloads Page](https://www.scala-sbt.org/download.html)
2. Open the Project directory `CloudOrg-Simulator/` in the terminal
3. Execute the following commands
    ```shell
   sbt clean
   sbt compile
   ```
4. Select the Simulation that you want to run, and get the name of the main function of the simulation.

    ```shell
    sbt runMain runSimulation1 
    ```

## Documentation

Find the ScalaDoc Documentation of this project here: [CloudOrg-Simulator Documentation](https://laxmena.github.io/CloudOrg-Simulator/).

## Report

This project was done as part of **CS441 Engineering Distributed Objects For Cloud Computing** under Professor [Dr.Mark Grechanik](https://www.cs.uic.edu/~drmark) at the University of Illinois at Chicago.

Find detailed observations, results and more information about project design here: **[CloudOrgSimulator - Report](report/README.md)**

## Project Overview

### List of Simulations
1. Infrastructure as a Service (IAAS)
2. Platform as a Service (PAAS)
3. Software as a Service (SAAS)
4. Combination of IAAS, PAAS and SAAS in same Cloud Organization
5. Comparison of Different VM Allocation Policies (BestFit vs RoundRobin vs FirstFit vs Simple)
6. Comparison of Different Scheduling Policies (TimeShared vs SpaceShared vs (TimeShared + SpaceShared))

### Implementation Details

Two parties are involved in the simulations
1. Cloud Organization (Org)
2. Client

Following factors are considered for this simulation project.

#### IAAS - Level of Control:

&nbsp;&nbsp; **Org:** Host Configurations, Network Topology, Allocation Policies.

&nbsp;&nbsp; **Client:** Select VMs, number of VMs, OS, Cloudlets to be deployed in the VMs, Cloudlet Scheduling Policies, etc.

#### PAAS - Level of Control:

&nbsp;&nbsp; **Org:** Host Configurations, Network Topology, VMs Configurations, Allocation Policies.

&nbsp;&nbsp; **Client:** Cloudlets to be deployed in the VMs, and number of Cloudlets

#### SAAS - Level of Control:

&nbsp;&nbsp; **Org:** Host Configurations, Network Topology, Allocation Policies, VM Configurations, Cloudlet Configrations.

&nbsp;&nbsp; **Client:** Number of Cloudlets, and which cloudlet the user is interested to use.

## References

- [CloudSim Plus](https://cloudsimplus.org/)
- [CloudSim Plus Examples](https://github.com/manoelcampos/cloudsimplus/tree/master/cloudsim-plus-examples)
- [PureConfig Library](https://github.com/pureconfig/pureconfig)
- CloudSim Plus: A Cloud Computing Simulation Framework Pursuing Software Engineering Principles for Improved Modularity, Extensibility and Correctness
- NetworkCloudSim: Modelling Parallel Applications in Cloud Simulations

## Other Resources used
- Educative.io [Learn Scala from Scratch](https://www.educative.io/courses/learn-scala-from-scratch) 
- [Rock The JVM Scala in Light Speed](https://www.youtube.com/watch?v=-8V6bMjThNo)
- [AWS Instance Types](https://aws.amazon.com/ec2/instance-types/)
- [AWS EBS](https://aws.amazon.com/ebs/features/)