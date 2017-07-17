/**
 * This class is used to represent objects that contain a qoute and it's author.
 * @author Hansie Schreuder
 */
class Quote {
	protected String quote;
	protected String author;
	protected int id;
	
	/**
	 * Public constructor.
	 * @param quote String representation of quote.
	 * @param author String representation of the author.
	 */
	public Quote(String quote, String author, int id) {
		this.quote = quote;
		this.author = author;
		this.id = id;
	}
	
	/**
	 * This method is used to retrieve the quote form this object.
	 * @return String representation of quote.
	 */
	public String getQuote() {
		return this.quote;
	}
	
	/**
	 * This method is used to retrieve the author of the quote contained.
	 * @return String representation of author.
	 */
	public String getAuthor() {
		return this.author;
	}
	
	@Override 
	public String toString() {
		return getQuote() + "\n-" + getAuthor();
	}
}
