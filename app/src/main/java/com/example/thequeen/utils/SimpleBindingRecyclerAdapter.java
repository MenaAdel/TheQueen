package com.example.thequeen.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.thequeen.R;

import java.util.ArrayList;
import java.util.List;


public class SimpleBindingRecyclerAdapter<UIModel, Binding extends ViewDataBinding>
        extends RecyclerView.Adapter<SimpleBindingRecyclerAdapter.ViewHolder> {

    private List<UIModel> items;
    private int itemLayoutId;
    private int variableId;
    private boolean isSelected = false;
    private ItemClickListener<UIModel> listener;
    private ItemClickListenerWithPosition<UIModel> listenerWithPosition;

    public SimpleBindingRecyclerAdapter(@LayoutRes int itemLayoutId, int variableId) {
        this.itemLayoutId = itemLayoutId;
        this.variableId = variableId;
        this.items = new ArrayList<>();
    }

    public void addItem(UIModel item) {
        int lastPosition = getLastPosition();
        this.items.add(item);
        notifyItemInserted(lastPosition);
    }

    public void addItems(List<UIModel> newItems) {
        int lastPosition = getLastPosition();
        this.items.addAll(newItems);
        notifyItemRangeInserted(lastPosition, newItems.size());
    }

    public void setItems(List<UIModel> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void setNewItems(List<UIModel> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void isSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    private int getLastPosition() {
        return items.size() + 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        Binding binding = DataBindingUtil.inflate(inflater, itemLayoutId, viewGroup, false);
        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final UIModel model = items.get(i);
        viewHolder.binding.setVariable(variableId, model);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemSelected(model);
                if (listenerWithPosition != null)
                    listenerWithPosition.onItemSelected(model, i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setListener(ItemClickListener<UIModel> listener) {
        this.listener = listener;
    }

    public void setPositionListener(ItemClickListenerWithPosition<UIModel> positionListener) {
        this.listenerWithPosition = positionListener;
    }

    public static class ViewHolder<Binding extends ViewDataBinding> extends RecyclerView.ViewHolder {

        public Binding binding;

        public ViewHolder(Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ItemClickListener<T> {
        void onItemSelected(T item);
    }

    public interface ItemClickListenerWithPosition<T> {
        void onItemSelected(T item, int position);
    }

}
