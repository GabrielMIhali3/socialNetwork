package org.example._socialnetwork_.Repository.database;

import org.example._socialnetwork_.Repository.Repository;
import org.example._socialnetwork_.domain.Utilizator;
import org.example._socialnetwork_.domain.validators.ValidationException;
import org.example._socialnetwork_.domain.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UtilizatorDBRepository implements Repository<Long, Utilizator> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Utilizator> validator;

    public UtilizatorDBRepository(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    private Utilizator createUserFromResultSet(ResultSet resultSet){
        try {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            Utilizator utilizator = new Utilizator(firstName, lastName);
            utilizator.setId(id);
            utilizator.setUsername(username);
            utilizator.setPassword(password);
            return utilizator;
        }catch (SQLException e){
            return null;
        }
    }

    @Override
    public Optional<Utilizator> findOne(Long id) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM Utilizator WHERE id = '%d'", id))){
            Utilizator utilizator;
            if (resultSet.next()) {
                utilizator = createUserFromResultSet(resultSet);
                return Optional.ofNullable(utilizator);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator>users = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Utilizator");
            ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()) {
                Utilizator utilizator = createUserFromResultSet(resultSet);
                users.add(utilizator);
            }
            return users;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<Utilizator> save(Utilizator entity) throws ValidationException {
        validator.validate(entity);
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Utilizator (first_name, last_name, username, password) VALUES (?, ?, ?, ?)")){

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getUsername());
            preparedStatement.setString(4, entity.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Utilizator> delete(Long id) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Utilizator WHERE id= ?")){

            Optional<Utilizator> utilizator = findOne(id);
            if (utilizator.isPresent()) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
            return utilizator;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public Optional<Utilizator> update(Utilizator entity) {
        if (entity == null){
            throw new IllegalArgumentException("Entity cannot be null");
        }
        validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Utilizator set first_name = ?, last_name = ?, username = ?, password = ? WHERE id = ?")){

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getUsername());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setLong(5, entity.getId());
            if (preparedStatement.executeUpdate() > 0)
                return Optional.empty();
            return Optional.ofNullable(entity);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
