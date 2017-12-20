package panjiangang.bwie.com.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import butterknife.BindView;
import panjiangang.bwie.com.myapplication.R;
import panjiangang.bwie.com.myapplication.bean.liebiaoBean;
import panjiangang.bwie.com.myapplication.presenter.DingdanPresenter;
import panjiangang.bwie.com.myapplication.presenter.LoginPresenter;

public class RegActivity extends Activity implements LoginPresenter.MainPresenter {
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
    private LoginSuccessActivity.MyAdapter myAdapter;
    boolean pd = false;
    private EditText rphone, rpassword;
    private Button lreg;
    private LoginPresenter loginPresenter;
    private EditText phone, password;
    private Button reg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        rphone = findViewById(R.id.reg_phone);
        rpassword = findViewById(R.id.reg_password);
        lreg = findViewById(R.id.reg_reg);
        loginPresenter = new LoginPresenter(this);

        lreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.loginClick("reg", rphone.getText().toString(), rpassword.getText().toString());
            }
        });
    }

    @Override
    public void lClick(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        if (msg.equals("注册成功")) {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("reg", true);
            startActivity(intent);
        }
    }

    @Override
    public void rClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

