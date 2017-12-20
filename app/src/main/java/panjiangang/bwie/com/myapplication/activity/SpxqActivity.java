package panjiangang.bwie.com.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import panjiangang.bwie.com.myapplication.IInterface;
import panjiangang.bwie.com.myapplication.R;
import panjiangang.bwie.com.myapplication.bean.AddCartBean;
import panjiangang.bwie.com.myapplication.bean.SpxqBean;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpxqActivity extends Activity {

    @BindView(R.id.spxq_sdv)
    Banner spxqSdv;
    @BindView(R.id.spxq_shopname)
    TextView spxqShopname;
    @BindView(R.id.spxq_jieshao)
    TextView spxqJieshao;
    @BindView(R.id.spxq_price)
    TextView spxqPrice;
    @BindView(R.id.spxq_addshopcar)
    Button spxqAddshopcar;
    String spxq_pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spxq);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        spxq_pid = intent.getStringExtra("spxq_pid");
        //http://120.27.23.105/product/getProductDetail?pid=45&source=android
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.27.23.105")
                .addConverterFactory(GsonConverterFactory.create())
                // call 转化成 Observerable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        IInterface iInterface = retrofit.create(IInterface.class);

        iInterface.get1(spxq_pid, "android")
                // 指定 被观察者 所在一个IO线程
                .subscribeOn(Schedulers.io())
                //指定观察者所在 主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SpxqBean>() {
                    @Override
                    public void accept(SpxqBean bean) throws Exception {
                        //Toast.makeText(SpxqActivity.this, "" + bean.getData().getTitle(), Toast.LENGTH_SHORT).show();
                        String[] strings = bean.getData().getImages().split("\\|");
                        final ArrayList<String> imas = new ArrayList<>();
                        for (int i = 0; i < strings.length; i++) {
                            imas.add(strings[i]);
                        }
                        spxqSdv.setImageLoader(new ImageLoader() {
                            @Override
                            public void displayImage(Context context, Object path, ImageView imageView) {
                                imageView.setImageURI(Uri.parse((String) path));
                            }

                            @Override
                            public ImageView createImageView(Context context) {
                                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
                                return simpleDraweeView;
                            }
                        });
                        spxqSdv.setImages(imas);
                        spxqSdv.start();

                        spxqShopname.setText(bean.getData().getTitle());

                        spxqJieshao.setText(bean.getData().getSubhead());

                        spxqPrice.setText("价格:" + bean.getData().getPrice());
                    }
                });
    }

    @OnClick(R.id.spxq_addshopcar)
    public void onViewClicked() {
        //http://120.27.23.105/product/addCart?uid=81&pid=10&source=android
        gethttp();
    }

    private void gethttp() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://120.27.23.105/product/addCart?uid=81&pid=" + spxq_pid + "&source=android")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                final String msg = gson.fromJson(response.body().string(), AddCartBean.class).getMsg();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SpxqActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
