CompaniesReader

The purpose of this small project is to extract data from a large json file.

The data is about companies.

The detailed goal is explained in the file "MIUROS_Backend Dev - Coding test.pdf".

The steps to achieve this goal are the following : 
1) Read a json file character by character, with capacity of discarting useless data and to monitor its use of memory.
2) Manage to follow the json object delimitation ({}).
3) Extract data from parts of a json object.
4) Call an external API, in a multithread manner, to retrieve some information, using given data.
5) Log advancement in a file.
6) Parse alphanumerical finance values into standard numerical values.
7) Keep track of data retrieved during the process and perform some computation at the end of the gathering process.
8) Output some final data.
9) (optional) Maybe handle different currency conversions.
10) (optional) Create a folder and CSV files.
11) (optional) Normalize and parse json data into csv rows and columns.
