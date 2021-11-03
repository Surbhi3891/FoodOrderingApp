package my.foodie.app;

public class CartItem {

    public String foodName, price, chefName,quantity, date,time,itemID;

    public CartItem(String foodName, String price, String chefName, String quantity, String date, String time, String itemID) {
        this.foodName = foodName;
        this.price = price;
        this.chefName = chefName;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.itemID = itemID;
    }
}
