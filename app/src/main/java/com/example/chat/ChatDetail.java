package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chat.Adapter.ChatAdapter;
import com.example.chat.Models.MessageModel;
import com.example.chat.databinding.ActivityChatDetailBinding;
import com.example.chat.databinding.ChatRowLayoutBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetail extends AppCompatActivity {


    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    ActivityChatDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();

        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        final String senderId=mAuth.getUid();
        String receiverId=getIntent().getStringExtra("userId");
        String username=getIntent().getStringExtra("userName");
        String profilepic=getIntent().getStringExtra("profilePic");

        binding.receivername.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.profile).into(binding.receiverImage);


        //arrow
        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChatDetail.this,Home.class);
                startActivity(intent);
            }
        });



       final ArrayList<MessageModel> messageModels=new ArrayList<>();
       final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this);
       binding.chatDetailRecycler.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.chatDetailRecycler.setLayoutManager(linearLayoutManager);

        final String senderRoom=senderId+receiverId;
        final String receiverRoom=receiverId+senderId;



         database.getReference().child("Chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 messageModels.clear();
                 for(DataSnapshot snapshot1:snapshot.getChildren())
                 {
                     MessageModel messageModel=snapshot1.getValue(MessageModel.class);
                     messageModels.add(messageModel);
                 }

                 //runtime update of message in recyclwr view
                 chatAdapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });


        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.messageNeedToBeSend.getText().toString();

                if (message.length() == 0) {
                    Toast.makeText(ChatDetail.this, "Enter the message", Toast.LENGTH_LONG).show();
                } else {

                    final MessageModel model = new MessageModel(senderId, message);

                    model.setTimestamp(new Date().getTime());

                    binding.messageNeedToBeSend.setText("");

                    //to create nood for chatting
                    database.getReference().child("Chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            database.getReference().child("Chats").child(receiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                        }
                    });
                }
            }
        });






    }
}