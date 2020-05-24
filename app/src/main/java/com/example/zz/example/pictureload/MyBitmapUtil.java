package com.example.zz.example.pictureload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;
import android.widget.ImageView;

import com.example.zz.example.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/24
 */


/**
 * 参考：北京day6下午视频
 * 1 开启子线程去加载图片
 * 2 获得byte[]
 * 2.1 从内存读取
 * 2.2 从磁盘读取
 * 2.3 从网络读取
 * 3 把byte[]转成bitmap对象
 * 4 回到主线程，给iv设置bitmap对象
 * 本类只做了加载网络图片的核心功能，还有例如加载后的内存磁盘清理等都没有细化
 * <p/>
 * <p/>
 * LruCache
 * Least recent used
 * <p/>
 * 使用：
 * 1 在构造的时候指定容量
 * 2 api和map集合是一样的
 * 3 一般需要覆盖掉LruCache的sizeOf方法
 * <p/>
 * <p/>
 * 线程池：
 * 1 构造方法   核心线程数量 , 总线程数量（核心+临时） ，空闲时间（临时线程超过此时间无工作就被杀死，避免资源占用）,空闲时间单位 ，等待被执行的任务的队列
 * 2 执行任务  execute(runnable)
 * <p/>
 * <p/>
 * AsyncTask 的优缺点：
 * 优点： 主线程和子线程的切换上很方便
 * 缺点： 内部的 ThreadPoolExecutor
 * 1 队列的容量一百多，如果同时执行太多任务，队列就爆了，出现异常，程序退出
 * 2 在旧版的AsyncTask上， 总线程数量过大，效率不高
 * 3 新版的同时执行的任务过小，优先级高的任务可能得不到及时执行
 * <p/>
 * 解决：
 * 使用两个参数的executeOnExecutor 额外指定 线程池执行器
 *
 * 三方框架对比：
 *  Picasso:体积小，集成简单，内存和硬盘二级缓存功能，默认加载缓存全尺寸图片耗内存大，也可以自己设置大小，每次显示都从全尺寸计算然后显示，显示速度稍慢，显示效果好；不支持GIF、不支持视频缩略图、加载速度中等
 *  Glide:体积中，集成中等，Picasso有的功能它都有，Glide默认加载和ImageView一样大小的图片，显示速度快，显示效果稍逊于Picasso；支持GIF、支持视频缩略图、加载速度高
 *  Fresco：体积大，集成难度大，Glide有的功能Fresco都有（都可以加载缩略图，可以加载gif）；缓存原图，有渐加载效果
 *
 *  由上分析可知Glide的优点：
 *
 * 1、支持多种图片格式的缓存，例如Gif、WebP、缩略图、Video。
 *
 * 2、支持根据Activity及Fragment的生命周期的变化来处理图片。
 *
 * 3、高效的缓存策略，灵活（Picasso只会缓存原始尺寸的图片，Glide缓存的是多种规格）。
 *
 * 4、加载速度快且内存开销小（默认Bitmap格式的不同，使得内存开销是Picasso的一半）
 *
 * 5、包大小只有500k左右，比Fresco小不少。
 *
 *  
 *
 * Fresco的优点：
 *
 * 1、Fresco最大的优势是在5.0以下系统的Bitmap加载，在5.0以下系统，Fresco将图片放置一个特别的内存区域(Ashmem区)。
 *
 * 2、大大减少OOM（在更底层的Native层对OOM进行处理，图片将不再占用App的内存）。
 *
 * 3、渐进式加载 JPEG 图片, 支持图片从模糊到清晰加载，尤其是慢网络有极大的利好，可带来更好的用户体验。
 *
 * 4、适用于需要高性能加载大量图片的场景。
 *
 *  
 *
 * Glide的缺点：
 *
 * 1、加载动图的情况下Java Heap比Fresco的高。
 *
 *  
 *
 * Fresco的缺点：
 *
 * 1、包比较大，是Glide的几倍大小。
 *
 * 2、API不够简洁，用法比较复杂。
 *
 * 3、底层设计C++ 领域，阅读及可开发性难度加大。
 */
