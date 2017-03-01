package time.hanwei.com;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/1/5.
 */
public class TimeUtils {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String str = format.format(new java.util.Date());
}
