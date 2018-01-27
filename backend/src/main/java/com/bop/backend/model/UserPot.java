package com.bop.backend.model;

import com.bop.backend.embedded.UserPotId;
import com.fasterxml.jackson.annotation.JsonRawValue;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;

@Entity
//@Transactional
//@Table(name = "user_pot")
public class UserPot implements Serializable
{
//    @EmbeddedId
    private UserPotId pk; //= new UserPotId();

    private float invested;
    private float interest;
//    @Temporal(TemporalType.DATE)
//    @Column(name = "CREATED_DATE")
//    private Date createdDate;
//    @Column(name = "CREATED_BY")
//    private String createdBy;

    public UserPot() {}
    public UserPot(UserPotId userPotId) {this.pk=userPotId;}

    @EmbeddedId
    //@JsonRawValue
    public UserPotId getPk() {return pk;}
    public void setPk(UserPotId pk) {this.pk = pk;}
    @Transient
    public User getUser() {return getPk().getUser();}
    public void setUser(User user) {getPk().setUser(user);}
    @Transient
    public Pot getPot() {return getPk().getPot();}
    public void setPot(Pot pot) {getPk().setPot(pot);}
    public float getInvested() {return invested;}
    public void setInvested(float invested) {this.invested = invested;}
    public float getInterest() {return interest;}
    public void setInterest(float interest) {this.interest = interest;}

    ////    @Temporal(TemporalType.DATE)
////    @Column(name = "CREATED_DATE")
//    public Date getCreatedDate() {return this.createdDate;}
//    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}
////    @Column(name = "CREATED_BY")
//    public String getCreatedBy() {return this.createdBy;}
//    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPot that = (UserPot) o;
        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;
        return true;
    }
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}