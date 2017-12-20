package panjiangang.bwie.com.myapplication.presenter;

import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import panjiangang.bwie.com.myapplication.bean.liebiaoBean;
import panjiangang.bwie.com.myapplication.model.DingdanModle;


public class DingdanPresenter implements DingdanModle.Modleinterface {

    private final DingdanModle mainModle;
    Preinterface preinterface;

    public DingdanPresenter(Preinterface preinterface) {
        this.preinterface = preinterface;
        mainModle = new DingdanModle(this);
    }

    public void setstatus(String i) {
        if (i != null) {
            mainModle.setstatu(i);
        } else {
            mainModle.get();
        }
    }

    public void setdingdan(int orderid, int status) {
        mainModle.setdd(orderid, status);
    }


    public void getstatus(TextView status, Button dingdan, int i) {
        if (i == 0) {
            preinterface.status0(status, dingdan);
        } else if (i == 1) {
            preinterface.status1(status, dingdan);
        } else if (i == 2) {
            preinterface.status2(status, dingdan);
        }
    }

    public void setpage(boolean b) {
        if (b) {
            mainModle.fresh();
        } else {
            mainModle.load();
        }
    }

    //huidiao
    @Override
    public void gethc(List<liebiaoBean.DataBean> data) {
        preinterface.gethcc(data);
    }

    @Override
    public void ddhc(String msg) {
        preinterface.dingdanhc(msg);
    }

    public interface Preinterface {
        void gethcc(List<liebiaoBean.DataBean> data);

        void status0(TextView status, Button dingdan);

        void status1(TextView status, Button dingdan);

        void status2(TextView status, Button dingdan);

        void dingdanhc(String msg);
    }
}
