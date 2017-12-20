package panjiangang.bwie.com.myapplication.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import panjiangang.bwie.com.myapplication.IInterface;
import panjiangang.bwie.com.myapplication.R;
import panjiangang.bwie.com.myapplication.bean.ClassifyBean;
import panjiangang.bwie.com.myapplication.bean.ClassifyBeanBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2017/12/11.
 */

public class Classify extends Fragment {
    ListView lv;
    ListView lv1;
    private List<ClassifyBean.DataBean> list = new ArrayList<>();
    private List<ClassifyBeanBean.DataBean.ListBean> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_classify, null);
        lv = view.findViewById(R.id.classify_lv);
        lv1 = view.findViewById(R.id.classify_lv1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        http://120.27.23.105/product/getCatagory
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.27.23.105")
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IInterface iInterface = retrofit.create(IInterface.class);
        Call<ClassifyBean> call = iInterface.get2();

        call.enqueue(new Callback<ClassifyBean>() {
            @Override
            public void onResponse(Call<ClassifyBean> call, Response<ClassifyBean> response) {
                ClassifyBean bean = response.body();
                System.out.println(Thread.currentThread().getName());
                list = bean.getData();
                System.out.println(list.toString());

                MyAdapter adapter = new MyAdapter();
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String id1 = "" + list.get(position).getCid();
                        gethttp(id1);
                    }
                });
            }

            @Override
            public void onFailure(Call<ClassifyBean> call, Throwable t) {
                System.out.println("失败");
            }
        });
    }

    private void gethttp(String s) {
        //http://120.27.23.105/product/getProductCatagory?cid=1&source=android
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.27.23.105")
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IInterface iInterface = retrofit.create(IInterface.class);
        //http://m.yunifang.com/yunifang/mobile/goods/getall?random=91873&encode=68301f6ea0d6adcc0fee63bd21815d69&category_id=9
        Call<ClassifyBeanBean> call = iInterface.get3(s, "android");
        call.enqueue(new Callback<ClassifyBeanBean>() {
            @Override
            public void onResponse(Call<ClassifyBeanBean> call, Response<ClassifyBeanBean> response) {
                ClassifyBeanBean bean = response.body();
                if (bean.getData().size()==0) {
                    Toast.makeText(getActivity(), "暂无该类", Toast.LENGTH_SHORT).show();
                } else {
                    data = bean.getData().get(0).getList();
                    MyAdapter1 adapter = new MyAdapter1();
                    lv1.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ClassifyBeanBean> call, Throwable t) {
                System.out.println("失败");
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Hand hand;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.layout_text, null);
                hand = new Hand();
                hand.te = convertView.findViewById(R.id.text_te);
                hand.sdv = convertView.findViewById(R.id.text_sdv);
                convertView.setTag(hand);
            } else {
                hand = (Hand) convertView.getTag();
            }

            Uri uri = Uri.parse(list.get(position).getIcon());
            hand.sdv.setImageURI(uri);

            hand.te.setText(list.get(position).getName());
            return convertView;
        }
    }

    class Hand {
        TextView te;
        SimpleDraweeView sdv;
    }

    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Hand1 hand;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item, null);
                hand = new Hand1();
                hand.te = convertView.findViewById(R.id.item_te);
                hand.te1 = convertView.findViewById(R.id.item_te1);
                hand.img = convertView.findViewById(R.id.item_img);
                convertView.setTag(hand);
            } else {
                hand = (Hand1) convertView.getTag();
            }

            hand.te.setText(data.get(position).getName());
            hand.te1.setText("" + data.get(position).getPscid());

            Uri uri = Uri.parse(data.get(position).getIcon());
            hand.img.setImageURI(uri);
            return convertView;
        }
    }

    class Hand1 {
        TextView te, te1;
        SimpleDraweeView img;
    }

}




