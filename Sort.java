import java.util.Comparator;

public class Sort implements Comparator<String> {
	public int compare(String item1, String item2) {
		if (item1.length() != item2.length()) {
			return item1.length() - item2.length();
		}
		return item1.compareTo(item2);

	}

}
