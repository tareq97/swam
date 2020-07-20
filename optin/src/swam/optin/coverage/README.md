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
./mill cli.run coverage --covf <True/false> 
--covout <Path / Default Path if not provided> 
--wat<if executing coverage on wat> 
--wasi <if the wasm file uses wasi-libc functions> 
Wat_Wasm_File_Path<Wasm or Wat file path>

###### Sample example command shown below

Linux Systems:
sudo mill cli.run coverage --covf --covout /home/user/Desktop/ --wasi Deconvolution-1D.wasm







