package my.foodie.app;

public class recview_model {
    String ChefName,foodCal,foodDesc,foodIngredients,foodItem,foodPrice,image,userid;

    public recview_model() {
    }

    public recview_model(String chefName, String foodCal, String foodDesc, String foodIngredients, String foodItem, String foodPrice, String image, String userid) {
        ChefName = chefName;
        this.foodCal = foodCal;
        this.foodDesc = foodDesc;
        this.foodIngredients = foodIngredients;
        this.foodItem = foodItem;
        this.foodPrice = foodPrice;
        this.image = image;
        this.userid = userid;
    }

    public String getChefName() {
        return ChefName;
    }

    public void setChefName(String chefName) {
        ChefName = chefName;
    }

    public String getFoodCal() {
        return foodCal;
    }

    public void setFoodCal(String foodCal) {
        this.foodCal = foodCal;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodIngredients() {
        return foodIngredients;
    }

    public void setFoodIngredients(String foodIngredients) {
        this.foodIngredients = foodIngredients;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
