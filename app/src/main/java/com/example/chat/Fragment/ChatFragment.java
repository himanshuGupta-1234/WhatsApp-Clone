package com.example.chat.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.chat.Adapter.UsersAdapter;
import com.example.chat.Models.Users;
import com.example.chat.R;
import com.example.chat.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }
    FragmentChatBinding fragmentChatBinding;
    ArrayList<Users> arrayList=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        firebaseDatabase=FirebaseDatabase.getInstance();
        fragmentChatBinding=FragmentChatBinding.inflate(inflater, container, false);
        UsersAdapter usersAdapter=new UsersAdapter(arrayList,getContext());
        fragmentChatBinding.chatRecycler.setAdapter(usersAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        fragmentChatBinding.chatRecycler.setLayoutManager(linearLayoutManager);

        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Users users=dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());

                    if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid())) {
                        arrayList.add(users);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return fragmentChatBinding.getRoot();
    }
}