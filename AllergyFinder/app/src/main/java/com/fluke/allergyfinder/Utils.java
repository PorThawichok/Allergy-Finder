package com.fluke.allergyfinder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Utils {

    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Toast.makeText(context, "Please connect the internet", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String checkCountry(Double lat, Double lng, Context context) throws IOException {
        String countryName = null;
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(lat, lng, 1);

        try {
            if (addresses.size() > 0) {
                countryName = addresses.get(0).getCountryName();
            }
            return countryName;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getAddress(Double lat, Double lng, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String add = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            //add = obj.getAddressLine(0);
//            add = add + "\n" + obj.getCountryName();
//            add = add + "\n" + obj.getCountryCode();
            add = add + " " + obj.getSubThoroughfare();
            add = add + " " + obj.getLocality();
            add = add + " " + obj.getSubAdminArea();
            add = add + " " + obj.getAdminArea();
            //add = add + " " + obj.getPostalCode();

            Log.v("zz", add.replace("null", ""));

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return add.replace("null", "").trim();
    }

    public static void showProgress(Context context) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("A moment...");
        pd.show();
    }

    public static void hideProgress(Context context) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.hide();
    }

    public static String dateThai(String strDate) {
        String Months[] = {
                "ม.ค.", "ก.พ.", "มี.ค.", "เม.ย.",
                "พ.ค.", "มิ.ย.", "ก.ค.", "ส.ค.",
                "ก.ย.", "ต.ค.", "พ.ย.", "ธ.ค."};

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        int year = 0, month = 0, day = 0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.format("%s %s %s", day, Months[month], year + 543);
    }

    public static String getMonth(String strDate) {
        String Months[] = {
                "ม.ค.", "ก.พ.", "มี.ค.", "เม.ย.",
                "พ.ค.", "มิ.ย.", "ก.ค.", "ส.ค.",
                "ก.ย.", "ต.ค.", "พ.ย.", "ธ.ค."};

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        int year = 0, month = 0, day = 0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.format("%s %s", Months[month], year + 543);
    }

    public static String getYear(String strDate) {
        String Months[] = {
                "Jan", "Feb", "March", "April",
                "May", "June", "July", "Aug",
                "Sep", "Oct", "Nov", "Dec"};

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        int year = 0, month = 0, day = 0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.format("%s", year + 543);
    }

//    public static void sendNotifiaction(String Sender_id, String Receive_id, final String Title, String Body, Context context) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("user")
//                .whereEqualTo("user_id", Receive_id)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                User user = document.toObject(User.class);
//                                String Token = user.getToken();
//                                Data data = new Data(Sender_id, R.drawable.logo, Body, Title);
//                                Sender sender = new Sender(data, Token);
//                                APIService apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
//                                apiService.sendNotification(sender)
//                                        .enqueue(new Callback<MyResponse>() {
//                                            @Override
//                                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                                                if (response.code() == 200) {
//                                                    if (response.body().success != 1) {
//                                                        Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onFailure(Call<MyResponse> call, Throwable t) {
//                                            }
//                                        });
//                            }
//                        } else {
//                            Toast.makeText(context, "เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                });
//    }

    public static void Logout(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Alert");
        dialog.setIcon(android.R.drawable.btn_star_big_on);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to logout?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                new AppPreferences(context).clearPrefs(); // clear session login

                Intent logout = new Intent(context, LoginActivity.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(logout);
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public static void noSession(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Alert");
        dialog.setIcon(android.R.drawable.btn_star_big_on);
        dialog.setCancelable(true);
        dialog.setMessage("Please login first");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                new AppPreferences(context).clearPrefs(); // clear session login

                Intent logout = new Intent(context, LoginActivity.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(logout);
            }
        });
        dialog.show();
    }

    public static void AddEmployee(String email, String password, String name, String tel, Context context) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("A moment...");
        pd.show();

//        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference mDatabase_user = mDatabase.getReference("user");
//        String User_id = mDatabase_user.push().getKey();
//
//        User data = new User(User_id, email, password, name, tel, "", "employee", "0", "0", "idle", "", 0);
//        mDatabase_user.child(User_id).setValue(data);
//
//        pd.dismiss();
//        Intent intent = new Intent(context, MainAdminActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("from", "user");
//        context.startActivity(intent);
    }

    public static void UpdateUser(String name, String password, String tel, String User_id, Context context) {
//        final ProgressDialog pd = new ProgressDialog(context);
//        pd.setMessage("กรุณารอสักครู่...");
//        pd.show();
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("name", name);
//        hashMap.put("password", password);
//        hashMap.put("tel", tel);
//
//        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference mDatabase_user = mDatabase.getReference("user");
//        mDatabase_user.child(User_id).updateChildren(hashMap);
//
//        pd.dismiss();
//        Intent intent = new Intent(context, MainAdminActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("from", "user");
//        context.startActivity(intent);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public static void UpdateToken(String User_id) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        String token = task.getResult();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("token", token);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("user").document(User_id).update(hashMap);
                    }
                });
    }

    public static void updateLocation(String Employee_id, HashMap<String, Object> hashMap) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").document(Employee_id).update(hashMap);
    }
}
