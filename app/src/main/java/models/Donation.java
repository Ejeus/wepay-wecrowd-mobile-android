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

    public void setCampaignID(Integer campaignID) { this.campaignID = campaignID; }
    public void setCreditCardID(String creditCardID) { this.creditCardID = creditCardID; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public void setCheckoutID(Integer checkoutID) { this.checkoutID = checkoutID; }

    public Integer getCampaignID() { return campaignID; }
    public String getCreditCardID() { return creditCardID; }
    public Integer getAmount() { return amount; }
    public Integer getCheckoutID() { return checkoutID; }
}
