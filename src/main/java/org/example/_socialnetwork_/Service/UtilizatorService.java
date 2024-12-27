package org.example._socialnetwork_.Service;

import org.example._socialnetwork_.Repository.Repository;
import org.example._socialnetwork_.Utils.events.ChangeEventType;
import org.example._socialnetwork_.Utils.events.UtilizatorEntityChangeEvent;
import org.example._socialnetwork_.Utils.observer.Observable;
import org.example._socialnetwork_.Utils.observer.Observer;
import org.example._socialnetwork_.domain.Utilizator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilizatorService implements Observable<UtilizatorEntityChangeEvent> {
    private Repository<Long, Utilizator> repository;
    private List<Observer<UtilizatorEntityChangeEvent>> observers = new ArrayList<>();

    public UtilizatorService(Repository<Long, Utilizator> repository) {
        this.repository = repository;
    }

    public Utilizator addUtilizator(Utilizator utilizator){
        if (repository.save(utilizator).isEmpty()){
            UtilizatorEntityChangeEvent event = new UtilizatorEntityChangeEvent(ChangeEventType.ADD, utilizator);
            notifyObservers(event);
            return null;
        }
        return utilizator;
    }

    public Utilizator findUtilizator(Long id){
        return repository.findOne(id).orElse(null);
    }

    public Utilizator deleteUtilizator(Long id){
        Optional<Utilizator> utilizator = repository.delete(id);
        if (utilizator.isPresent()){
            notifyObservers(new UtilizatorEntityChangeEvent(ChangeEventType.DELETE, utilizator.get()));
            return utilizator.get();
        }
        return null;
    }

    public Iterable<Utilizator> getAllUtilizatori(){
        return repository.findAll();
    }

    public Utilizator updateUtilizator(Utilizator utilizator){
        Optional<Utilizator> oldUtilizator = repository.findOne(utilizator.getId());
        if (oldUtilizator.isPresent()){
            Optional<Utilizator> updatedUtilizator = repository.update(utilizator);
            if (updatedUtilizator.isEmpty()){
                notifyObservers(new UtilizatorEntityChangeEvent(ChangeEventType.UPDATE, utilizator, oldUtilizator.get()));
            }
            return updatedUtilizator.orElse(null);
        }
        return oldUtilizator.orElse(null);

    }

    @Override
    public void addObserver(Observer<UtilizatorEntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<UtilizatorEntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(UtilizatorEntityChangeEvent t) {
        observers.stream().forEach(x -> x.update(t));
    }
}
