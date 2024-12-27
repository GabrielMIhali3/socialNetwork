package org.example._socialnetwork_.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example._socialnetwork_.Service.Service;
import org.example._socialnetwork_.domain.Prietenie;
import org.example._socialnetwork_.domain.Utilizator;

import java.time.LocalDateTime;

public class OperatiiPrietenie {
    private Service service;
    private Utilizator utilizator;
    private Utilizator utilizatorFriend;

    public OperatiiPrietenie(Service service, Utilizator utilizator, Utilizator utilizatorFriend) {
        this.service = service;
        this.utilizator = utilizator;
        this.utilizatorFriend = utilizatorFriend;
    }

    public Prietenie detPrietenie(){
        Iterable<Prietenie> list = service.getAllPrietenii();

        for (Prietenie p: list){
            if (p.getFirst() == utilizator.getId() && p.getSecond() == utilizatorFriend.getId() || p.getFirst() == utilizatorFriend.getId() && p.getSecond() == utilizator.getId()){
                return p;
            }
        }
        return null;
    }

    public void addFriend() {
        Prietenie prietenie = new Prietenie(utilizator.getId(), utilizatorFriend.getId(), LocalDateTime.now());
        prietenie.setStatus(false);
        prietenie.setSender(utilizatorFriend.getId());

        Iterable<Prietenie> list = service.getAllPrietenii();
        for (Prietenie p : list){
            if (p.getFirst() == prietenie.getSecond() && p.getSecond() == prietenie.getFirst() || p.getFirst() == prietenie.getFirst() && p.getSecond() == prietenie.getSecond()){
                if (!p.isStatus()){
                    p.setStatus(true);
                    p.setSender(0L);
                    service.updatePrietenie(p);
                }
            }
        }

        Prietenie prietenieAdded = service.addPrietenie(prietenie);
    }

}
