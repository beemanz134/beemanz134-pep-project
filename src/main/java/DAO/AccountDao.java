package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {

    public Account createAccount(Account account) {
        String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                account.setAccount_id(rs.getInt(1)); // Set the generated account_id
            }
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT * FROM account WHERE username = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // If a record exists, return true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}