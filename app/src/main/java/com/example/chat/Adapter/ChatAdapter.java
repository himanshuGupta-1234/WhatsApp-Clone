package com.example.chat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chat.Models.MessageModel;
import com.example.chat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends  RecyclerView.Adapter{

    ArrayList<MessageModel> list;
    Context context;
    MessageModel mm;

    int SENDER_VIEW_TYPE=1;
    int RECIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<MessageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==SENDER_VIEW_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel=list.get(position);
        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).sender_message.setText(messageModel.getMessage());
        }
        else
        {
            ((ReceiverViewHolder)holder).receiver_message.setText(messageModel.getMessage());
        }

    }


    @Override
    public int getItemViewType(int position) {


        if (list.get(position).getMessageId().equals(FirebaseAuth.getInstance().getUid())) {

             return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECIVER_VIEW_TYPE;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{

        TextView receiver_message,receiver_time;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiver_message=itemView.findViewById(R.id.receivermessage);
            receiver_time=itemView.findViewById(R.id.receivertime);

        }
    }

    public class SenderViewHolder extends   RecyclerView.ViewHolder
    {
        TextView sender_message,sender_time;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            sender_message=itemView.findViewById(R.id.sendermessage);
            sender_time=itemView.findViewById(R.id.sendertime);

        }
    }
}
