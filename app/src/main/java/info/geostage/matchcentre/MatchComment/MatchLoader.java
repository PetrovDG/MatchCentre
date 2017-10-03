package info.geostage.matchcentre.MatchComment;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of comments by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class MatchLoader extends AsyncTaskLoader<List<Match>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = MatchLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link MatchLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public MatchLoader(Context context, String url) {
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
    public List<Match> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of comments.
        List<Match> comments = QueryUtils.fetchMatchData(mUrl);
        return comments;
    }
}