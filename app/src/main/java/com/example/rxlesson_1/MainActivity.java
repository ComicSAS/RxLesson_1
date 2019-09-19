package com.example.rxlesson_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.rxlesson_1.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public User user = new User("Andrew", "Sukhovolskij", 21);

    public Disposable mObservable;
    public Disposable mListObservable;

    public List<User> mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("CheckObservableInput", user.toString());

        mUserList = initUserList();

        mObservable = Observable.just(user)
                .observeOn(Schedulers.io()) // create new thread inside Observable
                .observeOn(AndroidSchedulers.mainThread())  // result handling data of Observable will be returned into main thread
                .map(this::getOlder)
                .map(this::addPrefix)
                .subscribe(item -> Log.d("CheckObservableInput", item.toString()),
                        throwable -> {
                        },
                        () -> Log.d("CheckObservableInput", "complete"));

        mListObservable = Observable.just(mUserList)
                .fromIterable(mUserList)
                .concatMap(item -> Observable.just(addPrefix(item)))
                .concatMap(item -> Observable.just(getOlder(item)))
                .toList()
                .toObservable()
                .subscribe(item -> Log.d("CheckObservableInput", item.toString()),
                        throwable -> {
                        },
                        () -> Log.d("CheckObservableInput", "complete"));
    }

    private User getOlder(User item) {
        item.setAge(item.getAge() + 1);
        return item;
    }

    private User addPrefix(User item) {
        item.setName("Person - " + item.getName());
        return item;
    }

    public List<User> initUserList() {
        List<User> userList = new ArrayList<>();

        User user1 = new User("Name_1", "Last_Name_1", 1);
        User user2 = new User("Name_2", "Last_Name_2", 2);
        User user3 = new User("Name_3", "Last_Name_3", 3);
        User user4 = new User("Name_4", "Last_Name_4", 4);
        User user5 = new User("Name_5", "Last_Name_5", 5);
        User user6 = new User("Name_6", "Last_Name_6", 6);
        User user7 = new User("Name_7", "Last_Name_7", 7);
        User user8 = new User("Name_8", "Last_Name_8", 8);
        User user9 = new User("Name_9", "Last_Name_9", 9);
        User user10 = new User("Name_10", "Last_Name_10", 10);

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);
        userList.add(user7);
        userList.add(user8);
        userList.add(user9);
        userList.add(user10);

        for (User item : userList
        ) {
            Log.d("CheckObservableInput", item.toString());
        }

        return userList;
    }
}
