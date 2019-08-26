package com.example.carogamejwt.model;

import javax.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private User firstUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private User secondUser;

//    @OneToMany
//    private User watchUser;

    public Room() {
    }

    public Room(String password, User firstUser, User secondUser) {
        this.password = password;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
//        this.watchUser = watchUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }
//
//    public User getWatchUser() {
//        return watchUser;
//    }
//
//    public void setWatchUser(User watchUser) {
//        this.watchUser = watchUser;
//    }
}
