package DAO;
import java.sql.*;
import Model.Message;
import io.javalin.http.Context;
import Util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;

//\"posted_by\ int
//"\"message_text\" String
// "\"time_posted_epoch\ long

public class MessageDao {

    public static Message[] getAllMessagesForUser(int accountId) {
        String sql = "SELECT * FROM message WHERE posted_by = ?";
        List<Message> messagesList = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Message message = new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch")
                    );
                    messagesList.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message[0]; // Return an empty array on error
        }

        return messagesList.toArray(new Message[0]);
    }

    public static Message createMessage(Message message) {
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, message.getPosted_by());
            pstmt.setString(2, message.message_text);
            pstmt.setLong(3, message.time_posted_epoch);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                message.setMessage_id(rs.getInt(1));
            }
            return message;
        }
         catch(SQLException e){
            e.printStackTrace();
             return null;
        }
    }

    public Message deleteMessage(int messageId) {
        String sqlSelect = "SELECT * FROM message WHERE message_id = ?";
        String sqlDelete = "DELETE FROM message WHERE message_id = ?";
        Message messageToDelete = null;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete)) {

            pstmtSelect.setInt(1, messageId);
            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                messageToDelete = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
            }
            pstmtDelete.setInt(1, messageId);
            pstmtDelete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messageToDelete; // should be message that is deleted or null
    }
}
