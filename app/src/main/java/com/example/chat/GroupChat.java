package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chat.Adapter.ChatAdapter;
import com.example.chat.Models.MessageModel;
import com.example.chat.databinding.ActivityChatDetailBinding;
import com.example.chat.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChat extends AppCompatActivity {

    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();

        binding.receivernameG.setText("Group Friends");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter adapter = new ChatAdapter(messageModels, this);


        binding.chatDetailRecyclerG.setAdapter(adapter);
        final String senderId = mAuth.getUid();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatDetailRecyclerG.setLayoutManager(linearLayoutManager);

       //fetch from database

        database.getReference().child("Group Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    MessageModel messageModel= snapshot1.getValue(MessageModel.class);
                    messageModels.add(messageModel);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.sendMessageG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.messageNeedToBeSendG.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);

                model.setTimestamp(new Date().getTime());

                binding.messageNeedToBeSendG.setText("");



                //to create nood for chatting
                database.getReference().child("Group Chats").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });


        binding.backarrowG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupChat.this, Home.class);
                startActivity(intent);
            }
        });
    }
}