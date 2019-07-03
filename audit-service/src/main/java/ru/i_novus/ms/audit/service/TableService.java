package ru.i_novus.ms.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@EnableScheduling
public class TableService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final SimpleDateFormat TABLE_NAME_FORMAT = new SimpleDateFormat("yyyy_MM");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    //    language=SQL
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s PARTITION OF audit.audit FOR VALUES FROM ('%s') TO ('%s')";

    @Scheduled(cron = "0 0 0 20 1/1 *")
    private void createTableForNextMonth(){
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        createByCalendar(cal);
    }

    @PostConstruct
    private void createTableIfNotExists(){
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        for (int i =1; i<=12; i++){
           createByCalendar(cal);
        }
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
    }

    private void createTable(String tableName, String dateFrom, String dateTo){
        jdbcTemplate.execute(String.format(CREATE_TABLE,tableName, dateFrom, dateTo));
    }

    private void createByCalendar(Calendar cal){
        String tableName = "audit.audit_"+ TABLE_NAME_FORMAT.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String from = DATE_FORMAT.format(cal.getTime());
        cal.add(Calendar.MONTH, 1);
        String to = DATE_FORMAT.format(cal.getTime());
        createTable(tableName, from, to);

    }

}
