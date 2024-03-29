package com.example.chatapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.BR;
import com.example.chatapp.R;
import com.example.chatapp.databinding.RawChatBinding;
import com.example.chatapp.model.ChatMessage;

import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatMessage> chatMessageList;
    private Context context;

    public ChatAdapter(List<ChatMessage> chatMessageList, Context context) {
        this.chatMessageList = chatMessageList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.raw_chat,
                        parent,
                        false);
        RawChatBinding binding= DataBindingUtil.bind(view);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.chatMessages,chatMessageList.get(position));
        holder.getBinding().executePendingBindings();


    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private RawChatBinding binding;

        public MyViewHolder(RawChatBinding binding) {
            super(binding.getRoot());
            setBinding(binding);
        }

        public RawChatBinding getBinding() {
            return binding;
        }

        public void setBinding(RawChatBinding binding) {
            this.binding = binding;
        }
    }

}
