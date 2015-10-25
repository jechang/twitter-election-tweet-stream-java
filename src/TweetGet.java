import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.sql.*;

/**
 * <p>This is a code example of Twitter4J Streaming API - sample method support.<br>
 * Usage: java twitter4j.examples.PrintSampleStream<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class TweetGet {
    /**
     * Main entry of this application.
     *
     * @param args
     */
    public static void main(String[] args) throws TwitterException {

    	// Start creating database
    	try {
    		
	        String databaseName = "MyTwitterStream";
	        String userName = "Jonathan";
	        String password = "amazoncloud";
	        String url = "jdbc:mysql://twitterdb.cvszelfjqssa.us-west-1.rds.amazonaws.com:3306/";
	        
	        Connection connection = DriverManager.getConnection(url + databaseName, userName, password);
	        System.out.println(databaseName + " MySQL database has been opened...");
	        String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
	        
	        Statement statement = connection.createStatement();
	        
	        statement.executeUpdate(sql);
	        
	        statement.close();
	        connection.close();
	        
	        System.out.println(databaseName + " MySQL database has been created or already exists...");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    // End of database creation   
	    
	    // Start creating table
	    try {    	
	    	String databaseName = "MyTwitterStream";
	        String userName = "Jonathan";
	        String password = "amazoncloud";
	        String url = "jdbc:mysql://twitterdb.cvszelfjqssa.us-west-1.rds.amazonaws.com:3306/";	      
	        
	        Connection connection = DriverManager.getConnection(url + databaseName, userName, password);
	
	        String sql2 = "CREATE TABLE IF NOT EXISTS TWEETS " + "(sn VARCHAR(255) not NULL, " + " status VARCHAR(255) default NULL, " + " latitude VARCHAR(255) default NULL, " + 
	        		" longitude VARCHAR(255) not NULL, " + " category VARCHAR(255) not NULL, " + " PRIMARY KEY ( sn ))"; 
	        
	        Statement statement = connection.createStatement();
	        
	        statement.executeUpdate(sql2);

	        statement.close();
	        connection.close();
	        
	        System.out.println(databaseName + " MySQL table has been created or already exists...");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    // End table creation
	    
    	//just fill this
    	 ConfigurationBuilder cb = new ConfigurationBuilder();
         cb.setDebugEnabled(true)
           .setOAuthConsumerKey("97awir09gMiKoUitNnIc6e11n")
           .setOAuthConsumerSecret("4vUtj7jDw2iGyYOCL3i28hDRhSFTTKpJgqsHSIQWzRWM6JgWIi")
           .setOAuthAccessToken("3943692555-8ere0NGBIeVIor9fXnbvIry1OyJWk6XrH3rCTtE")
           .setOAuthAccessTokenSecret("i6xFpnDfNArsYTXgrRvZ3VZI6SQtR8NWh14ASGeHDVUfT");
         
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
            	
            	if (status.getGeoLocation() != null) {

            		String tweet_sn = status.getUser().getScreenName();
            		String tweet_status = status.getText();
            		double tweet_lat = status.getGeoLocation().getLatitude();
            		double tweet_lon = status.getGeoLocation().getLongitude();
            		System.out.println("@" + tweet_sn + " - " + tweet_status + " and has geolocation: " + status.getGeoLocation() + " with latitude: " + Double.toString(tweet_lat));
            		
            		try {
            			String databaseName = "MyTwitterStream";
            	        String userName = "Jonathan";
            	        String password = "amazoncloud";
            	        String url = "jdbc:mysql://twitterdb.cvszelfjqssa.us-west-1.rds.amazonaws.com:3306/";
            	        
            	        Connection connection = DriverManager.getConnection(url + databaseName, userName, password);
            	        
            	        // Route statuses containing keywords into corresponding category
            	        String sql = null;
            	        Statement statement = null;
            	        
            	        if (tweet_status.contains("Hillary") || tweet_status.contains("Clinton")) {
            	        	sql = "INSERT INTO TWEETS (sn, status, latitude, longitude, category) VALUES('" + tweet_sn + "', '" + tweet_status + "', '" + tweet_lat +  "', '" + tweet_lon + "', 'Clinton')";
            	        	statement = connection.createStatement();  
                	        statement.executeUpdate(sql);
                	        statement.close();
                	        System.out.println(databaseName + " Tweet has been inserted into MySQL database table...");
            	        }
            	        
            	        else if (tweet_status.contains("Donald") || tweet_status.contains("Trump")) {
            	        	sql = "INSERT INTO TWEETS (sn, status, latitude, longitude, category) VALUES('" + tweet_sn + "', '" + tweet_status + "', '" + tweet_lat +  "', '" + tweet_lon + "', 'Trump')";
            	        	statement = connection.createStatement();  
                	        statement.executeUpdate(sql);
                	        statement.close();
                	        System.out.println(databaseName + " Tweet has been inserted into MySQL database table...");
            	        }
            	        
            	        else if (tweet_status.contains("Bernie") || tweet_status.contains("Sanders")) {
            	        	sql = "INSERT INTO TWEETS (sn, status, latitude, longitude, category) VALUES('" + tweet_sn + "', '" + tweet_status + "', '" + tweet_lat +  "', '" + tweet_lon + "', 'Sanders')";
            	        	statement = connection.createStatement();  
                	        statement.executeUpdate(sql);
                	        statement.close();
                	        System.out.println(databaseName + " Tweet has been inserted into MySQL database table...");
            	        }
            	        
            	        else if (tweet_status.contains("Ben") || tweet_status.contains("Carson")) {
            	        	sql = "INSERT INTO TWEETS (sn, status, latitude, longitude, category) VALUES('" + tweet_sn + "', '" + tweet_status + "', '" + tweet_lat +  "', '" + tweet_lon + "', 'Carson')";
            	        	statement = connection.createStatement();  
                	        statement.executeUpdate(sql);
                	        statement.close();
                	        System.out.println(databaseName + " Tweet has been inserted into MySQL database table...");
            	        }
            	        
            	        else if (tweet_status.contains("Carly") || tweet_status.contains("Fiorina")) {
            	        	sql = "INSERT INTO TWEETS (sn, status, latitude, longitude, category) VALUES('" + tweet_sn + "', '" + tweet_status + "', '" + tweet_lat +  "', '" + tweet_lon + "', 'Fiorina')";
            	        	statement = connection.createStatement();  
                	        statement.executeUpdate(sql);
                	        statement.close();
                	        System.out.println(databaseName + " Tweet has been inserted into MySQL database table...");
            	        }
            	        
            	        else if (tweet_status.contains("Chris") || tweet_status.contains("Christie")) {
            	        	sql = "INSERT INTO TWEETS (sn, status, latitude, longitude, category) VALUES('" + tweet_sn + "', '" + tweet_status + "', '" + tweet_lat +  "', '" + tweet_lon + "', 'Christie')";
            	        	statement = connection.createStatement();  
                	        statement.executeUpdate(sql);
                	        statement.close();
                	        System.out.println(databaseName + " Tweet has been inserted into MySQL database table...");
            	        }
            	        
            	        else if (tweet_status.contains("Jeb") || tweet_status.contains("Bush")) {
            	        	sql = "INSERT INTO TWEETS (sn, status, latitude, longitude, category) VALUES('" + tweet_sn + "', '" + tweet_status + "', '" + tweet_lat +  "', '" + tweet_lon + "', 'Bush')";
            	        	statement = connection.createStatement();  
                	        statement.executeUpdate(sql);
                	        statement.close();
                	        System.out.println(databaseName + " Tweet has been inserted into MySQL database table...");
            	        }
            	        //String sql = "INSERT INTO TWEETS (sn, status, latitude, longitude) VALUES('" + tweet_sn + "', '" + tweet_status + "', '" + tweet_lat +  "', '" + tweet_lon + "')";
 
            	        //statement.executeUpdate(sql);
            	       
            	        connection.close();   
            	        
            	    } catch (Exception e) {
            	        e.printStackTrace();
            	    }
            	}
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                //System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                //System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        
        twitterStream.addListener(listener);

        // Filter by keywords
        FilterQuery filter = new FilterQuery();
        String[] keywordsArray = {"Hillary" , "Clinton" , "Donald" , "Trump", "Bernie" , "Sanders", "Ben" , "Carson", "Carly" , "Fiorina", "Chris" , "Christie", "Jeb" , "Bush"};
        filter.track(keywordsArray);
        filter.language(new String[]{"en"});
        twitterStream.filter(filter);

        // Listening
        //twitterStream.sample();
    }
}