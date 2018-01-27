package com.bop.backend.model;

import com.fasterxml.jackson.annotation.JsonRawValue;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * Created by Robert on 02.03.2017.
 */
@Entity
//@Transactional
public class PotSnapshot implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn (name = "loan_id")
    private Loan loan;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private float potPercentage;

    @JsonRawValue
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public Loan getLoan() {return loan;}
    public void setLoan(Loan loan) {this.loan = loan;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public float getPotPercentage() {return potPercentage;}
    public void setPotPercentage(float potPercentage) {this.potPercentage = potPercentage;}
}
