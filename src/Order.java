import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Order {
    int id;
    String orderId;
    OrderType orderType;
    int quantity;
    double price;
    ZonedDateTime dateTime;

    public Order(int id,  String orderId,OrderType orderType, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
        this.dateTime = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public String toString(int quantity) {
        String toString = " orderType = "+this.orderType+" quantity = "+quantity+" price = "+this.price;
        return toString;
    }
}

enum OrderType {
    BUY, SELL
}
