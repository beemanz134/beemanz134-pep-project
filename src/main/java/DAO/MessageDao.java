package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Message;
import io.javalin.http.Context;
import Util.ConnectionUtil;

//\"posted_by\ int
//"\"message_text\" String
// "\"time_posted_epoch\ long

public class MessageDao {
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
}
