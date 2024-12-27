package org.example._socialnetwork_.Repository.file;

import org.example._socialnetwork_.domain.Utilizator;
import org.example._socialnetwork_.domain.validators.Validator;

public class UtilizatorRepository extends AbstractFileRepository<Long, Utilizator>{
    public UtilizatorRepository(Validator<Utilizator> validator, String filename) {
        super(validator, filename);
    }

    @Override
    public Utilizator lineToEntity(String line) {
        String[] parts = line.split(";");
        Utilizator u = new Utilizator(parts[1], parts[2]);
        u.setId(Long.parseLong(parts[0]));
        return u;
    }

    @Override
    public String entityToLine(Utilizator entity) {
        return entity.getId() + ";" + entity.getFirstName() + ";" + entity.getLastName();
    }
}
