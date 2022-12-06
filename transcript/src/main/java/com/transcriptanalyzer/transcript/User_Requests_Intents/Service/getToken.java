package com.transcriptanalyzer.transcript.User_Requests_Intents.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * The getToken class given user email and password will create the authorization token used to add blocks on to the
 * Voiceflow canvas
 */
@Service
public class getToken {

    public static String login(String email, String password) throws IOException {
        URL urlGetToken = new URL("https://api.voiceflow.com/session");

        JsonObject user = getJsonUser(email, password);

        JsonObject device = getJsonDevice();

        JsonObject payload = getJsonPayload(user, device);

        HttpURLConnection con = setHttpURLConnection(urlGetToken);

        // Here we are writing to the connection using the payload Json Object
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = payload.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // We are reading the response given by the VF API using a buffered reader
        // and a StringBuilder
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // We use this to convert the StringBuilder response into a Json response
            JsonObject jsonResponse = new Gson().fromJson(response.toString(), JsonObject.class);

            // We find the authorization token within the Json response object and return it
            return jsonResponse.get("token").toString();
            }
        }


     // Helper functions for getToken
    @NotNull
    private static HttpURLConnection setHttpURLConnection(URL urlGetToken) throws IOException {
        // Here we open a connection to make an HTTP call to VF's API
        // Open connection using url defined on line 15
        HttpURLConnection con = (HttpURLConnection) urlGetToken.openConnection();
        con.setRequestMethod("PUT"); // Authentication requires a PUT request
        con.setRequestProperty("Content-type", "application/json"); // Type passed to VF is json
        con.setRequestProperty("Accept", "application/json"); // Type received from VF is json
        con.setDoOutput(true); // This allows us to receive an output from the VF API call
        return con;
    }

    @NotNull
    private static JsonObject getJsonPayload(JsonObject user, JsonObject device) {
        // Create the payload object that combines the user and device Json Objects
        // This will be passed over the connection created below to VF's API to
        // authenticate that a user is allowed to make this call
        JsonObject payload = new JsonObject();
        payload.add("user", user);
        payload.add("device", device);
        return payload;
    }

    @NotNull
    private static JsonObject getJsonDevice() {
        // Create the device Json Object that will be used in the authentication VF call
        // This object does not depend on the user, so I have created it with set values
        JsonObject device = new JsonObject();
        device.addProperty("os", "macOS");
        device.addProperty("version", "12.6");
        device.addProperty("browser", "chrome");
        device.addProperty("platform", "desktop");
        return device;
    }

    @NotNull
    private static JsonObject getJsonUser(String email, String password) {
        // Create the user Json Object that will be used in the authentication VF call using
        // the email and password passed to the login method as parameters
        JsonObject user = new JsonObject();
        user.addProperty("email", email);
        user.addProperty("password", password);
        return user;
    }

}
