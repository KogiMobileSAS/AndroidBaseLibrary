package com.kogimobile.android.baselibrary.disk;

import com.kogimobile.android.baselibrary.app.base.BaseApp;
import com.kogimobile.android.baselibrary.utils.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

abstract public class DiskObject<T>{

    public Observable<T> saveToDisk(final String fileName) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    FileUtils.saveObjectToDisk(fileName,DiskObject.this);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<T> getFromDisk(final String fileName) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                FileInputStream fis = null;
                T type = null;
                try {
                    fis = BaseApp.getGlobalContext().openFileInput(fileName);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    type = (T) is.readObject();
                    is.close();
                    subscriber.onNext(type);
                    subscriber.onCompleted();
                } catch (IOException | ClassNotFoundException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<Void> deleteFromDisk(final String fileName) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void > subscriber) {
                try {
                    FileUtils.deleteObjectOnDisk(fileName);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}