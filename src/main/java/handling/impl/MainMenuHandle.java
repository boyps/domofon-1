package handling.impl;

import database.UsersDao;
import handling.AbstractHandle;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import pro.nextbit.telegramconstructor.Json;
import pro.nextbit.telegramconstructor.components.keyboard.IKeyboard;
import pro.nextbit.telegramconstructor.components.keyboard.Keyboard;
import pro.nextbit.telegramconstructor.stepmapping.Step;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Base64;

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
                    .setText("Главное меню")
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


    @Step(value = "openDoor", commandText = "\uD83D\uDD11 Открыть дверь")
    public void openDoor() throws Exception {

        IKeyboard kb = new IKeyboard();
        kb.next();
        kb.add("Центральный вход акимат", Json.set("step", "openMainDoor"));

        clearMessageOnClick(bot.sendMessage(new SendMessage()
                .setText("Выберите дверь, которую вы хотите открыть")
                .setReplyMarkup(kb.generate())
                .setChatId(chatId)
        ));


    }


    @Step(value = "openMainDoor")
    public void openMainDoor() throws Exception {

        if (usersDao.UserList(chatId).size() != 0) {

            try {

                String urlStr = "http://10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote";
                String encoding = Base64.getEncoder().encodeToString("admin:admin".getBytes());

                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setRequestProperty("Authorization", "Basic " + encoding);

                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader in = new BufferedReader(isr);

                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());
                System.out.println("=============================================================================================");

                bot.sendMessage(new SendMessage()
                        .setText("Успешно!")
                        .setChatId(chatId)
                );

            } catch (Exception e) {
                e.printStackTrace();
                bot.sendMessage(new SendMessage()
                        .setText("Ошибка соединение")
                        .setChatId(chatId)
                );
            }

        } else {
            bot.sendMessage(new SendMessage()
                    .setText("У вас нет доступа ")
                    .setChatId(chatId)
            );
        }
    }


    @Step(value = "test", commandText = "/test")
    public void test() throws Exception {


        try {

            String command = "curl -D - --digest -vu \"admin:admin\" 'http://10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote'";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader( new InputStreamReader(process.getInputStream()));

            String line;
            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null){
                response.append(line);
            }

            process.waitFor();
            System.out.println("exit: " + process.exitValue());
            process.destroy();

            System.out.println("============================================================");
            System.out.println(response);
            System.out.println("============================================================");

            bot.sendMessage(new SendMessage()
                    .setText("Успешно!")
                    .setChatId(chatId)
            );

        } catch (Exception e){
            e.printStackTrace();
            bot.sendMessage(new SendMessage()
                    .setText("Ошибка соединение")
                    .setChatId(chatId)
            );
        }
    }




}
