import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class MainBot {

	public static void main(String[] args) {
		
		DatabaseHandler dh = null;
		
		try {
			
			dh = new DatabaseHandler("jdbc:mysql://localhost:3306/java_base", "java", "password");
			dh.connect(); // Connect to the database
			
			Twitter twitter = TwitterFactory.getSingleton();
			
			while (true) {
				
				Quote quote = dh.getRandomQuote();

				twitter.updateStatus(quote.toString());
				
				// Post every three hours
				Thread.sleep(3 * 60 * 60 * 1000);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			if (dh != null) {
				dh.closeResources();
			}
			
		}
	}

}
