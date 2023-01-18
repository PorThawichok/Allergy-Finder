package com.fluke.allergyfinder.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.Model.Product;
import com.fluke.allergyfinder.Product.ProductDetailActivity;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment2 extends Fragment {

    SearchFragment2_Adapter Adapter;
    private RecyclerView recyclerView;
    List<Product> DataList;
    //EditText edt_search;
    TextView txt1;
    FirebaseFirestore db;
    String User_id, Corn, Fluctose, Gluten, Lactose, No_Sugar, Nut, Shellfish, Vegan;
    List<String> my_lose = new ArrayList<>();

    public static Fragment newInstance() {
        SearchMainFragment fragment = new SearchMainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search2, container, false);

        AppPreferences appPreferences = new AppPreferences(getActivity());
        User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);
        Corn = appPreferences.getStringPrefs(AppPreferences.KEY_CORN);
        Fluctose = appPreferences.getStringPrefs(AppPreferences.KEY_FLUCTOSE);
        Gluten = appPreferences.getStringPrefs(AppPreferences.KEY_GLUTEN);
        Lactose = appPreferences.getStringPrefs(AppPreferences.KEY_LACTOSE);
        No_Sugar = appPreferences.getStringPrefs(AppPreferences.KEY_NO_SUGAR);
        Nut = appPreferences.getStringPrefs(AppPreferences.KEY_NUT);
        Shellfish = appPreferences.getStringPrefs(AppPreferences.KEY_SHELLFISH);
        Vegan = appPreferences.getStringPrefs(AppPreferences.KEY_VEGAN);

        String arrayLose[] = {Corn, Fluctose, Gluten, Lactose, No_Sugar, Nut, Shellfish, Vegan};
        for (int i = 0; i < arrayLose.length; i++) {
            if (!arrayLose[i].equals("")) {
                my_lose.add(arrayLose[i]);
            }
        }

//        edt_search = view.findViewById(R.id.edt_search);
//        edt_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String data = edt_search.getText().toString().trim();
//                Load_Data(data);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                // TODO Auto-generated method stub
//            }
//        });

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        txt1 = view.findViewById(R.id.txt1);
        DataList = new ArrayList<>();

        Load_Data("");
        return view;
    }

    private void Load_Data(String keyword) {
        DataList.clear();
        recyclerView.requestLayout();

        db.collection("product")
                .orderBy("dateTime", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product data = document.toObject(Product.class);

                                boolean lose = false;
                                for (int i = 0; i < data.ingredients.size(); i++) {
                                    for (int j = 0; j < my_lose.size(); j++) {
                                        if (data.ingredients.get(i).equals(my_lose.get(j))) {
                                            lose = true;
                                        }
                                    }
                                }

                                if (data.eat > 0 && !lose) {
                                    DataList.add(data);
                                    txt1.setVisibility(View.GONE);
                                }


                            }

                            if (DataList.size() == 0) {
                                txt1.setVisibility(View.VISIBLE);
                                return;
                            }
                            Adapter = new SearchFragment2_Adapter(DataList, getActivity());
                            recyclerView.setAdapter(Adapter);
                        } else {
                            Toast.makeText(getActivity(), "Something wrong, Please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
//        } else {
//            db.collection("product")
//                    .orderBy("name")
//                    .startAt(keyword)
//                    .endAt(keyword + "\uf8ff")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Product data = document.toObject(Product.class);
//                                    DataList.add(data);
//                                    txt1.setVisibility(View.GONE);
//                                }
//
//                                if (DataList.size() == 0) {
//                                    txt1.setVisibility(View.VISIBLE);
//                                    //Toast.makeText(getActivity(), "ไม่มีข้อมูลการประกาศ", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                Adapter = new SearchFragment2_Adapter(DataList, getActivity());
//                                recyclerView.setAdapter(Adapter);
//                            } else {
//                                Toast.makeText(getActivity(), "เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        }
//                    });
//        }
    }

    private class SearchFragment2_Adapter extends RecyclerView.Adapter<SearchFragment2_Adapter.ViewHolder> {
        private List<Product> DataList;
        private Context context;

        public SearchFragment2_Adapter(List<Product> DataList, FragmentActivity context) {
            this.DataList = DataList;
            this.context = context;
        }

        @Override
        public SearchFragment2_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recommend, parent, false);
            return new SearchFragment2_Adapter.ViewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final SearchFragment2_Adapter.ViewHolder holder, final int position) {
            final Product model = DataList.get(position);

//            boolean lose = false;
//            for (int i = 0; i < model.getIngredients().size(); i++) {
//                for (int j = 0; j < my_lose.size(); j++) {
//                    if (model.getIngredients().get(i).equals(my_lose.get(j))) {
//                        lose = true;
//                    }
//                }
//            }
//
//            if (model.getEat() > 0 && !lose) {
//                holder.img_recommend.setVisibility(View.VISIBLE);
//            } else {
//                holder.img_recommend.setVisibility(View.GONE);
//            }

            Glide.with(getActivity())
                    .load(model.getPhoto())
                    .fitCenter()
                    .into(holder.img_card);

            holder.txt_name.setText(model.getName());

            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.collection("favorite")
                            .whereEqualTo("barcode", model.getBarcode())
                            .whereEqualTo("user_id", User_id).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().size() == 0) {
                                            Intent tt = new Intent(context, ProductDetailActivity.class);
                                            tt.putExtra("barcode", model.getBarcode());
                                            tt.putExtra("favorite", "false");
                                            startActivity(tt);
                                        } else {
                                            Intent tt = new Intent(context, ProductDetailActivity.class);
                                            tt.putExtra("barcode", model.getBarcode());
                                            tt.putExtra("favorite", "true");
                                            startActivity(tt);
                                        }
                                    }
                                }
                            });
                }
            });
        }

        @Override
        public int getItemCount() {
            return DataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView card_view;
            ImageView img_card, img_recommend;
            TextView txt_name;

            // ประกาศตัวแปร
            ViewHolder(View itemView) {
                super(itemView);
                card_view = itemView.findViewById(R.id.card_view);
                img_card = itemView.findViewById(R.id.img_card);
                txt_name = itemView.findViewById(R.id.txt_name);
                img_recommend = itemView.findViewById(R.id.img_recommend);
            }
        }
    }
}