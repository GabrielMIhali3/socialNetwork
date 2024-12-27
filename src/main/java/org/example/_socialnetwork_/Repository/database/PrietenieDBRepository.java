package org.example._socialnetwork_.Repository.database;

import org.example._socialnetwork_.Repository.Repository;
import org.example._socialnetwork_.domain.Prietenie;
import org.example._socialnetwork_.domain.validators.ValidationException;
import org.example._socialnetwork_.domain.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public class PrietenieDBRepository implements Repository<Long, Prietenie> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Prietenie> validator;
    Connection connection;

    public PrietenieDBRepository(String url, String username, String password, Validator<Prietenie> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        try {
            connection = DriverManager.getConnection(url, username, password);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Prietenie createPrietenieFromResultSet(ResultSet resultSet) {
        try{
            Long id = resultSet.getLong("id");
            Long user1 = resultSet.getLong("user1");
            Long user2 = resultSet.getLong("user2");
            LocalDateTime datetime = resultSet.getTimestamp("date").toLocalDateTime();
            boolean status = resultSet.getBoolean("status");
            Long sender = resultSet.getLong("sender");
            Prietenie prietenie = new Prietenie(user1, user2, datetime);
            prietenie.setStatus(status);
            prietenie.setSender(sender);
            prietenie.setId(id);
            return prietenie;
        }catch (SQLException e){
            return null;
        }
    }

    @Override
    public Optional<Prietenie> findOne(Long id) {
        try(ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM Prietenie WHERE id = '%d'", id))){
            Prietenie prietenie;
            if (resultSet.next()) {
                prietenie = createPrietenieFromResultSet(resultSet);
                return Optional.ofNullable(prietenie);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> prietenii = new HashSet<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Prietenie");
            ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()) {
                Prietenie prietenie = createPrietenieFromResultSet(resultSet);
                prietenii.add(prietenie);
            }
            return prietenii;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return prietenii;
    }

    @Override
    public Optional<Prietenie> save(Prietenie entity) throws ValidationException {
        validator.validate(entity);
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Prietenie (user1, user2, date, status, sender) VALUES (?, ?, ?, ?, ?)")){

            preparedStatement.setLong(1, entity.getFirst());
            preparedStatement.setLong(2, entity.getSecond());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            preparedStatement.setBoolean(4, entity.isStatus());
            preparedStatement.setLong(5, entity.getSender());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Prietenie> delete(Long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Prietenie WHERE id= ?")){

            Optional<Prietenie> prietenie = findOne(id);
            if (prietenie.isPresent()) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
            return prietenie;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Prietenie> update(Prietenie entity) {
        if (entity == null){
            throw new IllegalArgumentException("Entity cannot be null");
        }
        validator.validate(entity);
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Prietenie set user1 = ?, user2 = ?, date = ?, status = ?, sender = ? WHERE id = ?")){

            preparedStatement.setLong(1, entity.getFirst());
            preparedStatement.setLong(2, entity.getSecond());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            preparedStatement.setBoolean(4, entity.isStatus());
            preparedStatement.setLong(5, entity.getSender());
            preparedStatement.setLong(6, entity.getId());
            if (preparedStatement.executeUpdate() > 0)
                return Optional.empty();
            return Optional.ofNullable(entity);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
