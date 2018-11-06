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
import java.net.*;
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
            kb.add("Центральный вход акимат");

            bot.sendMessage(new SendMessage()
                    .setReplyMarkup(kb.generate())
                    .setText("Выберите дверь для открытия")
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


    @Step(value = "openDoor", commandText = "Центральный вход акимат")
    public void openDoor() throws Exception {

        if (usersDao.UserList(chatId).size() != 0) {

            try {

                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("admin", "admin".toCharArray());
                    }
                });

                System.out.println("============================================================");
                String stringUrl = "";
                URL url = new URL(stringUrl);
                URLConnection uc = url.openConnection();
                uc.setRequestProperty("X-Requested-With", "Curl");

                InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
                BufferedReader input = new BufferedReader(inputStreamReader);
                String line = null;

                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println("============================================================");
                        System.out.println(line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                bot.sendMessage(new SendMessage()
                        .setText("Дверь успешно открыта!")
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





}
