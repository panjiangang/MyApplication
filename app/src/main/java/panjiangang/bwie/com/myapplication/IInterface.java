package panjiangang.bwie.com.myapplication;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import panjiangang.bwie.com.myapplication.bean.ClassifyBean;
import panjiangang.bwie.com.myapplication.bean.ClassifyBeanBean;
import panjiangang.bwie.com.myapplication.bean.HomePageBean;
import panjiangang.bwie.com.myapplication.bean.SpxqBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2017/12/11.
 */

public interface IInterface {
    @GET("/ad/getAd")
//首页
    Observable<HomePageBean> get();//@Path("user")String user

    //http://120.27.23.105/product/getProductDetail?pid=45&source=android
    @GET("/product/getProductDetail")
//商品详情
    Observable<SpxqBean> get1(@Query("pid") String pid, @Query("source") String source);//@Path("user")String user

    //    http://120.27.23.105/product/getCatagory
    @GET("/product/getCatagory")//商品分类
    Call<ClassifyBean> get2();

    //http://120.27.23.105/product/getProductCatagory?cid=1&source=android
    @GET("/product/getProductCatagory")//商品子分类
    Call<ClassifyBeanBean> get3(@Query("cid") String cid,@Query("source") String source);
}


