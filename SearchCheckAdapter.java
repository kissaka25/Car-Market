package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import com.ken.cars.R;

public class SearchCheckAdapter extends RecyclerView.Adapter<SearchCheckAdapter.ViewHolder> {
    private List<String> listItems;
    private OnCheckListener mOnCheckListener;
    Context context;
    boolean[] checked;

    public SearchCheckAdapter(List<String> listItems, Context context, OnCheckListener onCheckListener, boolean[] checked) {
        this.listItems = listItems;
        this.context = context;
        this.mOnCheckListener = onCheckListener;
        this.checked = checked;
    }

    @NonNull
    @Override
    public SearchCheckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_check_airbag_item, parent, false);

        return new ViewHolder(v, mOnCheckListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchCheckAdapter.ViewHolder holder, final int position) {
        holder.tvItem.setText(listItems.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.check.performClick();
            }
        });

        holder.check.setChecked(checked[position]);

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOnCheckListener.onSearchCheck(position);
            }
        });
//        if (id == 1) {
//            if (position == 0)
//                holder.check.setChecked(SearchFragment.searchConditions.getFuel().isDiesel());
//            else if (position == 1)
//                holder.check.setChecked(SearchFragment.searchConditions.getFuel().isPetrol());
//            else if (position == 2)
//                holder.check.setChecked(SearchFragment.searchConditions.getFuel().isGas());
//            else if (position == 3)
//                holder.check.setChecked(SearchFragment.searchConditions.getFuel().isElectric());
//            else
//                holder.check.setChecked(SearchFragment.searchConditions.getFuel().isHybrid());
//
//            holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (position == 0)
//                        SearchFragment.searchConditions.getFuel().setDiesel(isChecked);
//                    else if (position == 1)
//                        SearchFragment.searchConditions.getFuel().setPetrol(isChecked);
//                    else if (position == 2)
//                        SearchFragment.searchConditions.getFuel().setGas(isChecked);
//                    else if (position == 3)
//                        SearchFragment.searchConditions.getFuel().setElectric(isChecked);
//                    else
//                        SearchFragment.searchConditions.getFuel().setHybrid(isChecked);
//                }
//            });
//        } else if (id == 2) {
//            if (position == 1)
//                holder.check.setChecked(SearchFragment.searchConditions.getLicencePlates().isLocal());
//            else if (position == 2)
//                holder.check.setChecked(SearchFragment.searchConditions.getLicencePlates().isKenya());
//            else if (position == 3)
//                holder.check.setChecked(SearchFragment.searchConditions.getLicencePlates().isForeign());
//            else if (position == 0)
//                holder.check.setChecked(SearchFragment.searchConditions.getLicencePlates().isUnspecified());
//
//            holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (position == 0)
//                        SearchFragment.searchConditions.getLicencePlates().setUnspecified(isChecked);
//                    else if (position == 1)
//                        SearchFragment.searchConditions.getLicencePlates().setLocal(isChecked);
//                    else if (position == 2)
//                        SearchFragment.searchConditions.getLicencePlates().setKenya(isChecked);
//                    else if (position == 3)
//                        SearchFragment.searchConditions.getLicencePlates().setForeign(isChecked);
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvItem;
        public CheckBox check;
        OnCheckListener onCheckListener;

        public ViewHolder(View itemView, OnCheckListener onCheckListener) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tvDialogName);
            check = (CheckBox) itemView.findViewById(R.id.checkDialog);
            this.onCheckListener = onCheckListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onCheckListener.onSearchCheck(getAdapterPosition());
        }
    }

    public interface OnCheckListener {
        void onSearchCheck(int position);
    }
}

