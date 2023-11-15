import java.util.UUID;

public class PqBookProcessing {

    public synchronized boolean executeTransaction(Order buyOrder, Order sellOrder) {
        int minQuantity = (int)Math.min(buyOrder.quantity, sellOrder.quantity);
        buyOrder.quantity = buyOrder.quantity - minQuantity;
        sellOrder.quantity = sellOrder.quantity - minQuantity;
        
        boolean transactionStatus = completeTransaction(buyOrder, sellOrder, minQuantity);
        shouldAssignNewOrderId(buyOrder, sellOrder);
        return transactionStatus;
    }

    public boolean completeTransaction(Order buyOrder, Order sellOrder, int minQuantity) {
        System.out.println("Trade sucessfull: buy ->"+buyOrder.toString(minQuantity)+", sell ->"+sellOrder.toString(minQuantity));
        return true;
    }

    //will assign new order Id to leftover order if present
    public void shouldAssignNewOrderId(Order buyOrder, Order sellOrder) {
        if(buyOrder.quantity != 0) {
            assignNewOrderId(buyOrder);
        }
        if(sellOrder.quantity != 0) {
            assignNewOrderId(sellOrder);
        }
    }

    public boolean assignNewOrderId(Order order) {
        order.orderId = UUID.randomUUID().toString();
        return true;
    }
}
