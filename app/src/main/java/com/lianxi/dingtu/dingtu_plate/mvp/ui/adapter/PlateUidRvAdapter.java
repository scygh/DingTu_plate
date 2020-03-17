package com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lianxi.dingtu.dingtu_plate.R;

import java.util.List;

import android_serialport_api.ChangeTool;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description ：NetSettingRvAdapter
 * author : scy
 * email : 1797484636@qq.com
 * date : 2019/7/29 08:46
 */
public class PlateUidRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<String> rowsBeans;

    private OnMyItemClickListener myItemClickListener;

    public interface OnMyItemClickListener {
        void onItemLongClick(int positon);
    }

    public void setMyItemClickListener(OnMyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public PlateUidRvAdapter(Context context, List<String> rowsBeans) {
        this.context = context;
        this.rowsBeans = rowsBeans;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_uid)
        TextView tvUid;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_state)
        TextView tvState;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (myItemClickListener != null) {
                        myItemClickListener.onItemLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }

        public void bind() {
            tvNum.setText(getAdapterPosition() + 1 + "");
            tvUid.setText(rowsBeans.get(getAdapterPosition()).substring(0, 16));
            if (rowsBeans.get(getAdapterPosition()).length() == 16) {
                tvState.setText("已修改");
                tvPrice.setText("已修改");
                tvDate.setText("已修改");
            } else {
                int num = ChangeTool.HexToInt(rowsBeans.get(getAdapterPosition()).substring(16, 20));
                if (num != 0) {
                    tvState.setText("已写入 商品编号：" + num);
                    tvPrice.setText(ChangeTool.HexToInt(rowsBeans.get(getAdapterPosition()).substring(24, 30)) / 100.0 + "");
                    tvDate.setText(rowsBeans.get(getAdapterPosition()).substring(30, 32) + "年"
                            + rowsBeans.get(getAdapterPosition()).substring(32, 34) + "月"
                            + rowsBeans.get(getAdapterPosition()).substring(34, 36) + "日"
                            + rowsBeans.get(getAdapterPosition()).substring(36, 38) + "时");
                } else {
                    tvState.setText("已清空");
                    tvPrice.setText("已清空");
                    tvDate.setText("已清空");
                }

            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plate_uid_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind();
    }

    @Override
    public int getItemCount() {
        return rowsBeans != null ? rowsBeans.size() : 0;
    }

}
