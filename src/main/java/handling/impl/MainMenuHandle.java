package handling.impl;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import database.UsersDao;
import handling.AbstractHandle;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.server.Uri;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import pro.nextbit.telegramconstructor.Json;
import pro.nextbit.telegramconstructor.components.keyboard.IKeyboard;
import pro.nextbit.telegramconstructor.components.keyboard.Keyboard;
import pro.nextbit.telegramconstructor.stepmapping.Step;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

        try {

            if (usersDao.UserList(chatId).size() != 0) {

               /* URL url = new URL("http://10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote");
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("GET"); // PUT is another valid option
                http.setDoOutput(true);
                Map<String, String> arguments = new HashMap<>();
                arguments.put("login", "admin");
                arguments.put("password", "admin"); // This is a fake password obviously
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
                    System.out.println(" -------------------------------------------------"  );

                } catch (Exception e) {
                    bot.sendMessage(new SendMessage()
                            .setText("Ошибка соединение")
                            .setChatId(chatId)
                    );
                }*/

                try {

                   String url = "http://10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote";

                    URL obj = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

                    connection.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    System.out.println(response.toString());
                    System.out.println("=============================================================================================");



                    /*URL url = new URL("http://admin@admin:10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote -d '{\"admin\":[\"admin\"]}'");

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                            url.openStream(), "UTF-8"))) {
                        for (String line; (line = reader.readLine()) != null;) {
                            System.out.println(line);
                        }
                    }*/





/*
                        String stringUrl = "http://10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote";
                        URL url = new URL(stringUrl);
                        URLConnection uc = url.openConnection();

                        uc.setRequestProperty("X-Requested-With", "Curl");

                        String userpass = "admin" + ":" + "admin";
                        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
                        uc.setRequestProperty("Authorization", basicAuth);

                        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());*/
                        // read this input





                    bot.sendMessage(new SendMessage()
                            .setText("Успешно!")
                            .setChatId(chatId)
                    );
                }catch (Exception e){
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
        } catch (Exception e) {

            bot.sendMessage(new SendMessage()
                    .setText("Ошибка соединение")
                    .setChatId(chatId)
            );
        }

    }


    @Step(value = "test", commandText = "/test")
    public void test() throws Exception {





    }


}
