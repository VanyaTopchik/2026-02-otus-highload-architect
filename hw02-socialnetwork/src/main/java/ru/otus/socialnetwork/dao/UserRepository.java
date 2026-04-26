package ru.otus.socialnetwork.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.socialnetwork.model.User;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<User> rowMapper = (rs, rowNum) -> {
    User user = new User();
    user.setId(UUID.fromString(rs.getString("id")));
    user.setFirstName(rs.getString("first_name"));
    user.setSecondName(rs.getString("second_name"));
    user.setBirthdate(rs.getDate("birthdate").toLocalDate());
    user.setBiography(rs.getString("biography"));
    user.setGender(rs.getString("gender"));
    user.setCity(rs.getString("city"));
    user.setPasswordHash(rs.getString("password_hash"));
    if (rs.getObject("token") != null) {
      user.setToken(UUID.fromString(rs.getString("token")));
    }
    return user;
  };

  public Optional<User> findById(UUID id) {
    String sql = "SELECT * FROM users WHERE id = ?";
    try {
      User user = jdbcTemplate.queryForObject(sql, rowMapper, id);
      return Optional.ofNullable(user);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public Optional<User> findByToken(UUID token) {
    String sql = "SELECT * FROM users WHERE token = ?";
    try {
      User user = jdbcTemplate.queryForObject(sql, rowMapper, token);
      return Optional.ofNullable(user);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public User save(User user) {
    if (user.getId() == null) {
      // вставка
      String sql = "INSERT INTO users (first_name, second_name, birthdate, biography, gender, city, password_hash) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?)";
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getSecondName());
        ps.setDate(3, java.sql.Date.valueOf(user.getBirthdate()));
        ps.setString(4, user.getBiography());
        ps.setString(5, user.getGender());
        ps.setString(6, user.getCity());
        ps.setString(7, user.getPasswordHash());
        return ps;
      }, keyHolder);

      UUID generatedId = (UUID) keyHolder.getKeys().get("id");
      user.setId(generatedId);
    } else {
      // обновление (используется только для установки токена)
      String sql = "UPDATE users SET token = ? WHERE id = ?";
      jdbcTemplate.update(sql, user.getToken(), user.getId());
    }
    return user;
  }

  public void updateToken(UUID userId, UUID token) {
    String sql = "UPDATE users SET token = ? WHERE id = ?";
    jdbcTemplate.update(sql, token, userId);
  }

  public List<User> searchByFirstNameAndSecondName(String firstNamePart, String lastNamePart) {
    String sql = "SELECT * FROM users WHERE first_name ILIKE ? AND second_name ILIKE ?";
    String firstNamePattern = "%" + firstNamePart + "%";
    String lastNamePattern = "%" + lastNamePart + "%";
    return jdbcTemplate.query(sql, rowMapper, firstNamePattern, lastNamePattern);
  }
}