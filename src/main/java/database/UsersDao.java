package database;

import database.entity.UsersEntity;

import java.util.List;

public interface UsersDao {

    List<UsersEntity> UserList(long chatId);

    UsersEntity getByChatId(long chatId);

    List<UsersEntity>  getList();

    int insert(UsersEntity user);

    void updateAdmin(long adminId, boolean admin);

    List<UsersEntity> adminList(boolean admin);

    List<UsersEntity> getAdmin(long adminid,boolean admin);

    void delete(long chat_id);

}
