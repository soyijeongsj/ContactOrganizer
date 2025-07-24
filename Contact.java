/**
 * Contact object for storing ID, Name, and Phone #.
 * Implements Comparable to sort by pet ID (alphabetically & numerically).
 */

public class Contact implements Comparable<Contact> {
	String id;
	String name;
	String phone;

	Contact(String id, String name, String phone) {
		this.id = id;
		this.name = name;
		this.phone = phone;
	}

	/**
	 * Compares this contact to another via pet ID.
	 * IDs are first compared alphabetically, then numerically.
	 * 
	 * @param c The contact to compare to.
	 * @return An integer: negative if less than the other, 
	 * 	positive if greater, and zero if equal.
	 */
	@Override
	public int compareTo(Contact c) {
		String letOne = this.id.replaceAll("[^A-Z]", "");
		String letTwo = c.id.replaceAll("[^A-Z]", "");

		int numOne = Integer.parseInt(this.id.replaceAll("[^0-9]", "")); //parseInt to remove unecessary 0's
		int numTwo = Integer.parseInt(c.id.replaceAll("[^0-9]", ""));

		int compareLet = letOne.compareTo(letTwo);
		int compareNum = Integer.compare(numOne, numTwo);

		return (compareLet != 0) ? compareLet : compareNum;
	}
}
