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

    public static String generateNumberOperarion() {
        String result;
        Random rand = new Random();
        int numberRand = rand.nextInt(100000000);
        result = String.valueOf(numberRand);
        return result;
    }

    public static String generateBankAccount() {
        Random rand = new Random();
        String value;
        String result;
        int numberRand = rand.nextInt(100000000);
        value = "0011-0426-";
        result = value.concat(String.valueOf(numberRand));
        return result;
    }
}
