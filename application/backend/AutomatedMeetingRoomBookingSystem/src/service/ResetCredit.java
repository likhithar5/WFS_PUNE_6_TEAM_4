package service;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ResetCredit {

    private int count = 0;

    public void UpdateCredit() {
    	ResetCredit counter = new ResetCredit();

        // Schedule a task to run every day
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter.resetCreditOnMonday();
            }
        }, getFirstMonday(), 7 * 24 * 60 * 60 * 1000); // 7 days interval

        // This will keep the program running until manually stopped
    }

    private static Date getFirstMonday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // If today is Monday, return the current time; otherwise, return the next Monday
        if (calendar.getTime().before(new Date())) {
            calendar.add(Calendar.DATE, 7);
        }

        return calendar.getTime();
    }

    private void resetCreditOnMonday() {
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            
        	
        }
    }
}
