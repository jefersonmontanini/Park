package org.example.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkUtils {

    public static String generateReceipt() {
        LocalDateTime dateTime = LocalDateTime.now();
        String receipt = dateTime.toString().substring(0,19);
        return receipt.replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }
}
