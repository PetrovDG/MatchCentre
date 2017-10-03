package info.geostage.matchcentre.MatchStats;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of stats by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class MatchStatsLoader extends AsyncTaskLoader<List<MatchStats>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = MatchStatsLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link MatchStatsLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public MatchStatsLoader(Context context, String url) {
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
    public List<MatchStats> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of match stats.
        List<MatchStats> stats = MatchStatsQuery.fetchMatchStatsData(mUrl);
        return stats;
    }
}