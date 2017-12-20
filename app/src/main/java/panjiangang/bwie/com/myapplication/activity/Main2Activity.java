package panjiangang.bwie.com.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import panjiangang.bwie.com.myapplication.R;

import panjiangang.bwie.com.myapplication.fragment.Classify;
import panjiangang.bwie.com.myapplication.fragment.HomePage;
import panjiangang.bwie.com.myapplication.fragment.Person;
import panjiangang.bwie.com.myapplication.fragment.ShopCar;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.homePage)
    RadioButton homePage;
    @BindView(R.id.classify)
    RadioButton classify;
    @BindView(R.id.shopcar)
    RadioButton shopcar;
    @BindView(R.id.person)
    RadioButton person;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    boolean a = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        a = intent.getBooleanExtra("reg", a);
        if (a) {
            Person hp = new Person();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, hp);
            fragmentTransaction.commit();
        } else {
            HomePage hp = new HomePage();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, hp);
            fragmentTransaction.commit();
        }

    }

    @OnClick(R.id.homePage)
    public void onHomePageClicked() {
        HomePage hp = new HomePage();
        homePage.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_homepage02), null, null);
        classify.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_tao), null, null);
        shopcar.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_shopcar), null, null);
        person.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_person), null, null);

        homePage.setTextColor(Color.YELLOW);
        classify.setTextColor(Color.BLACK);
        shopcar.setTextColor(Color.BLACK);
        person.setTextColor(Color.BLACK);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.classify)
    public void onClassifyClicked() {
        Classify hp = new Classify();
        homePage.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_homepage), null, null);
        classify.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_tao02), null, null);
        shopcar.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_shopcar), null, null);
        person.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_person), null, null);

        homePage.setTextColor(Color.BLACK);
        classify.setTextColor(Color.YELLOW);
        shopcar.setTextColor(Color.BLACK);
        person.setTextColor(Color.BLACK);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.shopcar)
    public void onShoppingcartClicked() {
        ShopCar hp = new ShopCar();
        homePage.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_homepage), null, null);
        classify.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_tao), null, null);
        shopcar.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_shopcar02), null, null);
        person.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_person), null, null);

        homePage.setTextColor(Color.BLACK);
        classify.setTextColor(Color.BLACK);
        shopcar.setTextColor(Color.YELLOW);
        person.setTextColor(Color.BLACK);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.person)
    public void onPersonClicked() {
        Person hp = new Person();
        homePage.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_homepage), null, null);
        classify.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_tao), null, null);
        shopcar.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_shopcar), null, null);
        person.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_person02), null, null);

        homePage.setTextColor(Color.BLACK);
        classify.setTextColor(Color.BLACK);
        shopcar.setTextColor(Color.BLACK);
        person.setTextColor(Color.YELLOW);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }
}




