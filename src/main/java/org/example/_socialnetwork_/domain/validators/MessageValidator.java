package org.example._socialnetwork_.domain.validators;

import org.example._socialnetwork_.domain.Message;

public class MessageValidator implements Validator<Message>{
    @Override
    public void validate(Message entity) throws ValidationException {
        String error = "";
        if (entity.getMessage().isEmpty())
            error = "Message is empty";
        if (entity.getFrom() == null)
            error = "From is empty";
        if (entity.getTo() == null)
            error = "To is empty";
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