class MyBitmapUtil {

    File cacheDir;

    Runtime runtime = Runtime.getRuntime();
    private ImageView mPicImageView;
    private String mUrl;
    private Bitmap bitmap;

    {
        //   硬件的内存->操作系统的内存->进程的内存->java虚拟机的内存->应用的内存
        // 可以申请的最大内存
        runtime.maxMemory();
        // 已经申请的内存
        runtime.totalMemory();
        // 已经申请，但没有使用的内存
        runtime.freeMemory();
    }

    LruCache<String, byte[]> cache = new LruCache<String, byte[]>((int) (runtime.maxMemory() / 8)) {
        @Override
        protected int sizeOf(String key, byte[] value) {
            return value.length;
        }
    };
//    Map<String,byte[]> cache = new HashMap<>();

    public MyBitmapUtil(Context context) {
        cacheDir = new File(Environment.getExternalStorageDirectory(), "/MBU/" + context.getPackageName());
        cacheDir.mkdirs();
    }

    public void display(ImageView picImageView, String url) {
        this.mPicImageView = picImageView;
        this.mUrl = url;
        new LoadImageTask().execute(Pair.create(picImageView, url));
    }

    private class LoadImageTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            LogUtils.e("doInBackground = ");
            try {
                // 2 获得byte[]
                byte[] bytes = getPictureBytesFromUrl(mUrl);
                LogUtils.e("bytes = " + bytes);
                // 3 把byte[]转成bitmap对象
                bitmap = decodeBytesToBitmap(bytes);
                LogUtils.e("bitmap1 = " + bitmap);
                // 4 回到主线程，给iv设置bitmap对象
//            handler.post(new ShowImageRunnable(bitmap, iv));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            LogUtils.e("bitmap = " + bitmap);
            mPicImageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap decodeBytesToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private byte[] getPictureBytesFromUrl(String url) throws IOException {

        byte[] result = null;
        //2.1 从内存读取
        result = readFromMem(url);
        if (result != null) {
            Log.d("getBytes", "内存");
            return result;
        }
        //2.2 从磁盘读取
        result = readFromDisk(url);
        if (result != null) {
            Log.d("getBytes", "磁盘");
            writeToMem(url, result);
            return result;
        }
        //2.3 从网络读取
        result = readFromNet(url);
        Log.d("getBytes", "网络");
        writeToDisk(url, result);
        writeToMem(url, result);
        return result;
    }

    private byte[] readFromMem(String url) {
        return cache.get(url);
    }

    private void writeToMem(String url, byte[] result) {
        cache.put(url, result);
    }


    private byte[] readFromDisk(String url) throws IOException {
        // url -> File -> FileinputStream -> baos ->byte[]
        File file = getFile(url);
        if (!file.exists()) {
            return null;
        }
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            copyStream(fis, baos);
        } finally {
            close(fis);
            close(baos);
        }

        return baos.toByteArray();
    }

    private void writeToDisk(String url, byte[] result) throws IOException {
        // url -> File -> FileOutputStream -> 写入result
        File file = getFile(url);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(result);
        } finally {
            close(fos);
        }

    }


    private byte[] readFromNet(String url) throws IOException {
        // url ->  InputStream ->  ByteArrayOutputStream -> ->byte[]
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            is = new URL(url).openStream();
            baos = new ByteArrayOutputStream();
            copyStream(is, baos);

            return baos.toByteArray();
        } finally {
            close(is);
            close(baos);
        }

    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        int len = 0;
        byte[] buf = new byte[1024 * 8];

        while ((len = is.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
    }

    private File getFile(String url) {
        // 要保证不同的url得到不同的文件，还要保证同一个url得到同一个文件，而且文件名要用安全字符
        // url -> md5
        // 文件 = 文件夹 + 文件名
        return new File(cacheDir, md5(url));
    }

    public static String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
