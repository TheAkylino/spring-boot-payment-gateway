package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Utils {

    public static String getTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdfDate.format(now);
    }

    public static String getPaymentId() {
        String paymentId = null;
        try {
            UUID uuid = UUID.randomUUID();
            MD5Hash md5Hash = new MD5Hash();
            paymentId = md5Hash.doHash(uuid.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentId;
    }

    public static Long generateRandom() {
        Long leftLimit = 1l;
        Long rightLimit = 10l;
        return leftLimit + new Random().nextLong() * (rightLimit - leftLimit);
    }
}
