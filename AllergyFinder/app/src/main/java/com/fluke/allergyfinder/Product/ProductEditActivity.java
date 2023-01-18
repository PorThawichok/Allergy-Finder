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

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.Admin.MainAdminActivity;
import com.fluke.allergyfinder.FullImage.FullImageActivity;
import com.fluke.allergyfinder.Model.Product;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
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

public class ProductEditActivity extends AppCompatActivity {

    EditText edt_barcode, edt_product_name, edt_calories, edt_fat, edt_sugar, edt_protein, edt_carbohydrate, edt_sodium;
    CheckBox chk_gluten, chk_lactose, chk_nut, chk_shellfish, chk_corn, chk_fluctose, chk_vegan, chk_sugar;
    ImageView image_product,img_close1, img_close2, img_close3, img_close4, img_close5, img_close6, img_close7, img_close8;
    String Barcode;

    List<String> ingredients;
    Uri fileUri, selectedImage;
    String picturePath = "", ba1, User_id;
    int Calories, Protein, Carbohydrate, Fat, Sugar, Sodium;
    Bitmap photo, ba, bmp;
    AppPreferences appPreferences;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Product");

        db = FirebaseFirestore.getInstance();
        appPreferences = new AppPreferences(ProductEditActivity.this);
        User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);

        Intent tt = getIntent();
        Barcode = tt.getStringExtra("barcode");

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
        image_product = findViewById(R.id.image_product);

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

                                edt_product_name.setText(data.getName());
                                edt_barcode.setText(Barcode);

                                Calories = data.getCalories();
                                Carbohydrate = data.getCarbohydrate();
                                Protein = data.getProtein();
                                Fat = data.getFat();
                                Sugar = data.getSugar();
                                Sodium = data.getSodium();

                                edt_calories.setText(String.valueOf(Calories));
                                edt_carbohydrate.setText(String.valueOf(Carbohydrate));
                                edt_protein.setText(String.valueOf(Protein));
                                edt_fat.setText(String.valueOf(Fat));
                                edt_sodium.setText(String.valueOf(Sodium));
                                edt_sugar.setText(String.valueOf(Sugar));

                                Glide.with(ProductEditActivity.this).load(data.getPhoto()).into(image_product);

                                ingredients = data.ingredients;

                                image_product.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent tt = new Intent(ProductEditActivity.this, FullImageActivity.class);
                                        tt.putExtra("photo", data.getPhoto());
                                        startActivity(tt);
                                    }
                                });

                                for (int i = 0; i < ingredients.size(); i++) {
                                    if (ingredients.get(i).equals("gluten")) {
                                        chk_gluten.setChecked(true);
                                    } else if (ingredients.get(i).equals("lactose")) {
                                        chk_lactose.setChecked(true);
                                    } else if (ingredients.get(i).equals("nut")) {
                                        chk_nut.setChecked(true);
                                    } else if (ingredients.get(i).equals("shellfish")) {
                                        chk_shellfish.setChecked(true);
                                    } else if (ingredients.get(i).equals("corn")) {
                                        chk_corn.setChecked(true);
                                    } else if (ingredients.get(i).equals("fluctose")) {
                                        chk_fluctose.setChecked(true);
                                    } else if (ingredients.get(i).equals("vegan")) {
                                        chk_vegan.setChecked(true);
                                    } else if (ingredients.get(i).equals("no sugar")) {
                                        chk_sugar.setChecked(true);
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(ProductEditActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
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

    public void Choose_Image(View view) {
        ActivityCompat.requestPermissions(ProductEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 400);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(MediaStore.EXTRA_MEDIA_FOCUS, fileUri);
        startActivityForResult(intent, 400);
    }

    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        selectedImage = data.getData();

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(ProductEditActivity.this.getContentResolver(), selectedImage);
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = ProductEditActivity.this.getContentResolver().query(selectedImage, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            picturePath = cursor.getString(columnIndex); // returns null
            cursor.close();

            image_product.setImageBitmap(bitmap); // แสดงรุป
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = ProductEditActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void Product_Edit(View view) {
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

        if (Barcode.isEmpty()) {
            edt_barcode.setError("Please input barcode first");
            edt_barcode.requestFocus();
            return;
        }

        if (Calories.isEmpty()) {
            edt_calories.setError("Please input Calorie first");
            edt_calories.requestFocus();
            return;
        }

        if (Fat.isEmpty()) {
            edt_fat.setError("Please input Fat");
            edt_fat.requestFocus();
            return;
        }

        if (Sugar.isEmpty()) {
            edt_sugar.setError("Please input Sugar");
            edt_sugar.requestFocus();
            return;
        }

        if (Protein.isEmpty()) {
            edt_protein.setError("Please input Protein");
            edt_protein.requestFocus();
            return;
        }

        if (Carbohydrate.isEmpty()) {
            edt_carbohydrate.setError("Please input Carbohydrate");
            edt_carbohydrate.requestFocus();
            return;
        }

        if (Sodium.isEmpty()) {
            edt_sodium.setError("Please input Sodium");
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


        final ProgressDialog pDialog = new ProgressDialog(ProductEditActivity.this); //ประกาศ ProgressDialog
        pDialog.setCancelable(true);
        pDialog.setMessage("Moment ...");
        pDialog.show();

        if (picturePath.equals("")) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("product_id", Barcode);
            hashMap.put("barcode", Barcode);
            hashMap.put("name", Name);
            hashMap.put("calories", Integer.parseInt(Calories));
            hashMap.put("fat", Integer.parseInt(Fat));
            hashMap.put("sugar", Integer.parseInt(Sugar));
            hashMap.put("protein", Integer.parseInt(Protein));
            hashMap.put("carbohydrate", Integer.parseInt(Carbohydrate));
            hashMap.put("sodium", Integer.parseInt(Sodium));
            hashMap.put("ingredients", ingredients);

            db.collection("product").document(Barcode).update(hashMap);

            pDialog.dismiss();
            finish();

            Toast.makeText(ProductEditActivity.this, "Product edit complete", Toast.LENGTH_SHORT).show();
            Intent tt = new Intent(ProductEditActivity.this, MainAdminActivity.class);
            tt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            tt.putExtra("from", "");
            startActivity(tt);
        } else {
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
                    Toast.makeText(ProductEditActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    folderRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String Photo_Url = uri.toString();

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("product_id", Barcode);
                            hashMap.put("barcode", Barcode);
                            hashMap.put("name", Name);
                            hashMap.put("calories", Integer.parseInt(Calories));
                            hashMap.put("fat", Integer.parseInt(Fat));
                            hashMap.put("sugar", Integer.parseInt(Sugar));
                            hashMap.put("protein", Integer.parseInt(Protein));
                            hashMap.put("carbohydrate", Integer.parseInt(Carbohydrate));
                            hashMap.put("sodium", Integer.parseInt(Sodium));
                            hashMap.put("photo", Photo_Url);
                            hashMap.put("ingredients", ingredients);

                            db.collection("product").document(Barcode).update(hashMap);

                            pDialog.dismiss();
                            finish();

                            Toast.makeText(ProductEditActivity.this, "Product edit complete", Toast.LENGTH_SHORT).show();
                            Intent tt = new Intent(ProductEditActivity.this, MainAdminActivity.class);
                            tt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            tt.putExtra("from", "");
                            startActivity(tt);
                        }
                    });
                }
            });
        }
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