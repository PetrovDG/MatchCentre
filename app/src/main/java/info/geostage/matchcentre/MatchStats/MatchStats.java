package info.geostage.matchcentre.MatchStats;

/**
 * An {@link MatchStats} object contains information related to a single match stats.
 */
public class MatchStats {

    /**
     * Home name
     */
    private final String mHomeName;

    /**
     * Away Name
     */
    private final String mAwayName;

    /**
     * Home score
     */
    private final String mHomeScore;

    /**
     * Away score
     */
    private final String mAwayScore;

    /**
     * Home possession
     */
    private final double mHomePossession;

    /**
     * Away possession
     */
    private final double mAwayPossession;

    /**
     * Home shotsOnTarget
     */
    private final String mHomeShotsOnTarget;

    /**
     * Away shotsOnTarget
     */
    private final String mAwayShotsOnTarget;

    /**
     * Home corners
     */
    private final String mHomeCorners;

    /**
     * Away corners
     */
    private final String mAwayCorners;

    /**
     * Home fouls
     */
    private final String mHomeFouls;

    /**
     * Away fouls
     */
    private final String mAwayFouls;

    /**
     * Home yellowCards
     */
    private final String mHomeYellowCards;

    /**
     * Away yellowCards
     */
    private final String mAwayYellowCards;

    /**
     * Home redCards
     */
    private final String mHomeRedCards;

    /**
     * Away redCards
     */
    private final String mAwayRedCards;

    /**
     * Constructs a new {@link MatchStats} object.
     */
    public MatchStats(String homeName, String awayName, String homeScore, String awayScore,
                      double homePossession,  double awayPossession, String homeShotsOnTarget, String awayShotsOnTarget,
                      String homeCorners, String awayCorners, String homeFouls, String awayFouls,
                      String homeYellowCards, String awayYellowCards, String homeRedCards, String awayRedCards) {
        mHomeName = homeName;
        mAwayName = awayName;
        mHomeScore = homeScore;
        mAwayScore = awayScore;
        mHomePossession = homePossession;
        mAwayPossession = awayPossession;
        mHomeShotsOnTarget = homeShotsOnTarget;
        mAwayShotsOnTarget = awayShotsOnTarget;
        mHomeCorners = homeCorners;
        mAwayCorners = awayCorners;
        mHomeFouls = homeFouls;
        mAwayFouls = awayFouls;
        mHomeYellowCards = homeYellowCards;
        mAwayYellowCards = awayYellowCards;
        mHomeRedCards = homeRedCards;
        mAwayRedCards = awayRedCards;

    }
    public String getHomeName() {
        return mHomeName;
    }

    public String getAwayName() {
        return mAwayName;
    }

    public String getHomeScore() {
        return mHomeScore;
    }

    public String getAwayScore() {
        return mAwayScore;
    }

    public double getHomePossession() {
        return mHomePossession;
    }

    public double getAwayPossession() {
        return mAwayPossession;
    }

    public String getHomeShotsOnTarget() {
        return mHomeShotsOnTarget;
    }

    public String getAwayShotsOnTarget() {
        return mAwayShotsOnTarget;
    }

    public String getHomeCorners() {
        return mHomeCorners;
    }

    public String getAwayCorners() {
        return mAwayCorners;
    }

    public String getHomeFouls() {
        return mHomeFouls;
    }

    public String getAwayFouls() {
        return mAwayFouls;
    }

    public String getHomeYellowCards() {
        return mHomeYellowCards;
    }

    public String getAwayYellowCards() {
        return mAwayYellowCards;
    }

    public String getHomeRedCards() {
        return mHomeRedCards;
    }

    public String getAwayRedCards() {
        return mAwayRedCards;
    }
}