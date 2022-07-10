package com.academy.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Handler {

    public static void handler() {
        Logger logger = Logger.getLogger(Handler.class.getName());
        FileHandler fileHandler = null;
        SimpleDateFormat format = new SimpleDateFormat("M-d-HH");
        try {
            fileHandler = new FileHandler("files\\log\\logging." +
                    format.format(Calendar.getInstance().getTime()) + ".txt");
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fileHandler != null) {
            fileHandler.setFormatter(new SimpleFormatter() {
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:ss");
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTimeInMillis(record.getMillis());
                    return record.getLevel()
                            + logTime.format(calendar.getTime())
                            + " || "
                            + ": "
                            + record.getMessage() + "\n";
                }
            });
        }
    }
}
