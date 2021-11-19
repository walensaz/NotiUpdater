package notificationupdater.checks;

import notificationupdater.HttpGetter;
import notificationupdater.ListLoader;
import notificationupdater.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AnimeDBChecker extends HttpCheck {

    public AnimeDBChecker(HttpGetter httpGet) {
        super(httpGet);
    }

    @Override
    public void check() {
        try {
            HashMap<String, List<Integer>> newAnimeEpisodes = new HashMap<>();
            AtomicReference<String> currentAnime = new AtomicReference<>("");
            getHttpGet().getContent().forEach(line -> {
                String aLine = line.trim();
                if (aLine.contains("name anime")) {
                    int endOfName = aLine.indexOf("</a>");
                    String name = aLine.substring(0, endOfName);
                    name = name.split("title=")[1].split(">")[1];
                    currentAnime.set(name);
                } else if (aLine.contains("Regular Episode")) {
                    int episodeNumber = Integer.parseInt(aLine.split(">")[1]);
                    List<Integer> episodes = newAnimeEpisodes.getOrDefault(currentAnime.get(), new ArrayList<>());
                    episodes.add(episodeNumber);
                    newAnimeEpisodes.put(currentAnime.get(), episodes);
                }
            });
            newAnimeEpisodes.entrySet().stream().forEach(entry -> {
                if (ListLoader.getNames().contains(entry.getKey())) {
                    int highestEpisodeNumber = entry.getValue().stream().max(Integer::compareTo).get();
                    if (ListLoader.listOfUpdatableNotifications.get(entry.getKey()) < highestEpisodeNumber) {
                        Notification.displayWithOnClick(
                                entry.getKey(), "New Episode Number: " + highestEpisodeNumber,
                                () -> {
                                    System.out.println("Clicked");
                                });
                    }
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
