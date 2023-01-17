package com.fluke.allergyfinder.Admin;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.EditUserActivity;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.Utils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AccountFragment extends Fragment {

    public static Fragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    TextView txt_name, txt_password, txt_email, txt_short_name,txt_gender, txt_type;
    AppPreferences appPreferences;
    String User_id, Name, Password, Email, Photo, Type, Age, Gender;
    ImageView image_profile, btn_choose_photo, img_visible;

    boolean visiblePassword = true;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_admin, container, false);

        appPreferences = new AppPreferences(getActivity());
        storageReference = FirebaseStorage.getInstance().getReference("user");

        User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);
        Email = appPreferences.getStringPrefs(AppPreferences.KEY_EMAIL);
        Password = appPreferences.getStringPrefs(AppPreferences.KEY_PASSWORD);
        Name = appPreferences.getStringPrefs(AppPreferences.KEY_NAME);
        Type = appPreferences.getStringPrefs(AppPreferences.KEY_TYPE);
        Photo = appPreferences.getStringPrefs(AppPreferences.KEY_PHOTO);
        Age = appPreferences.getStringPrefs(AppPreferences.KEY_AGE);
        Gender = appPreferences.getStringPrefs(AppPreferences.KEY_GENDER);

        txt_name = view.findViewById(R.id.txt_name);
        txt_password = view.findViewById(R.id.txt_password);
        txt_email = view.findViewById(R.id.txt_email);
        txt_short_name = view.findViewById(R.id.txt_short_name);
        txt_gender = view.findViewById(R.id.txt_gender);
        txt_type = view.findViewById(R.id.txt_type);
        image_profile = view.findViewById(R.id.image_profile);
        btn_choose_photo = view.findViewById(R.id.btn_choose_photo);

        txt_name.setText(Name);
        txt_email.setText(Email);
        txt_password.setText(Password);
        txt_gender.setText(Gender);
        txt_type.setText(Type);

        if (!Photo.equals("")) {
            txt_short_name.setVisibility(View.GONE);
            Glide.with(getActivity()).load(Photo).into(image_profile);
        } else {
            txt_short_name.setText(Name.substring(0, 1));
        }

        img_visible = view.findViewById(R.id.img_visible);
        img_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visiblePassword) {
                    visiblePassword = false;
                    txt_password.setTransformationMethod(null);
                    img_visible.setImageResource(R.drawable.ic_visibility_off);
                } else {
                    visiblePassword = true;
                    txt_password.setTransformationMethod(new PasswordTransformationMethod());
                    img_visible.setImageResource(R.drawable.ic_visibility);
                }
            }
        });

        LinearLayout layout_edit = view.findViewById(R.id.layout_edit);
        layout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tt = new Intent(getActivity(), EditAdminActivity.class);
                tt.putExtra("user_id", User_id);
                tt.putExtra("email", Email);
                tt.putExtra("name", Name);
                tt.putExtra("password", Password);
                tt.putExtra("type", Type);
                tt.putExtra("photo", Photo);
                tt.putExtra("age", Age);
                tt.putExtra("gender", Gender);
                startActivity(tt);
            }
        });

        LinearLayout layout_logout = view.findViewById(R.id.layout_logout);
        layout_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.Logout(getActivity());
            }
        });

        btn_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

        return view;
    }

    private void openImage() {
        //CropImage.activity().start(getActivity());
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(), "Upload in preogress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        Utils.showProgress(getActivity());
        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        if (!Photo.equals("")) {
                            FirebaseStorage.getInstance().getReferenceFromUrl(Photo).delete();
                        }

                        Uri downloadUri = task.getResult();
                        String Url = downloadUri.toString();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("photo", "" + Url);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("user").document(User_id).update(map);

                        appPreferences.setStringPrefs(AppPreferences.KEY_PHOTO, Url);
                        Intent intent = new Intent(getActivity(), MainAdminActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("from", "edituser");
                        startActivity(intent);

                        Utils.hideProgress(getActivity());
                    } else {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        Utils.hideProgress(getActivity());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Utils.hideProgress(getActivity());
                }
            });
        } else {
            Toast.makeText(getContext(), "Please select a picture", Toast.LENGTH_SHORT).show();
        }
    }
}
