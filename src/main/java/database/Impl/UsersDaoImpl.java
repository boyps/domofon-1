package database.Impl;

import database.UsersDao;
import database.entity.UsersEntity;
import pro.nextbit.telegramconstructor.database.DataBaseUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsersDaoImpl implements UsersDao {

    private DataBaseUtils utils;

    public UsersDaoImpl(DataSource source) {
        this.utils = new DataBaseUtils(source);
    }


    @Override
    public List<UsersEntity> UserList(long chatId) {
        return utils.query("SELECT * FROM users WHERE chat_id = ?",
                new Object[]{chatId}, this::mapper
        );
    }

    @Override
    public List<UsersEntity> getList() {
        return utils.query("SELECT * FROM users ORDER BY user_name",
                new Object[]{}, this::mapper
        );
    }

    @Override
    public UsersEntity getByChatId(long chatId) {
        return utils.queryForObject("SELECT * FROM users WHERE chat_id = ?",
                new Object[]{chatId}, this::mapper
        );
    }


    @Override
    public int insert(UsersEntity user) {
        utils.update(
                "INSERT INTO users (chat_id, user_name, user_surname, " +
                        "id_access_level) " +
                        "VALUES (?,?,?,?)",
                user.getChat_id(),
                user.getUser_name(),
                user.getUser_surname(),
                user.getId_access_level()
        );

        return utils.queryDataRec("SELECT last_value FROM users_id_seq").getInt("last_value");

    }

    @Override
    public void updateAdmin(long adminId, boolean admin) {
        utils.update("UPDATE users SET admin = ? WHERE chat_id= ?", admin, adminId);
    }

    @Override
    public List<UsersEntity> adminList(boolean admin) {
        return utils.query("SELECT * FROM users WHERE admin = ?",
                new Object[]{admin}, this::mapper
        );
    }

    @Override
    public List<UsersEntity> getAdmin(long adminid,boolean admin) {
        return utils.query("SELECT * FROM users WHERE chat_id = ? AND admin = ?",
                new Object[]{adminid, admin}, this::mapper
        );
    }


    @Override
    public void delete(long chat_id) {
        utils.update("DELETE FROM users WHERE chat_id = ? ", chat_id);
    }


    private UsersEntity mapper(ResultSet rs, int index) throws SQLException {

        UsersEntity u = new UsersEntity();
        u.setId(rs.getInt("id"));
        u.setId_access_level(rs.getInt("id_access_level"));
        u.setChat_id(rs.getLong("chat_id"));
        u.setUser_name(rs.getString("user_name"));
        u.setUser_surname(rs.getString("user_surname"));
        u.setAdmin(rs.getBoolean("admin"));
        return u;
    }
}
