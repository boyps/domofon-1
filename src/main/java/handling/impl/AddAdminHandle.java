package handling.impl;

import database.UsersDao;
import database.entity.UsersEntity;
import handling.AbstractHandle;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import pro.nextbit.telegramconstructor.Json;
import pro.nextbit.telegramconstructor.components.keyboard.IKeyboard;
import pro.nextbit.telegramconstructor.stepmapping.Step;

import java.util.List;

public class AddAdminHandle extends AbstractHandle {
    UsersDao usersDao = daoFactory.usersDao();

    @Step(value = "admins", commandText = "\uD83D\uDCCD Админ")
    public void admins() throws Exception {

        IKeyboard kb = new IKeyboard();
        kb.next();
        for (UsersEntity rec : usersDao.adminList(true)) {
            kb.add(rec.getUser_name(), Json.set("step", "admins").set("id", rec.getChat_id()));
        }
        kb.add("➕ Добавить", Json.set("step", "addAdmin"));
        kb.add("❌ Удалить", Json.set("step", "deleteAdmin"));
        clearMessageOnClick(bot.sendMessage(new SendMessage()
                .setChatId(chatId)
                .enableHtml(true)
                .setText("Список администраторов")
                .setReplyMarkup(kb.generate())));
    }

    @Step(value = "addAdmin")
    public void addAdmin() throws Exception {
        bot.sendMessage(new SendMessage()
                .setText("Отправьте контакты пользователя")
                .setChatId(chatId));

        step = "addAdmin2";
    }

    @Step(value = "addAdmin2")
    public void addAdmin2() throws Exception {
        String sure_name = "";
        if (update.getMessage() != null) {
            Contact ct = update.getMessage().getContact();

            if (ct.getUserID() == null) {
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Данный пользоваетль не зарегистрирован в Telegram");
                bot.sendMessage(message);
            } else {
                List<UsersEntity> user = usersDao.getAdmin(ct.getUserID(), true);
                String text = "";
                if (user.size() != 0) {
                    text += "Админ уже добавлен ";
                } else {
                    List<UsersEntity> checkUser = usersDao.UserList(ct.getUserID());
                    if (ct.getLastName() != null) {
                        sure_name = ct.getLastName();
                    }
                    String user_name = ct.getFirstName() + " " + sure_name;
                    if (checkUser.size() == 0) {
                        UsersEntity userData = new UsersEntity();
                        userData.setUser_name(user_name);
                        userData.setAdmin(true);
                        userData.setChat_id(ct.getUserID());
                        userData.setId_access_level(2);
                        usersDao.insert(userData);
                        usersDao.updateAdmin(ct.getUserID(), true);

                    } else {
                        usersDao.updateAdmin(ct.getUserID(), true);
                    }
                    text += "Админ добавлен ";
                }

                bot.sendMessage(new SendMessage()
                        .setText(text)
                        .setChatId(chatId));

                redirect("admins");

            }
        } else {
            bot.sendMessage(new SendMessage()
                    .setText("Введен не правильный формат данных")
                    .setChatId(chatId));
            redirect("addAdmin");
        }
    }

    @Step(value = "deleteAdmin")
    public void deleteAdmin() throws Exception {
        IKeyboard kb = new IKeyboard();
        kb.next();
        for (UsersEntity rec : usersDao.adminList(true)) {
            kb.add(rec.getUser_name() + " " + rec.getUser_surname(), Json.set("step", "deleteAdmin2").set("id", rec.getChat_id()));

        }
        clearMessageOnClick(bot.sendMessage(new SendMessage()
                .setChatId(chatId)
                .enableHtml(true)
                .setText("Выберите пользователя для удаления")
                .setReplyMarkup(kb.generate())));
    }


    @Step(value = "deleteAdmin2")
    public void deleteAdmin2() throws Exception {
        usersDao.updateAdmin(queryData.getLong("id"), false);
        bot.sendMessage(new SendMessage()
                .setChatId(chatId)
                .enableHtml(true)
                .setText("Админ удален из списка "));
        redirect("admins");

    }
}
