package panjiangang.bwie.com.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import panjiangang.bwie.com.myapplication.R;
import panjiangang.bwie.com.myapplication.bean.liebiaoBean;
import panjiangang.bwie.com.myapplication.presenter.DingdanPresenter;


public class LoginSuccessActivity extends Activity implements DingdanPresenter.Preinterface {

    @BindView(R.id.xiala)
    SimpleDraweeView xiala;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.xl)
    LinearLayout xl;
    private DingdanPresenter mainPresenter;
    List<liebiaoBean.DataBean> data;
    private MyAdapter myAdapter;
    boolean pd = false;
    SharedPreferences sp;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fresco图片框架,初始化
        Fresco.initialize(this);
        setContentView(R.layout.activity_login_success);
        ButterKnife.bind(this);
        Button homepage = findViewById(R.id.success_homepage);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSuccessActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        sp = getSharedPreferences("SharedPreferences", MODE_APPEND);
        //boolean b = sp.getBoolean("b", true);

        xiala.setImageURI("res://" + getPackageName() + "/" + R.drawable.lv_icon);
        xl.setVisibility(View.GONE);
        sv.setHeader(new DefaultHeader(this));
        sv.setFooter(new DefaultFooter(this));
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.setpage(true);
            }

            @Override
            public void onLoadmore() {
                mainPresenter.setpage(false);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(LoginSuccessActivity.this, LinearLayoutManager.VERTICAL, false));

        mainPresenter = new DingdanPresenter(this);
        mainPresenter.setstatus(null);
    }

    @OnClick({R.id.xiala, R.id.dai, R.id.zhifu, R.id.quxiao, R.id.dai1, R.id.zhifu1, R.id.quxiao1, R.id.tcdl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xiala:
                pd = !pd;
                if (pd) {
                    xl.setVisibility(View.VISIBLE);
                } else {
                    xl.setVisibility(View.GONE);
                }
                break;
            case R.id.dai:
                mainPresenter.setstatus("0");
                break;
            case R.id.zhifu:
                mainPresenter.setstatus("1");
                break;
            case R.id.quxiao:
                mainPresenter.setstatus("2");
                break;
            case R.id.dai1:
                mainPresenter.setstatus("0");
                break;
            case R.id.zhifu1:
                mainPresenter.setstatus("1");
                break;
            case R.id.quxiao1:
                mainPresenter.setstatus("2");
                break;
            case R.id.tcdl:
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("Shared", false);
                edit.commit();
                Intent intent = new Intent(this, Main2Activity.class);
                intent.putExtra("reg", true);
                startActivity(intent);
                break;
        }
    }

    //huidiao
    @Override
    public void gethcc(List<liebiaoBean.DataBean> data) {
        this.data = data;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (myAdapter == null) {
                    myAdapter = new MyAdapter();
                    rv.setAdapter(myAdapter);
                } else {
                    myAdapter.notifyDataSetChanged();
                }
                sv.onFinishFreshAndLoad();
            }
        });
    }

    @Override
    public void dingdanhc(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginSuccessActivity.this, msg, Toast.LENGTH_SHORT).show();
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void status0(TextView status, Button dingdan) {
        status.setText("待支付");
        status.setTextColor(Color.RED);
        dingdan.setText("取消订单");
    }

    @Override
    public void status1(TextView status, Button dingdan) {
        status.setText("已支付");
        status.setTextColor(Color.BLACK);
        dingdan.setText("查看订单");
    }

    @Override
    public void status2(TextView status, Button dingdan) {
        status.setText("已取消");
        status.setTextColor(Color.BLACK);
        dingdan.setText("查看订单");
    }

    //shipei
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.title.setText(data.get(position).getTitle());
            holder.createtime.setText("创建时间" + data.get(position).getCreatetime());
            holder.price.setText("价格:" + data.get(position).getPrice());

            mainPresenter.getstatus(holder.status, holder.dingdan, data.get(position).getStatus());

            holder.dingdan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginSuccessActivity.this)
                            .setTitle("提示")
                            .setMessage("确定取消订单吗?")
                            .setPositiveButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setNegativeButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mainPresenter.setdingdan(data.get(position).getOrderid(), data.get(position).getStatus());
                                }
                            });
                    builder.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.status)
            TextView status;
            @BindView(R.id.price)
            TextView price;
            @BindView(R.id.createtime)
            TextView createtime;
            @BindView(R.id.dingdan)
            Button dingdan;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
