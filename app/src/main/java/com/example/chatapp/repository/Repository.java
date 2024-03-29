package com.example.chatapp.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.model.ChatGroup;
import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.view.GroupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    // It acts a bridge between the ViewModel And the data
    MutableLiveData<List<ChatGroup>> chatGroupMutableLiveData;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference groupReference;
    MutableLiveData<List<ChatMessage>> messageLiveData;


    public Repository() {
        this.chatGroupMutableLiveData=new MutableLiveData<>();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();   // Root reference
        messageLiveData=new MutableLiveData<>();
    }

    public void fireBaseAnonymousAuth(Context context){
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent i = new Intent(context, GroupActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    }
                });


    }

// getting current UserId
    public String getCurrentUSerId(){
        return FirebaseAuth.getInstance().getUid();
    }
// signOut Functionality
   public void signOut(){
        FirebaseAuth.getInstance().signOut();
   }

   // Getting Chat Group available from FireBase realtime Db


    public MutableLiveData<List<ChatGroup>> getChatGroupMutableLiveData() {
        List<ChatGroup> groupList=new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatGroup group=new ChatGroup(dataSnapshot.getKey());
                    groupList.add(group);
                }
                chatGroupMutableLiveData.postValue(groupList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return chatGroupMutableLiveData;
    }
    // Creating a new group
    public void createNewChatGroup(String groupName){
        reference.child(groupName).setValue(groupName);
    }
    // Getting Messages Live Data


    public MutableLiveData<List<ChatMessage>> getMessageLiveData(String groupName) {
        groupReference=database.getReference().child(groupName);
        List<ChatMessage> messageList=new ArrayList<>();
        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatMessage message=dataSnapshot.getValue(ChatMessage.class);
                    messageList.add(message);
                }
                messageLiveData.postValue(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return messageLiveData;
    }
    public void sendMessages(String messageText,String chatGroup){
        DatabaseReference ref=database.getReference(chatGroup);
        if(!messageText.trim().equals("")){
            ChatMessage msg=new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    messageText,
                    System.currentTimeMillis()
            );

            String randomKey=ref.push().getKey();
            ref.child(randomKey).setValue(msg);
        }

    }
}
