package org.example._socialnetwork_.domain.validators;

import org.example._socialnetwork_.domain.Prietenie;

public class PrietenieValidator implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        String error = "";
        if (entity.getFirst() == null){
            error += "first error ";
        }
        if (entity.getSecond() == null){
            error += "second error ";
        }
        if (error != ""){
            throw new ValidationException(error);
        }
    }
}
