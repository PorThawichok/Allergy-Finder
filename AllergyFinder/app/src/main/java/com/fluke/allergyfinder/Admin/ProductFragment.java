package com.fluke.allergyfinder.Admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.Model.Product;
import com.fluke.allergyfinder.Product.ProductAddActivity;
import com.fluke.allergyfinder.Product.ProductDetailActivity;
import com.fluke.allergyfinder.Product.ProductEditActivity;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.UserData.UserEditActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProductFragment extends Fragment {

    ProductFragment_Adapter Adapter;
    private RecyclerView recyclerView;
    List<Product> DataList;
    EditText edt_search;
    TextView txt1;
    FirebaseFirestore db;
    String User_id;

    boolean test;

    public static Fragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        AppPreferences appPreferences = new AppPreferences(getActivity());
        User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);

        edt_search = view.findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String data = edt_search.getText().toString().trim();
                Load_Data(data);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        txt1 = view.findViewById(R.id.txt1);
        DataList = new ArrayList<>();

        FloatingActionButton fab_add = view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tt = new Intent(getActivity(), ProductAddActivity.class);
                startActivity(tt);
            }
        });

        Load_Data("");
        return view;
    }

    private void Load_Data(String keyword) {
        DataList.clear();
        recyclerView.requestLayout();
        if (keyword.equals("")) {
            db.collection("product")
                    .orderBy("dateTime", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product data = document.toObject(Product.class);
                                    DataList.add(data);
                                    txt1.setVisibility(View.GONE);
                                }

                                if (DataList.size() == 0) {
                                    txt1.setVisibility(View.VISIBLE);
                                    return;
                                }
                                Adapter = new ProductFragment_Adapter(DataList, getActivity());
                                recyclerView.setAdapter(Adapter);
                            } else {
                                Toast.makeText(getActivity(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
        } else {
            db.collection("product")
                    .orderBy("name")
                    .startAt(keyword)
                    .endAt(keyword + "\uf8ff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product data = document.toObject(Product.class);
                                    DataList.add(data);
                                    txt1.setVisibility(View.GONE);
                                }

                                if (DataList.size() == 0) {
                                    txt1.setVisibility(View.VISIBLE);
                                    //Toast.makeText(getActivity(), "ไม่มีข้อมูลการประกาศ", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Adapter = new ProductFragment_Adapter(DataList, getActivity());
                                recyclerView.setAdapter(Adapter);
                            } else {
                                Toast.makeText(getActivity(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
        }
    }

    private class ProductFragment_Adapter extends RecyclerView.Adapter<ProductFragment_Adapter.ViewHolder> {
        private List<Product> DataList;
        private Context context;

        public ProductFragment_Adapter(List<Product> DataList, FragmentActivity context) {
            this.DataList = DataList;
            this.context = context;
        }

        @Override
        public ProductFragment_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);
            return new ProductFragment_Adapter.ViewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final ProductFragment_Adapter.ViewHolder holder, final int position) {
            final Product model = DataList.get(position);

            holder.txt_allergy.setVisibility(View.GONE);

            Glide.with(getActivity())
                    .load(model.getPhoto())
                    .fitCenter()
                    .into(holder.img_card);

            holder.txt_name.setText(model.getName());

            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tt = new Intent(context, ProductDetailActivity.class);
                    tt.putExtra("barcode", model.getBarcode());
                    tt.putExtra("favorite", "false");
                    startActivity(tt);
                }
            });

            holder.card_view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Alert");
                    dialog.setIcon(android.R.drawable.btn_star_big_on);
                    dialog.setCancelable(true);
                    dialog.setMessage("Do you want to edit this product?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent tt = new Intent(getActivity(), ProductEditActivity.class);
                            tt.putExtra("barcode", model.getBarcode());
                            startActivity(tt);
                        }
                    });

                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return DataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView card_view;
            ImageView img_card;
            TextView txt_name, txt_allergy;

            // ประกาศตัวแปร
            ViewHolder(View itemView) {
                super(itemView);
                card_view = itemView.findViewById(R.id.card_view);
                img_card = itemView.findViewById(R.id.img_card);
                txt_name = itemView.findViewById(R.id.txt_name);
                txt_allergy = itemView.findViewById(R.id.txt_allergy);
            }
        }
    }
}