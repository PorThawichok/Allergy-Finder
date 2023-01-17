package com.fluke.allergyfinder.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {



    public static Fragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        AppPreferences appPreferences = new AppPreferences(getActivity());
        String User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);
        String Name = appPreferences.getStringPrefs(AppPreferences.KEY_NAME);
        String Photo = appPreferences.getStringPrefs(AppPreferences.KEY_PHOTO);
        String Email = appPreferences.getStringPrefs(AppPreferences.KEY_EMAIL);

//        viewPager = view.findViewById(R.id.ViewPager);
//        sliderDotspanel = view.findViewById(R.id.SliderDots);
//
//        try {
//            JSONObject obj = new JSONObject(loadJSONFromAsset());
//            JSONArray userArray = obj.getJSONArray("banner");
//            for (int i = 0; i < userArray.length(); i++) {
//                JSONObject userDetail = userArray.getJSONObject(i);
//                id.add(userDetail.getString("id"));
//                image.add(userDetail.getString("image"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        viewPagerAdapter = new ViewPagerAdapter(getActivity(), image);
//        viewPager.setAdapter(viewPagerAdapter);
//        sliderDotspanel.setViewPager(viewPager);

//        db = FirebaseFirestore.getInstance();
//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        txt1 = view.findViewById(R.id.txt1);
//        DataList = new ArrayList<>();
//
//        Load_Data("");
        return view;
    }

//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getActivity().getAssets().open("banner.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//
//    public class ViewPagerAdapter extends PagerAdapter {
//        private Context context;
//        private LayoutInflater layoutInflater;
//        private ArrayList<String> image;
//
//        public ViewPagerAdapter(Context context, ArrayList<String> image) {
//            this.context = context;
//            this.image = image;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, final int position) {
//            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            //View view = view_array[position];
//            View view = layoutInflater.inflate(R.layout.list_banner, null);
//
//            CardView layout_cardview = view.findViewById(R.id.layout_cardview);
//            ImageView img_infographic = view.findViewById(R.id.img_infographic);
//
//            img_infographic.setImageResource(getImageId(context, image.get(position)));
//            layout_cardview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, image.get(position), Toast.LENGTH_SHORT).show();
////                    Intent tt = new Intent(getActivity(), YoutubeActivity.class);
////                    tt.putExtra("url", model.getUrl());
////                    startActivity(tt);
//                }
//            });
//
//            ViewPager vp = (ViewPager) container;
//            vp.addView(view, 0);
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return image.size();
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            ViewPager vp = (ViewPager) container;
//            View view = (View) object;
//            vp.removeView(view);
//        }
//    }
//
//    public static int getImageId(Context context, String imageName) {
//        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
//    }
//
//    private void Load_Data(String keyword) {
//        DataList.clear();
//        recyclerView.requestLayout();
//        if (keyword.equals("")) {
//            db.collection("product")
//                    .orderBy("dateTime")
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
//                                    return;
//                                }
//                                Adapter = new HomeFragment_Adapter(DataList, getActivity());
//                                recyclerView.setAdapter(Adapter);
//                            } else {
//                                Toast.makeText(getActivity(), "เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        }
//                    });
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
//                                Adapter = new HomeFragment_Adapter(DataList, getActivity());
//                                recyclerView.setAdapter(Adapter);
//                            } else {
//                                Toast.makeText(getActivity(), "เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        }
//                    });
//        }
//    }
//
//    private class HomeFragment_Adapter extends RecyclerView.Adapter<HomeFragment_Adapter.ViewHolder> {
//        private List<Product> DataList;
//        private Context context;
//
//        public HomeFragment_Adapter(List<Product> DataList, FragmentActivity context) {
//            this.DataList = DataList;
//            this.context = context;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);
//            return new ViewHolder(v);
//        }
//
//        @SuppressLint("SetTextI18n")
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, final int position) {
//            final Product model = DataList.get(position);
//
//            Glide.with(getActivity())
//                    .load(model.getPhoto())
//                    .centerCrop()
//                    .into(holder.img_card);
//
//            holder.txt_name.setText(model.getName());
//
//            holder.card_view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent tt = new Intent(context, ProductDetailActivity.class);
//                    tt.putExtra("product_id", model.getProduct_id());
//                    tt.putExtra("price", String.valueOf(model.getPrice()));
//                    tt.putExtra("name", model.getName());
//                    tt.putExtra("amount", model.getAmount());
//                    tt.putExtra("detail", model.getDetail());
//                    tt.putExtra("photo", model.getPhoto());
//                    tt.putExtra("type", model.getType());
//                    startActivity(tt);
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return DataList.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//
//            CardView card_view;
//            ImageView img_card;
//            TextView txt_name;
//
//            // ประกาศตัวแปร
//            ViewHolder(View itemView) {
//                super(itemView);
//                card_view = itemView.findViewById(R.id.card_view);
//                img_card = itemView.findViewById(R.id.img_card);
//                txt_name = itemView.findViewById(R.id.txt_name);
//            }
//        }
//    }
}