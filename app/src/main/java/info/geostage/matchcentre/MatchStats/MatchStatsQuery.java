package info.geostage.matchcentre.MatchStats;

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


public final class MatchStatsQuery {
    private static final String KEY_HOME_NAME = "name";
    private static final String KEY_AWAY_NAME = "name";
    private static final String KEY_HOME_SCORE = "score";
    private static final String KEY_AWAY_SCORE = "score";
    private static final String KEY_HOME_POSS = "possession";
    private static final String KEY_AWAY_POSS = "possession";
    private static final String KEY_HOME_SHOTS = "shotsOnTarget";
    private static final String KEY_AWAY_SHOTS = "shotsOnTarget";
    private static final String KEY_HOME_CORNERS = "corners";
    private static final String KEY_AWAY_CORNERS = "corners";
    private static final String KEY_HOME_FOULS = "fouls";
    private static final String KEY_AWAY_FOULS = "fouls";
    private static final String KEY_HOME_YELLOW = "yellowCards";
    private static final String KEY_AWAY_YELLOW = "yellowCards";
    private static final String KEY_HOME_RED = "redCards";
    private static final String KEY_AWAY_RED = "redCards";

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = MatchStatsQuery.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link MatchStatsQuery} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name MatchStatsQuery (and an object instance of MatchStatsQuery is not needed).
     */
    private MatchStatsQuery() {
    }

    /**
     * Query the JSON data and return a list of {@link MatchStats} objects.
     */
    public static List<MatchStats> fetchMatchStatsData(String requestUrl) {

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
        List<MatchStats> stats = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link MatchStats}
        return stats;
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
     * Return a list of {@link MatchStats} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<MatchStats> extractFeatureFromJson(String matchStatsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(matchStatsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding stats to
        List<MatchStats> stats = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject currentStat = new JSONObject(matchStatsJSON);
            JSONObject homeObject = currentStat.getJSONObject("home");
            JSONObject awayObject = currentStat.getJSONObject("away");
            JSONObject homeStatObject = currentStat.getJSONObject("homeStats");
            JSONObject awayStatObject = currentStat.getJSONObject("awayStats");

            // Variables for JSON parsing
            String homeName;
            String awayName;
            String homeScore;
            String awayScore;
            double homePossession;
            double awayPossession;
            String homeShotsOnTarget;
            String awayShotsOnTarget;
            String homeCorners;
            String awayCorners;
            String homeFouls;
            String awayFouls;
            String homeYellowCards;
            String awayYellowCards;
            String homeRedCards;
            String awayRedCards;

            // Check if key "home" exists and if yes, return value
            if (homeObject.has(KEY_HOME_NAME)) {
                homeName = homeObject.getString(KEY_HOME_NAME);
            } else {
                homeName = null;
            }
            // Check if key "name" exists and if yes, return value
            if (awayObject.has(KEY_AWAY_NAME)) {
                awayName = awayObject.getString(KEY_AWAY_NAME);
            } else {
                awayName = null;
            }
            // Check if key "score" exists and if yes, return value
            if (homeStatObject.has(KEY_HOME_SCORE)) {
                homeScore = homeStatObject.getString(KEY_HOME_SCORE);
            } else {
                homeScore = null;
            }
            // Check if key "score" exists and if yes, return value
            if (awayStatObject.has(KEY_AWAY_SCORE)) {
                awayScore = awayStatObject.getString(KEY_AWAY_SCORE);
            } else {
                awayScore = null;
            }
            // Check if key "possession" exists and if yes, return value
            if (homeStatObject.has(KEY_HOME_POSS)) {
                homePossession = homeStatObject.getDouble(KEY_HOME_POSS);
            } else {
                homePossession = 0;
            }
            // Check if key "possession" exists and if yes, return value
            if (awayStatObject.has(KEY_AWAY_POSS)) {
                awayPossession = awayStatObject.getDouble(KEY_AWAY_POSS);
            } else {
                awayPossession = 0;
            }
            // Check if key "shotsOnTarget" exists and if yes, return value
            if (homeStatObject.has(KEY_HOME_SHOTS)) {
                homeShotsOnTarget = homeStatObject.getString(KEY_HOME_SHOTS);
            } else {
                homeShotsOnTarget = null;
            }
            // Check if key "shotsOnTarget" exists and if yes, return value
            if (awayStatObject.has(KEY_AWAY_SHOTS)) {
                awayShotsOnTarget = awayStatObject.getString(KEY_AWAY_SHOTS);
            } else {
                awayShotsOnTarget = null;
            }
            // Check if key "corners" exists and if yes, return value
            if (homeStatObject.has(KEY_HOME_CORNERS)) {
                homeCorners = homeStatObject.getString(KEY_HOME_CORNERS);
            } else {
                homeCorners = null;
            }
            // Check if key "corners" exists and if yes, return value
            if (awayStatObject.has(KEY_AWAY_CORNERS)) {
                awayCorners = awayStatObject.getString(KEY_AWAY_CORNERS);
            } else {
                awayCorners = null;
            }
            // Check if key "fouls" exists and if yes, return value
            if (homeStatObject.has(KEY_HOME_FOULS)) {
                homeFouls = homeStatObject.getString(KEY_HOME_FOULS);
            } else {
                homeFouls = null;
            }
            // Check if key "fouls" exists and if yes, return value
            if (awayStatObject.has(KEY_AWAY_FOULS)) {
                awayFouls = awayStatObject.getString(KEY_AWAY_FOULS);
            } else {
                awayFouls = null;
            }
            // Check if key "yellowCards" exists and if yes, return value
            if (homeStatObject.has(KEY_HOME_YELLOW)) {
                homeYellowCards = homeStatObject.getString(KEY_HOME_YELLOW);
            } else {
                homeYellowCards = null;
            }
            // Check if key "yellowCards" exists and if yes, return value
            if (awayStatObject.has(KEY_AWAY_YELLOW)) {
                awayYellowCards = awayStatObject.getString(KEY_AWAY_YELLOW);
            } else {
                awayYellowCards = null;
            }
            // Check if key "redCards" exists and if yes, return value
            if (homeStatObject.has(KEY_HOME_RED)) {
                homeRedCards = homeStatObject.getString(KEY_HOME_RED);
            } else {
                homeRedCards = null;
            }
            // Check if key "redCards" exists and if yes, return value
            if (awayStatObject.has(KEY_AWAY_RED)) {
                awayRedCards = awayStatObject.getString(KEY_AWAY_RED);
            } else {
                awayRedCards = null;
            }
            // Create the MatchStats object and add it to the ArrayList
            MatchStats stat = new MatchStats(homeName, awayName, homeScore, awayScore, homePossession,
                    awayPossession, homeShotsOnTarget, awayShotsOnTarget, homeCorners, awayCorners,
                    homeFouls, awayFouls, homeYellowCards, awayYellowCards, homeRedCards, awayRedCards);
            stats.add(stat);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the stats JSON results", e);
        }

        // Return the list of match stats
        return stats;
    }

}