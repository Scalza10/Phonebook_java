// Classes required for the program to run
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DatabaseProject extends Application
{
	// Create two URL strings to store the DB address
	final static String DB_URL = "jdbc:derby:PhoneBook;create=false";
	final static String DB_URL_CREATE = "jdbc:derby:PhoneBook;create=true";
	
	//Import Phonebook image to put in the main menu
	static Image PhoneBookImg = new Image("/modelos/PhoneBook.png");
	static ImageView PhoneBookImgView = new ImageView(PhoneBookImg);
		
	// Main method which launch the GUI program.
   public static void main(String[] args)
   {
	   launch(args);   
   }
   
   // At the start of the application some methods are implemnted.
   @Override
	public void start(Stage primaryStage) {
		try {
			createDB();
			showMainMenu();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

   
   /**
    * This method creates the PhoneBook database.
    */
   public static void createDB() {
	     
	      try
	      {
	         // Create a connection to the database.
	         Connection conn = DriverManager.getConnection(DB_URL_CREATE);
						 
				// If the DB already exists, drop the tables.
				dropTables(conn);

				// Build the Coffee table.
				buildEntriesTable(conn);

	         // Close the connection.
	         conn.close();
	      }
	      catch (Exception ex)
	      {
	         System.out.println("ERROR: " + ex.getMessage());
	      }
	   
   }
   /**
	 * The dropTables method drops any existing
	 * in case the database already exists.
	 */
	public static void dropTables(Connection conn)
	{
		System.out.println("Checking for existing tables.");
		
		try
		{
			// Get a Statement object.
			Statement stmt  = conn.createStatement();;

			try
			{
	         // Drop the Entries table.
	         stmt.execute("DROP TABLE Entries");
				System.out.println("Entries table dropped.");
			}
			catch(SQLException ex)
			{
				// No need to report an error.
				// The table simply did not exist.
			}

		}
  		catch(SQLException ex)
		{
	      System.out.println("ERROR: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * The buildEntriesTable method creates the
	 * Entries table and adds some rows to it.
	 */
	public static void buildEntriesTable(Connection conn)
	{
		try
		{
         // Get a Statement object.
         Statement stmt = conn.createStatement();
         
			// Create the table.
			stmt.execute("CREATE TABLE Entries (" +
   				       "Name CHAR(30), " +
                      "PhoneNumber CHAR(13) NOT NULL PRIMARY KEY" +
                      ")");
							 
			// Insert row #1.
			stmt.execute("INSERT INTO Entries VALUES ( " +
                      "'John Walkins', " +
                      "'321-777-1234' )" );

			// Insert row #1.
			stmt.execute("INSERT INTO Entries VALUES ( " +
                    "'Alejandro Ruiz', " +
                    "'304-674-8732' )" );

			// Insert row #2.
			stmt.execute("INSERT INTO Entries VALUES ( " +
                    "'Maria Chrysler', " +
                    "'521-908-3321' )" );

			// Insert row #3.
			stmt.execute("INSERT INTO Entries VALUES ( " +
                    "'Susan Lourdes', " +
                    "'921-888-7512' )" );
				 
			System.out.println("Entries table created.");
		}
		catch (SQLException ex)
      {
         System.out.println("ERROR: " + ex.getMessage());
      }
	}
		//Method that displays the main menu of the application.
	public static void showMainMenu() {
				
		//standardize variables for width and height of the scene.
		 final int WIDTH = 500;
		 final int HEIGTH = 250;
		 Stage mainStage = new Stage(); //Creating new stage
		 
		 // Displaying the image and setting its characteristics
		 HBox image = new HBox(PhoneBookImgView);
		PhoneBookImgView.setFitHeight(150);
		PhoneBookImgView.setFitWidth(100);
		PhoneBookImgView.setPreserveRatio(true);
		
		//setting the alignment of the image container
		image.setAlignment(Pos.CENTER);
        
		//Creating a series of buttons that take the user to a different screen and close the previous one
        Button lookUpButton = new Button("Look up phone #");
        lookUpButton.setOnAction(event ->
        {// closing old stage.
        	lookUpScreen();
        	mainStage.close();
        	});
        
        Button changeNumButton = new Button("Change a phone #");
        changeNumButton.setOnAction(e ->
        {// closing old stage.
        	chagePhone();
        	mainStage.close();});
        
        Button deleteRecordButton = new Button("Delete a row");
        deleteRecordButton.setOnAction(e ->
        {// closing old stage.
        	deleteRecord();
        	mainStage.close();});
        
        Button showAllRecordsButton = new Button("See all records");
        showAllRecordsButton.setOnAction(e ->
        {// closing old stage.
        	showRecords();
        	mainStage.close();});
        
        Button addNumber = new Button("Add new number");
        //addNumber.setPrefWidth(150);
        addNumber.setOnAction(e ->
        {// closing old stage.
        	addNewPhone();
        	mainStage.close();});
        
        //Put all buttons into containers.
        HBox topLayer = new HBox(10,addNumber, lookUpButton, changeNumButton);
        HBox bottomLayer = new HBox(50, deleteRecordButton, showAllRecordsButton);
        
        topLayer.setAlignment(Pos.CENTER);
        bottomLayer.setAlignment(Pos.CENTER);
        
        //Putting all containers into one.
        VBox mainVBox = new VBox(20, image, topLayer,bottomLayer);
        mainVBox.setAlignment(Pos.CENTER);        
        
        // Create a Scene with the HBox as its root node.
        Scene scene = new Scene(mainVBox, WIDTH , HEIGTH);

        // Set the stage title.
        mainStage.setTitle("PhoneBook Database");
        
        // Set scene.
        mainStage.setScene(scene);
        
        // Show the window.
        mainStage.show();
	}
	
	//method that creates a new screen.
	public static void lookUpScreen() {		
		final int WIDTH = 500;
		final int HEIGTH = 200;
		Stage newMainStage = new Stage();
		
		//Creating new button and setting its properties.
		Button goBackButton = new Button("Main screen");
        
		goBackButton.setOnAction(e ->
        {// close stage and takes the user back to the main screen.
        	newMainStage.close();
        	showMainMenu();});
        
		
        HBox buttonBox = new HBox(goBackButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        //Creating label and text field to gather the input from the user
        Label promptLabel = new Label("Enter a name to look up:");
        Label resultLabel = new Label(""); // Result label that displays what happens.
        
        TextField myTextField = new TextField();
        
        
        HBox hbox = new HBox(10, promptLabel, myTextField);
        hbox.setAlignment(Pos.CENTER);
        
        //Button that performs the search in the database
        Button searchButton = new Button("Search");
        
        searchButton.setOnAction(e ->
        {// close stage.
        	String input = myTextField.getText();
        	try {
        		 // Create a connection to the database.
        	      Connection conn = DriverManager.getConnection(DB_URL);
        	      
        	      // Create a Statement object for the query.
        	      Statement stmt = conn.createStatement();
        	            
        	      // Execute the query.
        	      ResultSet resultSet = stmt.executeQuery("SELECT * FROM Entries WHERE UPPER(Name) LIKE '%" + input.toUpperCase() + "%'");
        	      
        	      // If there is a match the record found is displayed if not then no record found will be displayed.
        	      if (resultSet.next())
        	         {
        	    	  resultLabel.setText(resultSet.getString("Name") + resultSet.getString("PhoneNumber"));
        	            
        	         }
        	      else {
        	    	  resultLabel.setText("No record found");
        	    	  
        	      }
        	}
        	catch(Exception ex){
        		System.out.println("ERROR: " + ex.getMessage());
        	}
        	});
        
        //Puts everithing into containers in order to display them
        HBox searchBox = new HBox(searchButton);
        searchBox.setAlignment(Pos.CENTER);
        
        HBox labelBox = new HBox(resultLabel);
        labelBox.setAlignment(Pos.CENTER);
        
        //Gathers all containers into one to display it in the scene.
        VBox mainBox = new VBox(10,hbox, searchBox, labelBox, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        
        //Creates scene
        Scene scene = new Scene(mainBox, WIDTH , HEIGTH);

        // Set the stage title.
        newMainStage.setTitle("Look up sceen");
        
        // Set scene.
        newMainStage.setScene(scene);

        // Show the window.
        newMainStage.show();
	}
	
	public static void addNewPhone() {
		final int WIDTH = 500;
		final int HEIGTH = 200;
		Stage newMainStage = new Stage();
        Label resultLabel = new Label("");
		
		Button goBackButton = new Button("Main screen");
        
		goBackButton.setOnAction(e ->
        {// closes stage and takes the user back to the main screen.
        	newMainStage.close();
        	showMainMenu();});
        
        HBox buttonBox = new HBox(goBackButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        // Creates prompts and text fields to gather input.
        Label promptLabelFirst = new Label("Enter a first name to insert:"); 
        TextField firstNameText = new TextField();
    
        HBox firstName = new HBox(10, promptLabelFirst, firstNameText);
        firstName.setAlignment(Pos.CENTER);
        
        Label promptLabelLast = new Label("Enter a last name to insert:");
        TextField lastNameText = new TextField();
    
        HBox lastName = new HBox(10, promptLabelLast, lastNameText);
        lastName.setAlignment(Pos.CENTER);
        
        Label promptLabelNumber = new Label("Enter phone # (format 999-999-9999):");
        TextField NumberText = new TextField();
    
        HBox phoneNum = new HBox(10, promptLabelNumber, NumberText);
        phoneNum.setAlignment(Pos.CENTER);
        
        Button addButton = new Button("Add");
        
        addButton.setOnAction(e ->
        {   
        	// Stores the input into variables
        	String first = firstNameText.getText();
        	String last = lastNameText.getText();
        	String name = first + " " + last;
        	String phoneNumber = NumberText.getText();
        	try {
        		 // Create a connection to the database.
        	      Connection conn = DriverManager.getConnection(DB_URL);
        	      
        	      // Create a Statement object for the query.
        	      Statement stmt = conn.createStatement();
        	            
        	      // Execute the query.
        	      int resultSet = stmt.executeUpdate("INSERT INTO Entries VALUES ('" + name + "', '" + phoneNumber + "')");
        	      
        	      //if there was a match the Contact added label will be displayed if not then ERROR will be shown.
        	      if (resultSet>0)
        	         {
        	    	  resultLabel.setText("Contact added");
        	            
        	         }
        	      else {
        	    	  resultLabel.setText("ERROR");
        	    	  
        	      }
        	}
        	catch(Exception ex){
        		System.out.println("ERROR: " + ex.getMessage());
        	}
        	});
        
       // Stores everything into containers
        HBox searchBox = new HBox(addButton);
        searchBox.setAlignment(Pos.CENTER);
        
        HBox labelBox = new HBox(resultLabel);
        labelBox.setAlignment(Pos.CENTER);
        
        //Gathers all containers into one
        VBox mainBox = new VBox(10,firstName, lastName, phoneNum, searchBox, labelBox, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(mainBox, WIDTH , HEIGTH);

        // Set the stage title.
        newMainStage.setTitle("New contact window");
        
        // Set scene.
        newMainStage.setScene(scene);

        // Show the window.
        newMainStage.show();
	}
	
	public static void chagePhone() {
		final int WIDTH = 500;
		final int HEIGTH = 200;
		Stage newMainStage = new Stage();
        Label resultLabel = new Label("");
		
		Button goBackButton = new Button("Main screen");
        
		goBackButton.setOnAction(e ->
        {// close stage and takes the user back to the main screen.
        	newMainStage.close();
        	showMainMenu();});
        
		// Puts all input text fields into containers to display them
        HBox buttonBox = new HBox(goBackButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        Label promptLabelFirst = new Label("Contact to modify:"); 
        TextField name = new TextField();
    
        HBox firstName = new HBox(10, promptLabelFirst, name);
        firstName.setAlignment(Pos.CENTER);

        Label promptLabelNumber = new Label("Enter new phone # (format 999-999-9999):");
        TextField NumberText = new TextField();
    
        HBox phoneNum = new HBox(10, promptLabelNumber, NumberText);
        phoneNum.setAlignment(Pos.CENTER);
        
        Button changeButton = new Button("Modify");
        
        changeButton.setOnAction(e ->
        {// close stage.
        	String first = name.getText();
        	String phoneNumber = NumberText.getText();
        	try {
        		 // Create a connection to the database.
        	      Connection conn = DriverManager.getConnection(DB_URL);
        	      
        	      // Create a Statement object for the query.
        	      Statement stmt = conn.createStatement();
        	            
        	      // Execute the query.
        	      ResultSet resultSet = stmt.executeQuery("SELECT * FROM Entries WHERE UPPER(Name) LIKE '%" + first.toUpperCase() + "%'");
        	      
        	      if (resultSet.next())
        	         {
        	    	  String sqlStatement = "UPDATE Entries SET PhoneNumber = '" + phoneNumber + "' WHERE Name = '" + first + "'";
        	    	  int rows = stmt.executeUpdate(sqlStatement);
        	    	  resultLabel.setText("Successfully modified");
        	            
        	         }
        	      else {
        	    	  resultLabel.setText("Name not found");
        	    	  
        	      }
        	}
        	catch(Exception ex){
        		System.out.println("ERROR: " + ex.getMessage());
        	}
        	});
        
        HBox changeBox = new HBox(changeButton);
        changeBox.setAlignment(Pos.CENTER);
        
        HBox labelBox = new HBox(resultLabel);
        labelBox.setAlignment(Pos.CENTER);
        
        // Gathers all containers into one.
        VBox mainBox = new VBox(10,firstName, phoneNum, changeBox, labelBox, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(mainBox, WIDTH , HEIGTH);

        // Set the stage title.
        newMainStage.setTitle("Change number window");
        
        // Set scene.
        newMainStage.setScene(scene);

        // Show the window.
        newMainStage.show();
	}
	
	public static void deleteRecord() {
		final int WIDTH = 500;
		final int HEIGTH = 200;
		Stage newMainStage = new Stage();
        Label resultLabel = new Label("");
		
		Button goBackButton = new Button("Main screen");
        
		goBackButton.setOnAction(e ->
        {// close stage and takes the user back to the main screen.
        	newMainStage.close();
        	showMainMenu();});
        
		// Input boxes in container to gather user input.
        HBox buttonBox = new HBox(goBackButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        Label promptLabelFirst = new Label("Contact to delete:"); 
        TextField name = new TextField();
    
        HBox firstName = new HBox(10, promptLabelFirst, name);
        firstName.setAlignment(Pos.CENTER);
        
        Button deleteButton = new Button("Delete record");
        
        deleteButton.setOnAction(e ->
        {	// stores user input into a variable
        	String first = name.getText();
        	try {
        		 // Create a connection to the database.
        	      Connection conn = DriverManager.getConnection(DB_URL);
        	      
        	      // Create a Statement object for the query.
        	      Statement stmt = conn.createStatement();
        	            
        	      // Execute the query.
        	      int resultSet = stmt.executeUpdate("DELETE FROM Entries WHERE UPPER(Name) = '" + first.toUpperCase() + "'");
        	      
        	      //If there is a match the Successfully deleted message is displayed if not then name not found is displayed.
        	      if (resultSet>0)
        	         {
        	    	  resultLabel.setText("Successfully deleted");
        	            
        	         }
        	      else {
        	    	  resultLabel.setText("Name not found");
        	    	  
        	      }
        	}
        	catch(Exception ex){
        		System.out.println("ERROR: " + ex.getMessage());
        	}
        	});
        
        HBox deleteBox = new HBox(deleteButton);
        deleteBox.setAlignment(Pos.CENTER);
        
        HBox labelBox = new HBox(resultLabel);
        labelBox.setAlignment(Pos.CENTER);
        
        //Gathers all the containers into one.
        VBox mainBox = new VBox(10,firstName, deleteBox, labelBox, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(mainBox, WIDTH , HEIGTH);

        // Set the stage title.
        newMainStage.setTitle("Dalete window");
        
        // Set scene.
        newMainStage.setScene(scene);

        // Show the window.
        newMainStage.show();
	}
	
	public static void showRecords() {
		final int WIDTH = 500;
		final int HEIGTH = 200;
		Stage newMainStage = new Stage();
		
		Button goBackButton = new Button("Main screen");
        
		goBackButton.setOnAction(e ->
        {// close stage and takes the user back to the main screen.
        	newMainStage.close();
        	showMainMenu();});
        
        HBox buttonBox = new HBox(goBackButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        //Creates a text area to displays results.
        TextArea resultsTextArea = new TextArea();
        resultsTextArea.setPrefColumnCount(100);
        resultsTextArea.setPrefRowCount(100);
        
        Button recordsButton = new Button("Show records");
        
        recordsButton.setOnAction(e ->
        {// close stage.
        	try
            {
               // Create a connection to the database.
               Connection conn = DriverManager.getConnection(DB_URL);
               
               // Create a Statement object.
               Statement stmt = conn.createStatement();
               
               // Execute the query.
               ResultSet resultSet = stmt.executeQuery("SELECT * FROM Entries");
               
               // Get the result set meta data.
               ResultSetMetaData meta = resultSet.getMetaData();
               
               // Create a string to hold the results
               String output = "";
               
               // Get the results.
               while (resultSet.next())
               {               
                  // Get a row.
                  for (int i = 1; i <= meta.getColumnCount(); i++)
                  {
                     output += resultSet.getString(i);
                  }
                  output += '\n';
               }
               
               // Display the results.
               resultsTextArea.setText(output);
      
               // Close the statement and the connection.
               stmt.close();
               conn.close();
    
            }
            catch (SQLException error)
            {
               error.printStackTrace();
               System.exit(0);
            }
        	});
        
        HBox recordsBox = new HBox(recordsButton);
        recordsBox.setAlignment(Pos.CENTER);
        
        
        //Gathers all containers into one.
        VBox mainBox = new VBox(10,resultsTextArea,  recordsBox, buttonBox);
        
        Scene scene = new Scene(mainBox, WIDTH , HEIGTH);

        // Set the stage title.
        newMainStage.setTitle("Records window");
        
        // Set scene.
        newMainStage.setScene(scene);

        // Show the window.
        newMainStage.show();
	}
		
}