package helpers;

import java.time.Instant;

public class DateUtil {

    public static boolean isTokenExpired(String expiresAt) {

        Instant expiryTime = Instant.parse(expiresAt);
        Instant nowDate = Instant.now();

        return expiryTime.isAfter(nowDate);
    }

}
