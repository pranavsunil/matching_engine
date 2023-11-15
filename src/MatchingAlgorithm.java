//import java.util.Random;

class MatchingAlgorithm {

    static MatchHandler worker = new MatchUsingMap();
    public static void main(String args[]) {
        Order order1 = new Order(1, "24", OrderType.BUY, 3, 100);
        Order order2 = new Order(1, "25", OrderType.SELL, 2, 99);
        Order order3 = new Order(1, "26", OrderType.SELL, 1, 100);
        Order order4 = new Order(1, "27", OrderType.BUY, 2, 200);
        Order order6 = new Order(1, "29", OrderType.SELL, 2, 95);
        Order order7 = new Order(1, "27", OrderType.BUY, 1, 300);
        worker.addOrder(order1);
        worker.addOrder(order2);
        worker.addOrder(order3);
        worker.addOrder(order4);
        worker.execute(1);
        worker.addOrder(order6);
        worker.addOrder(order7);
        worker.execute(1);
        //uncomment to check with mutiple orderId
        /*Random random = new Random();
        for(int i=1;i<10000;++i) {
            int orderId = random.nextInt(11);
            if(i%2==0) {
            worker.addOrder(new Order(orderId, i+"", OrderType.BUY, 2, 10+i));
            }
            else{
            worker.addOrder(new Order(orderId, i+"", OrderType.SELL, 1, 9+i));
            }
        }
        worker.execute(2);*/
    }
}