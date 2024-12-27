package org.example._socialnetwork_.Repository.file;

import org.example._socialnetwork_.Repository.memory.InMemoryRepository;
import org.example._socialnetwork_.domain.Entity;
import org.example._socialnetwork_.domain.validators.Validator;

import java.io.*;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private String filename;

    public AbstractFileRepository(Validator<E> validator, String filename) {
        super(validator);
        this.filename = filename;
    }

    public abstract E lineToEntity(String line);
    public abstract String entityToLine(E entity);

    private void writeToFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for (Map.Entry<ID, E> e : entities.entrySet()) {
                writer.write(entityToLine(e.getValue()));
                writer.newLine();
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void readToFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            entities.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                E entity = lineToEntity(line);
                entities.put(entity.getId(), entity);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public  Optional<E> findOne(ID id) {
        readToFile();
        return super.findOne(id);
    }

    @Override
    public  Iterable<E> findAll() {
        readToFile();
        return super.findAll();
    }

    @Override
    public Optional<E> save(E entity) {
        readToFile();
        Optional<E> result = super.save(entity);
        writeToFile();
        return result;
    }

    @Override
    public Optional<E> delete(ID id) {
        readToFile();
        Optional<E> result = super.delete(id);
        writeToFile();
        return result;
    }

    @Override
    public Optional<E> update(E entity) {
        readToFile();
        Optional<E> result = super.update(entity);
        writeToFile();
        return result;
    }
}
