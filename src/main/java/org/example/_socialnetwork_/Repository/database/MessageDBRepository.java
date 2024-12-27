package org.example._socialnetwork_.Repository.database;

import org.example._socialnetwork_.Repository.Repository;
import org.example._socialnetwork_.controller.MessageAlert;
import org.example._socialnetwork_.domain.Message;
import org.example._socialnetwork_.domain.Utilizator;
import org.example._socialnetwork_.domain.validators.UtilizatorValidator;
import org.example._socialnetwork_.domain.validators.ValidationException;
import org.example._socialnetwork_.domain.validators.Validator;

import java.net.ConnectException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MessageDBRepository implements Repository<Long, Message> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Message> validator;
    UtilizatorDBRepository utilizatorDBRepository;
    Connection connection;

    public MessageDBRepository(String url, String username, String password, Validator<Message> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        utilizatorDBRepository = new UtilizatorDBRepository(url, username, password, new UtilizatorValidator());
        try{
            connection = DriverManager.getConnection(url, username, password);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Message createMessageFromResultSet(ResultSet resultSet){
        try{
            Long id = resultSet.getLong("id");
            String text = resultSet.getString("message");
            Long fromID = resultSet.getLong("fromID");
            Long toID = resultSet.getLong("toID");
            LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();
            Utilizator from = utilizatorDBRepository.findOne(fromID).get();
            Utilizator to = utilizatorDBRepository.findOne(toID).get();
            Message message = new Message(text, from, to, dateTime);
            message.setId(id);
            return message;
        }catch (SQLException e){
            return null;
        }
    }

    @Override
    public Optional<Message> findOne(Long id) {
        try(ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM Messages WHERE id = '%d'", id))){

            Message message;
            if (resultSet.next()){
                message = createMessageFromResultSet(resultSet);
                return Optional.ofNullable(message);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Messages")){

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Message message = createMessageFromResultSet(resultSet);
                messages.add(message);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Optional<Message> save(Message entity) throws ValidationException {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Messages(message, fromID, toID, date) VALUES (?, ?, ?, ?)")){
            statement.setString(1, entity.getMessage());
            statement.setLong(2, entity.getFrom().getId());
            statement.setLong(3, entity.getTo().getId());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getDate()));

            statement.executeUpdate();
        }catch (SQLException e){
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Message> delete(Long id) {
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM Messages WHERE id = ?")){

            Optional<Message> message = findOne(id);
            if (message.isPresent()){
                statement.setLong(1, id);
                statement.executeUpdate();
            }
            return message;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Message> update(Message entity) {
        try(PreparedStatement statement = connection.prepareStatement("UPDATE Messages set message = ?, fromID = ?, toID = ?, date = ? WHERE id = ?")){

            statement.setString(1, entity.getMessage());
            statement.setLong(2, entity.getFrom().getId());
            statement.setLong(3, entity.getTo().getId());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getDate()));
            statement.setLong(5, entity.getId());

            if (statement.executeUpdate() > 0){
                return Optional.empty();
            }
            return Optional.ofNullable(entity);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}