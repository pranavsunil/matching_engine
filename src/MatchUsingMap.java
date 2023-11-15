import java.util.*;

class MatchUsingMap implements MatchHandler{

    //for production code will prefer storing this info in database instead of hash maps
    HashMap<Integer, OrderBooks> matchOrder;

    MatchUsingMap() {
        matchOrder = new HashMap<>();
    }

    public void addOrder(Order order) {
        if(!matchOrder.containsKey(order.id)) {
            matchOrder.put(order.id, new PqBook());
        }
        matchOrder.get(order.id).addOrder(order);
    }

    public void execute(int orderId) {
        matchOrder.get(orderId).matchOrder();
    }
}