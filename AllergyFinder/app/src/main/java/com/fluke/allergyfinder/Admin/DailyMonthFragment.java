package com.fluke.allergyfinder.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fluke.allergyfinder.Model.Month;
import com.fluke.allergyfinder.Model.User;
import com.fluke.allergyfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DailyMonthFragment extends Fragment {

    DailyMonthFragment_Adapter Adapter;
    private RecyclerView recyclerView;
    List<Month> DataList;
    TextView txt1;
    FirebaseFirestore db;

    public static Fragment newInstance() {
        DailyMonthFragment fragment = new DailyMonthFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        txt1 = view.findViewById(R.id.txt1);
        DataList = new ArrayList<>();
        Load_Data();

        return view;
    }

    private void Load_Data() {
        DataList.clear();
        recyclerView.requestLayout();
        db.collection("month")
                .orderBy("dateTime", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Month data = document.toObject(Month.class);
                                DataList.add(data);
                                txt1.setVisibility(View.GONE);
                            }

                            if (DataList.size() == 0) {
                                txt1.setVisibility(View.VISIBLE);
                                return;
                            }
                            Adapter = new DailyMonthFragment_Adapter(DataList, getActivity());
                            recyclerView.setAdapter(Adapter);
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

    }

    private class DailyMonthFragment_Adapter extends RecyclerView.Adapter<DailyMonthFragment_Adapter.ViewHolder> {
        private List<Month> DataList;
        private Context context;

        public DailyMonthFragment_Adapter(List<Month> DataList, FragmentActivity context) {
            this.DataList = DataList;
            this.context = context;
        }

        @Override
        public DailyMonthFragment_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_calories_month, parent, false);
            return new DailyMonthFragment_Adapter.ViewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final DailyMonthFragment_Adapter.ViewHolder holder, final int position) {
            final Month model = DataList.get(position);

            holder.txt_product_name.setText(String.format("%,d", model.getCalories()));
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
                                    holder.txt_name.setText(data.getName());
                                }
                            }
                        }
                    });

            holder.txt_day_time.setText(model.getMonth());

            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tt = new Intent(context, EatingListActivity.class);
                    tt.putExtra("user_id", model.getUser_id());
                    startActivity(tt);
                }
            });
        }

        @Override
        public int getItemCount() {
            return DataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            CardView card_view;
            TextView txt_product_name, txt_name, txt_day_time;

            // ประกาศตัวแปร
            ViewHolder(View itemView) {
                super(itemView);
                card_view = itemView.findViewById(R.id.card_view);
                txt_product_name = itemView.findViewById(R.id.txt_product_name);
                txt_name = itemView.findViewById(R.id.txt_name);
                txt_day_time = itemView.findViewById(R.id.txt_day_time);
            }
        }
    }
}