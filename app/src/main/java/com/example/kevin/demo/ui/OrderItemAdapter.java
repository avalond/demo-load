package com.example.kevin.demo.ui;

import com.example.kevin.demo.R;

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

  private Context mContext;


  public OrderItemAdapter(Context context) {
    this.mContext = context;
  }


  @Override public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
    return new OrderItemViewHolder(view);
  }


  @Override public void onBindViewHolder(OrderItemViewHolder holder, int position) {

  }


  @Override public int getItemCount() {
    return 0;
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
