package s2017s40.kr.hs.mirim.seoulapp_jsd;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<XmlDTO> mDataset;
    private ClickCallback callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView mShltrNm, mRdnmadr, mDistan;
        public ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mShltrNm = (TextView)view.findViewById(R.id.item_main_title_text);
            mRdnmadr = (TextView)view.findViewById(R.id.item_main_addr_text);
            mDistan = (TextView)view.findViewById(R.id.item_main_dist_text);
            mImageView = view.findViewById(R.id.item_main_image);
        }
    }
    public MainAdapter(ArrayList<XmlDTO> myDataset, ClickCallback clickCallback) {
        mDataset = myDataset;
        this.callback = clickCallback;
    }
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recycler_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mShltrNm.setText(mDataset.get(position).getShltrNm());
        holder.mRdnmadr.setText(mDataset.get(position).getRdnmadr());
        holder.mDistan.setText(mDataset.get(position).getLatitude());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public interface ClickCallback {
        void onItemClick(int position);
    }
}