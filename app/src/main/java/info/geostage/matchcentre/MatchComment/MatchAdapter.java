package info.geostage.matchcentre.MatchComment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import info.geostage.matchcentre.R;

/**
 * An {@link MatchAdapter} knows how to create a list item layout for each comment
 * in the data source (a list of {@link Match} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class MatchAdapter extends ArrayAdapter<Match> {

    /**
     * Constructs a new {@link MatchAdapter}.
     *
     * @param context  of the app
     * @param comments is the list of comments, which is the data source of the adapter
     */
    public MatchAdapter(Context context, List<Match> comments) {
        super(context, 0, comments);
    }

    /**
     * Returns a list view that displays information about the comment at the given position
     * in the list of comments.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.comment_list, parent, false);
        }

        // Get the {@link Match} object located at this position in the list
        Match currentComment = getItem(position);

        // Find the TextView in the comment_list.xml layout with the ID heading.
        TextView headingTextView = (TextView) listItemView.findViewById(R.id.heading);
        // Get the heading from the currentComment object and set this text on the TextView.
        headingTextView.setText(currentComment.getHeading());

        // Find the TextView in the comment_list.xml layout with the ID description.
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description);
        // Get the description from the currentComment object and set this text on the TextView.
        descriptionTextView.setText(currentComment.getDescription());

        // Find the TextView in the comment_list.xml layout with the ID time.
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        // Get the time from the currentComment object and set this text on the TextView.
        timeTextView.setText(currentComment.getTime());

        // Find the TextView in the comment_list.xml layout with the ID minute.
        TextView minuteTextView = (TextView) listItemView.findViewById(R.id.minute);
        // Get the minute from the currentComment object and set this text on the TextView.
        minuteTextView.setText(currentComment.getMinute());

        // Find the TextView in the comment_list.xml layout with the ID second.
        TextView secondTextView = (TextView) listItemView.findViewById(R.id.second);
        // Get the second from the currentComment object and set this text on the TextView.
        secondTextView.setText(currentComment.getSecond());

        return listItemView;
    }

}
