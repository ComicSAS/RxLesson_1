package com.example.rxlesson_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.rxlesson_1.model.User;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public User user = new User("Andrew", "Sukhovolskij", 21);

    private Disposable mObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("CheckObservableInput", user.toString());

        mObservable =  Observable.just(user)
                .observeOn(Schedulers.io()) // create new thread inside Observable
                .observeOn(AndroidSchedulers.mainThread())  // result handling data of Observable will be returned into main thread
                .map(this::getOlder)
                .map(item -> addPrefix("Person - ", item))
                .subscribe(item -> Log.d("CheckObservableInput", item.toString()),
                        throwable -> { },
                        () -> Log.d("CheckObservableInput", "complete"));
    }

    private User getOlder(User item) {
        item.setAge(item.getAge() + 1);
        return item;
    }

    private User addPrefix(String prefix, User item){
        item.setName(prefix + item.getName());
        return item;
    }
}
