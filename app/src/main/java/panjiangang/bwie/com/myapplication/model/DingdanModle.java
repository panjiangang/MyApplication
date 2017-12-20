package panjiangang.bwie.com.myapplication.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import panjiangang.bwie.com.myapplication.activity.zhuangtaiBean;
import panjiangang.bwie.com.myapplication.bean.liebiaoBean;

public class DingdanModle {

    Modleinterface modleinterface;
    DingdanOkHttpUtils okHttpUtils;
    private final Map<String, String> canshu;
    static int page = 1;
    private List<liebiaoBean.DataBean> data;

    public DingdanModle(Modleinterface modleinterface) {
        this.modleinterface = modleinterface;
        okHttpUtils = DingdanOkHttpUtils.getInstance();

        canshu = new HashMap<>();
        canshu.put("uid", "71");
        canshu.put("page", "" + page);
    }

    public void get() {
        okHttpUtils.get("getOrders", canshu, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                data = gson.fromJson(response.body().string(), liebiaoBean.class).getData();
                modleinterface.gethc(data);
            }
        });
    }

    public void setstatu(String i) {
        canshu.put("status", i);
        canshu.put("page", "1");
        okHttpUtils.get("getOrders", canshu, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                data = gson.fromJson(response.body().string(), liebiaoBean.class).getData();
                modleinterface.gethc(DingdanModle.this.data);
            }
        });
    }

    public void fresh() {
        page = 1;
        canshu.put("page", "" + page);
        okHttpUtils.get("getOrders", canshu, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                data = gson.fromJson(response.body().string(), liebiaoBean.class).getData();
                modleinterface.gethc(data);
            }
        });
    }

    public void load() {
        page += page;
        canshu.put("page", "" + page);
        okHttpUtils.get("getOrders", canshu, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                List<liebiaoBean.DataBean> list = gson.fromJson(response.body().string(), liebiaoBean.class).getData();
                for (int j = 0; j < list.size(); j++) {
                    data.add(list.get(j));
                }
                modleinterface.gethc(data);
            }
        });
    }

    public void setdd(int orderid, int status) {
        canshu.put("orderid", "" + orderid);
        canshu.put("status", "2");
        canshu.remove("page");
        okHttpUtils.get("updateOrder", canshu, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String msg = gson.fromJson(response.body().string(), zhuangtaiBean.class).getMsg();
                modleinterface.ddhc(msg);
            }
        });
        canshu.remove("status");
    }

    //jieokou
    public interface Modleinterface {
        void gethc(List<liebiaoBean.DataBean> data);

        void ddhc(String msg);
    }
}
