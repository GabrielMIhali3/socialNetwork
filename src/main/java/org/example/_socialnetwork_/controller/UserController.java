package org.example._socialnetwork_.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example._socialnetwork_.Service.Service;
import org.example._socialnetwork_.Utils.events.PrietenieEntityChangeEvent;
import org.example._socialnetwork_.Utils.observer.Observer;
import org.example._socialnetwork_.domain.Prietenie;
import org.example._socialnetwork_.domain.Utilizator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserController implements Observer<PrietenieEntityChangeEvent> {
    private Service service;
    Stage dialogStage;
    Utilizator utilizator;

    @FXML
    private Label name;
    @FXML
    private VBox vBoxProfiles;
    @FXML
    private VBox vBoxFriendsRequests;
    @FXML
    private VBox vBoxAddFriend;
    @FXML
    private Button btnFriends;
    @FXML
    private ScrollPane scrollPaneProfiles;
    @FXML
    private ScrollPane scrollPaneFriedsRequests;
    @FXML
    private ScrollPane scrollPaneAddFriends;
    @FXML
    private ScrollPane scrollPaneMessageFriends;
    @FXML
    private VBox vBoxMessageFriend;
    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private AnchorPane notificareAnchorPane;
    @FXML
    private Label notificariLabel;

    private int numarNotificariAnterior = 0;


    public void setServiceUserController(Service service, Stage Stage, Utilizator utilizator) {
        this.service = service;
        this.dialogStage = Stage;
        this.utilizator = utilizator;
        service.getPrietenieService().addObserver(this);
        name.setText(utilizator.getLastName() + " " + utilizator.getFirstName());
        initFriedsList();
        initFriendsRequest();
    }

    @FXML
    void initialize() {
        btnFriends.getStyleClass().add("selected");

        scrollPaneProfiles.setVisible(true);
        scrollPaneFriedsRequests.setVisible(false);
        scrollPaneAddFriends.setVisible(false);
        scrollPaneMessageFriends.setVisible(false);

        notificariLabel.setVisible(false);
        notificareAnchorPane.setMouseTransparent(true);
    }

    @FXML
    private void logOut(ActionEvent event) {
        service.getPrietenieService().removeObserver(this);
        dialogStage.close();
    }

    @FXML
    private void showFriendsList(ActionEvent event) {
        scrollPaneProfiles.setVisible(true);
        scrollPaneFriedsRequests.setVisible(false);
        scrollPaneAddFriends.setVisible(false);
        scrollPaneMessageFriends.setVisible(false);

        handleButtonClick(event);
        initFriedsList();
    }

    @FXML
    private void showMessageFriendsList(ActionEvent event) {
        scrollPaneProfiles.setVisible(false);
        scrollPaneFriedsRequests.setVisible(false);
        scrollPaneAddFriends.setVisible(false);
        scrollPaneMessageFriends.setVisible(true);

        handleButtonClick(event);
        initMessageFriends();
    }

    @FXML
    private void showFriendsRequestList(ActionEvent event) {
        notificariLabel.setVisible(false);

        scrollPaneProfiles.setVisible(false);
        scrollPaneFriedsRequests.setVisible(true);
        scrollPaneAddFriends.setVisible(false);
        scrollPaneMessageFriends.setVisible(false);

        handleButtonClick(event);
        initFriendsRequest();
    }

    @FXML
    private void showAddFriends(ActionEvent event) {
        scrollPaneProfiles.setVisible(false);
        scrollPaneFriedsRequests.setVisible(false);
        scrollPaneAddFriends.setVisible(true);
        scrollPaneMessageFriends.setVisible(false);

        handleButtonClick(event);
        initAddFriendsList();
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        selectButton(button);
    }


    private void selectButton(Button button) {
        if (button.getParent() instanceof StackPane) {
            button.getParent().getParent().getChildrenUnmodifiable().forEach(node -> {
                if (node instanceof Button) {
                    node.getStyleClass().remove("selected");
                }
            });
        } else {
            button.getParent().getChildrenUnmodifiable().forEach(node -> {
                if (node instanceof Button) {
                    node.getStyleClass().remove("selected");
                }
                if (node instanceof StackPane) {
                    Node child = ((StackPane) node).getChildren().getFirst();
                    child.getStyleClass().remove("selected");
                }
            });
        }
        button.getStyleClass().add("selected");
    }

    private void initList(List<Utilizator> users){
        vBoxProfiles.getChildren().clear();
        for (Utilizator u : users){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/_socialnetwork_/views/profil.fxml"));
                AnchorPane profilePane = loader.load();

                ProfilController profilController = loader.getController();
                profilController.setServiceProfileController(service, utilizator, u);

                vBoxProfiles.getChildren().add(profilePane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Utilizator> FriendsList(){
        Iterable<Prietenie> all_pritenii = service.getAllPrietenii();
        List<Utilizator> users = new ArrayList<>();
        for (Prietenie prietenie : all_pritenii){
            prietenie.setDate(LocalDateTime.now());
            if (service.findUtilizator(prietenie.getFirst()).getId() == utilizator.getId() && prietenie.isStatus()){
                users.add(service.findUtilizator(prietenie.getSecond()));
            }else if (service.findUtilizator(prietenie.getSecond()).getId() == utilizator.getId() && prietenie.isStatus()){
                users.add(service.findUtilizator(prietenie.getFirst()));
            }
        }
        return users;
    }

    private void initFriedsList(){
        List<Utilizator> users = FriendsList();
        initList(users);
    }

    @FXML
    private void initMessageFriends(){
        List<Utilizator> users = FriendsList();

        vBoxMessageFriend.getChildren().clear();
        for (Utilizator u : users){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/_socialnetwork_/views/MessageFriendProfil.fxml"));
                AnchorPane profilePane = loader.load();

                MessageFriendProfilController messageFriendProfilController = loader.getController();
                messageFriendProfilController.setServiceMessageFriendsController(service, utilizator, u, anchorPaneMain);
                vBoxMessageFriend.getChildren().add(profilePane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initAddFriendsList(){
        Iterable<Utilizator> utilizatori = service.getAllUtilizatori();
        Iterable<Prietenie> prietenii = service.getAllPrietenii();
        List<Utilizator> users = new ArrayList<>();

        for (Utilizator user : utilizatori) {
            boolean found = false;
            if (user.getId() != utilizator.getId()) {
                for (Prietenie prietenie : prietenii) {
                    if (user.getId() == prietenie.getFirst() && utilizator.getId() == prietenie.getSecond()
                            || user.getId() == prietenie.getSecond() && utilizator.getId() == prietenie.getFirst()) {
                        Long to;
                        if (prietenie.getFirst() != prietenie.getSender())
                            to = prietenie.getFirst();
                        else
                            to = prietenie.getSecond();
                        if (prietenie.isStatus() || utilizator.getId() == to)
                            found = true;
                        break;
                    }
                }
                if (!found) {
                    users.add(user);
                }
            }
        }

        vBoxAddFriend.getChildren().clear();
        for (Utilizator u : users){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/_socialnetwork_/views/addFriend.fxml"));
                AnchorPane profilePane = loader.load();

                AddFriend addFriend = loader.getController();
                addFriend.setServiceFriendsRequest(service, utilizator, u);

                vBoxAddFriend.getChildren().add(profilePane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initFriendsRequest(){
        List<Utilizator> users = new ArrayList<>();
        Iterable<Prietenie> listPrietenii = service.getAllPrietenii();
        for (Prietenie prietenie : listPrietenii) {
            if (!prietenie.isStatus() && prietenie.getSender() == utilizator.getId()){
                Long id;
                if (prietenie.getFirst() != utilizator.getId())
                    id = prietenie.getFirst();
                else
                    id = prietenie.getSecond();
                users.add(service.findUtilizator(id));
            }
        }

        vBoxFriendsRequests.getChildren().clear();
        for (Utilizator u : users){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/_socialnetwork_/views/friendsRequest.fxml"));
                AnchorPane profilePane = loader.load();

                FriendsRequest friendsRequestController = loader.getController();
                friendsRequestController.setServiceFriendsRequest(service, utilizator, u);

                vBoxFriendsRequests.getChildren().add(profilePane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        notificariLabel.setText(String.valueOf(users.size()));
        if (numarNotificariAnterior != users.size() && !users.isEmpty()){
            notificariLabel.setVisible(true);
        }
        numarNotificariAnterior = users.size();
    }

    @Override
    public void update(PrietenieEntityChangeEvent prietenieEntityChangeEvent) {
        initFriedsList();
        initAddFriendsList();
        initFriendsRequest();
        initMessageFriends();
    }
}
