package handling.impl;

import database.UsersDao;
import database.entity.UsersEntity;
import handling.AbstractHandle;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import pro.nextbit.telegramconstructor.Json;
import pro.nextbit.telegramconstructor.components.keyboard.IKeyboard;
import pro.nextbit.telegramconstructor.components.keyboard.Keyboard;
import pro.nextbit.telegramconstructor.database.DataRec;
import pro.nextbit.telegramconstructor.stepmapping.Step;

import java.util.List;

public class UserHandle extends AbstractHandle {
    private UsersDao usersDao = daoFactory.usersDao();




    @Step(value = "userList", commandText = "\uD83D\uDD11 Доступ")
    public void userList() throws Exception {

        IKeyboard kb = new IKeyboard();
        kb.next();
        for (UsersEntity rec : usersDao.getList()) {
            kb.add(rec.getUser_name(), Json.set("step", "acceptTask").set("id", rec.getChat_id()));
        }
        kb.add("➕ Добавить", Json.set("step", "addUser"));
        kb.add("❌ Удалить", Json.set("step", "deleteUserList"));
        clearMessageOnClick(bot.sendMessage(new SendMessage()
                .setChatId(chatId)
                .enableHtml(true)
                .setText("Список пользователей")
                .setReplyMarkup(kb.generate())));
    }

    @Step(value = "addUser")
    public void addLogist() throws Exception {
        bot.sendMessage(new SendMessage()
                .setText("Отправьте контакты пользователя")
                .setChatId(chatId));
        step = "addUser2";
    }

    @Step(value = "addUser2")
    public void addUser2() throws Exception {
        String sure_name = "";
        if (update.getMessage() != null) {
            Contact ct = update.getMessage().getContact();

            if (ct.getUserID() == null) {
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Данный пользоваетль не зарегистрирован в Telegram");
                bot.sendMessage(message);
            } else {
                List<UsersEntity> user = usersDao.UserList(ct.getUserID());
                String text = "";
                if (user.size() != 0) {
                    text += "Пользователь уже добавлен ";
                } else {
                    text += "Пользователь добавлен ";
                    if (ct.getLastName() != null) {
                        sure_name = ct.getLastName();
                    }

                    String user_name = ct.getFirstName() + " " + sure_name;
                    UsersEntity userData = new UsersEntity();
                    userData.setUser_name(user_name);
                    userData.setAdmin(false);
                    userData.setChat_id(ct.getUserID());
                    userData.setId_access_level(2);
                    usersDao.insert(userData);
                }

                bot.sendMessage(new SendMessage()
                        .setText(text)
                        .setChatId(chatId));

                redirect("userList");
            }
        } else {
            bot.sendMessage(new SendMessage()
                    .setText("Введен не правильный формат данных")
                    .setChatId(chatId));
            redirect("userList");
        }
    }

    @Step(value = "deleteUserList")
    public void deleteUserList() throws Exception {
        IKeyboard kb = new IKeyboard();
        kb.next();
        for (UsersEntity rec : usersDao.getList()) {
            kb.add(rec.getUser_name(), Json.set("step", "deleteUser").set("id", rec.getChat_id()));
        }
        clearMessageOnClick(bot.sendMessage(new SendMessage()
                .setChatId(chatId)
                .enableHtml(true)
                .setText("Выберите пользователя для удаления")
                .setReplyMarkup(kb.generate())));
    }


    @Step(value = "deleteUser")
    public void deleteUser() throws Exception {

        usersDao.delete(queryData.getLong("id"));

        bot.sendMessage(new SendMessage()
                .setChatId(chatId)
                .enableHtml(true)
                .setText("Пользователь удален"));
        redirect("userList");

    }


}
