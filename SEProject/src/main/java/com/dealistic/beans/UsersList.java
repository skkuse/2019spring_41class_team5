package com.dealistic.beans;


import java.util.ArrayList;
import java.util.List;
public class UsersList {
    private List<User> UserRecords;
    private static UsersList stdregd = null;
    private UsersList(){
    UserRecords = new ArrayList<User>();
    }
    public static UsersList getInstance() {
        if(stdregd == null) {
              stdregd = new UsersList();
              return stdregd;
            }
            else {
                return stdregd;
            }
    }
    public void add(User std) {
    UserRecords.add(std);
    }
public String upDateUser(User std) {
for(int i=0; i<UserRecords.size(); i++)
        {
            User stdn = UserRecords.get(i);
            if(stdn.getRegistrationNumber().equals(std.getRegistrationNumber())) {
              UserRecords.set(i, std);//update the new record
              return "Update successful";
            }
        }
return "Update un-successful";
}
public String deleteUser(String registrationNumber) {
for(int i=0; i<UserRecords.size(); i++)
        {
            User stdn = UserRecords.get(i);
            if(stdn.getRegistrationNumber().equals(registrationNumber)){
              UserRecords.remove(i);//update the new record
              return "Delete successful";
            }
        }
return "Delete un-successful";
}
    public List<User> getUserRecords() {
    return UserRecords;
    }
}