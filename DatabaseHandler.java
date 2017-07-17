import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is used to to handle the database connection and retrieve data
 * from the database.
 * @author Hansie Schreuder
 * @version 1.0
 */
class DatabaseHandler {
	
	final private String databaseURL;
	final private String password;
	final private String username;
	
	private Connection connect;
	
	/**
	 * This is the public constructor for DatabaseHandler.
	 * @param databaseURL The URL where database is located.
	 * @param username User name of the user that wants to use the database.
	 * @param password Password of the user that was specified.
	 */
	public DatabaseHandler(String databaseURL, String username, String password) {
		this.databaseURL = databaseURL;
		this.username = username;
		this.password = password;
		
	}
	
	/**
	 * This method is used to connect to the database that was previously specified.
	 * @throws ClassNotFoundException Thrown if driver for the database is not available.
	 * @throws SQLException Thrown when connect() is unable to connect to the database.
	 */
	public void connect() throws ClassNotFoundException, SQLException  {
		Class.forName("com.mysql.jdbc.Driver");
		this.connect = DriverManager.getConnection(this.databaseURL 
				+ "?autoReconnect=true&useSSL=false",
				this.username, this.password);
	}
	
	/**
	 * This method is used to retrieve all quotes by the specified author. 
	 * @param author String representation of the author's name.
	 * @return ArrayList<Quote> An ArrayList that holds all the quotes by author.
	 * @throws SQLException When the PreparedStatement cannot be prepared
	 * executed.
	 */
	public ArrayList<Quote> getAllQuotesByAuthor(String author) throws SQLException {
		PreparedStatement preparedStatement = connect.prepareStatement("SELECT id,quote,"
			+ " author FROM quotes WHERE author = '" + author + "'");
		ResultSet resultSet = preparedStatement.executeQuery();
		
		ArrayList<Quote> arrayList = new ArrayList<>();
	
		while (resultSet.next()) {
			arrayList.add(new Quote(resultSet.getString("quote"), 
					resultSet.getString("author"), resultSet.getInt("id")));
		}
		
		return arrayList;
	}
	
	/**
	 * getAllQuotesByGenre() is used to retrieve all the quotes in a specified genre.
	 * @param genre The genre of quotes that need to be retrieved.
	 * @return ArrayList<Quote> An ArrayList of all the quotes of the specified genre.
	 * @throws SQLException Thrown if PreparedStatement cannot be created or executed.
	 */
	public ArrayList<Quote> getAllQuotesByGenre(String genre) throws SQLException {
		PreparedStatement ps = connect.prepareStatement("SELECT * FROM quotes "
				+ "WHERE genre ='" + genre + "'");
		
		ResultSet resultSet = ps.executeQuery();
		
		ArrayList<Quote> arrayList = new ArrayList<>();
		
		while (resultSet.next()) {
			arrayList.add(new Quote(resultSet.getString("quote"), 
					resultSet.getString("author"), resultSet.getInt("id")));
		}
		
		return arrayList;
	}
	
	/**
	 * This method is used to retrieve a random quote from the database 
	 * using it's id field.
	 * @return A Quote object that represents the quote and it's author.
	 * @throws SQLException Thrown if a PreparedStatement cannot be created.
	 */
	public Quote getRandomQuote() throws SQLException {
		PreparedStatement ps = connect.prepareStatement("SELECT * FROM quotes WHERE id = ?");
		
		// ThreadLocalRandom.current().nextInt(min , max + 1) is used to get a 
		// random integer between 1 and 75963 (+1)-to make the max inclusive
		ps.setInt(1, ThreadLocalRandom.current().nextInt(1, 75963 + 1));
		ResultSet resultSet = ps.executeQuery();
		resultSet.next();
		
		return new Quote(resultSet.getString("quote"), resultSet.getString("author"), resultSet.getInt("id"));
		
	}
	
	/**
	 * deleteRow() is used to delete a quote specified by it's id.
	 * @param id ID of the quote that is to be deleted.
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private void deleteRow(int id) throws SQLException {
		PreparedStatement statement = connect.prepareStatement("DELETE FROM"
				+ " quotes WHERE id = " + id);
		statement.executeUpdate();
	}
	
	/**
	 * closeResources() is used to close the connection to the database 
	 * when it's no longer needed.
	 */
	public void closeResources() {
		try {
			
			connect.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
