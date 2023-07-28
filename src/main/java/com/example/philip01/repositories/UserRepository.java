package com.example.philip01.repositories;

import com.example.philip01.helpers.Convert;
import com.example.philip01.helpers.RandomS;
import com.example.philip01.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class UserRepository  {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getAllUserNames() {
        List<String> usernameList = new ArrayList<>();
        usernameList.addAll(jdbcTemplate.queryForList("SELECT userName FROM user_philip;", String.class));
        return usernameList;
    }

    public List<Users> getAllUsers() {
//        List<Users> userList = new ArrayList<>();
        String sql = "SELECT * FROM user_philip";
//        userList.addAll(jdbcTemplate.query(sql, new UserRowMapper()));
        List<Users> users = jdbcTemplate.query(sql, new UserRowMapper());
        return users;
    }

    public List<Users> findByUserName(String userName) {
        String sql = "SELECT * FROM user_philip WHERE userName = ?";
        List<Users> users = jdbcTemplate.query(sql, new Object[]{userName}, new UserRowMapper());
        return users;
    }

    public Users saveUser(Users user) {
        String sql = "INSERT INTO user_philip (userName, passWord) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getPassWord());
        return findByUserName(user.getUserName()).get(0);
    }

    public void createMoreUsers() {
        for (int i = 0; i < 1000000; i++) {
            Random random = new Random();
            int randomNumber = random.nextInt(7) + 6;
            String username = RandomS.generateRandomString(randomNumber);
            String password = Convert.convertString2SHA1(RandomS.generateRandomString(randomNumber));
            saveUser(new Users(username, password));
        }
    }

    public void clearAllUsers() {
        String sql = "TRUNCATE TABLE user_philip";
        jdbcTemplate.execute(sql);
    }

    private class UserRowMapper implements RowMapper<Users> {
        @Override
        public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
            Users user = new Users();
            user.setUserID(rs.getLong("userID"));
            user.setUserName(rs.getString("userName"));
            user.setPassWord(rs.getString("passWord"));
            return user;
        }
    }
}

