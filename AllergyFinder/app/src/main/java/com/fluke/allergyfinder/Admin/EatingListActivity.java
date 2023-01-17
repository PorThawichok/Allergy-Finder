package com.fluke.allergyfinder.Admin;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.Model.Eating;
import com.fluke.allergyfinder.Model.Product;
import com.fluke.allergyfinder.Model.User;
import com.fluke.allergyfinder.R;
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

public class EatingListActivity extends AppCompatActivity {

    EatingListActivity_Adapter Adapter;
    private RecyclerView recyclerView;
    List<Eating> DataList;
    TextView txt1;
    FirebaseFirestore db;
    String User_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eating_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent tt = getIntent();
        User_id = tt.getStringExtra("user_id");

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(EatingListActivity.this));
        txt1 = findViewById(R.id.txt1);
        DataList = new ArrayList<>();

        Load_Data();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void Load_Data() {
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
                            Adapter = new EatingListActivity_Adapter(DataList, EatingListActivity.this);
                            recyclerView.setAdapter(Adapter);
                        } else {
                            Toast.makeText(EatingListActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

    }

    private class EatingListActivity_Adapter extends RecyclerView.Adapter<EatingListActivity_Adapter.ViewHolder> {
        private List<Eating> DataList;
        private Context context;

        public EatingListActivity_Adapter(List<Eating> DataList, FragmentActivity context) {
            this.DataList = DataList;
            this.context = context;
        }

        @Override
        public EatingListActivity_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_eating, parent, false);
            return new EatingListActivity_Adapter.ViewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final EatingListActivity_Adapter.ViewHolder holder, final int position) {
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
                                            .centerCrop()
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
                holder.txt_day_time.setText("Date : " + model.getTime());
            } else {
                holder.txt_day_time.setText("Time : " + model.getDay());
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