package com.daily.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@SuppressWarnings("all")
public class FileUtil {
    /**
     * 删除指定目录下文件及目录
     *
     * @param filepath
     */
    public static void deleteFolderFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                        file.delete();
                        return;
                    }
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath());
                    }
                } else {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件夹大小
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     */
//    public static long getFolderSize(File file) throws Exception {
//        long size = 0;
//        try {
//            File[] fileList = file.listFiles();
//            for (int i = 0; i < fileList.length; i++) {
//                if (fileList[i].isDirectory()) {
//                    size = size + getFolderSize(fileList[i]);
//                } else {
//                    size = size + fileList[i].length();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return size;
//    }

    /**
     * 压缩
     *
     * @param zipFile
     * @param targetDir
     * @return
     */
    public static void unzip(final String zipFile, final String targetDir) throws Exception {
        int BUFFER = 4096;
        String strEntry;
        BufferedOutputStream dest = null;
        FileInputStream fis = new FileInputStream(zipFile);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            int count;
            byte data[] = new byte[BUFFER];
            strEntry = entry.getName();
            File entryFile = new File(targetDir + strEntry);
            File entryDir = new File(entryFile.getParent());
            if (!entryDir.exists()) {
                entryDir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(entryFile);
            dest = new BufferedOutputStream(fos, BUFFER);
            while ((count = zis.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
        }
        zis.close();
        fis.close();
    }


    private static final int SIZE_B = 1;//获取文件大小单位为B的double值
    private static final int SIZE_KB = 2;//获取文件大小单位为KB的double值
    private static final int SIZE_MB = 3;//获取文件大小单位为MB的double值
    private static final int SIZE_GB = 4;//获取文件大小单位为GB的double值


    /**
     * prevent the image to be scaned by Gallery
     *
     * @param directory
     */
    public static void createNoMediaFile(File directory) {
        try {
            File noMediaFile = new File(directory, ".nomedia");
            noMediaFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除最新拍的图片
     */
    public static void deleteLastPhotoTaken(Activity activity) {
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE};
        final Cursor cursor = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            int column_index_data =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String image_path = cursor.getString(column_index_data);
            File file = new File(image_path);
            if (file.exists()) {
                if (file.delete())
                    scanMediaFile(activity, file.getAbsolutePath());
            }
        }
    }

    /**
     * 重新触发媒体扫描，刷新Gallery数据库
     *
     * @param mediaDir
     */
    private static void scanMediaFile(Activity activity, String mediaDir) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mediaDir);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getTargetFileSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFolderSize(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return formatSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getFileSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFolderSize(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return formatSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param folder
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File folder) {
        long size = 0;
        try {
            File fileList[] = folder.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + getFileSize(fileList[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String formatSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double formatSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 从Uri中获取图片真实路径：
     * 适用于Android4.4以后uri未返回真实路径的场景
     *
     * @param context
     * @param ourUri
     * @return
     */

    public static String getPathFromUri(Context context, Uri ourUri){
        String result;
        Cursor cursor = context.getContentResolver().query(ourUri, null, null, null, null);
        if (cursor == null) {
            result = ourUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}

