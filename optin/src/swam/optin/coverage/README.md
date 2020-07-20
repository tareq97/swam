# Wasmcov - Coverage Tool for WebAssembly byte code

#### Detailed Summary on Working of Coverage tool

##### What does the tool does not cover?
 The tool 

#### Example reports with Wasi and without Wasi

#### Coverage tool Features

Currently, the coverage tool has all the options available, similar to the run command(--help, --wat,--wasi, --time, --dir ,--traces, --trace-file,--debug, --main), except for the options flag mentioned below that are added to provide the output path for the coverage reports and to filter the wasi-libc methods from the report and showmap.  

--covout <PATH> - Coverage reports are generated in the output path provided within the new directory cov_results which is a static folder created to hold all the results.

--covf - Enables the filter and provides the coverage report and showmap without the Wasi Methods.

###### Generic Command
```
./mill cli.run coverage --covf <True/false> <br /> 
--covout <Path / Default Path if not provided> <br /> 
--wat<if executing coverage on wat> <br /> 
--wasi <if the wasm file uses wasi-libc functions> <br /> 
Wat_Wasm_File_Path<Wasm or Wat file path> <br /> 
```
###### Sample example command shown below

On Linux System:
sudo mill cli.run coverage --covf --covout /home/user/Desktop/ --wasi Deconvolution-1D.wasm

After running this command the if the run is successfull a separate folder "cov_results" is generated in the directory /home/user/Desktop. The coverage results are saved with a folder name of the wasm file appended with "_covreport". 

This folder contains the two files one is the coverage report and another one is showmap file.

[Coverage Report Sample](optin/src/swam/optin/coverage/sample-reports/Deconvolution-1D.ic.csv)
Below is the coverage report for Deconvolution-1D.wasm




###### Showmap Sample





