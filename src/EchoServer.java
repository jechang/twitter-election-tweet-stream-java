import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import org.json.simple.*;

/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo
 * Where "localhost" is the address of the host,
 * "EchoChamber" is the name of the package
 * and "echo" is the address to access this class from the server
 */
@ServerEndpoint("/echo") 
public class EchoServer {
    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(Session session){
        System.out.println(session.getId() + " has opened a connection"); 
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws IOException 
     */
    @OnMessage
    public void onMessage(String message, Session session) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
    	
        System.out.println("Message from " + session.getId() + ": " + message);
        
        String test_status, test_lat, test_lng = null;

        TweetHandler th = new TweetHandler();
        ResultSet rs = th.query("SELECT * FROM TWEETS WHERE sn = 'beftorres'");
        rs.next();
		test_status = rs.getString("status");
		test_lat = rs.getString("latitude");
		test_lng = rs.getString("longitude");
		
		JSONObject obj = new JSONObject();
		obj.put("status", test_status);
		obj.put("latitude", test_lat);
		obj.put("longitude", test_lng);
		
		StringWriter out = new StringWriter();
	    obj.writeJSONString(out);
	      
	    String jsonText = out.toString();
	    System.out.print(jsonText);

		//session.getBasicRemote().sendText(test_status + test_lat + test_lng);
		session.getBasicRemote().sendText(jsonText);
    }
 
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " +session.getId() + " has ended");
    }
}