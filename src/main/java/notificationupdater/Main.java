package notificationupdater;



import notificationupdater.checks.AnimeDBChecker;
import notificationupdater.checks.Check;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static List<String> files = new ArrayList<>(Collections.singletonList("animedblist.txt"));

    public static List<Check> checkers = new ArrayList<>(Collections.singletonList(
            new AnimeDBChecker(new HttpGetter("https://anidb.net/latest"))
    ));

    public static ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

    public static void main(String[] args) throws Exception {
        ListLoader.load(files);
        service.scheduleAtFixedRate(() -> checkers.forEach(Check::check), 100L, 5000L, TimeUnit.MILLISECONDS);
    }


}
