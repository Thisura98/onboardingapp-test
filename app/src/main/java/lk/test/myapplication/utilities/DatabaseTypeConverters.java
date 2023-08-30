package lk.test.myapplication.utilities;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseTypeConverters {
    static DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @TypeConverter
    public static Date fromTimestamp(String value) {
        try {
            return value == null ? null : df.parse(value);
        }
        catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String dateToString(Date value){
        return value == null ? null : df.format(value);
    }
}
