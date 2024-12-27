package org.example._socialnetwork_.Repository.file;

import org.example._socialnetwork_.domain.Prietenie;
import org.example._socialnetwork_.domain.validators.Validator;

import java.time.LocalDateTime;

public class PrietenieRepository extends AbstractFileRepository<Long, Prietenie> {
    public PrietenieRepository(Validator<Prietenie> validator, String filename) {
        super(validator, filename);
    }

    @Override
    public Prietenie lineToEntity(String line) {
        String[] parts = line.split(";");
        Prietenie p = new Prietenie(Long.parseLong(parts[1]), Long.parseLong(parts[2]), LocalDateTime.now());
        p.setId(Long.parseLong(parts[0]));
        return p;
    }

    @Override
    public String entityToLine(Prietenie entity) {
        return entity.getId() + ";" + entity.getFirst() + ";" + entity.getSecond();
    }
}
