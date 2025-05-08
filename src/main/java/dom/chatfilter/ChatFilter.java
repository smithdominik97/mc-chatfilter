package dom.chatfilter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;


public class ChatFilter implements Listener {
    // Flask Server
    private static final String SERVER_URL = "http://127.0.0.1:5000/check_toxicity";


    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) throws IOException{
        //Bukkit.broadcastMessage("Message captured");
        String message = event.getMessage();

        if (checkMessage(message)) {
            event.setCancelled(true);
            Bukkit.broadcastMessage("Message filtered");
        }

    }

    public boolean checkMessage(String message) throws IOException {

        JsonObject json = new JsonObject();

        json.addProperty("message", message);
        // JSON String which will be sent to the API.
        //String data_to_send = "{\"message\": \"" + message + "\"}";
        String data_to_send = json.toString();
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();

        try {
            // attempts to establish a connection to the URL represented by the obj.
            connection = connect();

            // Calling the API and send request data
            // connection.getOutputStream() purpose is to obtain an output stream for sending data to the server.
            try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
                os.writeBytes(data_to_send);
                os.flush();
            }

            // Get response code and handle response
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // HTTP_OK or 200 response code generally means that the server ran successfully without any errors


                // Read response content
                // connection.getInputStream() purpose is to obtain an input stream for reading the server's response.
                try (
                        BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line); // Adds every line to response till the end of file.
                    }
                }
                System.out.println("Response: " + response.toString());
            }
            else {
                System.out.println("Error: HTTP Response code - " + responseCode);
            }
            connection.disconnect();
        }
        catch (IOException e) {
            // If any error occurs during api call it will go into catch block
            System.out.println(e.toString());
            e.printStackTrace();
        }

        // Parse response json
        JsonObject jObj = JsonParser.parseString(response.toString()).getAsJsonObject();
        // check toxicity property
        String toxicity = jObj.get("toxicity").getAsString();

        // if toxic return true
        return toxicity.equals("toxic");

    }

    public HttpURLConnection connect() throws IOException {
        URL url = new URL(SERVER_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        connection.setRequestMethod("POST");  // Ensure POST is set here as well for the first request
        connection.setDoOutput(true);  // Enable output stream
        connection.setRequestProperty("Content-Type", "application/json");

        // Establish the connection
        return connection;
    }


}