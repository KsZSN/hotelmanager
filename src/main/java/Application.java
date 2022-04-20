import pojo.OrdertInfo;
import pojo.Room;
import threadpool.BookThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Application {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //测试预定房间功能
        //模拟100位顾客同时预定
//        testBook();

        //测试某位顾客预定信息
//        testGetOrderInfo2Guest();

        //测试指定日期可用房间
        //testGetAvailableRooms();
    }

    /**
     *
     * 这里模拟100位顾客同时预定
     */
    public static void testBook()  throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);

        BookManger bookManger = new BookManger();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Future future = bookManger.book("tom", "2020-04-20");
                        Boolean result = (Boolean) future.get();
                        if (result.equals(true)) {
                            countDownLatch.countDown();
                        }
                    } catch (Exception e) {

                    }
                }
            },"Thread-" + i ).run();

        }

        countDownLatch.await();

        BookThreadPool.shutdown();
    }

    /**
     * 测试指定日期可用房间
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void testGetAvailableRooms() throws InterruptedException, ExecutionException {
        List<Future> list = new ArrayList<>();

        BookManger bookManger = new BookManger();
        list.add(bookManger.book("tom", "2020-04-20"));
        list.add(bookManger.book("tom", "2020-04-20"));
        list.add(bookManger.book("nancy", "2020-04-21"));
        list.add(bookManger.book("pdd", "2020-04-22"));
        list.add(bookManger.book("jerry", "2020-04-23"));

        for (Future future : list) {
            future.get();
        }

        List<Room> availableRooms = bookManger.getAvailableRooms("2020-04-23");
        System.out.println(availableRooms);
        BookThreadPool.shutdown();
    }

    /**
     * 测试某位顾客预定信息
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void testGetOrderInfo2Guest() throws InterruptedException, ExecutionException {
        List<Future> list = new ArrayList<>();

        BookManger bookManger = new BookManger();
        list.add(bookManger.book("tom", "2020-04-20"));
        list.add(bookManger.book("tom", "2020-04-20"));
        list.add(bookManger.book("nancy", "2020-04-21"));
        list.add(bookManger.book("pdd", "2020-04-22"));
        list.add(bookManger.book("jerry", "2020-04-23"));

        for (Future future : list) {
            future.get();
        }

        List<OrdertInfo> guestOrderInfo = bookManger.getOrderInfo2Guest("tom");
        System.out.println(guestOrderInfo);
        BookThreadPool.shutdown();
    }

}
