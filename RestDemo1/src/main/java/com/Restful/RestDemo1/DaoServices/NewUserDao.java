package com.Restful.RestDemo1.DaoServices;

import com.Restful.RestDemo1.ModelClasses.Address;
import com.Restful.RestDemo1.ModelClasses.NewUser;
import io.swagger.annotations.ApiModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component

public class NewUserDao {


    private static List<NewUser> NewUsersList = new ArrayList<>();

    private static int UserCount=5;

    static
    {
        NewUsersList.add(new NewUser("Pinki" ,"Bhati",21,new Address("New Delhi","Delhi")));
        NewUsersList.add(new NewUser("Parineeta","Jain",22,new Address ("Gurgaon","Haryana")));
        NewUsersList.add(new NewUser("Himanshu" , "Bhansali",23,new Address ("Chandigarh","Punjab")));
        NewUsersList.add(new NewUser("Azeem"," Faisal",24,new Address( "Chapra","Bihar")));
        NewUsersList.add(new NewUser("Shivam " ,"Sharma",25,new Address( "Gk","Delhi")));

    }

    public List<NewUser> findAllUsers(){return NewUsersList; }
}
