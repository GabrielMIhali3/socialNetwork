package org.example._socialnetwork_.domain.validators;


import org.example._socialnetwork_.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String errMsg = "";
        if (entity.getFirstName() == null || "".equals(entity.getFirstName())) {
            errMsg += "firstName error ";
        }
        if (entity.getLastName() == null || "".equals(entity.getLastName())) {
            errMsg += "lastName error ";
        }
        if (errMsg != ""){
            throw new ValidationException(errMsg);
        }
    }
}
