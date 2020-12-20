package com.example.meetme;

import java.util.ArrayList;

public class AllClients {
    ArrayList<User>allClientsInDB = new ArrayList<>();
    public AllClients(){
        allClientsInDB= new ArrayList<>();
    }
    public AllClients(ArrayList<User> allClientsInDB) {
        this.allClientsInDB = allClientsInDB;
    }

    public ArrayList<User> getAllClientsInDB() {
        return allClientsInDB;
    }

    public void setAllClientsInDB(ArrayList<User> allClientsInDB) {
        this.allClientsInDB = allClientsInDB;
    }

    public void addUser(User userTemp) {
        if(allClientsInDB!=null)
            allClientsInDB.add(userTemp);
    }
}
