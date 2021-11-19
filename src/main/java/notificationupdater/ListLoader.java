package notificationupdater;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class ListLoader {

    public static HashMap<String, Integer> listOfUpdatableNotifications;

    public static void load(List<String> lists) {
        listOfUpdatableNotifications = new HashMap<>();
        List<String> allLines = lists.stream().map(fileName -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ListLoader.class.getClassLoader().getResourceAsStream(fileName))));
            return reader.lines().collect(Collectors.toList());
        }).flatMap(Collection::stream).collect(Collectors.toList());
        allLines.forEach(line -> {
            String[] info = line.split(";");
            if (info.length > 1) listOfUpdatableNotifications.put(info[0], Integer.valueOf(info[1]));
            else listOfUpdatableNotifications.put(info[0], 0);
        });
    }

    public static Set<String> getNames() {
        return listOfUpdatableNotifications.keySet();
    }

}
