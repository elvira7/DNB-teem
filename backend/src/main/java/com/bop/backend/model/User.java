package com.bop.backend.model;

import com.bop.backend.model.Pot;
import com.bop.backend.model.UserPot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "user")
public class User implements Serializable
{
    @Id
    @Column(name = "USER_ID", unique = true, nullable = false)
    private int telephone;
    private String password;
    private Double score;
    private String firstName;
    private String lastName;
    @CreationTimestamp
    private Date created;
    private float money;
    private boolean dnbCustomer;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<PotSnapshot> investments = new HashSet<>(0);
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user")
//    private Set<UserPot> userPots = new HashSet<>(0);
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "user_pot_invites",
            joinColumns = @JoinColumn(name = "USER_TEL", referencedColumnName = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "POT_ID", referencedColumnName = "POT_ID"))
    private Set<Pot> invites = new HashSet<>(0);
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Loan> loans = new HashSet<>(0);

//    public User() {}
//    public User(String firstName) {this.firstName = firstName;}
//    public User(String firstName, Set<UserPot> userPots) {
//        this.firstName = firstName;
//        this.userPots = userPots;
//    }

    @JsonRawValue
    public int getTelephone() {return this.telephone;}
    public void setTelephone(int userId) {this.telephone = userId;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String name) {this.firstName = name;}
//    public Set<UserPot> getUserPots() {return userPots;}
//    public void setUserPots(Set<UserPot> userPots) {this.userPots = userPots;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public Double getScore() {return score;}
    public void setScore(Double score) {this.score = score;}
    public Set<Pot> getInvites() {return invites;}
    public void setInvites(Set<Pot> invites) {this.invites = invites;}
    public boolean isDnbCustomer() {return dnbCustomer;}
    public void setDnbCustomer(boolean dnbCustomer) {this.dnbCustomer = dnbCustomer;}
    public Date getCreated() {return created;}
    public void setCreated(Date created) {this.created = created;}
//    public Set<Loan> getLoans() {return loans;}
//    public void setLoans(Set<Loan> loans) {this.loans = loans;}
    public Set<PotSnapshot> getInvestments() {return investments;}
    public void setInvestments(Set<PotSnapshot> investments) {this.investments = investments;}
    public float getMoney() {return money;}
    public void setMoney(float money) {this.money = money;}
}