package info.geostage.matchcentre;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class MatchCenterQuery {
    private static final String KEY_COMPET = "competition";
    private static final String KEY_VENUE = "venue";
    private static final String KEY_ATTEND = "attendance";
    private static final String KEY_REFEREE = "referee";
    private static final String KEY_HOME_NAME = "name";
    private static final String KEY_HOME_MANAGER = "manager";
    private static final String KEY_HOME_FORM = "formation";
    private static final String KEY_AWAY_NAME = "name";
    private static final String KEY_AWAY_MANAGER = "manager";
    private static final String KEY_AWAY_FORM = "formation";

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = MatchCenterQuery.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link MatchCenterQuery} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name MatchCenterQuery (and an object instance of MatchCenterQuery is not needed).
     */
    private MatchCenterQuery() {
    }

    /**
     * Query the JSON data and return a list of {@link MatchCenter} objects.
     */
    public static List<MatchCenter> fetchMatchCenterData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link MatchCenter}
        List<MatchCenter> matches = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link MatchCenter}
        return matches;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the matches JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link MatchCenter} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<MatchCenter> extractFeatureFromJson(String matchCenterJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(matchCenterJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding matches to
        List<MatchCenter> matches = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject currentMatch = new JSONObject(matchCenterJSON);
            JSONObject homeObject = currentMatch.getJSONObject("home");
            JSONObject awayObject = currentMatch.getJSONObject("away");

            // Variables for JSON parsing
            String competition;
            String venue;
            String attendance;
            String referee;
            long timestamp;
            String homeName;
            String homeManager;
            String homeFormation;
            String awayName;
            String awayManager;
            String awayFormation;

            // Check if key "competition" exists and if yes, return value
            if (currentMatch.has(KEY_COMPET)) {
                competition = currentMatch.getString(KEY_COMPET);
            } else {
                competition = null;
            }

            // Check if key "venue" exists and if yes, return value
            if (currentMatch.has(KEY_VENUE)) {
                venue = currentMatch.getString(KEY_VENUE);
            } else {
                venue = null;
            }

            // Check if key "attendance" exists and if yes, return value
            if (currentMatch.has(KEY_ATTEND)) {
                attendance = currentMatch.getString(KEY_ATTEND);
            } else {
                attendance = null;
            }

            // Check if key "referee" exists and if yes, return value
            if (currentMatch.has(KEY_REFEREE)) {
                referee = currentMatch.getString(KEY_REFEREE);
            } else {
                referee = null;
            }

            // Check if key "timestamp" exists and if yes, return value
            if (currentMatch.has("timestamp")) {
                timestamp = currentMatch.getLong("timestamp");
            } else {
                timestamp = 0;
            }

            // Check if key "home" exists and if yes, return value
            if (homeObject.has(KEY_HOME_NAME)) {
                homeName = homeObject.getString(KEY_HOME_NAME);
            } else {
                homeName = null;
            }

            // Check if key "manager" exists and if yes, return value
            if (homeObject.has(KEY_HOME_MANAGER)) {
                homeManager = homeObject.getString(KEY_HOME_MANAGER);
            } else {
                homeManager = null;
            }

            // Check if key "formation" exists and if yes, return value
            if (homeObject.has(KEY_HOME_FORM)) {
                homeFormation = homeObject.getString(KEY_HOME_FORM);
            } else {
                homeFormation = null;
            }

            // Check if key "name" exists and if yes, return value
            if (awayObject.has(KEY_AWAY_NAME)) {
                awayName = awayObject.getString(KEY_AWAY_NAME);
            } else {
                awayName = null;
            }
            // Check if key "manager" exists and if yes, return value
            if (awayObject.has(KEY_AWAY_MANAGER)) {
                awayManager = awayObject.getString(KEY_AWAY_MANAGER);
            } else {
                awayManager = null;
            }
            // Check if key "formation" exists and if yes, return value
            if (awayObject.has(KEY_AWAY_FORM)) {
                awayFormation = awayObject.getString(KEY_AWAY_FORM);
            } else {
                awayFormation = null;
            }

            // Create the Match object and add it to the ArrayList
            MatchCenter match = new MatchCenter(competition, venue, attendance, referee, timestamp,
                    homeName, homeManager, homeFormation, awayName, awayManager, awayFormation);
            matches.add(match);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the matches JSON results", e);
        }

        // Return the list of Matches
        return matches;
    }

}