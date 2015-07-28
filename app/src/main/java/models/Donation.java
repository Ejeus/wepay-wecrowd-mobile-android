package models;

/**
 * Created by zachv on 7/27/15.
 */
public class Donation {
    private Integer campaignID;
    private String creditCardID;
    private Integer amount;
    private Integer checkoutID;

    public Donation() {}

    public void setDonationInfo(Integer campaignID) { this.campaignID = campaignID; }
    public void setDonationCheckoutInfo(Integer checkoutID) { this.checkoutID = checkoutID; }
    public void setDonationInfo(String creditCardID, Integer amount)
    {
        this.creditCardID = creditCardID;
        this.amount = amount;
    }

    public Integer getCampaignID() { return campaignID; }
    public String getCreditCardID() { return creditCardID; }
    public Integer getAmount() { return amount; }
    public Integer getCheckoutID() { return checkoutID; }
}
