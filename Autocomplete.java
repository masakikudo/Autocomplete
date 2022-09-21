import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of 
// string and weights, using Term and BinarySearchDeluxe. 
public class Autocomplete {
	private final Term[] terms;

	// Initialize the data structure from the given array of terms.
	public Autocomplete(Term[] terms) {

		if (terms == null)
			throw new NullPointerException();

		this.terms = terms.clone();

		Arrays.sort(this.terms);
	}

	// All terms that start with the given prefix, in descending order of
	// weight.
	public Term[] allMatches(String prefix) {

		if (prefix == null)
			throw new NullPointerException();

		Term[] matches;

		if (prefix.length() == 0)
			matches = terms.clone();

		else {
			Term prefixTerm = new Term(prefix);
			int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));

			if (firstIndex < 0)
				matches = new Term[0];

			else {
				int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));

				matches = new Term[lastIndex - firstIndex + 1];

				for (int i = 0; i < lastIndex - firstIndex + 1; i++)
					matches[i] = terms[i + firstIndex];
			}
		}

		Arrays.sort(matches, Term.byReverseWeightOrder());
		return matches;
	}

	// The number of terms that start with the given prefix.
	public int numberOfMatches(String prefix) {

		if (prefix == null)
			throw new NullPointerException();

		if (prefix.length() == 0)
			return terms.length;

		Term prefixTerm = new Term(prefix);

		int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));

		if (firstIndex < 0)
			return 0;

		int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));

		return lastIndex - firstIndex + 1;
	}

	// Entry point. [DO NOT EDIT]
	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Term[] terms = new Term[N];
		for (int i = 0; i < N; i++) {
			long weight = in.readLong();
			in.readChar();
			String query = in.readLine();
			terms[i] = new Term(query.trim(), weight);
		}
		int k = Integer.parseInt(args[1]);
		Autocomplete autocomplete = new Autocomplete(terms);
		while (StdIn.hasNextLine()) {
			String prefix = StdIn.readLine();
			Term[] results = autocomplete.allMatches(prefix);
			for (int i = 0; i < Math.min(k, results.length); i++) {
				StdOut.println(results[i]);
			}
		}
	}
}
