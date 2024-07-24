/**
 * This class uses 2 ways to terminate a thread
 * busy feeds --
 * <pre>
 * https://feeds.bbci.co.uk/news/world/rss.xml
 * https://feeds.washingtonpost.com/rss/world
 * </pre>
 */

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> feedUrls = new ArrayList<>();
        final String end = "done";

        String url = "";

        System.out.println("Enter RSS feed URLs (type 'done' to finish):");
        while (!url.equals(end)) {
            url = scanner.nextLine();
            if (!url.equalsIgnoreCase(end)) {
                feedUrls.add(url);
            }
        }
//From here to Point AAAA, is the code block for a thread be executed from 40 seconds
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
//
//        double start = (double) System.currentTimeMillis()/1000;
//        double endTime = start + 30;
//
//        scheduler.scheduleAtFixedRate(() -> {
//
//            double time = (double)System.currentTimeMillis()/1000;
//            for (String feedUrl : feedUrls) {
//                RSSFeedChecker checker = new RSSFeedChecker(feedUrl);
//                checker.checkFeed();
//                if(time >= endTime ){
//                    scheduler.shutdown();
//                    double now = (double) System.currentTimeMillis()/1000;
//                    System.out.printf("~~~~~Now thread terminates. execution time: %.0f seconds\n", (now - start));
//                }
//            }
//            System.out.println("~~~~~~~~~ wait for 5 seconds to start the next round if thread being executed ~~~~~~~~~");
//        }, 1, 5, TimeUnit.SECONDS);//wait for 5 seconds

//This is Point AAAA. The above code is a thread be terminated after 40 seconds

// What is the corePoolSize?
        //From this point to Point BBBB uses Interruption to terminate the thread
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);

        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"Running");

        for(int i=0;i<4;i++){
            for (String feedUrl : feedUrls) {
                RSSFeedChecker checker = new RSSFeedChecker(feedUrl);
                checker.checkFeed();
            }

            try {
                thread.sleep(2000);
                //Thread dormancy, delayed by one second
            } catch (InterruptedException e) {

                e.printStackTrace();
                System.out.println("Thread error");
            }
            System.out.printf("%n%n~~~~~~ Round %d of 4 Done ~~~~~~%n%n", i+1);
        }

        scheduled.shutdown();
        //This is Point BBBB. Above code used Interruption to terminate a thread.

    }

}