# About The Project
Built for a local dog grooming business - Pet Stop Grooming.  
This tool cleans a messy csv export of 2000+ Apple contacts viewed on **Google Sheets**, sorting them by ID, Name, and Phone # for upload into Clover POS.


> Note: This project was created for a very specific contact export format (`gs_contacts.csv`) from Pet Stop Grooming.  
> Due to the extremely chaotic formatting of the orignial contact sheet, it's not intended for general use.

# Built With
- Java 22 (Standard Edition)
- java.util.regex : for pattern matching
- java.io : for file input/output

# Usage
### Input: Messy Contacts CSV (`gs_contacts.csv`)
![Messy CSV Screenshot](images/input_sample.png)
### Output: Cleaned & Sorted Contacts CSV (`cleaned_contacts.csv`)
![Clean CSV Screenshot](images/output_sample.png)
### Sample Data
A censored sample input file is included to show how the original formatting looked.
[`sample_data/gs_contacts_sample.csv`](/sample_data/gs_contacts_sample.csv)

> Real client data was excluded for privacy. All examples are fictional and altered.

# Author
Created by [Soyi Jeong] (https://github.com/soyijeongsj)  
#### Feel free to reach out:  
📧 soyijeong.sj@gmail.com<br>




