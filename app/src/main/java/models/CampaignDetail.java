package models;

/**
 * Created by zachv on 7/21/15.
 */
public class CampaignDetail extends Campaign {
    String description;
    Integer progress;

    public CampaignDetail(String ID,
                          String title,
                          Integer goal,
                          String description,
                          Integer progress)
    {
        super(ID, title, goal);

        this.description = description;
        this.progress = progress;
    }

    // Accessors
    public String getDescription() { return description; }
    public Integer getProgress() { return progress; }
}
