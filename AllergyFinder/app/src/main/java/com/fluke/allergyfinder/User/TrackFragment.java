package com.fluke.allergyfinder.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.Admin.EatingListActivity;
import com.fluke.allergyfinder.FullImage.FullImageActivity;
import com.fluke.allergyfinder.Model.Day;
import com.fluke.allergyfinder.Model.Eating;
import com.fluke.allergyfinder.Model.Product;
import com.fluke.allergyfinder.Model.User;
import com.fluke.allergyfinder.Product.ProductDetailActivity;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TrackFragment extends Fragment {

    TextView txt_calories, txt_protein, txt_carbohydrate, txt_fat, txt_sugar, txt_sodium;
    ProgressBar progress_calories, progress_protein, progress_carbohydrate, progress_fat, progress_sugar, progress_sodium;
    FirebaseFirestore db;
    int Duration = 1000;


    TrackFragment_Adapter Adapter;
    private RecyclerView recyclerView;
    List<Eating> DataList;
    TextView txt1;

    public static Fragment newInstance() {
        TrackFragment fragment = new TrackFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, container, false);

        AppPreferences appPreferences = new AppPreferences(getActivity());
        String User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);
        int Calories = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_CALORIES);
        int Protein = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_PROTEIN);
        int Carbohydrate = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_CARBOHYDRATE);
        int Fat = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_FAT);
        int Sugar = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_SUGAR);
        int Sodium = appPreferences.getIntPrefs(AppPreferences.KEY_DAILY_SODIUM);

        txt_calories = view.findViewById(R.id.txt_calories);
        txt_protein = view.findViewById(R.id.txt_protein);
        txt_carbohydrate = view.findViewById(R.id.txt_carbohydrate);
        txt_fat = view.findViewById(R.id.txt_fat);
        txt_sugar = view.findViewById(R.id.txt_sugar);
        txt_sodium = view.findViewById(R.id.txt_sodium);
        progress_calories = view.findViewById(R.id.progress_calories);
        progress_protein = view.findViewById(R.id.progress_protein);
        progress_carbohydrate = view.findViewById(R.id.progress_carbohydrate);
        progress_fat = view.findViewById(R.id.progress_fat);
        progress_sugar = view.findViewById(R.id.progress_sugar);
        progress_sodium = view.findViewById(R.id.progress_sodium);

        progress_calories.setMax(Calories);
        progress_protein.setMax(Protein);
        progress_carbohydrate.setMax(Carbohydrate);
        progress_fat.setMax(Fat);
        progress_sugar.setMax(Sugar);
        progress_sodium.setMax(Sodium);

        db = FirebaseFirestore.getInstance();
        db.collection("day")
                .whereEqualTo("day", Utils.dateThai(new SimpleDateFormat("dd-MM-yyyy").format(new Date())))
                .whereEqualTo("user_id", User_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() == 0) {
                                txt_calories.setText("0/" + String.format("%,d", Calories));
                                txt_protein.setText("0/" + String.format("%,d", Protein));
                                txt_carbohydrate.setText("0/" + String.format("%,d", Carbohydrate));
                                txt_fat.setText("0/" + String.format("%,d", Fat));
                                txt_sugar.setText("0/" + String.format("%,d", Sugar));
                                txt_sodium.setText("0/" + String.format("%,d", Sodium));
                            } else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Day data = document.toObject(Day.class);

                                    ProgressBarAnimation anim1 = new ProgressBarAnimation(progress_calories, 0, data.getCalories());
                                    txt_calories.setText(data.getCalories() + "/" + String.format("%,d", Calories));
                                    anim1.setDuration(Duration);
                                    progress_calories.startAnimation(anim1);

                                    ProgressBarAnimation anim2 = new ProgressBarAnimation(progress_protein, 0, data.getProtein());
                                    txt_protein.setText(data.getProtein() + "/" + String.format("%,d", Protein));
                                    anim2.setDuration(Duration);
                                    progress_protein.startAnimation(anim2);

                                    ProgressBarAnimation anim3 = new ProgressBarAnimation(progress_carbohydrate, 0, data.getCarbohydrate());
                                    txt_carbohydrate.setText(data.getCarbohydrate() + "/" + String.format("%,d", Carbohydrate));
                                    anim3.setDuration(Duration);
                                    progress_carbohydrate.startAnimation(anim3);

                                    ProgressBarAnimation anim4 = new ProgressBarAnimation(progress_fat, 0, data.getFat());
                                    txt_fat.setText(data.getFat() + "/" + String.format("%,d", Fat));
                                    anim4.setDuration(Duration);
                                    progress_fat.startAnimation(anim4);

                                    ProgressBarAnimation anim5 = new ProgressBarAnimation(progress_sugar, 0, data.getSugar());
                                    txt_sugar.setText(data.getSugar() + "/" + String.format("%,d", Sugar));
                                    anim5.setDuration(Duration);
                                    progress_sugar.startAnimation(anim5);

                                    ProgressBarAnimation anim6 = new ProgressBarAnimation(progress_sodium, 0, data.getSodium());
                                    txt_sodium.setText(data.getSodium() + "/" + String.format("%,d", Sodium));
                                    anim6.setDuration(Duration);
                                    progress_sodium.startAnimation(anim6);
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), "Something wrong, Please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        txt1 = view.findViewById(R.id.txt1);
        DataList = new ArrayList<>();

        Load_Data(User_id);

        return view;
    }

    private void Load_Data(String User_id) {
        DataList.clear();
        recyclerView.requestLayout();
        db.collection("eating")
                .whereEqualTo("user_id", User_id)
                .orderBy("dateTime", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Eating data = document.toObject(Eating.class);
                                DataList.add(data);
                                txt1.setVisibility(View.GONE);
                            }

                            if (DataList.size() == 0) {
                                txt1.setVisibility(View.VISIBLE);
                                return;
                            }
                            Adapter = new TrackFragment_Adapter(DataList, getActivity());
                            recyclerView.setAdapter(Adapter);
                        } else {
                            Toast.makeText(getActivity(), "Something wrong, Please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

    }

    private class TrackFragment_Adapter extends RecyclerView.Adapter<TrackFragment_Adapter.ViewHolder> {
        private List<Eating> DataList;
        private Context context;

        public TrackFragment_Adapter(List<Eating> DataList, FragmentActivity context) {
            this.DataList = DataList;
            this.context = context;
        }

        @Override
        public TrackFragment_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_eating, parent, false);
            return new TrackFragment_Adapter.ViewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final TrackFragment_Adapter.ViewHolder holder, final int position) {
            final Eating model = DataList.get(position);

            db.collection("product")
                    .whereEqualTo("barcode", model.getBarcode())
                    .limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product data = document.toObject(Product.class);

                                    Glide.with(context)
                                            .load(data.getPhoto())
                                            .fitCenter()
                                            .into(holder.img_product);

                                    holder.txt_product_name.setText(data.getName());
                                }
                            }
                        }
                    });

            db.collection("user")
                    .whereEqualTo("user_id", model.getUser_id())
                    .limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User data = document.toObject(User.class);
                                    holder.txt_name.setText("Name: " + data.getName());
                                }
                            }
                        }
                    });

            if (model.getDay().equals(Utils.dateThai(new SimpleDateFormat("dd-MM-yyyy").format(new Date())))) {
                holder.txt_day_time.setText("Time : " + model.getTime());
            } else {
                holder.txt_day_time.setText("Date : " + model.getDay());
            }
        }

        @Override
        public int getItemCount() {
            return DataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            CardView card_view;
            ImageView img_product;
            TextView txt_product_name, txt_name, txt_day_time;

            // ประกาศตัวแปร
            ViewHolder(View itemView) {
                super(itemView);
                card_view = itemView.findViewById(R.id.card_view);
                img_product = itemView.findViewById(R.id.img_product);
                txt_product_name = itemView.findViewById(R.id.txt_product_name);
                txt_name = itemView.findViewById(R.id.txt_name);
                txt_day_time = itemView.findViewById(R.id.txt_day_time);
            }
        }
    }
}

class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float to;

    public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }

}