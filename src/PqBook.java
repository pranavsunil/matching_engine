import java.util.*;
import java.util.concurrent.TimeUnit;

public class PqBook implements OrderBooks{
    
    PriorityQueue<Order> buyOrders;
    PriorityQueue<Order> sellOrders;
    PqBookProcessing pqBookProcessing;
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    PqBook() {
        buyOrders = new PriorityQueue<>(new SortOrders().reversed());
        sellOrders = new PriorityQueue<>(new SortOrders());
        pqBookProcessing = new PqBookProcessing();
    }

    public synchronized boolean addOrder(Order order) {
        try {
            if(order.orderType == OrderType.BUY) {
                buyOrders.add(order);
            } else {
                sellOrders.add(order);
            }
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public synchronized boolean matchOrder() {
        int attempt = 1;

        while(attempt <= MAX_RETRIES) {
            try{
                boolean isSuccess = attemptMatchOrder();
                if(isSuccess) {
                    return true;
                }
            } catch (Exception e){
                System.out.println("Matching attempt number "+attempt+" failed: " + e.getMessage());
                attempt++;
                waitBeforeRetry();
            }
        }
        return false;
    }

    public synchronized boolean attemptMatchOrder() {
        try {
            Order buyOrder = null;
            Order sellOrder = null;
            while(isTransactionPossible(buyOrder, sellOrder)) {
                if(buyOrder==null || buyOrder.quantity == 0) {
                    buyOrder = buyOrders.peek();
                }
                if(sellOrder == null || sellOrder.quantity == 0) {
                    sellOrder = sellOrders.peek();
                }
                if(buyOrder.price >= sellOrder.price ) {
                    boolean isSucessfull = executeTransaction(buyOrder, sellOrder);
                    if(!isSucessfull) {
                        throw new RuntimeException("Incomplete transaction");
                    }
                } else {
                    break;
                }
            }
            analyzePendingOrder(buyOrder, sellOrder);
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isTransactionPossible(Order buyOrder, Order sellOrder) {
        if(!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            return true;
        } else if(buyOrder != null && buyOrder.quantity!=0 && !sellOrders.isEmpty()) {
            return true;
        } else if(sellOrder != null && sellOrder.quantity!=0 && !buyOrders.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean executeTransaction(Order buyOrder, Order sellOrder) {
        boolean sucessfullyExecuted = pqBookProcessing.executeTransaction(buyOrder, sellOrder);
        if(sucessfullyExecuted) {
            shouldPollOrder(buyOrder, sellOrder);
            return true;
        }
        return false;
    }

    public void shouldPollOrder(Order buyOrder, Order sellOrder) {
        if(!buyOrders.isEmpty() && buyOrder.orderId == buyOrders.peek().orderId) {
            buyOrders.poll();
        }
        if(!sellOrders.isEmpty() && sellOrder.orderId == sellOrders.peek().orderId) {
            sellOrders.poll();
        }
    }

    public void analyzePendingOrder(Order buyOrder, Order sellOrder) {
        if(buyOrder != null && buyOrder.quantity != 0) {
            addOrder(buyOrder);
        }
        if(sellOrder != null && sellOrder.quantity != 0) {
            addOrder(sellOrder);
        }
    }

    private void waitBeforeRetry() {
        try {
            TimeUnit.MILLISECONDS.sleep(RETRY_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

class SortOrders implements Comparator<Order> {

    public int compare(Order order1, Order order2) {
        if(order1.price > order2.price) {
            return 1;
        } else if(order1.price == order2.price) {
            if(order1.dateTime.isAfter(order2.dateTime)) {
                return 1;
            }
            return -1;
        }
        return -1;
    }

}


