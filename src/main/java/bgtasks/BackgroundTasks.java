package bgtasks;

import database.*;
import database.entity.BookingListEntity;
import database.entity.BorthDayEntity;
import database.entity.PartnersEntity;
import database.entity.UsersEntity;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.api.methods.send.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import pro.nextbit.telegramconstructor.database.DataRec;
import pro.nextbit.telegramconstructor.database.DataTable;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BackgroundTasks {

    private static TelegramLongPollingBot bot;
    private DaoFactory daoFactory = DaoFactory.getInstance();

    public void setBot(TelegramLongPollingBot inputBot) {
        bot = inputBot;
    }


    // описание cron (секунд минут час день месяц год)
    // если * тогда повторяется
   /* @Scheduled(cron = "0 0 12 * * ?")
    public void createData() {

        try {
            // Здесь должен быть код
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


}



