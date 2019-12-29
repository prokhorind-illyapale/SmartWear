package ua.javaee.springreact.web.rabbit;

import java.util.HashSet;
import java.util.Iterator;

public class AlertUtils {

    private static HashSet<Long> userDevices = new HashSet<>();

    public static void add(long id) {
        userDevices.add(id);
    }

    public static Long get(long id) {
        Iterator<Long> it = userDevices.iterator();
        while (it.hasNext()) {
            Long value = it.next();
            if (value.longValue() == id) {
                return value;
            }
        }
        return null;
    }

    public static void remove(long id) {
        Iterator<Long> it = userDevices.iterator();
        while (it.hasNext()) {
            Long value = it.next();
            if (value.longValue() == id) {
                userDevices.remove(id);
                return;
            }
        }
    }
}
