package com.example.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivityGroupBinding;
import com.example.chatapp.model.ChatGroup;
import com.example.chatapp.view.adapter.GroupAdapter;
import com.example.chatapp.viewModel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {
    private ArrayList<ChatGroup> chatGroupArrayList;
    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private ActivityGroupBinding binding;
    private MyViewModel myViewModel;
    // Dialog
    private Dialog chatGroupDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        binding= DataBindingUtil.setContentView(
                this,
                R.layout.activity_group
        );

        // Define the ViewModel
        myViewModel=new ViewModelProvider(this).get(MyViewModel.class);

        // RecyclerView with DataBinding
        recyclerView=binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setup an observer to listen for changes in a "live data" object
        myViewModel.getGroupList().observe(this, new Observer<List<ChatGroup>>() {
            @Override
            public void onChanged(List<ChatGroup> chatGroups) {
                // the updated data is received as "chatGroups" parameter in onChanged()
                chatGroupArrayList=new ArrayList<>();
                chatGroupArrayList.addAll(chatGroups);
                groupAdapter =new GroupAdapter(chatGroupArrayList);
                recyclerView.setAdapter(groupAdapter);
                groupAdapter.notifyDataSetChanged();
            }
        });
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }
    public void showDialog(){
        chatGroupDialog=new Dialog(this);
        chatGroupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view= LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout,null);
        chatGroupDialog.setContentView(view);
        chatGroupDialog.show();
        Button submit=view.findViewById(R.id.button);
        EditText edit=view.findViewById(R.id.editText);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName=edit.getText().toString();
                Toast.makeText(GroupActivity.this, "Your ChatGroup"+groupName, Toast.LENGTH_SHORT).show();

                myViewModel.createGroup(groupName);

                chatGroupDialog.dismiss();
            }
        });
    }
}