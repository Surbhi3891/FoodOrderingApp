package my.foodie.app;

public class OrderModel {
    String orderid;
    String name;
    String orderStatus;
   String customerPhoneNumber;
   String chefAddress;
   String chefID,customerid;

    public String getChefID() {
        return chefID;
    }

    public String getChefAddress() {
        return chefAddress;
    }

    public OrderModel() {
    }

    String address;

    public String getOrderStatus() {
        return orderStatus;
    }

    public OrderModel(String orderid, String name, String address, String zip, String chefname, String items, String price, String paymenttype, String deliverymode, String date, String time, String orderStatus, String customerPhoneNumber,String chefAddress,String chefID,String customerid) {

        this.orderid=orderid;
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
        this.orderStatus = orderStatus;
        this.customerPhoneNumber=customerPhoneNumber;
        this.chefAddress=chefAddress;
        this.chefID=chefID;
        this.customerid=customerid;
    }



    public String getOrderid() {
        return orderid;
    }

    public String getName() {
        return name;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getCustomerid() {
        return customerid;
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
