package com.bop.backend.model;

import com.bop.backend.embedded.UserPotId;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
//@Transactional
//@Table(name = "user_pot")
public class Loan implements Serializable
{
    @Id
    @GeneratedValue
    private int loanId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "pot_id")
    private Pot pot;
    private Date createdDate;
    private Float money;
//    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
//    private Set<PotSnapshot> investors;

//    public Loan() {}
    @JsonRawValue
    public int getLoanId() {return loanId;}
    public void setLoanId(int loanId) {this.loanId = loanId;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public Pot getPot() {return pot;}
    public void setPot(Pot pot) {this.pot = pot;}
    public Date getCreatedDate() {return createdDate;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}
    public Float getMoney() {return money;}
    public void setMoney(Float money) {this.money = money;}
//    public Set<PotSnapshot> getInvestors() {return investors;}
//    public void setInvestors(Set<PotSnapshot> investors) {this.investors = investors;}
}