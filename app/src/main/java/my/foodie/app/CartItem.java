package my.foodie.app;

public class CartItem {

    public String foodName, price, chefName,quantity, date,time,itemID,chefID,chefPhoneNumber,chefAddress,foodCal;

    public CartItem(String foodName, String price, String chefName, String quantity, String date, String time, String itemID,String chefID,String chefPhoneNumber,String chefAddress,String foodCal) {
        this.foodName = foodName;
        this.price = price;
        this.chefName = chefName;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.itemID = itemID;
        this.chefID=chefID;
        this.chefPhoneNumber=chefPhoneNumber;
        this.chefAddress=chefAddress;
        this.foodCal = foodCal;
    }
}
