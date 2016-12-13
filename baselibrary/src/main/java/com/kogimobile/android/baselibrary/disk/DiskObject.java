package com.kogimobile.android.baselibrary.disk;

import com.kogimobile.android.baselibrary.app.base.BaseApp;
import com.kogimobile.android.baselibrary.utils.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import rx.Observable;
import rx.Subscriber;

abstract public class DiskObject<T>{

    public Observable<T> saveToDisk(final String fileName) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    FileUtils.saveObjectToDisk(fileName,DiskObject.this);
                    subscriber.onNext((T)DiskObject.this);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
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

    public Observable<Boolean> deleteFromDisk(final String fileName) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean > subscriber) {
                try {
                    FileUtils.deleteObjectOnDisk(fileName);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}