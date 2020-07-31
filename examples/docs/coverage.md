
# Wasmcov - Coverage Tool for WebAssembly byte code
### Coverage tool Features

Currently, the coverage tool has all the options available, similar to the run command(--help, --wat,--wasi, --time, --dir ,--traces, --trace-file,--debug, --main), except for the options flag mentioned below that are added to provide the output path for the coverage reports and to filter the wasi-libc methods from the report and showmap.

--covout <PATH> - Coverage reports are generated in the output path provided within the new directory cov_results which is a static folder created to hold all the results.

--cov-filter - Enables the filter and provides the coverage report and showmap without the Wasi Methods.

--func-filter <pattern> - If the option is provided with the pattern string it will print the coverage of the functions matching the pattern.

--report - If the option is mentioned explicitly will try to create the showmap. Based on what filter is applied

--showmap - If the option is mentioned explicitly will try to create the showmap. Based on what filter is applied

--seed - If this is sub-command is given it provides same random number to the instruction for all the run.

Generic Command

./mill cli.run coverage --covf <True/false> <br /> 
--covout <Path / Default Path if not provided> <br /> 
--wat<if executing coverage on wat> <br /> 
--wasi <if the wasm file uses wasi-libc functions> <br /> 
Wat_Wasm_File_Path<Wasm or Wat file path> <br /> 

Sample example command shown below
On Linux System:
sudo mill cli.run coverage --cov-filter --seed 10 --covout /home/her/Desktop/without_wasi --wasi optin/test/resources/coverage-test/formal_data/Deconvolution-1D.wasm 
sudo mill cli.run coverage --cov-filter func-filter ^__original_main$ --function --covout /home/user/Desktop/ --wasi Deconvolution-1D.wasm

After running the above command a separate folder "cov_results" is generated in the directory /home/user/Desktop. The coverage results are saved with a folder name of the wasm file appended with "_covreport".

This folder contains the two files one is the coverage report and another one is showmap file.
Example reports without Wasi

Coverage Report Sample

Showmap Sample
