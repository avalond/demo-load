package com.example.kevin.demo.ui;

import com.example.kevin.demo.R;
import com.example.kevin.demo.utils.LoggerUtils;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author by kevin.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
  private static final String TAG = OrderItemAdapter.class.getSimpleName();
  private Context mContext;
  private List<Map<String, String>> mapList;


  public OrderItemAdapter(Context context, List<Map<String, String>> mOrderMapList) {
    this.mContext = context;
    this.mapList = mOrderMapList;
  }


  @Override public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
    return new OrderItemViewHolder(view);
  }


  @Override public void onBindViewHolder(OrderItemViewHolder holder, int position) {
    // map.put("id", id);
    // map.put("orderId", orderId);
    // map.put("orderUUId", orderUUId);
    // map.put("orderType", orderType);
    // map.put("orderStatus", orderStatus);
    // map.put("orderNote", orderNote);

    holder.mOrderIdTv.setText(mapList.get(position).get("orderId"));
    holder.mOrderUUIdtV.setText(mapList.get(position).get("orderUUId"));
    holder.mTypeTv.setText(mapList.get(position).get("orderType"));
    holder.mOrderStatusTv.setText(mapList.get(position).get("orderStatus"));
    holder.mOrderNoteTv.setText(mapList.get(position).get("orderNote"));
  }


  @Override public int getItemCount() {
    LoggerUtils.e(TAG, "order list size---->" + mapList.size());
    return mapList.size();
  }


  public class OrderItemViewHolder extends RecyclerView.ViewHolder {
    private TextView mOrderIdTv;
    private TextView mOrderUUIdtV;
    private TextView mTypeTv;
    private TextView mOrderStatusTv;
    private TextView mOrderNoteTv;


    public OrderItemViewHolder(View itemView) {
      super(itemView);
      mOrderIdTv = (TextView) itemView.findViewById(R.id.order);
      mOrderUUIdtV = (TextView) itemView.findViewById(R.id.orderUuid);
      mTypeTv = (TextView) itemView.findViewById(R.id.type);
      mOrderStatusTv = (TextView) itemView.findViewById(R.id.order_status);
      mOrderNoteTv = (TextView) itemView.findViewById(R.id.order_note);

    }
  }
}
