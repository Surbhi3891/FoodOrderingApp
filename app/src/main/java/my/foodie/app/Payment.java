package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButton;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

public class Payment extends AppCompatActivity {

    private static final String YOUR_CLIENT_ID = "AYgS6ei5dM1QpuQTVrz0KucM68ITG_RwLcbPGUsp7FB1WM4q1EzqVF3Mj-jNDDuPnJpTOXXo9PGsDc-2";

    PaymentButton payPalButton;
    Button cashPayment;
    String chefname, itemsAndquantity="";
    int subtotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        payPalButton = findViewById(R.id.payPalButton);
        String amount = String.valueOf(Cust_Cart.TotalBill);
        cashPayment= findViewById(R.id.cashpay);
        FirebaseDatabase.getInstance().getReference("Cart List").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Count = (int) snapshot.getChildrenCount();
                for(DataSnapshot ds :snapshot.getChildren()) {

                    chefname = ds.child("chefName").getValue().toString();
                    itemsAndquantity= itemsAndquantity +ds.child("foodName").getValue().toString() +","+ ds.child("quantity").getValue().toString()+"; ";
                    //subtotal = subtotal + (Integer.valueOf(ds.child("price").getValue().toString())* Integer.valueOf(ds.child("quantity").getValue().toString()));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cashPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-------------------order name--------"+ ConfirmDetails.cnfName);
                placeOrder("Pay by Cash");
            }
        });

        CheckoutConfig config = new CheckoutConfig(
                getApplication(),
                YOUR_CLIENT_ID,
                Environment.SANDBOX,
                String.format("%s://paypalpay", "my.foodie.app"),
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                new SettingsConfig(
                        true,
                        false

                )
        );
        PayPalCheckout.setConfig(config);

        payPalButton.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {
                        ArrayList purchaseUnits = new ArrayList<>();
                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.USD)
                                                        .value(amount)
                                                        .build()
                                        )
                                        .build()
                        );
                        Order order = new Order(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                    }
                },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
                                startActivity(new Intent(Payment.this,ConfirmDetails.class));
                            }
                        });
                    }
                }
        );
    }

    private void placeOrder(String paymentMode) {

        String orderid = "Order" + String.valueOf(100001+ (int)(Math.random()*899999));
        //final String[] chefname = new String[1];
        String saveCurrentDate, saveCurrTime;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        Calendar caldate = Calendar.getInstance();

        SimpleDateFormat currDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currDate.format(caldate.getTime());
        SimpleDateFormat currTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrTime = currTime.format(caldate.getTime());

//        FirebaseDatabase.getInstance().getReference("Cart List").child(id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                //Count = (int) snapshot.getChildrenCount();
//                for(DataSnapshot ds :snapshot.getChildren()) {
//
//                    chefname = ds.child("chefName").getValue().toString();
//                    itemsAndquantity= itemsAndquantity +ds.child("foodName").getValue().toString() +","+ ds.child("quantity").getValue().toString()+"; ";
//                    subtotal = subtotal + (((Integer) ds.child("price").getValue())* (Integer)ds.child("quantity").getValue());
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        OrderModel orderdetails = new OrderModel(ConfirmDetails.cnfName, ConfirmDetails.cnfAddress, ConfirmDetails.cnfZip, chefname, itemsAndquantity, String.valueOf(Cust_Cart.TotalBill), paymentMode, "Pickup", saveCurrentDate, saveCurrTime,"Confirmed");
        FirebaseDatabase.getInstance().getReference("Orders").child(id).child(orderid).setValue(orderdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Payment.this, "Youe order is placed successfully", Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference("Cart List").child(id).removeValue();

                startActivity(new Intent( Payment.this,CustomerView.class));
                finish();

            }
        });



    }

}