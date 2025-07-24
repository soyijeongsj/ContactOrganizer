/**
 * << ContactOrganizer >>
 * 
 * A utility to organize contact data from Pet Stop Grooming.
 * Specifically processes `gs_contacts.csv` and outputs a cleaned version.
 * 
 * Features:
 * 	- Removes empty/irrelevant columns.
 * 	- Filters personal (non-pet-related) contacts.
 * 	- Eliminates duplicate pet ID's.
 * 	- Sorts pet IDs in alphabetical and numerical order.
 * 
 * Output:
 * 	- Writes cleaned contacts into `cleaned_contacts.csv` in the same directory.
 * 
 * Notes:
 * 	- Regex patterns were customized for the quirks of `gs_contacts.csv`.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ContactOrganizer {
	private static final String INPUT_FILE = "gs_contacts.csv";
	private static final String OUTPUT_FILE = "cleaned_contacts.csv";

	public static void main(String[] args) throws IOException{
		//Open messy contacts CSV
		BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));

		//Create new CSV for cleaned output
		PrintWriter pw = new PrintWriter(OUTPUT_FILE);

		//Printing headers
		pw.println("Id,Name,Phone Number");

		List<Contact> contacts = new ArrayList<>();
		String line;
		boolean isHeader = true;

		//Regex Patterns
		Pattern idPattern = Pattern.compile("(?i)[a-z]\\d{1,3}");
		Pattern phonePattern = Pattern.compile("\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{4}|\\+\\d{10,}");

		//Process each contact line
		while ((line = br.readLine()) != null) {
			if (isHeader) {
				isHeader = false;
				continue;
			}

			String[] fields = line.split(",", -1);
			String id = "", name = "", phone = "";

			//Finding ID
			for (String s : fields) {
				Matcher matcher = idPattern.matcher(s);
				if (matcher.find()) {
					id = matcher.group().trim();
					break;
				}
			}
			
			//Skipping Personal Contacts
			if (id.isEmpty()) {
				continue;
			}

			//Reformatting ID
			id = id.toUpperCase().replaceFirst("([A-Z])0*", "$1"); //Removes 0's before the #

			//Finding Name Block
			StringBuilder nameBuilder = new StringBuilder();
			for (String s : fields) {
				if (s.contains("Imported on")) {
					break;
				}
				nameBuilder.append(s.trim());
			}
			name = nameBuilder.toString().replaceAll(id, "").trim();

			//Extra Name Reformatting
			name = name.replaceAll("\"", "").trim(); //Gets rid of quotations
			name = name.replaceAll("\\s{2,}", "").trim(); //Gets rid of 2+ consecutive spaces
			name = name.replaceAll("(\\(\\s{0,}\\))", "").trim(); //Gets rid of empty parenthesis
			name = name.replaceAll("(?i)\\b[a-z]0*\\d{1,3}\\b", "").trim(); //Gets rid of ID's with 0's

			//Finding Phone #
			for (String s : fields) {
				Matcher matcher = phonePattern.matcher(s);
				if (matcher.find()) {
					phone = matcher.group().trim();
					break;
				}
			}

			//Reformatting Phone # 
			phone = phone.replaceAll("[^0-9]", "");
			if (phone.length() == 10) {
				phone = String.format("(%s) %s-%s",
				phone.substring(0, 3),
				phone.substring(3, 6),
				phone.substring(6));
			}
			else if (phone.length() == 11 && phone.startsWith("1")) {
				phone = String.format("+1 (%s) %s-%s",
				phone.substring(1,4),
				phone.substring(4,7),
				phone.substring(7));
			}

			//Adding to list for sorting
			contacts.add(new Contact(id, name, phone));
		}

		contacts.sort(null); //Use compareTo() to sort contacts by ID
		
		//Print sorted contacts to output file
		for (Contact c : contacts) {
			pw.println(c.id + "," + c.name + "," + c.phone);
		}

		//Close reader/writers
		br.close();
		pw.close();

		System.out.println("Contacts cleaned. Output saved to " + OUTPUT_FILE);
	}
}