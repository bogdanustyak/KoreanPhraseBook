package com.leoart.koreanphrasebook.dialogs;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.chapters.models.DialogsModel;
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse;
import com.leoart.koreanphrasebook.dialogs.models.Dialog;

import java.util.List;

/**
 * Created by bogdan on 11/5/16.
 */
public class DialogsRecyclerAdapter extends RecyclerView.Adapter<DialogsRecyclerAdapter.DialogViewHolder> {

    private List<DialogResponse> dialogs;
    private DialogsListInteractionListener interactionListener;

    public DialogsRecyclerAdapter(List<DialogResponse> dialogs, DialogsListInteractionListener interactionListener) {
        this.dialogs = dialogs;
        this.interactionListener = interactionListener;
    }

    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_dialog, parent, false);
        final DialogViewHolder viewHolder = new DialogViewHolder(itemView);
        viewHolder.tv_dialog_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interactionListener != null) {
                    interactionListener.onDialogClick(dialogs.get(viewHolder.getAdapterPosition()));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DialogViewHolder holder, int position) {
        final DialogResponse dialog = dialogs.get(position);
        if (dialog != null) {
            if (!TextUtils.isEmpty(dialog.getName())) {
                holder.tv_dialog_name.setText(dialog.getName());
            } else {
                holder.tv_dialog_name.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (dialogs != null) {
            return dialogs.size();
        }
        return 0;
    }

    public void setDialogs(List<DialogResponse> dialogs){
        this.dialogs = dialogs;
        notifyDataSetChanged();
    }

    static class DialogViewHolder extends RecyclerView.ViewHolder {
        TextView tv_dialog_name;

        public DialogViewHolder(View itemView) {
            super(itemView);
            tv_dialog_name = (TextView) itemView.findViewById(R.id.tv_dialog_name);
        }
    }

    public interface DialogsListInteractionListener {
        void onDialogClick(DialogResponse dialog);
    }
}
