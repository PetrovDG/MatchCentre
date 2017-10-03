package info.geostage.matchcentre;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.geostage.matchcentre.MatchComment.MatchCommentActivity;
import info.geostage.matchcentre.MatchStats.MatchStatActivity;

public class MatchCentreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MatchCenter>> {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = MatchCommentActivity.class.getName();

    /**
     * URL for JSON data
     */
    private static final String LINK = "https://feeds.tribehive.co.uk/DigitalStadiumServer/opta?pageType=match&value=803294&v=5";

    /**
     * Constant value for the match center loader ID.
     */
    private static final int MATCH_CENTER_LOADER_ID = 1;

    /**
     * Adapter for the list of matches
     */
    private MatchCenterAdapter matchCenterAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * Progress Bar to use while running query
     */
    View progressBar;

    /**
     * ImageView which will call MatchCommentActivity
     */
    ImageView commentImage;

    /**
     * ImageView which will call MatchStatActivity
     */
    ImageView statImage;

    /**
     * SharedPreferences for change color and etc.
     */
    private SharedPreferences mSharedPreferences;

    private RelativeLayout mRelativeLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_centre);

        // Find a reference to the {@link ListView} in the layout
        ListView matchCenterListView = (ListView) findViewById(R.id.list);
        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.list_layout);
        // Find the {@link ImageView} object in the layout with ID match_commentary
        commentImage = (ImageView) findViewById(R.id.match_commentary);
        // Find the {@link ImageView} object in the layout with ID match_stats
        statImage = (ImageView) findViewById(R.id.match_stats);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        matchCenterListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of matches as input
        matchCenterAdapter = new MatchCenterAdapter(this, new ArrayList<MatchCenter>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        matchCenterListView.setAdapter(matchCenterAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MATCH_CENTER_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        // Get the instance of SharedPreferences object
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Retrieve background color value as a string
        String backgroundColor = mSharedPreferences.getString(getString(R.string.key_background_color), "#FFFFFF");
        // Set a layout background color based on user preference settings
        mRelativeLayout.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    private static class MatchCenterLoader extends AsyncTaskLoader<List<MatchCenter>> {

        /**
         * Tag for log messages
         */
        private final String LOG_TAG = MatchCenterLoader.class.getName();

        /**
         * Query URL
         */
        private String mUrl;

        /**
         * Constructs a new {@link MatchCenterLoader}.
         *
         * @param context of the activity
         * @param url     to load data from
         */
        public MatchCenterLoader(Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        /**
         * This is on a background thread.
         */
        @Override
        public List<MatchCenter> loadInBackground() {
            if (mUrl == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of data.
            List<MatchCenter> matches = MatchCenterQuery.fetchMatchCenterData(mUrl);
            return matches;
        }
    }

    @Override
    public Loader<List<MatchCenter>> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(LINK);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new MatchCenterLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<MatchCenter>> loader, List<MatchCenter> matches) {

        // Hide progress bar because the data has been loaded
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        // Set empty state text to display "No Match found!"
        mEmptyStateTextView.setText(R.string.no_match);

        // Clear the adapter of previous comments data
        matchCenterAdapter.clear();

        // If there is a valid list of {@link Match}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (matches != null && !matches.isEmpty()) {
            matchCenterAdapter.addAll(matches);
        } else {
            // Hide progress bar because the data has been loaded
            progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MatchCenter>> loader) {
        // Loader reset, so we can clear out our existing data.
        matchCenterAdapter.clear();
    }

    // Called when the user taps the Match commentary ImageView
    public void match_commentary(View view) {
        // Start MatchCommentActivity in order to show the query result
        Intent intent = new Intent(this, MatchCommentActivity.class);
        startActivity(intent);
    }

    // Called when the user taps the Match stats ImageView
    public void match_stats(View view) {
        // Start MatchStatActivity in order to show the query result
        Intent statIntent = new Intent(this, MatchStatActivity.class);
        startActivity(statIntent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
