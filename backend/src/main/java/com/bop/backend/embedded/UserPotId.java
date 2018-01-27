package com.bop.backend.embedded;

import com.bop.backend.model.Pot;
import com.bop.backend.model.User;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserPotId implements Serializable
{
    private User user;
    private Pot pot;

    public UserPotId() {}
    public UserPotId(User user, Pot pot){
        this.user=user;
        this.pot=pot;
    }

    @ManyToOne
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    @ManyToOne
    public Pot getPot() {return pot;}
    public void setPot(Pot pot) {this.pot = pot;}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPotId userPotId = (UserPotId) o;

        if (user != null ? !user.equals(userPotId.user) : userPotId.user != null) return false;
        return pot != null ? pot.equals(userPotId.pot) : userPotId.pot == null;
    }
    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (pot != null ? pot.hashCode() : 0);
        return result;
    }
}