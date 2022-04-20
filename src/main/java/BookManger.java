import pojo.OrdertInfo;
import pojo.Room;
import threadpool.BookThreadPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 房间预定、查询处理
 * 这里假定有100间房间，且房间号为 1-100
 */

public class BookManger {

    private static ConcurrentHashMap<String,OrdertInfo> map = new ConcurrentHashMap(100);

    private static CopyOnWriteArrayList<Room> roomList = new CopyOnWriteArrayList<Room>();

    private volatile Integer availableRoomCount  = 100;

    static {
        for (int i = 1; i <= 100; i++) {
            Room room = new Room();
            room.setRoomId(i);
            room.setIsOrder(false);
            roomList.add(room);
        }
    }


    /**
     * 房间预定方法
     * @param guestName 客户名
     * @param checkInDate 入住时间
     */
    public Future book(final String guestName, final String checkInDate) {
        FutureTask futureTask = new FutureTask<>(new Callable<Boolean>() {
            boolean result = false;

            @Override
            public Boolean call() throws Exception {
                if (availableRoomCount < 1) {
                    return result;
                }

                Room room = new Room();
                room.setIsOrder(true);
                room.setCheckInDate(checkInDate);

                synchronized (BookManger.class) {
                    //设置预定信息
                    room.setRoomId(availableRoomCount);
                    room.setIsOrder(true);
                    OrdertInfo ordertInfo = new OrdertInfo(guestName, new Date(), room);
                    map.put(UUID.randomUUID().toString(), ordertInfo);

                    //设置库中房间状态
                    for (Room r : roomList) {
                        if (r.getRoomId() == availableRoomCount) {
                            r.setIsOrder(true);
                            r.setCheckInDate(checkInDate);
                        }
                    }

                    System.out.println("guest: " + guestName + " booked " + availableRoomCount + " room," + "check in time is " + checkInDate);
                    availableRoomCount--;
                    result = true;
                }

                return result;
            }
        });

        BookThreadPool.execute(futureTask);
        return futureTask;
    }

    /**
     * 房间预定查询
     * @param dateStr  日期
     * @return 该日期预定的房间列表
     */
    public List<Room> getAvailableRooms(String dateStr) {
        List<Room> collect = roomList.stream()
                .filter(i -> !dateStr.equals(i.getCheckInDateDate()))
                .collect(Collectors.toList());

//        for (Room room : roomList) {
//            if (room.getIsOrder() == false )
//        }
        return collect;
    }


    /**
     * 某位顾客预定的房间列表
     * @param guestName
     * @return
     */
    public List<OrdertInfo> getOrderInfo2Guest(String guestName) {
        ArrayList<OrdertInfo> ordertInfos = new ArrayList<>();
        for (OrdertInfo value : map.values()) {
            if (value.getGuestName().equals(guestName)) {
                ordertInfos.add(value);
            }
        }

        return ordertInfos;
    }


}
