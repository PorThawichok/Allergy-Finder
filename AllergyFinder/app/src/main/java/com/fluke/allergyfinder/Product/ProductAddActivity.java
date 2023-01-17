package com.fluke.allergyfinder.Product;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fluke.allergyfinder.Admin.MainAdminActivity;
import com.fluke.allergyfinder.Model.Product;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductAddActivity extends AppCompatActivity {

    EditText edt_barcode, edt_product_name, edt_calories, edt_fat, edt_sugar, edt_protein, edt_carbohydrate, edt_sodium;
    CheckBox chk_gluten, chk_lactose, chk_nut, chk_shellfish, chk_corn, chk_fluctose, chk_vegan, chk_sugar;
    ImageView image_product, img_close1, img_close2, img_close3, img_close4, img_close5, img_close6, img_close7, img_close8;

    ProgressDialog pDialog;
    Uri fileUri, selectedImage;
    String picturePath = "", ba1, User_id;
    Bitmap photo, ba, bmp;
    TextView txt1;

    AppPreferences appPreferences;
    private Integer MY_PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Product");

        appPreferences = new AppPreferences(ProductAddActivity.this);
        User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);

        edt_barcode = findViewById(R.id.edt_barcode);
        edt_product_name = findViewById(R.id.edt_product_name);
        edt_calories = findViewById(R.id.edt_calories);
        edt_fat = findViewById(R.id.edt_fat);
        edt_sugar = findViewById(R.id.edt_sugar);
        edt_protein = findViewById(R.id.edt_protein);
        edt_carbohydrate = findViewById(R.id.edt_carbohydrate);
        edt_sodium = findViewById(R.id.edt_sodium);
        chk_gluten = findViewById(R.id.chk_gluten);
        chk_lactose = findViewById(R.id.chk_lactose);
        chk_nut = findViewById(R.id.chk_nut);
        chk_shellfish = findViewById(R.id.chk_shellfish);
        chk_corn = findViewById(R.id.chk_corn);
        chk_fluctose = findViewById(R.id.chk_fluctose);
        chk_vegan = findViewById(R.id.chk_vegan);
        chk_sugar = findViewById(R.id.chk_sugar);
        img_close1 = findViewById(R.id.img_close1);
        img_close2 = findViewById(R.id.img_close2);
        img_close3 = findViewById(R.id.img_close3);
        img_close4 = findViewById(R.id.img_close4);
        img_close5 = findViewById(R.id.img_close5);
        img_close6 = findViewById(R.id.img_close6);
        img_close7 = findViewById(R.id.img_close7);
        img_close8 = findViewById(R.id.img_close8);

        EditText array_edt[] = {edt_barcode, edt_product_name, edt_calories, edt_fat, edt_sugar, edt_protein, edt_carbohydrate, edt_sodium};
        ImageView array_img[] = {img_close1, img_close2, img_close3, img_close4, img_close5, img_close6, img_close7, img_close8};

        for (int i = 0; i < array_edt.length; i++) {
            int finalI = i;
            array_edt[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count != 0) {
                        array_img[finalI].setVisibility(View.VISIBLE);
                    } else {
                        array_img[finalI].setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                }
            });
        }

        txt1 = findViewById(R.id.txt1);
        image_product = findViewById(R.id.image_product);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void Choose_Image(View view) {
        ActivityCompat.requestPermissions(ProductAddActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 400);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(MediaStore.EXTRA_MEDIA_FOCUS, fileUri);
        startActivityForResult(intent, 400);
//        CharSequence[] items = {"ถ่ายรูป", "เลือกรูป"};
//        AlertDialog.Builder dialog = new AlertDialog.Builder(ProductAddActivity.this);
//
//        dialog.setTitle("กรุณาเลือกคำสั่ง");
//        dialog.setIcon(R.mipmap.ic_launcher);
//        dialog.setItems(items, new DialogInterface.OnClickListener() {
//            @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (item == 0) {
//                    if (ProductAddActivity.this.getPackageManager().hasSystemFeature(
//                            PackageManager.FEATURE_CAMERA)) {
//                        ActivityCompat.requestPermissions(ProductAddActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                        startActivityForResult(intent, 100);
//                    }
//                } else if (item == 1) {
//
//                }
//            }
//        });
//        dialog.show();
    }

    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        selectedImage = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(ProductAddActivity.this.getContentResolver(), selectedImage);
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = ProductAddActivity.this.getContentResolver().query(selectedImage, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            picturePath = cursor.getString(columnIndex); // returns null
            cursor.close();

            image_product.setImageBitmap(bitmap); // แสดงรุป
            txt1.setVisibility(View.GONE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = ProductAddActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public static boolean checkBarcode(String barcode){

        return barcode.isEmpty();
    }

    public static boolean checkCalories(String calories){

        return calories.isEmpty();
    }
    public static boolean checkFat(String fat){

        return fat.isEmpty();
    }
    public static boolean checkSugar(String sugar){

        return sugar.isEmpty();
    }
    public static boolean checkProtein(String protein){

        return protein.isEmpty();
    }
    public static boolean checkCarbohydrate(String carbohydrate){

        return carbohydrate.isEmpty();
    }
    public static boolean checkSodium(String sodium){

        return sodium.isEmpty();
    }

    public void Product_Add(View view) {
        String gluten, lactose, nut, shellfish, corn, fluctose, vegan, no_sugar;
        List<String> ingredients = new ArrayList<>();
        final String Barcode = edt_barcode.getText().toString().trim();
        final String Name = edt_product_name.getText().toString().trim();
        final String Calories = edt_calories.getText().toString().trim();
        final String Fat = edt_fat.getText().toString().trim();
        final String Sugar = edt_sugar.getText().toString().trim();
        final String Protein = edt_protein.getText().toString().trim();
        final String Carbohydrate = edt_carbohydrate.getText().toString().trim();
        final String Sodium = edt_sodium.getText().toString().trim();

        if (picturePath.equals("")) {
            Toast.makeText(ProductAddActivity.this, "กรุณาเลือกรูปก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkBarcode(Barcode)) {
            edt_barcode.setError("กรุณาใส่รหัสบาร์โค้ดก่อน");
            edt_barcode.requestFocus();
            return;
        }

        if (checkCalories(Calories)) {
            edt_calories.setError("กรุณาใส่ชื่อสินค้าก่อน");
            edt_calories.requestFocus();
            return;
        }

        if (checkFat(Fat)) {
            edt_fat.setError("Please enter fat yet");
            edt_fat.requestFocus();
            return;
        }

        if (checkSugar(Sugar)) {
            edt_sugar.setError("Please enter sugar yet");
            edt_sugar.requestFocus();
            return;
        }

        if (checkProtein(Protein)) {
            edt_protein.setError("กรุณาใส่ชื่อสินค้าก่อน");
            edt_protein.requestFocus();
            return;
        }

        if (checkCarbohydrate(Carbohydrate)) {
            edt_carbohydrate.setError("กรุณาใส่ชื่อสินค้าก่อน");
            edt_carbohydrate.requestFocus();
            return;
        }

        if (checkSodium(Sodium)) {
            edt_sodium.setError("กรุณาใส่ชื่อสินค้าก่อน");
            edt_sodium.requestFocus();
            return;
        }

        if (chk_gluten.isChecked()) {
            gluten = "gluten";
            ingredients.add(gluten);
        }

        if (chk_lactose.isChecked()) {
            lactose = "lactose";
            ingredients.add(lactose);
        }

        if (chk_nut.isChecked()) {
            nut = "nut";
            ingredients.add(nut);
        }

        if (chk_shellfish.isChecked()) {
            shellfish = "shellfish";
            ingredients.add(shellfish);
        }
        if (chk_corn.isChecked()) {
            corn = "corn";
            ingredients.add(corn);
        }

        if (chk_fluctose.isChecked()) {
            fluctose = "fluctose";
            ingredients.add(fluctose);
        }
        if (chk_vegan.isChecked()) {
            vegan = "vegan";
            ingredients.add(vegan);
        }

        if (chk_sugar.isChecked()) {
            no_sugar = "no sugar";
            ingredients.add(no_sugar);
        }
        final ProgressDialog pDialog = new ProgressDialog(ProductAddActivity.this); //ประกาศ ProgressDialog
        pDialog.setCancelable(true);
        pDialog.setMessage("กรุณารอสักครู่ ...");
        pDialog.show();

        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 30, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);

        final StorageReference folderRef = FirebaseStorage.getInstance().getReference().child("Product/"
                + Name + "_" + System.currentTimeMillis()
                + "." + getFileExtension(selectedImage));

        final UploadTask uploadTask = folderRef.putBytes(ba);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("error", exception.getMessage());
                Toast.makeText(ProductAddActivity.this, "เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                folderRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String Photo_Url = uri.toString();

                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        final String Barcode = edt_barcode.getText().toString().trim();
                        final String Name = edt_product_name.getText().toString().trim();
                        final String Calories = edt_calories.getText().toString().trim();
                        final String Fat = edt_fat.getText().toString().trim();
                        final String Sugar = edt_sugar.getText().toString().trim();
                        final String Protein = edt_protein.getText().toString().trim();
                        final String Carbohydrate = edt_carbohydrate.getText().toString().trim();
                        final String Sodium = edt_sodium.getText().toString().trim();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("product_id", Barcode);
                        hashMap.put("barcode", Barcode);
                        hashMap.put("name", Name);
                        hashMap.put("calories", Integer.parseInt(Calories));
                        hashMap.put("eat", 0);
                        hashMap.put("fat", Integer.parseInt(Fat));
                        hashMap.put("sugar", Integer.parseInt(Sugar));
                        hashMap.put("protein", Integer.parseInt(Protein));
                        hashMap.put("carbohydrate", Integer.parseInt(Carbohydrate));
                        hashMap.put("sodium", Integer.parseInt(Sodium));
                        hashMap.put("photo", Photo_Url);
                        hashMap.put("ingredients", ingredients);
                        hashMap.put("dateTime", FieldValue.serverTimestamp());

                        db.collection("product").document(Barcode).set(hashMap);

                        pDialog.dismiss();
                        finish();

                        Toast.makeText(ProductAddActivity.this, "เพิ่มสินค้าสำเร็จ", Toast.LENGTH_SHORT).show();
                        Intent tt = new Intent(ProductAddActivity.this, MainAdminActivity.class);
                        tt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        tt.putExtra("from", "");
                        startActivity(tt);
                    }
                });
            }
        });
    }

    public void Close1(View view) {
        edt_barcode.setText("");
    }

    public void Close2(View view) {
        edt_product_name.setText("");
    }

    public void Close3(View view) {
        edt_calories.setText("");
    }

    public void Close4(View view) {
        edt_fat.setText("");
    }

    public void Close5(View view) {
        edt_sugar.setText("");
    }

    public void Close6(View view) {
        edt_protein.setText("");
    }

    public void Close7(View view) {
        edt_carbohydrate.setText("");
    }

    public void Close8(View view) {
        edt_sodium.setText("");
    }
}