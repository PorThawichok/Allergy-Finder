package com.fluke.allergyfinder.User;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.fluke.allergyfinder.FullImage.FullImageActivity;
import com.fluke.allergyfinder.Model.FavoriteModel;
import com.fluke.allergyfinder.Model.Product;
import com.fluke.allergyfinder.Product.ProductDetailActivity;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookmarkFragment extends Fragment {

    BookmarkFragment_Adapter Adapter;
    private RecyclerView recyclerView;
    List<FavoriteModel> DataList;
    TextView txt1;
    FirebaseFirestore db;
    String User_id;

    public static Fragment newInstance() {
        BookmarkFragment fragment = new BookmarkFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        AppPreferences appPreferences = new AppPreferences(getActivity());
        User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        txt1 = view.findViewById(R.id.txt1);
        DataList = new ArrayList<>();

        Load_Data();
        return view;
    }

    private void Load_Data() {
        DataList.clear();
        recyclerView.requestLayout();
        db.collection("favorite")
                .whereEqualTo("user_id", User_id)
                .orderBy("dateTime", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FavoriteModel data = document.toObject(FavoriteModel.class);
                                DataList.add(data);
                                txt1.setVisibility(View.GONE);
                            }

                            if (DataList.size() == 0) {
                                txt1.setVisibility(View.VISIBLE);
                                return;
                            }
                            Adapter = new BookmarkFragment_Adapter(DataList, getActivity());
                            recyclerView.setAdapter(Adapter);
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    private class BookmarkFragment_Adapter extends RecyclerView.Adapter<BookmarkFragment_Adapter.ViewHolder> {
        private List<FavoriteModel> DataList;
        private Context context;

        public BookmarkFragment_Adapter(List<FavoriteModel> DataList, FragmentActivity context) {
            this.DataList = DataList;
            this.context = context;
        }

        @Override
        public BookmarkFragment_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_favorite, parent, false);
            return new BookmarkFragment_Adapter.ViewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final BookmarkFragment_Adapter.ViewHolder holder, final int position) {
            final FavoriteModel model = DataList.get(position);

            db.collection("product")
                    .whereEqualTo("barcode", model.getBarcode())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product data = document.toObject(Product.class);

                                    Glide.with(getActivity())
                                            .load(data.getPhoto())
                                            .fitCenter()
                                            .into(holder.img_card);

                                    holder.txt_name.setText(data.name);
                                    holder.card_view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            db.collection("favorite")
                                                    .whereEqualTo("barcode", model.getBarcode())
                                                    .whereEqualTo("user_id", User_id)
                                                    .limit(1)
                                                    .get()
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
                            } else {
                                Toast.makeText(context, "เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });

            holder.btn_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.btn_bookmark.setImageDrawable(getResources().getDrawable(R.drawable.ic_love_gray));

                    db.collection("favorite")
                            .whereEqualTo("barcode", model.getBarcode())
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

                                        Load_Data();
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
            ImageView img_card, btn_bookmark;
            TextView txt_name;

            // ประกาศตัวแปร
            ViewHolder(View itemView) {
                super(itemView);
                card_view = itemView.findViewById(R.id.card_view);
                img_card = itemView.findViewById(R.id.img_card);
                btn_bookmark = itemView.findViewById(R.id.btn_bookmark);
                txt_name = itemView.findViewById(R.id.txt_name);
            }
        }
    }
}