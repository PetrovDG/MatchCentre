package info.geostage.matchcentre.MatchComment;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
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

public final class QueryUtils {
    private static final String KEY_TIME = "time";
    private static final String KEY_HEADING = "heading";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_SECOND = "second";

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the JSON data and return a list of {@link Match} objects.
     */
    public static List<Match> fetchMatchData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Match}
        List<Match> comments = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Match}
        return comments;
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
            Log.e(LOG_TAG, "Problem retrieving the commentary JSON results.", e);
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
     * Return a list of {@link Match} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Match> extractFeatureFromJson(String matchJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(matchJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding comments to
        List<Match> comments = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Extract the JSONArray
            JSONArray commentArray = new JSONArray(matchJSON);

            // Variables for JSON parsing
            JSONObject currentComment;
            String heading;
            String description;
            String time;
            String minute;
            String second;

            // For each comment in the commentArray, create an {@link Match} object
            for (int i = 0; i < commentArray.length(); i++) {

                // Get a single comment at position i within the list of comments
                currentComment = commentArray.getJSONObject(i);

                // Check if key "heading" exists and if yes, return value
                if (currentComment.has(KEY_HEADING)) {
                    heading = currentComment.getString(KEY_HEADING);
                } else {
                    heading = null;
                }

                // Check if key "description" exists and if yes, return value
                if (currentComment.has(KEY_DESCRIPTION)) {
                    description = currentComment.getString(KEY_DESCRIPTION);
                } else {
                    description = null;
                }

                // Check if key "time" exists and if yes, return value
                if (currentComment.has(KEY_TIME)) {
                    time = currentComment.getString(KEY_TIME);
                } else {
                    time = null;
                }

                // Check if key "minute" exists and if yes, return value
                if (currentComment.has(KEY_MINUTE)) {
                    minute = currentComment.getString(KEY_MINUTE);
                } else {
                    minute = null;
                }

                // Check if key "second" exists and if yes, return value
                if (currentComment.has(KEY_SECOND)) {
                    second = currentComment.getString(KEY_SECOND);
                } else {
                    second = null;
                }

                // Create the Match object and add it to the ArrayList
                Match comment = new Match(heading, description, time, minute, second);
                comments.add(comment);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the Match commentary JSON results", e);
        }

        // Return the list of Comments
        return comments;
    }

}
