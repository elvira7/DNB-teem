package com.bop.backend.model;

/**
 * Created by Clem on 21/04/2017.
 */
public class Invitation {
    private int userId;
    private int potId;

    public Invitation(int userId, int potId) {
        this.userId = userId;
        this.potId = potId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPotId() {
        return potId;
    }

    public void setPotId(int potId) {
        this.potId = potId;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "userId=" + userId +
                ", potId=" + potId +
                '}';
    }
}
