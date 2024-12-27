package org.example._socialnetwork_.Service;

import org.example._socialnetwork_.Repository.database.MessageDBRepository;
import org.example._socialnetwork_.Repository.database.PrietenieDBRepository;
import org.example._socialnetwork_.Repository.database.UtilizatorDBRepository;
import org.example._socialnetwork_.domain.Message;
import org.example._socialnetwork_.domain.Prietenie;
import org.example._socialnetwork_.domain.Utilizator;
import org.example._socialnetwork_.domain.validators.MessageValidator;
import org.example._socialnetwork_.domain.validators.PrietenieValidator;
import org.example._socialnetwork_.domain.validators.UtilizatorValidator;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private String username;
    private String password;
    private String url;
    private UtilizatorService utilizatorService;
    private PrietenieService prietenieService;
    private MessageService messageService;

    public Service(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;

        utilizatorService = new UtilizatorService(new UtilizatorDBRepository(url, username, password, new UtilizatorValidator()));
        prietenieService = new PrietenieService(new PrietenieDBRepository(url, username, password, new PrietenieValidator()));
        messageService = new MessageService(new MessageDBRepository(url, username, password, new MessageValidator()));
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public UtilizatorService getUtilizatorService() {
        return utilizatorService;
    }

    public PrietenieService getPrietenieService() {
        return prietenieService;
    }

    public Utilizator addUtilizator(Utilizator utilizator) {
        return utilizatorService.addUtilizator(utilizator);
    }

    public Utilizator deleteUtilizator(Long id) {
        Iterable<Prietenie> list = prietenieService.getAllPrietenii();
        List<Prietenie> ListaPrietenii = new ArrayList<>();
        list.forEach(ListaPrietenii::add);
        ListaPrietenii
                .forEach(value -> {
                    if (value.getFirst() == id || value.getSecond() == id)
                        prietenieService.deletePrietenie(value.getId());
                });
        return utilizatorService.deleteUtilizator(id);
    }

    public Utilizator findUtilizator(Long id) {
        return utilizatorService.findUtilizator(id);
    }

    public Iterable<Utilizator> getAllUtilizatori() {
        return utilizatorService.getAllUtilizatori();
    }

    public Utilizator updateUtilizator(Utilizator utilizator) {
        return utilizatorService.updateUtilizator(utilizator);
    }

    public Prietenie addPrietenie(Prietenie prietenie) {
        Iterable<Utilizator> list = utilizatorService.getAllUtilizatori();
        boolean u1 = false, u2 = false;
        for (Utilizator utilizator : list) {
            if (prietenie.getFirst() == utilizator.getId())
                u1 = true;
            if (prietenie.getSecond() == utilizator.getId())
                u2 = true;
        }

        Iterable<Prietenie> listPrietenii = prietenieService.getAllPrietenii();
        for (Prietenie p : listPrietenii){
            if (p.getFirst() == prietenie.getFirst() && p.getSecond() == prietenie.getSecond())
                u1 = false;
            else if (p.getSecond() == prietenie.getFirst() && p.getFirst() == prietenie.getSecond())
                u2 = false;
        }
        if (u1 && u2)
            return prietenieService.addPrietenie(prietenie);
        return null;
    }

    public Prietenie updatePrietenie(Prietenie prietenie) {
        return prietenieService.updatePrietenie(prietenie);
    }

    public Prietenie deletePrietenie(Long id) {
        return prietenieService.deletePrietenie(id);
    }

    public Iterable<Prietenie> getAllPrietenii() {
        return prietenieService.getAllPrietenii();
    }

    public Message addMessage(Message message) {
        return messageService.addMessage(message);
    }

    public Message deleteMessage(Long id) {
        return messageService.deleteMessage(id);
    }

    public Iterable<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    public Message findMessage(Long id) {
        return messageService.findMessage(id);
    }

}
