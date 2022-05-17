package server.server;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OwnDateGetter {
    public static Date getDate(){
        Calendar calendar = new GregorianCalendar();
        return calendar.getTime();
    }
}
