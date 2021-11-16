package my.foodie.app;

public class OrderModel {
    String orderid;
    String name;
    String OrderStatus;

    public OrderModel() {
    }

    String address;

    public OrderModel( String name, String address, String zip, String chefname, String items, String price, String paymenttype, String deliverymode, String date, String time,String OrderStatus) {

        this.name = name;
        this.address = address;
        this.zip = zip;
        this.chefname = chefname;
        this.items = items;
        this.price = price;
        this.paymenttype = paymenttype;
        this.deliverymode = deliverymode;
        this.date = date;
        this.time = time;
        this.OrderStatus = OrderStatus;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getChefname() {
        return chefname;
    }

    public String getItems() {
        return items;
    }

    public String getPrice() {
        return price;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public String getDeliverymode() {
        return deliverymode;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    String zip;
    String chefname;
    String items;
    String price;
    String paymenttype;
    String deliverymode;
    String date;
    String time;
}
