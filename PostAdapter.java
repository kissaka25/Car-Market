package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Models.ListItem;
import com.ken.cars.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<ListItem> listItems;
    Context context;
    private PostAdapter.OnItemListener onItemListener;

    public PostAdapter(List<ListItem> listItems, Context context, OnItemListener onItemListener) {
        this.listItems = listItems;
        this.context = context;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);

        holder.tvHead.setText(listItem.getHead());
        holder.tvDesc.setText(listItem.getDesc());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvHead;
        public TextView tvDesc;
        OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener onItemListener) {
            super(itemView);

            tvHead = (TextView) itemView.findViewById(R.id.tvPostHead);
            tvDesc = (TextView) itemView.findViewById(R.id.tvPostDesc);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}
