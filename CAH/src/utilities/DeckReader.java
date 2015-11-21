package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/* the purpose of this class is to read in a text file 
 * and create a deck of white and black cards
 */
public class DeckReader {
	// input: (BOOL) white - is the text file for black cards?
	// output: (STR[][]) - a 2D array of card info; [[desc1, white], [desc2, black]...]
	public String[][] readDeck(String fileName, boolean black) throws IOException {
		String[][] deck = null;
		FileReader reader = null;
		String deckText = "";
		String blackStr = black ? "true" : "false";

		// get the text from the file
		for (String line : Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)) {
			deckText += line;
		}
		
		// remove extra text that we don't need
		deckText.replace("cards=", "");
		
		// put info in the deck
		int i = 0;
		String[] words = deckText.split("<>");
		deck = new String[words.length][2];
		for (String word : words) {
			deck[i][0] = word;
			deck[i][1] = blackStr;
			i++;
		}

		return deck;
	}
}
