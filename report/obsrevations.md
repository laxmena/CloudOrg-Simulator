# Other Observations

## IAAS vs SAAS vs PAAS
![Comparison](assets/cloud.png)

Source: [Redhat](https://www.redhat.com/cms/managed-files/iaas-paas-saas-diagram5.1-1638x1046.png)

This image is used as a reference to build this project.

## Utilization Ratio

Utilization Ratio is a very important factor in the simulations. Understanding Utilization Ratio and configuring proper utlization ration was challenging till proper understanding was reached.

Initial Utilization ratio is the percentage that we configure in the simulation. If the Minimum utilization ratio is 15%, then 15% of the resources on the VM it is executing will be allocated the Cloudlet.

If 10 cloudlets are passed to a VM to be executed, then the Initial Utilization ratio should be 10% in order to execute all the cloudlets at the same time.

If the initialUtilization ratio is 20% for each cloudlet, then 5 cloudlet will execute simultaneously, and the remaining cloudlets will wait in the queue for resources to be released.

## Resource Allocation 

VM with higher requirements than any available Host will not be assigned to a Host, and the simulator will fall in an infinite loop to find a suitable host(in case of Default implementations of Vm Allocators and Brokers)

Cloudlet requirements that are higher than any available VM's will not be assigned and will fall into an infinite loop.

MIPS of the cloudlets can be higher than that of VMs. Execution time is calculated by (Cloudlet length in mips) / (VM mips).

## TimeShared vs SpaceShared Execution time

In multiple instances, the same configurations when executed with TimeShared scheduling resulted in different Cloudlet finish times.
This is reasonable, as the CPU resource is shared by multiple cloudlets, and we dont have control on which order it executes, it results in different finish times.

But in SpaceShared scheduling, two cloudlets executing at the same time will finish at the same time.

## Network CloudSim Plus and Message Passing Implementations

Network CloudSim Plus library module helps computing the network costs within the datacenter. This impact can be seen especially with Data Locality policies are employed.

NetworkCloudlets, NetworkDatacenter and NetworkHosts are extenions on top of the CloudSim implementations.

## Custom Policies

Custom policies can be created by two methods, one is to implement the interfaces(eg. Datacenter, DatacenterBroker, CloudletScheduler, etc).
Other way is to extend the preexisting implementations(Eg. CloudletSimple, VmSimple, DatacenterSimple, VmAllocationPolicySimple, etc) and override the methods that we want to modify.

[<< Back to Index](README.md)