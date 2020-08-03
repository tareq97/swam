### Manual sample outputs of the path coverage:

Added an index to all the instruction in the Control Flow graph(CFG). Compared with the output instruction spilled by the coverage tool. The instruction match except for few instructions. If instruction Br_if, if and return are not considered then they match perfectly for the below mentioned data.

Please find the sample report in which the mapping of the instructions in the CFG and the instruction in the Instantiator code is given below:

The below CSV report contains the following columns :
1) method 	
2) instruction 	
3) instruction_index 	
4) hitcount 	
5) Instruction Id in CFG 	
6) Block Id as per CFG generated

[Report for Deconv](https://github.com/tareq97/swam/blob/master/results/path_coverage/Deconvolution-1D.showmap.csv) <br>

[Report for Informal data- Check if](https://github.com/tareq97/swam/blob/master/results/path_coverage/if_else-check-if.showmap.csv)
