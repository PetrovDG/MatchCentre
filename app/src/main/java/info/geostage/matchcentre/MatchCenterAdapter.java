package info.geostage.matchcentre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An {@link MatchCenterAdapter} knows how to create a list item layout for each comment
 * in the data source (a list of {@link MatchCenter} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class MatchCenterAdapter extends ArrayAdapter<MatchCenter> {

    /**
     * Constructs a new {@link MatchCenterAdapter}.
     *
     * @param context of the app
     * @param matches is the list of matches, which is the data source of the adapter
     */
    public MatchCenterAdapter(Context context, List<MatchCenter> matches) {
        super(context, 0, matches);
    }

    /**
     * Returns a list view that displays information about the comment at the given position
     * in the list of matches.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.match_center_list, parent, false);
        }

        // Get the {@link MatchCenter} object located at this position in the list
        MatchCenter currentMatch = getItem(position);

        // Find the TextView in the match_center_list.xml layout with the ID competition.
        TextView competitionTextView = (TextView) listItemView.findViewById(R.id.competition);
        // Get the competition from the currentMatch object and set this text on the TextView.
        competitionTextView.setText(currentMatch.getCompetition());

        // Find the TextView in the match_center_list.xml layout with the ID venue.
        TextView venueTextView = (TextView) listItemView.findViewById(R.id.venue);
        // Get the venue from the currentMatch object and set this text on the TextView.
        venueTextView.setText(currentMatch.getVenue());

        // Find the TextView in the match_center_list.xml layout with the ID attendance.
        TextView attendanceTextView = (TextView) listItemView.findViewById(R.id.attendance);
        // Get the attendance from the currentMatch object and set this text on the TextView.
        attendanceTextView.setText(currentMatch.getAttendance());

        // Find the TextView in the match_center_list.xml layout with the ID referee.
        TextView refereeTextView = (TextView) listItemView.findViewById(R.id.referee);
        // Get the referee from the currentMatch object and set this text on the TextView.
        refereeTextView.setText(currentMatch.getReferee());

        // Find the TextView in the match_center_list.xml layout with the ID timestamp.
        TextView timestampTextView = (TextView) listItemView.findViewById(R.id.timestamp);
        // Create a new Date object from the time in milliseconds of the match
        Date dateObject = new Date(currentMatch.getTimestamp());
        String formattedDate = formatDate(dateObject);
        // Display the date of the current match in that TextView
        timestampTextView.setText(formattedDate);

        // Find the TextView in the match_center_list.xml layout with the ID home_name.
        TextView homeNameTextView = (TextView) listItemView.findViewById(R.id.home_name);
        // Get the home_name from the currentMatch object and set this text on the TextView.
        homeNameTextView.setText(currentMatch.getHomeName());

        // Find the TextView in the match_center_list.xml layout with the ID home_manager.
        TextView homeManagerTextView = (TextView) listItemView.findViewById(R.id.home_manager);
        // Get the home_manager from the currentMatch object and set this text on the TextView.
        homeManagerTextView.setText(currentMatch.getHomeManager());

        // Find the TextView in the match_center_list.xml layout with the ID home_formation.
        TextView homeFormationTextView = (TextView) listItemView.findViewById(R.id.home_formation);
        // Get the home_formation from the currentMatch object and set this text on the TextView.
        homeFormationTextView.setText(currentMatch.getHomeFormation());

        // Find the TextView in the match_center_list.xml layout with the ID away_name.
        TextView awayNameTextView = (TextView) listItemView.findViewById(R.id.away_name);
        // Get the away_name from the currentMatch object and set this text on the TextView.
        awayNameTextView.setText(currentMatch.getAwayName());

        // Find the TextView in the match_center_list.xml layout with the ID away_manager.
        TextView awayManagerTextView = (TextView) listItemView.findViewById(R.id.away_manager);
        // Get the away_manager from the currentMatch object and set this text on the TextView.
        awayManagerTextView.setText(currentMatch.getAwayManager());

        // Find the TextView in the match_center_list.xml layout with the ID away_formation.
        TextView awayFormationTextView = (TextView) listItemView.findViewById(R.id.away_formation);
        // Get the away_formation from the currentMatch object and set this text on the TextView.
        awayFormationTextView.setText(currentMatch.getAwayFormation());


        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984; 4:30 PM") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy; h:mm a");
        return dateFormat.format(dateObject);
    }

}