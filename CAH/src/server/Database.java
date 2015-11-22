package server;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utilities.DeckReader;

public class Database {
	private static String jdbcDriver = "com.mysql.jdbc.Driver";
	
	private static String db_name= "CAH_db";
	private static String db_username = "root";
	private static String db_password = "root";

    private static Connection connection = null;
	
    public static void main(String[] args) {
    	Database db = new Database();
//    	db.createPlayers();
    }
    
    public Database() {
    	// create connection to db
    	selectDriver();
    	connect();

    	// check if the database already exists.. if it doesn't,
    	// create the database, tables, decks, etc
    	 if (!doesDatabaseExist(db_name)) {
    		 createEverything();
    	 }
    }

    public Database(boolean createEverything) {
    	// create connection to db
    	selectDriver();
    	connect();

    	// don't create the databse, default decks, etc.. they should already be in the mysql database if we return here
    	if (!createEverything) {
    		return;
    	}
    	createEverything(); // creates db, tables, default decks, etc
    }

    
    private void createEverything() {
    	// create db
    	createDatabase(db_name, true);
    	
    	// create the tables
    	generateTables();
    	
    	// create the default decks
    	try {
			createDefaultDecks();
			disconnect();
		} catch (IOException e) {
			disconnect();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void createPlayers() {
    	insertRecord("Player_Table", new String[] {"user_name", "password", "name"},
    			new String[] {"jm", "pw", "Jamal"});
    }
    
    private boolean doesDatabaseExist(String databaseName) {
    	String db_name = "";
    	ResultSet rs;
		try {
			rs = connection.getMetaData().getCatalogs();
			while (rs.next()) {
				db_name = rs.getString(1);
				if (db_name.equals(databaseName)) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Cannot find database: " + databaseName);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }

    // input/output: none
    // creates the decks (and cards) based on the textfiles (which must be specified inside this function)
    private void createDefaultDecks() throws IOException {
    	DeckReader dr = new DeckReader();
    	String path = "decks/"; // path to textfile
    	String[][] whiteCards1, blackCards1, blackCards2, blackCards3;

    	URL location = Database.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());
        
    	// get data from files
    	whiteCards1 = dr.readDeck(path+"wcards.txt", false); // last parameter determines if the cards are white are black
    	blackCards1 = dr.readDeck(path+"bcards.txt", true);
    	blackCards2 = dr.readDeck(path+"bcards.txt", true);
    	blackCards3 = dr.readDeck(path+"bcards.txt", true);
    	
    	// create the info for the decks
    	String[][] deck1 = mergeArrays(whiteCards1, blackCards1);
    	String[][] deck2 = mergeArrays(whiteCards1, blackCards2);
    	String[][] deck3 = mergeArrays(whiteCards1, blackCards3);
    	String deck1Name = "Default Deck 1";
    	String deck2Name = "Default Deck 2";
    	String deck3Name = "Default Deck 3";

    	// create the decks
    	try {
    		createDeck(deck1Name, deck1);
    		createDeck(deck2Name, deck2);
			createDeck(deck3Name, deck3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("could not create the decks :(");
			e.printStackTrace();
		}
    }
    
    // helper function for createDefaultDecks - merges two 2D string arrays
    // input: (STR[][]) array1, (STR[][]) array 2
    // output: STR[][] that has all elements of array1 and array2
    // mergeArrays([[1], [2,3]], [[4], [5]]) => [[1], [2, 3], [4], [5]]
    private String[][] mergeArrays(String[][] a, String[][] b) {
    	String[][] result = new String[a.length + b.length][];
    	System.arraycopy(a, 0, result, 0, a.length);
    	System.arraycopy(b, 0, result, a.length, b.length);
    	return result;
    }
    
    // creates decks and cards in SQL
    private void createDeck(String name, String[][] deckInfo) throws SQLException {
    	System.out.println("Creating a deck.. " + name);

    	// create the deck record
    	String[] deckArgs = {name};
    	String[] deckColumns = {"deck_name"};
    	ResultSet deckResult = insertRecord("Decks_Table", deckColumns, deckArgs);

    	// get the id of the deck we just made
    	String deckId = ""; 
    	while(deckResult.next()) { deckId = Integer.toString(deckResult.getInt(1)); }

    	// create the cards
    	for (int i = 0; i < deckInfo.length; i++) {
    		String[] cardInfo = {deckInfo[i][0], deckInfo[i][1], deckId};
    		String[] cardColumns = {"text, is_black, deck_id"};
//    		System.out.println("Card info: " + Arrays.toString(cardInfo));
    		insertRecord("Cards_Table", cardColumns, cardInfo);
    	}
    }
    
    // input: none
    // output: none
    // create the player, deck, etc tables
    private void generateTables() {
    	// Player Table
    	String[] playerAttributes = {
    			"user_name varchar(255)",
    			"password varchar(255)",
    			"name varchar(255)"
    	};
    	insertTable("Player_Table", playerAttributes, true);

    	// Deck Table
    	String[] deckAttributes = {
    			"deck_name varchar(255)"
    	};
    	insertTable("Decks_Table", deckAttributes, true);
    	
    	// Players_Decks_Table - join table
    	String[] playersDecksTableAttributes = {
    			"deck_id int",
    			"player_id int"
    	};
    	insertTable("Players_Decks_Table", playersDecksTableAttributes, true);
    	
    	// Cards_Table
    	String[] cardsTableAttributes = {
    			"text varchar(255)",
    			"is_black boolean",
    			"deck_id int"
    	};
    	insertTable("Cards_Table", cardsTableAttributes, true);
    }

    // Input: (STR) database_name, (BOOL) overwrite (if the database exists, recreate it) 
    // Output: none
    // Result: creates a database with the given name if it does not exist
    private void createDatabase(String name, boolean overwrite) {
    	System.out.println("===========================");
    	System.out.println("Creating database.");
    	Statement s = null;
    	try {
    		s = connection.createStatement();
    		s.executeUpdate("CREATE DATABASE " + name);
		} catch (SQLException e) {
			int error = e.getErrorCode();

			// the database already exists
			if (error == 1007) {
				System.err.println("Database already exists");
				if (overwrite) {
					System.err.println("Overwriting (deleting) database.");
					try {
						s.executeUpdate("DROP DATABASE " + db_name);
						createDatabase(name, overwrite);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			} else {
				System.err.println("Unknown error. Code: " + error);
				e.printStackTrace();
			}
		}
    }
    
    // Input: (STR) tableName, (STR[]) tableArgs - list of table values, (BOOL) overwrite 
    // Output: none
    // Result: creates a a table w/ parameters if it does not exist
    private void insertTable(String tableName, String[] tableArgs, boolean overwrite) {
    	System.out.println("===========================");
    	System.out.println("Creating table");
    	Statement s = null;
    	
   		// create the string + pass arguments for mySQL command
    	String id = "id int NOT NULL PRIMARY KEY AUTO_INCREMENT,";
   		String command = "CREATE TABLE " + tableName + "(" + id;
   		for(int i = 0; i < tableArgs.length; i++) {
   			command += tableArgs[i];
   			if (i != (tableArgs.length-1)) { 
   				command += ",";
   			}
   		}
   		command += ");";
    		
    	// execute the command
    	try {
    		s = connection.createStatement();
    		System.out.println("Command:\n" + command);
    		s.executeUpdate(command);
    		System.out.println("Database update successful.");
    	} catch (SQLException e) {
    		int error = e.getErrorCode();

    		if (error == 1050) {
    			System.err.println("Table already exists");
    			if (overwrite) { System.err.println("...but the table will be overwritten. Dropping it."); }
    			try {
					s.executeUpdate("DROP TABLE " + tableName);
					insertTable(tableName, tableArgs, false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		} else {
    			System.out.println("Error code: " + error);
    			e.printStackTrace();
    		}
    	}
    }

    // input: (STR) name of table, (STR) list of column names - optional, (STR) the values
    // output: the ResultSet containing the ID of the inserted record
    // this can be used to create any record.. card, deck, player, etc
    public ResultSet insertRecord(String tableName, String[] columnNames, String[] values) {
//    	System.out.println("===========================");
//    	System.out.println("Creating record.");
    	String command;
    	PreparedStatement ps;

    	// initialize the command
    	command = "INSERT INTO " + tableName + "("; // VALUES (";
    	
    	// add the column names if they are given 
    	if (columnNames != null) {
    		for(int i = 0; i < columnNames.length; i++) {
    			command += columnNames[i];
    			// the arguments for the SQL command are 
    			// delimited by commas, except the last one
    			if ((i+1) != columnNames.length) {
    				command += ",";
    			}
    		}
    	}
    	
    	command += ") VALUES ("; 

    	// add the arguments to the SQL call
    	int paramsLength = values.length;
    	for (int i = 0; i < paramsLength; i++) {
    		command += "?";
    		if ( (i+1) != paramsLength) {
    			command += ",";
    		}
    	}
    	
    	// finish the command
    	command += ");";

    	// execute command
//    	if (columnNames != null) { 
//    		System.out.println("ColumnNames:\n" + Arrays.toString(columnNames));
//    	}
//    	System.out.println("Values:\n" + Arrays.toString(values));
    	try {
    		ps = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
    		for (int i = 1; i <= paramsLength; i++) {
    			
    			// check for and use booleans
    			if (values[i-1].equals("true") || values[i-1].equals("false")) {
//    				System.out.println("Found a true/false statement.." + values[i-1]);
    				boolean val = values[i-1].equals("true");
    				ps.setBoolean(i, val);
    			} else {
    				ps.setString(i, values[i-1]);
    			}
    		}
    		ps.executeUpdate();
    		System.out.println("Record creation successful.");

    		return (ResultSet) ps.getGeneratedKeys();
    	} catch (SQLException e) {
    		System.err.println("Error while creating record.");
    		System.out.println("SQL Cmd: " + command);
    		e.printStackTrace();

    		return null;
    	}
    }
    
    private void connect() {
    	System.out.println("Connecting to mySQL database.");
    	try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull",
				    db_username, db_password);
    		System.out.println("Successfully connected to database.");

    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    private void selectDriver() {
    	System.out.println("Selecting the jdbc driver.");
    	// connect
    	try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    private static void disconnect(){
		try {
			connection.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
}