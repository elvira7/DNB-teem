package com.bop.backend.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Transactional
//@Table(name = "pot")
public class Pot implements Serializable
{
    @Id
    @GeneratedValue
//    @Generated(AUTO)
    @Column(name = "POT_ID", unique = true, nullable = false)
    private Integer potId;
    private Float availableMoney;
    private Float totalMoney;
    private String name;
    @CreationTimestamp
    private Date created;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.pot")
//    private Set<UserPot> userPots = new HashSet<UserPot>(0);
//    @OneToMany(mappedBy = "pot")
//    private Set<Loan> loans = new HashSet<>(0);
//    private Set<User> usersInvite = new HashSet<>(0);

//    public Pot() {
//        this.availableMoney=0f;
//        this.totalMoney=0f;
//    }
//    public Pot(Float availableMoney) {this.availableMoney = availableMoney;}
//    public Pot(Float availableMoney, Set<UserPot> userPots) {
//        this.availableMoney = availableMoney;
//        this.userPots = userPots;
//    }

    @JsonRawValue
    public Integer getPotId() {return potId;}
    public void setPotId(Integer potId) {this.potId = potId;}
    public Float getAvailableMoney() {return availableMoney;}
    public void setAvailableMoney(Float availableMoney) {this.availableMoney = availableMoney;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Date getCreated() {return created;}
    public void setCreated(Date created) {this.created = created;}
//    public Set<UserPot> getUserPots() {return userPots;}
//    public void setUserPots(Set<UserPot> userPots) {this.userPots = userPots;}
    public Float getTotalMoney() {return totalMoney;}
    public void setTotalMoney(Float totalMoney) {this.totalMoney = totalMoney;}
//    public Set<Loan> getLoans() {return loans;}
//    public void setLoans(Set<Loan> loans) {this.loans = loans;}

}