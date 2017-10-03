package info.geostage.matchcentre;

/**
 * An {@link MatchCenter} object contains information related to a single match.
 */
public class MatchCenter {

    /**
     * Competition
     */
    private final String mCompetition;

    /**
     * Venue
     */
    private final String mVenue;

    /**
     * Attendance
     */
    private final String mAttendance;

    /**
     * Referee
     */
    private final String mReferee;

    /**
     * Timestamp
     */
    private final long mTimestamp;

    /**
     * Home name
     */
    private final String mHomeName;

    /**
     * AwayName
     */
    private final String mAwayName;

    /**
     * HomeManager
     */
    private final String mHomeManager;

    /**
     * HomeFormation
     */
    private final String mHomeFormation;

    /**
     * AwayManager
     */
    private final String mAwayManager;

    /**
     * AwayFormation
     */
    private final String mAwayFormation;

    /**
     * Constructs a new {@link MatchCenter} object.
     */
    public MatchCenter(String competition, String venue, String attendance, String referee, long timestamp, String homeName,
                       String homeManager, String homeFormation, String awayName, String awayManager, String awayFormation) {
        mCompetition = competition;
        mVenue = venue;
        mAttendance = attendance;
        mReferee = referee;
        mTimestamp = timestamp;
        mHomeName = homeName;
        mHomeManager = homeManager;
        mHomeFormation = homeFormation;
        mAwayName = awayName;
        mAwayManager = awayManager;
        mAwayFormation = awayFormation;

    }

    public String getCompetition() {
        return mCompetition;
    }

    public String getVenue() {
        return mVenue;
    }

    public String getAttendance() {
        return mAttendance;
    }

    public String getReferee() {
        return mReferee;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public String getHomeName() {
        return mHomeName;
    }

    public String getHomeManager() {
        return mHomeManager;
    }

    public String getHomeFormation() {
        return mHomeFormation;
    }
    public String getAwayName() {
        return mAwayName;
    }

    public String getAwayManager() {
        return mAwayManager;
    }

    public String getAwayFormation() {
        return mAwayFormation;
    }

}