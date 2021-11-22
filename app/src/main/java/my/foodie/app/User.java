package my.foodie.app;

public class User {

    //public String fname,lname,email,pwd, cpwd,phone,address,city,state,zip, type;
    public String fname,lname,email,phone,address,city,state,zip;

    public User(){

    }
    public User (String fname,String lname,String email,String phone,String address,String city,String state,String zip){
        this.fname = fname;
        this.lname=lname;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.city=city;
        this.state=state;
        this.zip=zip;
        //this.type=type;


    }

}
