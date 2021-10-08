package my.foodie.app;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ReusableCodeForAll {

    public static void ShowAlert(Context context,String title, String msg){


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d , int i) {
                d.dismiss();


            }
        }).setTitle(title).setMessage(msg).show();
    }
}
