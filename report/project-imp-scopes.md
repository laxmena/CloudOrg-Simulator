# Project Improvement Scopes

### FAAS Datacenter Simulation

FAAS Datacenter simulation can be implemented. FAAS Services are similar to AWS Lambda functions.

In FAAS, the client will be able to provide the multiple cloudlets with varying size and length(mips).
Client cannot configure the number of PE's to the cloudlet.

The scaling will be done, both vertically and horizontally based on the demand burst for the cloudlet.

### Custom Policy and Classes Implementation

Implement custom policies like VmAllocationPolicy, VmSchedulingPolicy, Clodulet Scheduling Policy and DataCenter Broker
to implement our own logic in allocation and selection of policies for Vms and cloudlets.

### Implement Vm Migration

Detect increased load for the components using event listeners, and implement scaling resources and vm migration.

### Simulate DataLocality

Simulate DataLocality, and network costs involved for each set of configurations.

[<< Back to Index](README.md)
