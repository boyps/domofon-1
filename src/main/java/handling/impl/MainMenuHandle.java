package handling.impl;

import database.UsersDao;
import handling.AbstractHandle;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import pro.nextbit.telegramconstructor.components.keyboard.Keyboard;
import pro.nextbit.telegramconstructor.stepmapping.Step;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class MainMenuHandle extends AbstractHandle {

    private UsersDao usersDao = daoFactory.usersDao();


    @Step(value = "mainMenu", commandText = "\uD83D\uDD18 Главное меню")
    public void mainMenu() throws Exception {
        redirect("A_start");
    }


    @Step(value = "A_start", commandText = "/start")
    public void A_start() throws Exception {

        if (usersDao.UserList(chatId).size() != 0) {
            Keyboard kb = new Keyboard();
            kb.next();
            kb.add("\uD83D\uDD11 Открыть дверь");

            bot.sendMessage(new SendMessage()
                    .setReplyMarkup(kb.generate())
                    .setText("Центральный дверь Акимата")
                    .setChatId(chatId)
            );

        } else {
            bot.sendMessage(new SendMessage()
                    .setText("К сожалению, у вас нет доступа к боту")
                    .setChatId(chatId)
            );
        }
    }


    @Step(value = "admin", commandText = "/admin")
    public void admin() throws Exception {

        if (usersDao.getAdmin(chatId, true).size() != 0) {
            Keyboard keyboard = new Keyboard();

            keyboard.next(2, 1);
            keyboard.addButton("\uD83D\uDCCD Админ");
            keyboard.addButton("\uD83D\uDD11 Доступ");
            keyboard.addButton("\uD83D\uDD18 Главное меню");

            bot.sendMessage(new SendMessage()
                    .setReplyMarkup(keyboard.generate())
                    .setText("Панел администратора")
                    .setChatId(chatId)
            );
        } else {
            bot.sendMessage(new SendMessage()
                    .setText("У вас нет доступа к панели администратора")
                    .setChatId(chatId)
            );
        }
    }


    @Step(value = "openMainDoor", commandText = "\uD83D\uDD11 Открыть дверь")
    public void openMainDoor() throws Exception {

        if (usersDao.UserList(chatId).size() != 0) {

            URL url = new URL("http://10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("GET"); // PUT is another valid option
            http.setDoOutput(true);
            Map<String, String> arguments = new HashMap<>();
            arguments.put("admin","admin");
            // arguments.put("password", "sjh76HSn!"); // This is a fake password obviously
            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try (OutputStream os = http.getOutputStream()) {
                os.write(out);
            }catch (Exception e){

            }



            bot.sendMessage(new SendMessage()
                    .setText("Успешно!")
                    .setChatId(chatId)
            );
        } else {
            bot.sendMessage(new SendMessage()
                    .setText("У вас нет доступа ")
                    .setChatId(chatId)
            );
        }

    }
}
