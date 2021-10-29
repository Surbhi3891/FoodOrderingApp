package my.foodie.app;

public class FoodMenu {
    public String ChefName, foodItem,foodIngredients,foodDesc,foodCal,foodPrice,image,rand,userid;


    public FoodMenu(String ChefName, String foodItem, String foodIngredients, String foodDesc, String foodCal, String foodPrice, String image, String rand, String userid) {
        this.ChefName = ChefName;
        this.foodItem = foodItem;
        this.foodIngredients = foodIngredients;
        this.foodDesc = foodDesc;
        this.foodCal = foodCal;
        this.foodPrice = foodPrice;
        this.image = image;
        this.rand = rand;
        this.userid = userid;
    }
}
