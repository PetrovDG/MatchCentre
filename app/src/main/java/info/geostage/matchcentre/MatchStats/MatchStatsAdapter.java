package info.geostage.matchcentre.MatchStats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import info.geostage.matchcentre.R;

/**
 * An {@link MatchStatsAdapter} knows how to create a list item layout for each comment
 * in the data source (a list of {@link MatchStats} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class MatchStatsAdapter extends ArrayAdapter<MatchStats> {

    /**
     * Constructs a new {@link MatchStatsAdapter}.
     *
     * @param context of the app
     * @param stats is the list of match stats, which is the data source of the adapter
     */
    public MatchStatsAdapter(Context context, List<MatchStats> stats) {
        super(context, 0, stats);
    }

    /**
     * Returns a list view that displays information about the comment at the given position
     * in the list of match stats.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.stats_list, parent, false);
        }

        // Get the {@link MatchCenter} object located at this position in the list
        MatchStats currentStat = getItem(position);

        // Find the TextView in the stats_list.xml layout with the ID home_name.
        TextView homeNameTextView = (TextView) listItemView.findViewById(R.id.home_name);
        // Get the home_name from the currentStat object and set this text on the TextView.
        homeNameTextView.setText(currentStat.getHomeName());

        // Find the TextView in the stats_list.xml layout with the ID away_name.
        TextView awayNameTextView = (TextView) listItemView.findViewById(R.id.away_name);
        // Get the away_name from the currentStat object and set this text on the TextView.
        awayNameTextView.setText(currentStat.getAwayName());

        // Find the TextView in the stats_list.xml layout with the ID home_score.
        TextView homeScoreTextView = (TextView) listItemView.findViewById(R.id.home_score);
        // Get the home_score from the currentStat object and set this text on the TextView.
        homeScoreTextView.setText(currentStat.getHomeScore());

        // Find the TextView in the stats_list.xml layout with the ID away_score.
        TextView awayScoreTextView = (TextView) listItemView.findViewById(R.id.away_score);
        // Get the away_score from the currentStat object and set this text on the TextView.
        awayScoreTextView.setText(currentStat.getAwayScore());

        // Find the TextView in the stats_list.xml layout with the ID home_possession.
        TextView homePossessionTextView = (TextView) listItemView.findViewById(R.id.home_possession);
        String formattedHomePossession = formatHomePossession(currentStat.getHomePossession());
        // Get the home_possession from the currentStat object and set this text on the TextView.
        homePossessionTextView.setText(formattedHomePossession);

        // Find the TextView in the stats_list.xml layout with the ID away_possession.
        TextView awayPossessionTextView = (TextView) listItemView.findViewById(R.id.away_possession);
        String formattedAwayPossession = formatAwayPossession(currentStat.getAwayPossession());
        // Get the away_possession from the currentStat object and set this text on the TextView.
        awayPossessionTextView.setText(formattedAwayPossession);

        // Find the TextView in the stats_list.xml layout with the ID home_shotsOnTarget.
        TextView homeShotsOnTargetTextView = (TextView) listItemView.findViewById(R.id.home_shotsOnTarget);
        // Get the home_shotsOnTarget from the currentStat object and set this text on the TextView.
        homeShotsOnTargetTextView.setText(currentStat.getHomeShotsOnTarget());

        // Find the TextView in the stats_list.xml layout with the ID home_shotsOnTarget.
        TextView awayShotsOnTargetTextView = (TextView) listItemView.findViewById(R.id.away_shotsOnTarget);
        // Get the away_shotsOnTarget from the currentStat object and set this text on the TextView.
        awayShotsOnTargetTextView.setText(currentStat.getAwayShotsOnTarget());

        // Find the TextView in the stats_list.xml layout with the ID home_corners.
        TextView homeCornersTextView = (TextView) listItemView.findViewById(R.id.home_corners);
        // Get the home_corners from the currentStat object and set this text on the TextView.
        homeCornersTextView.setText(currentStat.getHomeCorners());

        // Find the TextView in the stats_list.xml layout with the ID away_corners.
        TextView awayCornersTextView = (TextView) listItemView.findViewById(R.id.away_corners);
        // Get the away_corners from the currentStat object and set this text on the TextView.
        awayCornersTextView.setText(currentStat.getAwayCorners());

        // Find the TextView in the stats_list.xml layout with the ID home_fouls.
        TextView homeFoulsTextView = (TextView) listItemView.findViewById(R.id.home_fouls);
        // Get the home_fouls from the currentStat object and set this text on the TextView.
        homeFoulsTextView.setText(currentStat.getHomeFouls());

        // Find the TextView in the stats_list.xml layout with the ID away_fouls.
        TextView awayFoulsTextView = (TextView) listItemView.findViewById(R.id.away_fouls);
        // Get the away_fouls from the currentStat object and set this text on the TextView.
        awayFoulsTextView.setText(currentStat.getAwayFouls());

        // Find the TextView in the stats_list.xml layout with the ID home_yellowCards.
        TextView homeYellowCardsTextView = (TextView) listItemView.findViewById(R.id.home_yellowCards);
        // Get the home_yellowCards from the currentStat object and set this text on the TextView.
        homeYellowCardsTextView.setText(currentStat.getHomeYellowCards());

        // Find the TextView in the stats_list.xml layout with the ID away_yellowCards.
        TextView awayYellowCardsTextView = (TextView) listItemView.findViewById(R.id.away_yellowCards);
        // Get the away_yellowCards from the currentStat object and set this text on the TextView.
        awayYellowCardsTextView.setText(currentStat.getAwayYellowCards());

        // Find the TextView in the stats_list.xml layout with the ID home_redCards.
        TextView homeRedCardsTextView = (TextView) listItemView.findViewById(R.id.home_redCards);
        // Get the homeRedCards from the currentStat object and set this text on the TextView.
        homeRedCardsTextView.setText(currentStat.getHomeRedCards());

        // Find the TextView in the stats_list.xml layout with the ID away_redCards.
        TextView awayRedCardsTextView = (TextView) listItemView.findViewById(R.id.away_redCards);
        // Get the away_redCards from the currentStat object and set this text on the TextView.
        awayRedCardsTextView.setText(currentStat.getAwayRedCards());

        return listItemView;
    }

    /**
     * Return the formatted homePossession string showing 1 decimal place (i.e. "47.2")
     */
    private String formatHomePossession(double homePossession) {
        DecimalFormat homePossessionFormat = new DecimalFormat("0.0");
        return homePossessionFormat.format(homePossession);
    }

    /**
     * Return the formatted awayPossession string showing 1 decimal place (i.e. "47.2")
     */
    private String formatAwayPossession(double awayPossession) {
        DecimalFormat awayPossessionFormat = new DecimalFormat("0.0");
        return awayPossessionFormat.format(awayPossession);
    }

}