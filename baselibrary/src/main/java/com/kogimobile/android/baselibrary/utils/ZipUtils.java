package com.kogimobile.android.baselibrary.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private static final String MAC_OSX_FOLDER_NAME = "MACOSX";
    public static final String TAG = ZipUtils.class.getSimpleName();
    private static final int BUFFER = 2048;

    public static void unzipFromPath(String targetLocation, String sourceLocation) throws FileNotFoundException {
        try {
            Log.i(ZipUtils.class.getSimpleName(), String.format("Unzipping file \"%s\"", sourceLocation));
            unzip(targetLocation, new ZipInputStream(new BufferedInputStream(new FileInputStream(sourceLocation))));
            File file = new File(sourceLocation);
            file.delete();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    public static void unzip(String targetLocation, ZipInputStream zipInputStream) throws IOException {
        targetLocation = targetLocation.endsWith(File.separator) ? targetLocation : targetLocation.concat(File.separator);
        FileUtils.createDir(targetLocation);
        ZipEntry zipEntry = null;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if(!zipEntry.getName().contains(MAC_OSX_FOLDER_NAME)) {
                if (zipEntry.isDirectory()) {
                    FileUtils.createDir(targetLocation + zipEntry.getName());
                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = zipInputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, count);
                    }
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    FileOutputStream fileOutputStream = new FileOutputStream(targetLocation + zipEntry.getName());
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    bufferedOutputStream.write(bytes);
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                    zipInputStream.closeEntry();
                    byteArrayOutputStream.close();
                }
            }
        }
        zipInputStream.close();
    }

    /**
     *
     * This method receive a list of paths for compress in a simple .zip file
     * ths process can take time please try to use in a background threat.
     *
     * @param _files List of path for the file that you want to compress.
     * @param _zipFile path of the .zip file
     * @return String with the path of the final .zio file
     */
    public String zipFiles(String[] _files, String _zipFile) {
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(_zipFile);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte data[] = new byte[BUFFER];
            for (int i = 0; i < _files.length; i++) {
                FileInputStream fi = new FileInputStream(_files[i]);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
            return _zipFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}