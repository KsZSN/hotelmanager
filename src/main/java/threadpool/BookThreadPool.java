package threadpool;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BookThreadPool{

    private static ThreadPoolExecutor bookThreadPool = new ThreadPoolExecutor(2,
                                                6,
                                                    10,
                                                    TimeUnit.SECONDS,
                                                    new LinkedBlockingQueue<Runnable>());


    public static void execute(Runnable runnable){
        bookThreadPool.execute(runnable);
    }

    public static Future submit(Runnable runnable){
      return bookThreadPool.submit(runnable);
    }

    public static void shutdown() {
        bookThreadPool.shutdown();
    }
}
