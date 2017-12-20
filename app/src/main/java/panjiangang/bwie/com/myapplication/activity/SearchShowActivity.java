package panjiangang.bwie.com.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import panjiangang.bwie.com.myapplication.R;
import panjiangang.bwie.com.myapplication.bean.SearchBean;

public class SearchShowActivity extends AppCompatActivity {

    @BindView(R.id.searchshow_imgbtn_return)
    ImageButton searchshowImgbtnReturn;
    @BindView(R.id.searchshow_edit_sousuo)
    EditText searchshowEditSousuo;
    @BindView(R.id.searchshow_btn_sousuo)
    Button searchshowBtnSousuo;
    @BindView(R.id.searchshow_xRecyclerView)
    XRecyclerView searchshowXRecyclerView;
    String menu;
    private Gson gson = new Gson();
    private int page = 1;
    String path;
    private List<SearchBean.DataBean> list = new ArrayList<>();
    boolean ss = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_show);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        menu = intent.getStringExtra("name");
        searchshowEditSousuo.setText(menu);
        Toast.makeText(SearchShowActivity.this, "" + menu, Toast.LENGTH_SHORT).show();
        LinearLayoutManager manager = new LinearLayoutManager(SearchShowActivity.this);
        searchshowXRecyclerView.setLayoutManager(manager);
        try {
            //http://120.27.23.105/product/searchProducts?keywords=%E7%AC%94%E8%AE%B0%E6%9C%AC&page=1
            path = "http://120.27.23.105/product/searchProducts?keywords=" + URLEncoder.encode(menu, "utf-8") + "&page=" + page + "&source=android";
            if (menu.equals("")) {
                Toast.makeText(SearchShowActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
            } else {
                new My().execute();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class My extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            String string = "";
            try {
                URL url = new URL(path);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                int code = con.getResponseCode();
                if (code == 200) {
                    InputStream is = con.getInputStream();
                    byte[] b = new byte[1024];
                    int length = 0;
                    while ((length = is.read(b)) != -1) {
                        String str = new String(b, 0, length);
                        string += str;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return string;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Toast.makeText(SearchShowActivity.this, "o"+o.toString(), Toast.LENGTH_SHORT).show();
            SearchBean fromJson = gson.fromJson((String) o, SearchBean.class);
            if (fromJson.getData().size() == 0) {
                Toast.makeText(SearchShowActivity.this, "暂时没有该类商品", Toast.LENGTH_SHORT).show();
            } else {
                list = fromJson.getData();
                final MyAdapter showAdapter = new MyAdapter();
                searchshowXRecyclerView.setAdapter(showAdapter);
                searchshowXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        showAdapter.notifyDataSetChanged();
                        searchshowXRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onLoadMore() {
                        page++;
                        new My().execute();
                        Toast.makeText(SearchShowActivity.this, "page = " + page, Toast.LENGTH_SHORT).show();
                        showAdapter.notifyDataSetChanged();
                        searchshowXRecyclerView.loadMoreComplete();
                    }
                });
            }
        }
    }


    @OnClick({R.id.searchshow_imgbtn_return, R.id.searchshow_btn_sousuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchshow_imgbtn_return:
                Intent intent1 = new Intent(SearchShowActivity.this, SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.searchshow_btn_sousuo:
                String s = searchshowEditSousuo.getText().toString().trim();
                Intent intent = new Intent(SearchShowActivity.this, SearchShowActivity.class);
                intent.putExtra("name", s);
                startActivity(intent);
                break;
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    SearchShowActivity.this).inflate(R.layout.item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.te.setText(list.get(position).getTitle());
            holder.te1.setText("价格:" + list.get(position).getPrice());
            String s = list.get(position).getImages().split("\\|")[0];
            Uri uri = Uri.parse(s);
            holder.img.setImageURI(uri);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchShowActivity.this, SpxqActivity.class);
                    intent.putExtra("spxq_pid", "" + list.get(position).getPid());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView te, te1;
            SimpleDraweeView img;

            public MyViewHolder(View itemView) {
                super(itemView);
                te = itemView.findViewById(R.id.item_te);
                te1 = itemView.findViewById(R.id.item_te1);
                img = itemView.findViewById(R.id.item_img);
            }
        }
    }


}

