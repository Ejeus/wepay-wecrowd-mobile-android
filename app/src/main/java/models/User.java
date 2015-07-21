package models;

/**
 * Created by zachv on 7/21/15.
 */
public class User {
    Integer ID;
    String token;

    public User(Integer ID, String token) {
        this.ID = ID;
        this.token = token;
    }
}
