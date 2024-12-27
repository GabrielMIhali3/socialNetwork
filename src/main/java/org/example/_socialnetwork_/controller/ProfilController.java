package org.example._socialnetwork_.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example._socialnetwork_.Service.Service;
import org.example._socialnetwork_.domain.Prietenie;
import org.example._socialnetwork_.domain.Utilizator;
import javafx.scene.control.Label;


public class ProfilController {
    private Service service;
    Utilizator utilizator;
    Utilizator utilizatorFriend;
    Prietenie prietenie;

    @FXML
    private Label name;
    @FXML
    private Label username;

    public void setServiceProfileController(Service service, Utilizator utilizatorAdmin, Utilizator utilizatorFriend) {
        this.service = service;
        this.utilizator = utilizatorAdmin;
        this.utilizatorFriend = utilizatorFriend;
        name.setText(this.utilizatorFriend.getLastName() + " " + this.utilizatorFriend.getFirstName());
        username.setText(this.utilizatorFriend.getUsername());
        prietenie = new OperatiiPrietenie(service, utilizator, this.utilizatorFriend).detPrietenie();
    }

    @FXML
    public void handleDeletePrieten(ActionEvent actionEvent) {
        if (prietenie != null) {
            Prietenie deleted = service.deletePrietenie(prietenie.getId());
        }
    }
}
