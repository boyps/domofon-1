package handling.impl;

import database.UsersDao;
import handling.AbstractHandle;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import pro.nextbit.telegramconstructor.Json;
import pro.nextbit.telegramconstructor.components.keyboard.IKeyboard;
import pro.nextbit.telegramconstructor.components.keyboard.Keyboard;
import pro.nextbit.telegramconstructor.stepmapping.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;

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
            kb.add("\uD83D\uDD18 Открыть дверь");

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


    @Step(value = "openDoor", commandText = "\uD83D\uDD18 Открыть дверь")
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

                System.out.println("============================================================");
                String stringUrl = "http://10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote";
                URL url = new URL(stringUrl);
                URLConnection uc = url.openConnection();

                uc.setRequestProperty("X-Requested-With", "Curl");

                String userpass = "admin" + ":" + "admin";
                String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
                uc.setRequestProperty("Authorization", basicAuth);

                InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());


            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                System.out.println("============================================================");
                String stringUrl = "http://admin:admin@10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote";
                URL url = new URL(stringUrl);
                URLConnection uc = url.openConnection();
                uc.setRequestProperty("X-Requested-With", "Curl");
                InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());


            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                String command = "curl  --digest -u \"admin:admin\" 'http://10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote'";
                Process p = Runtime.getRuntime().exec(command);

                new Thread(new Runnable() {
                    public void run() {
                        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line = null;

                        try {
                            System.out.println("============================================================");
                            while ((line = input.readLine()) != null)
                                System.out.println(line);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                p.waitFor();

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

          /*  String url = "http://admin:admin@10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote";

            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();*/

            String command = "curl  --digest -u \"admin:admin\" 'http://10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote'";
            Process process = Runtime.getRuntime().exec(command);
            // System.out.println(response.toString());
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
    }

        @Step(value = "test2", commandText = "/test2")
        public void test2() throws Exception {


            try {

            String url = "http://admin:admin@10.205.1.82/cgi-bin/accessControl.cgi?action=openDoor&channel=1&UserID=101&Type=Remote";

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
        }


}
