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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.fluke.allergyfinder.LoginActivity;
import com.fluke.allergyfinder.Model.User;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.UserData.UserAddActivity1;
import com.fluke.allergyfinder.UserData.UserDetailActivity;
import com.fluke.allergyfinder.UserData.UserEditActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    UserFragment_Adapter Adapter;
    private RecyclerView recyclerView;
    List<User> DataList;
    EditText edt_search;
    TextView txt1;
    FirebaseFirestore db;

    public static Fragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        AppPreferences appPreferences = new AppPreferences(getActivity());
        String User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);
        String Name = appPreferences.getStringPrefs(AppPreferences.KEY_NAME);

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        txt1 = view.findViewById(R.id.txt1);
        DataList = new ArrayList<>();

        FloatingActionButton fab_add = view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tt = new Intent(getActivity(), UserAddActivity1.class);
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
            db.collection("user")
                    .orderBy("dateTime", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User data = document.toObject(User.class);
                                    if (!data.type.equals("admin")) {
                                        DataList.add(data);
                                    }
                                    txt1.setVisibility(View.GONE);
                                }

                                if (DataList.size() == 0) {
                                    txt1.setVisibility(View.VISIBLE);
                                    return;
                                }
                                Adapter = new UserFragment_Adapter(DataList, getActivity());
                                recyclerView.setAdapter(Adapter);
                            } else {
                                Toast.makeText(getActivity(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
        } else {
            db.collection("user")
                    .orderBy("name")
                    .startAt(keyword)
                    .endAt(keyword + "\uf8ff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User data = document.toObject(User.class);
                                    if (!data.type.equals("admin")) {
                                        DataList.add(data);
                                    }
                                    txt1.setVisibility(View.GONE);
                                }

                                if (DataList.size() == 0) {
                                    txt1.setVisibility(View.VISIBLE);
                                    //Toast.makeText(getActivity(), "ไม่มีข้อมูลการประกาศ", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Adapter = new UserFragment_Adapter(DataList, getActivity());
                                recyclerView.setAdapter(Adapter);
                            } else {
                                Toast.makeText(getActivity(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
        }
    }

    private class UserFragment_Adapter extends RecyclerView.Adapter<UserFragment_Adapter.ViewHolder> {
        private List<User> DataList;
        private Context context;

        public UserFragment_Adapter(List<User> DataList, FragmentActivity context) {
            this.DataList = DataList;
            this.context = context;
        }

        @Override
        public UserFragment_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);
            return new UserFragment_Adapter.ViewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final UserFragment_Adapter.ViewHolder holder, final int position) {
            final User model = DataList.get(position);

            if (model.getPhoto().equals("")) {
                holder.txt_short_name.setText(model.getName().substring(0, 1));
                holder.img_user.setVisibility(View.GONE);
                holder.txt_short_name.setVisibility(View.VISIBLE);
            } else {
                holder.img_user.setVisibility(View.VISIBLE);
                holder.txt_short_name.setVisibility(View.GONE);

                Glide.with(getActivity())
                        .load(model.getPhoto())
                        .centerCrop()
                        .into(holder.img_user);
            }

            holder.txt_name.setText(model.getName());
            holder.txt_email.setText(model.getEmail());
            holder.txt_age.setText("Age : " + model.getAge());

            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tt = new Intent(context, UserDetailActivity.class);
                    tt.putExtra("user_id", model.getUser_id());
                    tt.putExtra("email", model.getEmail());
                    tt.putExtra("password", model.getPassword());
                    tt.putExtra("name", model.getName());
                    tt.putExtra("type", model.getType());
                    tt.putExtra("photo", model.getPhoto());
                    tt.putExtra("gender", model.getGender());
                    tt.putExtra("age", model.getAge());
                    tt.putExtra("corn", model.getCorn());
                    tt.putExtra("fluctose", model.getFluctose());
                    tt.putExtra("gluten", model.getGluten());
                    tt.putExtra("height", String.valueOf(model.getHeight()));
                    tt.putExtra("lactose", model.getLactose());
                    tt.putExtra("no_sugar", model.getNo_sugar());
                    tt.putExtra("nut", model.getNut());
                    tt.putExtra("shellfish", model.getShellfish());
                    tt.putExtra("vegan", model.getVegan());
                    tt.putExtra("weight", String.valueOf(model.getWeight()));
                    tt.putExtra("exercise", model.getExercise());
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
                    dialog.setMessage("Do you want to edit this account?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent tt = new Intent(getActivity(), UserEditActivity.class);
                            tt.putExtra("user_id", model.getUser_id());
                            tt.putExtra("email", model.getEmail());
                            tt.putExtra("password", model.getPassword());
                            tt.putExtra("name", model.getName());
                            tt.putExtra("type", model.getType());
                            tt.putExtra("photo", model.getPhoto());
                            tt.putExtra("gender", model.getGender());
                            tt.putExtra("age", model.getAge());
                            tt.putExtra("corn", model.getCorn());
                            tt.putExtra("fluctose", model.getFluctose());
                            tt.putExtra("gluten", model.getGluten());
                            tt.putExtra("lactose", model.getLactose());
                            tt.putExtra("no_sugar", model.getNo_sugar());
                            tt.putExtra("nut", model.getNut());
                            tt.putExtra("shellfish", model.getShellfish());
                            tt.putExtra("vegan", model.getVegan());
                            tt.putExtra("height", String.valueOf(model.getHeight()));
                            tt.putExtra("weight", String.valueOf(model.getWeight()));
                            tt.putExtra("exercise", model.getExercise());
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
            ImageView img_user;
            TextView txt_short_name, txt_name, txt_email, txt_age;

            // ประกาศตัวแปร
            ViewHolder(View itemView) {
                super(itemView);
                card_view = itemView.findViewById(R.id.card_view);
                img_user = itemView.findViewById(R.id.img_user);
                txt_short_name = itemView.findViewById(R.id.txt_short_name);
                txt_name = itemView.findViewById(R.id.txt_name);
                txt_email = itemView.findViewById(R.id.txt_email);
                txt_age = itemView.findViewById(R.id.txt_age);
            }
        }
    }
}