//package panjiangang.bwie.com.myapplication.view;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.facebook.drawee.view.SimpleDraweeView;
//
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import panjiangang.bwie.com.myapplication.R;
//import panjiangang.bwie.com.myapplication.bean.HomePageBean;
//
//
//class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
//
//    private List<HomePageBean.TuijianBean.ListBean> listBeans;
//
//    RvAdapter(List<HomePageBean.TuijianBean.ListBean> listBeans) {
//        this.listBeans = listBeans;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hd_rv_item, null);
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.goodsImage.setImageURI(goodsList.get(position).getGoodsImage());
//        holder.goodsName.setText(goodsList.get(position).getGoodsName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return goodsList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.goodsImage)
//        SimpleDraweeView goodsImage;
//        @BindView(R.id.goodsName)
//        TextView goodsName;
//
//        ViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }
//}
