package org.example._socialnetwork_.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example._socialnetwork_.Service.Service;
import org.example._socialnetwork_.Utils.events.MessageEntityChangeEvent;
import org.example._socialnetwork_.Utils.observer.Observer;
import org.example._socialnetwork_.domain.Message;
import org.example._socialnetwork_.domain.Utilizator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageViewController implements Observer<MessageEntityChangeEvent> {
    private Service service;
    Utilizator utilizator;
    Utilizator utilizatorFriend;
    private AnchorPane anchorPaneMain;
    private AnchorPane messagePane;

    @FXML
    private Label name;
    @FXML
    private VBox messagesBox;
    @FXML
    private TextField sendMessageTextField;
    @FXML
    private ScrollPane scrollPane;

    private List<Message> messagesList(){
        Iterable<Message> list = service.getAllMessages();
        List<Message> messages = new ArrayList<>();

        for (Message m : list){
            if (m.getFrom().getId() == utilizator.getId() && m.getTo().getId() == utilizatorFriend.getId() ||
                m.getFrom().getId() == utilizatorFriend.getId() && m.getTo().getId() == utilizator.getId()){
                messages.add(m);
            }
        }

        messages.sort((m1, m2)-> m1.getDate().compareTo(m2.getDate()));
        return messages;
    }

    @FXML
    void initialize() {
        scrollPane.setVvalue(1.0);
    }

    private void showMessages(){
        messagesBox.getChildren().clear();

        List<Message> list = messagesList();

        for (Message m : list){
            HBox hbox = new HBox();

            TextFlow textFlow = new TextFlow(new Text(m.getMessage()));
            if (utilizator.getId() == m.getFrom().getId()){
                hbox.setAlignment(Pos.CENTER_RIGHT);
                textFlow.setStyle("-fx-background-color: #DCF8C6; -fx-background-radius: 15px; -fx-padding: 10px;");
            } else{
                hbox.setAlignment(Pos.CENTER_LEFT);
                textFlow.setStyle("-fx-background-color: #ADD8E6; -fx-background-radius: 15px; -fx-padding: 10px;");
            }
            textFlow.setMaxWidth(300);

            hbox.getChildren().add(textFlow);

            messagesBox.getChildren().add(hbox);
        }

        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }

    public void setServiceMessageController(Service service, Utilizator utilizatorAdmin, Utilizator utilizatorFriend, AnchorPane anchorPaneMain, AnchorPane messagePane) {
        this.service = service;
        this.utilizator = utilizatorAdmin;
        this.utilizatorFriend = utilizatorFriend;
        this.anchorPaneMain = anchorPaneMain;
        this.messagePane = messagePane;

        service.getMessageService().addObserver(this);
        name.setText(utilizatorFriend.getLastName() + " " + utilizatorFriend.getFirstName());
        showMessages();
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        String messageText = sendMessageTextField.getText();

        if (!messageText.isEmpty()) {
            Message message = new Message(messageText, utilizator, utilizatorFriend, LocalDateTime.now());
            service.addMessage(message);

            sendMessageTextField.setText("");
        }
    }

    @FXML
    private void close(ActionEvent event) {
        service.getMessageService().removeObserver(this);
        anchorPaneMain.getChildren().remove(messagePane);
    }

    @Override
    public void update(MessageEntityChangeEvent messageEntityChangeEvent) {
        showMessages();
    }
}
