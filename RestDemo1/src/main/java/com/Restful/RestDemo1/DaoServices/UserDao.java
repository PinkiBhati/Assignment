package com.Restful.RestDemo1.DaoServices;

import com.Restful.RestDemo1.ModelClasses.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.sql.Types.NULL;

@Component

public class UserDao {

    private static List<User> listOfUser= new ArrayList<User>();

    private static int Usercount=4;

    static {
        listOfUser.add(new User("pinki",1,22));
        listOfUser.add(new User("chetna",2,24));
        listOfUser.add(new User("parineeta",3,26));
        listOfUser.add(new User("Himanshu",4,29));
    }


    public List<User> findAllUsers()
    {
        return listOfUser;
    }

    public User createUser(User user)
    {
        if (user.getRollNo() == NULL)
        {
            user.setRollNo(++Usercount);
        }
        else
            listOfUser.add(user);

        return user;
    }


    public User findOneUser(int rollNo)
    {
        for (User user: listOfUser)
        {
            if (user.getRollNo()== rollNo)
             return user;

        }
        return null;
    }


    public User deleteUser(int rollNo)
    {
        Iterator<User> iterator= listOfUser.iterator();
        while (iterator.hasNext())
        {
             User user= iterator.next();
            if (user.getRollNo()== rollNo)
            {
                iterator.remove();
                return user;
            }
        }
        return null;
    }

    public User updateUser(User user)
    {

        listOfUser.add(user);
        return user;

    }

}
