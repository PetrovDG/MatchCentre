package info.geostage.matchcentre;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of matches by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class MatchCenterLoader extends AsyncTaskLoader<List<MatchCenter>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = MatchCenterLoader.class.getName();

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

        // Perform the network request, parse the response, and extract a list of matches.
        List<MatchCenter> matches = MatchCenterQuery.fetchMatchCenterData(mUrl);
        return matches;
    }
}
