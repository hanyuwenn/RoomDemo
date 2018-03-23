package com.example.www.roomdemo;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        // 获得 AppDatabase 的对象，传入的第三个参数为数据库中表的名字
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "user").build();
        final UserDao userDao = db.userDao();

        // 访问数据库的操作要在子线程中执行
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.deleteAll();

                System.out.println("----------- INSERT U1 U2 -------------");
                User u1 = new User(1, "a", "b");
                User u2 = new User(2, "c", "d");
                userDao.insertAll(u1, u2);
                List<User> users = userDao.getAll();
                System.out.println(users.toString());

                System.out.println("------------- DELETE U2 --------------");
                userDao.delete(u2);
                users = userDao.getAll();
                System.out.println(users.toString());
                u1.setFirstName("aa");
                u1.setLastName("bb");


                System.out.println("------------- UPDATE U1 --------------");
                userDao.update(u1);
                users = userDao.getAll();
                System.out.println(users.toString());

            }
        });
        thread.start();
    }

}
