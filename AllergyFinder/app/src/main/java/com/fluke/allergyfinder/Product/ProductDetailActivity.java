package com.fluke.allergyfinder.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.Admin.MainAdminActivity;
import com.fluke.allergyfinder.FullImage.FullImageActivity;
import com.fluke.allergyfinder.LoginActivity;
import com.fluke.allergyfinder.Model.Day;
import com.fluke.allergyfinder.Model.FavoriteModel;
import com.fluke.allergyfinder.Model.Month;
import com.fluke.allergyfinder.Model.Product;
import com.fluke.allergyfinder.Model.User;
import com.fluke.allergyfinder.Model.Year;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.User.MainUserActivity;
import com.fluke.allergyfinder.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView image_product, btn_bookmark;
    TextView txt_product_name, txt_barcode, txt_calories, txt_carbohydrate, txt_protein, txt_fat, txt_sodium, txt_sugar, txt_eat, txt_ingredients;
    LinearLayout layout_eating;
    String Barcode, FavoriteTest, User_id;
    List<String> ingredients;
    FirebaseFirestore db;
    boolean checkfavorite = false;
    int ProductCalories, ProductProtein, ProductCarbohydrate, ProductFat, ProductSugar, ProductSodium;
    List<String> daily_ingredients = new ArrayList<>();
    List<String> daily_allergy = new ArrayList<>();
    AppPreferences appPreferences;

    public static boolean checkType(String type){
        return type.equals("admin");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        db = FirebaseFirestore.getInstance();
        appPreferences = new AppPreferences(ProductDetailActivity.this);
        User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);
        String Type = appPreferences.getStringPrefs(AppPreferences.KEY_TYPE);

        Intent tt = getIntent();
        Barcode = tt.getStringExtra("barcode");
        FavoriteTest = tt.getStringExtra("favorite");

        //Toast.makeText(this, Favorite, Toast.LENGTH_SHORT).show();

        image_product = findViewById(R.id.image_product);
        txt_product_name = findViewById(R.id.txt_product_name);
        txt_barcode = findViewById(R.id.txt_barcode);
        txt_calories = findViewById(R.id.txt_calories);
        txt_carbohydrate = findViewById(R.id.txt_carbohydrate);
        txt_protein = findViewById(R.id.txt_protein);
        txt_fat = findViewById(R.id.txt_fat);
        txt_sodium = findViewById(R.id.txt_sodium);
        txt_sugar = findViewById(R.id.txt_sugar);
        txt_eat = findViewById(R.id.txt_eat);
        txt_ingredients = findViewById(R.id.txt_ingredients);
        btn_bookmark = findViewById(R.id.btn_bookmark);
        layout_eating = findViewById(R.id.layout_eating);

        if (checkType(Type)) {
            btn_bookmark.setVisibility(View.GONE);
            layout_eating.setVisibility(View.GONE);
        } else {
            btn_bookmark.setVisibility(View.VISIBLE);
            layout_eating.setVisibility(View.VISIBLE);
        }

        try {
            if (FavoriteTest.equals("false")) {
                checkfavorite = false;
                btn_bookmark.setImageDrawable(getDrawable(R.drawable.ic_love_gray));
            } else {
                checkfavorite = true;
                btn_bookmark.setImageDrawable(getDrawable(R.drawable.ic_love_red));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db = FirebaseFirestore.getInstance();
        db.collection("product")
                .whereEqualTo("barcode", Barcode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product data = document.toObject(Product.class);

                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                getSupportActionBar().setTitle(data.getName());

                                txt_product_name.setText(data.getName());
                                txt_barcode.setText(Barcode);

                                ProductCalories = data.getCalories();
                                ProductCarbohydrate = data.getCarbohydrate();
                                ProductProtein = data.getProtein();
                                ProductFat = data.getFat();
                                ProductSugar = data.getSugar();
                                ProductSodium = data.getSodium();

                                txt_calories.setText(String.format("%,d", ProductCalories));
                                txt_carbohydrate.setText(String.format("%,d", ProductCarbohydrate));
                                txt_protein.setText(String.format("%,d", ProductProtein));
                                txt_fat.setText(String.format("%,d", ProductFat));
                                txt_sodium.setText(String.format("%,d", ProductSodium));
                                txt_sugar.setText(String.format("%,d", ProductSugar));
                                txt_eat.setText(String.valueOf(data.getEat()));

                                ingredients = data.ingredients;
                                Glide.with(ProductDetailActivity.this).load(data.getPhoto()).into(image_product);

                                image_product.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent tt = new Intent(ProductDetailActivity.this, FullImageActivity.class);
                                        tt.putExtra("photo", data.getPhoto());
                                        startActivity(tt);
                                    }
                                });
                            }

                            if (ingredients.size() == 0) {
                                txt_ingredients.setText("No Data Found");
                            } else {
                                String data = "";
                                for (int i = 0; i < ingredients.size(); i++) {
                                    data = data + "," + ingredients.get(i);
                                }

                                try {
                                    if (data.trim().charAt(0) == ',') {
                                        txt_ingredients.setText(data.substring(1));
                                    } else {
                                        txt_ingredients.setText(data);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void Eat(View view) {
        daily_ingredients.clear();
        String Corn = appPreferences.getStringPrefs(AppPreferences.KEY_CORN);
        String Fluctose = appPreferences.getStringPrefs(AppPreferences.KEY_FLUCTOSE);
        String Gluten = appPreferences.getStringPrefs(AppPreferences.KEY_GLUTEN);
        String Lactose = appPreferences.getStringPrefs(AppPreferences.KEY_LACTOSE);
        String No_Sugar = appPreferences.getStringPrefs(AppPreferences.KEY_NO_SUGAR);
        String Nut = appPreferences.getStringPrefs(AppPreferences.KEY_NUT);
        String Shellfish = appPreferences.getStringPrefs(AppPreferences.KEY_SHELLFISH);
        String Vegan = appPreferences.getStringPrefs(AppPreferences.KEY_VEGAN);

        String[] arr_my_allergy = {Corn, Fluctose, Gluten, Lactose, No_Sugar, Nut, Shellfish, Vegan};

        int DailyCalories = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_CALORIES);
        int DailyProtein = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_PROTEIN);
        int DailyCarbohydrate = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_CARBOHYDRATE);
        int DailyFat = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_FAT);
        int DailySugar = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_SUGAR);
        int DailySodium = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_SODIUM);

        db = FirebaseFirestore.getInstance();
        db.collection("day")
                .whereEqualTo("day", Utils.dateThai(new SimpleDateFormat("dd-MM-yyyy").format(new Date())))
                .whereEqualTo("user_id", User_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Day data = document.toObject(Day.class);

                                    if (data.getCalories() > DailyCalories) {
                                        daily_ingredients.add("calories");
                                    }

                                    if (data.getProtein() > DailyProtein) {
                                        daily_ingredients.add("protein");
                                    }

                                    if (data.getCarbohydrate() > DailyCarbohydrate) {
                                        daily_ingredients.add("carbohydrate");
                                    }

                                    if (data.getFat() > DailyFat) {
                                        daily_ingredients.add("fat");
                                    }

                                    if (data.getSugar() > DailySugar) {
                                        daily_ingredients.add("sugar");
                                    }

                                    if (data.getSodium() > DailySodium) {
                                        daily_ingredients.add("sodium");
                                    }
                                    Eating(arr_my_allergy);
                                }
                            } else {
                                if (ProductCalories > DailyCalories) {
                                    daily_ingredients.add("calories");
                                }

                                if (ProductProtein > DailyProtein) {
                                    daily_ingredients.add("protein");
                                }

                                if (ProductCarbohydrate > DailyCarbohydrate) {
                                    daily_ingredients.add("carbohydrate");
                                }

                                if (ProductFat > DailyFat) {
                                    daily_ingredients.add("fat");
                                }

                                if (ProductSugar > DailySugar) {
                                    daily_ingredients.add("sugar");
                                }

                                if (ProductSodium > DailySodium) {
                                    daily_ingredients.add("sodium");
                                }

                                Eating(arr_my_allergy);
                            }
                        }
                    }
                });
    }

    private void Eating(String[] arr_my_allergy) {
        String data2 = "";
        for (int i = 0; i < ingredients.size(); i++) {
            for (int j = 0; j < arr_my_allergy.length; j++) {
                if (arr_my_allergy[j].equals(ingredients.get(i))) {
                    data2 = data2 + "," + ingredients.get(i);
                }
            }
        }

        if (daily_ingredients.size() == 0 && data2.equals("")) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ProductDetailActivity.this);
            dialog.setTitle("แจ้งเตือน");
            dialog.setIcon(android.R.drawable.btn_star_big_on);
            dialog.setCancelable(true);
            dialog.setMessage("คุณต้องการรับประทานอาหารนี้ใช่หรือไม่?");
            dialog.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    UpdateData();
                    Toast.makeText(ProductDetailActivity.this, "รับประทานแล้ว", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
        } else {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.activity_pop_up, null);
            TextView txt_ingredients = alertLayout.findViewById(R.id.txt_ingredients);
            TextView txt_allergy = alertLayout.findViewById(R.id.txt_allergy);
            TextView txt_header1 = alertLayout.findViewById(R.id.txt_header1);
            TextView txt_header2 = alertLayout.findViewById(R.id.txt_header2);

            String data1 = "";
            for (int i = 0; i < daily_ingredients.size(); i++) {
                data1 = data1 + "," + daily_ingredients.get(i);
            }
            if (data1.equals("")) {
                txt_ingredients.setVisibility(View.GONE);
                txt_header1.setVisibility(View.GONE);
            } else {
                try {
                    if (data1.trim().substring(0, 1).equals(",")) {
                        txt_ingredients.setText(data1.substring(1));
                    } else {
                        txt_ingredients.setText(data1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (data2.equals("")) {
                txt_header2.setVisibility(View.GONE);
                txt_allergy.setVisibility(View.GONE);
            } else {
                try {
                    if (data2.trim().substring(0, 1).equals(",")) {
                        txt_allergy.setText(data2.substring(1));
                    } else {
                        txt_allergy.setText(data2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            AlertDialog.Builder alert = new AlertDialog.Builder(ProductDetailActivity.this, R.style.AlertDialogTheme);
            alert.setView(alertLayout);
            alert.setCancelable(false);
            AlertDialog dialog = alert.create();
            dialog.show();

            Button btn_ok = alertLayout.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateData();
                    dialog.dismiss();
                }
            });

            Button btn_cancel = alertLayout.findViewById(R.id.btn_cancel);
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.setOnKeyListener(new Dialog.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) { // กด back จะปิด pop up
                        dialog.dismiss();
                    }
                    return true;
                }
            });
        }


    }

    private void UpdateData() {
        String Today = Utils.dateThai(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        String ThisMonth = Utils.getMonth(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        String ThisYear = Utils.getYear(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        Calendar currentTime = Calendar.getInstance();
        String Time = currentTime.get(Calendar.HOUR_OF_DAY) + ":" + (String.valueOf(currentTime.get(Calendar.MINUTE)).length() == 1 ? "0" + currentTime.get(Calendar.MINUTE) + " น." : currentTime.get(Calendar.MINUTE) + " น.");

        final ProgressDialog pDialog = new ProgressDialog(ProductDetailActivity.this); //ประกาศ ProgressDialog
        pDialog.setCancelable(true);
        pDialog.setMessage("กรุณารอสักครู่ ...");
        pDialog.show();

        db.collection("day")
                .whereEqualTo("day", Today)
                .whereEqualTo("user_id", User_id)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() == 0) {
                                DocumentReference ref = db.collection("day").document();

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("calories", ProductCalories);
                                hashMap.put("carbohydrate", ProductCarbohydrate);
                                hashMap.put("day", Today);
                                hashMap.put("day_id", ref.getId());
                                hashMap.put("fat", ProductFat);
                                hashMap.put("protein", ProductProtein);
                                hashMap.put("sodium", ProductSodium);
                                hashMap.put("sugar", ProductSugar);
                                hashMap.put("dateTime", FieldValue.serverTimestamp());
                                hashMap.put("user_id", User_id);

                                db.collection("day").document(ref.getId()).set(hashMap);
                            } else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Day data = document.toObject(Day.class);
                                    String Day_Id = data.getDay_id();

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("calories", ProductCalories + data.getCalories());
                                    hashMap.put("carbohydrate", ProductCarbohydrate + data.getCarbohydrate());
                                    hashMap.put("fat", ProductFat + data.getFat());
                                    hashMap.put("protein", ProductProtein + data.getProtein());
                                    hashMap.put("sodium", ProductSodium + data.getSodium());
                                    hashMap.put("sugar", ProductSugar + data.getSugar());

                                    db.collection("day").document(Day_Id).update(hashMap);
                                }
                            }
                        }
                    }
                });

        db.collection("month")
                .whereEqualTo("month", ThisMonth)
                .whereEqualTo("user_id", User_id)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() == 0) {
                                DocumentReference ref = db.collection("month").document();
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("calories", ProductCalories);
                                hashMap.put("carbohydrate", ProductCarbohydrate);
                                hashMap.put("month", ThisMonth);
                                hashMap.put("month_id", ref.getId());
                                hashMap.put("fat", ProductFat);
                                hashMap.put("protein", ProductProtein);
                                hashMap.put("sodium", ProductSodium);
                                hashMap.put("sugar", ProductSugar);
                                hashMap.put("dateTime", FieldValue.serverTimestamp());
                                hashMap.put("user_id", User_id);

                                db.collection("month").document(ref.getId()).set(hashMap);
                            } else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Month data = document.toObject(Month.class);
                                    String Month_Id = data.getMonth_id();

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("calories", ProductCalories + data.getCalories());
                                    hashMap.put("carbohydrate", ProductCarbohydrate + data.getCarbohydrate());
                                    hashMap.put("fat", ProductFat + data.getFat());
                                    hashMap.put("protein", ProductProtein + data.getProtein());
                                    hashMap.put("sodium", ProductSodium + data.getSodium());
                                    hashMap.put("sugar", ProductSugar + data.getSugar());

                                    db.collection("month").document(Month_Id).update(hashMap);
                                }
                            }
                        }
                    }
                });

        db.collection("year")
                .whereEqualTo("year", ThisYear)
                .whereEqualTo("user_id", User_id)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() == 0) {
                                DocumentReference ref = db.collection("year").document();
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("calories", ProductCalories);
                                hashMap.put("carbohydrate", ProductCarbohydrate);
                                hashMap.put("year", ThisYear);
                                hashMap.put("year_id", ref.getId());
                                hashMap.put("fat", ProductFat);
                                hashMap.put("protein", ProductProtein);
                                hashMap.put("sodium", ProductSodium);
                                hashMap.put("sugar", ProductSugar);
                                hashMap.put("dateTime", FieldValue.serverTimestamp());
                                hashMap.put("user_id", User_id);

                                db.collection("year").document(ref.getId()).set(hashMap);
                            } else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Year data = document.toObject(Year.class);
                                    String Year_Id = data.getYear_id();

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("calories", ProductCalories + data.getCalories());
                                    hashMap.put("carbohydrate", ProductCarbohydrate + data.getCarbohydrate());
                                    hashMap.put("fat", ProductFat + data.getFat());
                                    hashMap.put("protein", ProductProtein + data.getProtein());
                                    hashMap.put("sodium", ProductSodium + data.getSodium());
                                    hashMap.put("sugar", ProductSugar + data.getSugar());

                                    db.collection("year").document(Year_Id).update(hashMap);
                                }
                            }
                        }
                    }
                });

        db.collection("product")
                .whereEqualTo("barcode", Barcode)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product data = document.toObject(Product.class);
                                int Eat = data.getEat() + 1;

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("eat", Eat);
                                db.collection("product").document(Barcode).update(hashMap);
                            }

                        }
                    }
                });

        DocumentReference ref = db.collection("eating").document();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("barcode", Barcode);
        hashMap.put("day", Today);
        hashMap.put("time", Time);
        hashMap.put("eating_id", ref.getId());
        hashMap.put("dateTime", FieldValue.serverTimestamp());
        hashMap.put("user_id", User_id);

        db.collection("eating").document(ref.getId()).set(hashMap);
        Toast.makeText(this, "รับประทานแล้ว", Toast.LENGTH_SHORT).show();
        pDialog.dismiss();
        Intent intent = new Intent(ProductDetailActivity.this, MainUserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("from", "eating");
        startActivity(intent);
    }

    public void Favorite(View view) {
        if (checkfavorite) {
            checkfavorite = false;
            btn_bookmark.setImageDrawable(getDrawable(R.drawable.ic_love_gray));

            db.collection("favorite")
                    .whereEqualTo("barcode", Barcode)
                    .whereEqualTo("user_id", User_id)
                    .limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    FavoriteModel data = document.toObject(FavoriteModel.class);
                                    db.collection("favorite").document(data.favorite_id).delete();
                                }
                            }

                        }
                    });
        } else {
            checkfavorite = true;
            btn_bookmark.setImageDrawable(getDrawable(R.drawable.ic_love_red));
            DocumentReference ref = db.collection("favorite").document();
            String favoriteid = ref.getId();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("favorite_id", favoriteid);
            hashMap.put("barcode", Barcode);
            hashMap.put("user_id", User_id);
            hashMap.put("dateTime", FieldValue.serverTimestamp());
            db.collection("favorite").document(favoriteid).set(hashMap);
        }
    }
}

