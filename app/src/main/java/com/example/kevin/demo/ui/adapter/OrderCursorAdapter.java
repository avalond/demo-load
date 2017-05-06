package com.example.kevin.demo.ui.adapter;

import com.example.kevin.demo.R;
import com.example.kevin.demo.database.LokobeeDatabaseTable;
import com.example.kevin.demo.services.LokobeeService;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author by kevin.
 */

public class OrderCursorAdapter extends BaseCursorRecyclerViewAdapter<OrderCursorAdapter.OrderCursorItemViewHolder> {
  private LokobeeService.myLocalIbind mLocalIbind;


  public OrderCursorAdapter(Context context, ItemClickListener listener, LokobeeService.myLocalIbind localIbind) {
    super(context, null, listener);
    this.mLocalIbind = localIbind;
  }


  @Override public OrderCursorItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
    return new OrderCursorItemViewHolder(view);
  }


  @Override public int getItemCount() {
    return super.getItemCount();
  }


  @Override public void onBindViewHolder(OrderCursorItemViewHolder viewHolder, final Cursor cursor) {
    cursor.moveToPosition(cursor.getPosition());
    viewHolder.setData(cursor);
    // viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
    //   @Override public void onClick(View v) {
    //     String orderId = cursor.getString(cursor.getColumnIndex(LokobeeDatabaseTable.COLUMN_ORDER_ID));
    //     mLocalIbind.getLokobeeService().deleteOrderWithId(orderId);
    //   }
    // });
  }


  @Override public int getItemViewType(int position) {
    return super.getItemViewType(position);
  }


  public class OrderCursorItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mOrderIdTv;
    private TextView mOrderStatusTv;
    private TextView mOrderNoteTv;
    public Button mButton;


    public OrderCursorItemViewHolder(View itemView) {
      super(itemView);
      mOrderIdTv = (TextView) itemView.findViewById(R.id.order);
      mOrderStatusTv = (TextView) itemView.findViewById(R.id.order_status);
      mOrderNoteTv = (TextView) itemView.findViewById(R.id.order_note);
      mButton = (Button) itemView.findViewById(R.id.delete);
      mButton.setOnClickListener(this);
    }


    public void setData(Cursor data) {

      mOrderIdTv.setText(data.getString(data.getColumnIndex(LokobeeDatabaseTable.COLUMN_ORDER_ID)));
      mOrderStatusTv.setText(data.getString(data.getColumnIndex(LokobeeDatabaseTable.COLUMN_ORDER_STATUS)));
      mOrderNoteTv.setText(data.getString(data.getColumnIndex(LokobeeDatabaseTable.COLUMN_ORDER_NOTE)));
    }


    @Override public void onClick(View v) {
      if (v == mButton) {
        mListener.onClick(this, v, getLayoutPosition());
      }
    }
  }
}
