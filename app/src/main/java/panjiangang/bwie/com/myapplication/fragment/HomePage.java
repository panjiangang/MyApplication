package panjiangang.bwie.com.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import panjiangang.bwie.com.myapplication.IInterface;
import panjiangang.bwie.com.myapplication.R;
import panjiangang.bwie.com.myapplication.activity.SearchActivity;
import panjiangang.bwie.com.myapplication.activity.SpxqActivity;
import panjiangang.bwie.com.myapplication.activity.WebActivity;
import panjiangang.bwie.com.myapplication.bean.HomePageBean;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePage extends Fragment {

    XRecyclerView xRecyclerView;
    List<HomePageBean.DataBean> dataBean = new ArrayList<>();
    List<HomePageBean.TuijianBean.ListBean> listBean = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_homepage, null);
        xRecyclerView = view.findViewById(R.id.xRecyclerView);
        ImageView camera = view.findViewById(R.id.homepage_iv_camera);
        ImageView shaoyishao = view.findViewById(R.id.homepage_iv_shaoyishao);
        EditText search = view.findViewById(R.id.homepage_bt_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getActivity().startActivityForResult(intent, 1);
            }
        });

        shaoyishao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
            }
        });
        zip1();//rxjava 与 retrofit网络请求结合
        return view;
    }

    private void zip1() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        xRecyclerView.setLayoutManager(manager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.27.23.105")
                .addConverterFactory(GsonConverterFactory.create())
                // call 转化成 Observerable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        IInterface iInterface = retrofit.create(IInterface.class);
        iInterface.get()
                // 指定 被观察者 所在一个IO线程
                .subscribeOn(Schedulers.io())
                //指定观察者所在 主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomePageBean>() {
                    @Override
                    public void accept(HomePageBean bean) throws Exception {
                        dataBean = bean.getData();
                        listBean = bean.getTuijian().getList();
                        final MyAdapter adapter = new MyAdapter();
                        xRecyclerView.setAdapter(adapter);
                        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                            @Override
                            public void onRefresh() {
                                //刷新
                                zip1();
                                adapter.notifyDataSetChanged();
                                xRecyclerView.refreshComplete();
                            }

                            @Override
                            public void onLoadMore() {

                            }
                        });
                    }
                });
    }

    public class MyAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 0 : 1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_lunbo, null);
                return new ViewHolder(view);

            } else if (viewType == 1) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_huodong, null);
                return new ViewHolder1(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof ViewHolder) {
                List<String> images = new ArrayList<>();
                for (int i = 0; i < dataBean.size(); i++) {
                    images.add(dataBean.get(i).getIcon());
                }
                ((ViewHolder) holder).banner.setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        //用fresco加载图片简单用法，记得要写下面的createImageView方法
                        Uri uri = Uri.parse((String) path);
                        imageView.setImageURI(uri);
                    }

                    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
                    @Override
                    public ImageView createImageView(Context context) {
                        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
                        return new SimpleDraweeView(context);
                    }
                }).setImages(images).start();

            } else if (holder instanceof ViewHolder1) {

                ((ViewHolder1) holder).itemRv.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 2));
                ((ViewHolder1) holder).itemRv.setAdapter(new RvAdapter());
            }
        }

        @Override
        public int getItemCount() {
            return dataBean.size();
        }


        class ViewHolder1 extends RecyclerView.ViewHolder {
            @BindView(R.id.hd_te)
            TextView hdSdv;
            @BindView(R.id.item_rv)
            RecyclerView itemRv;

            ViewHolder1(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.banner)
            Banner banner;
            @BindView(R.id.read)
            Button read;
            @BindView(R.id.music)
            Button music;
            @BindView(R.id.movie)
            Button movie;
            @BindView(R.id.newlist)
            Button newlist;
            @BindView(R.id.yule)
            Button yule;
            @BindView(R.id.meishi)
            Button meishi;
            @BindView(R.id.lvyou)
            Button lvyou;
            @BindView(R.id.baidu)
            Button baidu;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

                read.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳到阅读页面
                        Intent read = new Intent(getActivity(), WebActivity.class);
                        read.putExtra("url", "https://yuedu.baidu.com/");
                        startActivity(read);
                    }
                });
                music.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳到音乐页面
                        Intent music = new Intent(getActivity(), WebActivity.class);
                        music.putExtra("url", "http://music.baidu.com/tag/%E6%B5%81%E8%A1%8C");
                        startActivity(music);
                    }
                });
                movie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳到电影页面
                        Intent movie = new Intent(getActivity(), WebActivity.class);
                        movie.putExtra("url", "http://v.baidu.com/movie/list/filter-true+order-hot+pn-1+channel-movie");
                        startActivity(movie);
                    }
                });
                newlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newlist = new Intent(getActivity(), WebActivity.class);
                        newlist.putExtra("url", "http://jian.news.baidu.com/");
                        startActivity(newlist);
                    }
                });
                yule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent yule = new Intent(getActivity(), WebActivity.class);
                        yule.putExtra("url", "http://top.baidu.com/buzz?b=618&c=9");
                        startActivity(yule);
                    }
                });
                meishi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent meishi = new Intent(getActivity(), WebActivity.class);
                        meishi.putExtra("url", "http://jingyan.baidu.com/meishi/");
                        startActivity(meishi);
                    }
                });
                lvyou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent lvyou = new Intent(getActivity(), WebActivity.class);
                        lvyou.putExtra("url", "https://lvyou.baidu.com/luguhu/local/");
                        startActivity(lvyou);
                    }
                });
                baidu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent baidu = new Intent(getActivity(), WebActivity.class);
                        baidu.putExtra("url", "https://www.baidu.com/");
                        startActivity(baidu);
                    }
                });
            }
        }
    }

    class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hd_rv_item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            String s = listBean.get(position).getImages().split("\\|")[0];
            Uri uri = Uri.parse(s);
            holder.goodsImage.setImageURI(uri);

            holder.goodsName.setText(listBean.get(position).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SpxqActivity.class);
                    intent.putExtra("spxq_pid", "" + listBean.get(position).getPid());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listBean.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.goodsImage)
            SimpleDraweeView goodsImage;
            @BindView(R.id.goodsName)
            TextView goodsName;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}




