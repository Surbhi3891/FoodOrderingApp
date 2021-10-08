package my.foodie.app;

public class User {

    public String fname,lname,email,pwd, cpwd,phone,address,city,state,zip, type;

    public User(){

    }
    public User (String fname,String lname,String email,String pwd, String cpwd,String phone,String address,String city,String state,String zip, String type){
        this.fname = fname;
        this.lname=lname;
        this.email=email;
        this.pwd=pwd;
        this.cpwd=cpwd;
        this.phone=phone;
        this.address=address;
        this.city=city;
        this.state=state;
        this.zip=zip;
        this.type=type;


    }

}
