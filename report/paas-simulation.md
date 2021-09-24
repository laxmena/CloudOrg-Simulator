# PAAS DataCenter Simulation

Following factors are considered for this simulation project.

#### PAAS - Level of Control:

&nbsp;&nbsp; **Org:** Host Configurations, Network Topology, VMs Configurations, Allocation Policies.

&nbsp;&nbsp; **Client:** Cloudlets to be deployed in the VMs, and number of Cloudlets

## Simulation Details:

- Resources/Configurations of the PAAS Organization: [org.config](https://github.com/laxmena/CloudOrg-Simulator/blob/main/src/main/resources/simulation2/org.conf)
- List of resources Client requests to the PAAS Organization: [client.config](https://github.com/laxmena/CloudOrg-Simulator/blob/main/src/main/resources/simulation2/client.conf)

PAAS Organization has DataCenters with predefined hosts and VMs already created. 

Client requests to run custom cloudlets on the PAAS Datacenters.

For example: Client's PHP Project is a cloudlet, and the client can request the AWS for PAAS Service, and host their project on a LAMP Stack offered by AWS.

Here is an example of the PAAS Client request to the Organization: 
```
paas = [{
    cloudlets = [{
        count = 3
        pes = 3 
        length = 2000 #mips
        size = 1028
    }]
}]
```
This example represents Client request to run 3 cloudlets, where each cloudlet uses 3 PEs(CPU Cores), with 2000 million instructions per second and size of 1GB.

## Results:
![PAAS DataCenter Simulation Result](assets/Simulation2/paas.PNG)


| description | value |
|-------------|-------|
| Number of Cloudlets  | 12 |
| Number of VMs | 6 |
| Execution Time | 5.32s |
| Processing Cost | $0.40 |
| Memory Cost | $170.24 |
| Storage Cost | $37.00 |
| Bandwidth Cost | $125.10 |
| Total | $332.74 |

## Observation:

In PAAS Organization will provide Platforms to the customers, on top of which the clients can run their own modules.
PAAS Organization will manage the VM's, Hosts for the Client, and Client can just send their cloudlets to be executed on PAAS servers.

PAAS Organizations will take care of horizontal and vertical scaling of resources, when there is a burst of load for the clients cloudlet. 
Clients pay for the additional resources used.

[<< Back to Index](README.md)