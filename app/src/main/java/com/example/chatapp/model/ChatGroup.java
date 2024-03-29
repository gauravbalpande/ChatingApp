package com.example.chatapp.model;

public class ChatGroup {
    String groupName;

    public ChatGroup(String groupName) {
        this.groupName = groupName;
    }

    public ChatGroup() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
