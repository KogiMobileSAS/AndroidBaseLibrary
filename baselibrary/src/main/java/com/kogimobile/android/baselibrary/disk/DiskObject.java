package com.kogimobile.android.baselibrary.disk;

import com.kogimobile.android.baselibrary.app.base.BaseApp;
import com.kogimobile.android.baselibrary.utils.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;


abstract public class DiskObject<T>{

    public Observable<T> saveToDisk(final String fileName) {

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    FileUtils.saveObjectToDisk(fileName,DiskObject.this);
                    emitter.onComplete();
                } catch (Throwable e) {
                    emitter.onError(e);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<T> getFromDisk(final String fileName) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                FileInputStream fis = null;
                T type = null;
                try {
                    fis = BaseApp.getGlobalContext().openFileInput(fileName);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    type = (T) is.readObject();
                    is.close();
                    emitter.onNext(type);
                    emitter.onComplete();
                } catch (IOException | ClassNotFoundException e) {
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<Void> deleteFromDisk(final String fileName) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
                try {
                    FileUtils.deleteObjectOnDisk(fileName);
                    emitter.onComplete();
                } catch (IOException e) {
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

}