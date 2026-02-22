package String;

import java.util.ArrayList;
import java.util.List;

public class BinaryWatch {
    

    public List<String> readBinaryWatch(int turnedOn) {

        final int MAX_LED_AT_ONCE = 8;

        if (turnedOn > MAX_LED_AT_ONCE) {
            return new ArrayList<>();
        }

        int hour = 0;
        List<String> result = new ArrayList<>();

        while (hour < 12) {
            int hourBit = countSetBits(hour);

            for (int minute = 0; minute < 60; minute++) {
                int minuteBits = countSetBits(minute);

                if (hourBit + minuteBits == turnedOn) {
                    result.add(convertTimeToString(hour, minute));
                }
            }

            hour++;
        }

        return result;

    }
    
    private String convertTimeToString(int hour, int minute) {
        StringBuilder ans = new StringBuilder();
        
        ans.append(hour);
        ans.append(':');

        if (minute < 10) {
            ans.append(0);
        }

        ans.append(minute);

        return ans.toString();

    }

    private int countSetBits(int num) {
        int ans = 0;

        while (num > 0) {
            ans++;
            num = num & (num - 1);
        }

        return ans;
    }


}
