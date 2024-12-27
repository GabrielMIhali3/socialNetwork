package org.example._socialnetwork_.Service;

import org.example._socialnetwork_.Repository.Repository;
import org.example._socialnetwork_.Utils.events.ChangeEventType;
import org.example._socialnetwork_.Utils.events.PrietenieEntityChangeEvent;
import org.example._socialnetwork_.Utils.observer.Observable;
import org.example._socialnetwork_.Utils.observer.Observer;
import org.example._socialnetwork_.domain.Prietenie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrietenieService implements Observable<PrietenieEntityChangeEvent> {
    private Repository<Long, Prietenie> repository;
    private List<Observer<PrietenieEntityChangeEvent>> observers = new ArrayList<>();


    public PrietenieService(Repository<Long, Prietenie> repository) {
        this.repository = repository;
    }

    public Prietenie addPrietenie(Prietenie prietenie) {
        if (repository.save(prietenie).isEmpty()){
            PrietenieEntityChangeEvent event = new PrietenieEntityChangeEvent(ChangeEventType.ADD, prietenie);
            notifyObservers(event);
            return null;
        }
        return prietenie;
    }

    public Prietenie deletePrietenie(Long id) {
        Optional<Prietenie> prietenie = repository.delete(id);
        if (prietenie.isPresent()){
            notifyObservers(new PrietenieEntityChangeEvent(ChangeEventType.DELETE, prietenie.get()));
            return prietenie.get();
        }
        return null;
    }

    public Iterable<Prietenie> getAllPrietenii() {
        return repository.findAll();
    }

    public Prietenie updatePrietenie(Prietenie prietenie) {
        Optional<Prietenie> oldPrietenie = repository.findOne(prietenie.getId());
        if (oldPrietenie.isPresent()){
            Optional<Prietenie> updatedPrietenie = repository.update(prietenie);
            if (updatedPrietenie.isEmpty()){
                notifyObservers(new PrietenieEntityChangeEvent(ChangeEventType.UPDATE, prietenie, oldPrietenie.get()));
            }
            return updatedPrietenie.orElse(null);
        }
        return oldPrietenie.orElse(null);
    }

    @Override
    public void addObserver(Observer<PrietenieEntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<PrietenieEntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(PrietenieEntityChangeEvent t) {
        observers.stream().forEach(x -> x.update(t));
    }
}
