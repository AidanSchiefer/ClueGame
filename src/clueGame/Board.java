package clueGame;

public class Board {
	
	// Member variables
	private String _clueLayoutCSV;
	private String _clueSetupTXT;
	
    /*
    * variable and methods used for singleton pattern
    */
    private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
    }
    
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    
    // Method to set the configuration files
    public void setConfigFiles(String layoutCsv, String setupTxt) {
    	_clueLayoutCSV = layoutCsv;
    	_clueSetupTXT = setupTxt;
    }
    
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize()
    {
    }
}
