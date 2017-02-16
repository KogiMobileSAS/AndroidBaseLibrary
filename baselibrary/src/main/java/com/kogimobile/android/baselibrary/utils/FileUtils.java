package com.kogimobile.android.baselibrary.utils;

import android.content.Context;

import com.kogimobile.android.baselibrary.app.base.BaseApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLConnection;

/**
 * @author Julian Cardona on 4/1/15.
 */
public class FileUtils {

    public static void saveObjectToDisk(String fileName,Object object) throws IOException {
        FileOutputStream fileOutputStream = BaseApp.getGlobalContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static Object getObjectFromDisk(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = null;
        Object object = null;
        fis = BaseApp.getGlobalContext().openFileInput(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        object = is.readObject();
        is.close();
        return object;
    }

    public static void deleteObjectOnDisk(String fileName) throws IOException {
        FileOutputStream fileOutputStream = BaseApp.getGlobalContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.reset();
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static boolean createDir(String path) {
        boolean isDirCreated = true;
        File f = new File(path);
        if (!f.exists()) {
            isDirCreated = f.mkdirs();
        }
        return isDirCreated;
    }

    private static boolean checkExist(String path) {
        File temp = new File(path);
        return temp.exists();
    }

    public static String getMimeType(String fileName) {
        return URLConnection.guessContentTypeFromName(fileName);
    }

}
