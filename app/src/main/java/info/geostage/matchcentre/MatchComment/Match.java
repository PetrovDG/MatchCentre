package info.geostage.matchcentre.MatchComment;


/**
 * An {@link Match} object contains information related to a single comment.
 */
public class Match {

    /**
     * Heading of the comment
     */
    private final String mHeading;

    /**
     * Description of the comment
     */
    private final String mDescription;

    /**
     * Time of the comment
     */
    private final String mTime;

    /**
     * Minute of the comment
     */
    private final String mMinute;

    /**
     * Second of the comment
     */
    private final String mSecond;


    /**
     * Constructs a new {@link Match} object.
     */
    public Match(String heading, String description, String time, String minute, String second) {
        mHeading = heading;
        mDescription = description;
        mTime = time;
        mMinute = minute;
        mSecond = second;
    }

    /**
     * Returns the heading of the comment.
     */
    public String getHeading() {
        return mHeading;
    }

    /**
     * Returns the description.
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Returns the time.
     */
    public String getTime() {
        return mTime;
    }

    /**
     * Returns the minute.
     */
    public String getMinute() {
        return mMinute;
    }

    /**
     * Returns the second.
     */
    public String getSecond() {
        return mSecond;
    }
}