package br.com.doubletouch.vendasup.util.image;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.net.URLEncoder;

/**
 * Created by LADAIR on 10/02/2015.
 */
public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        if(android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"vendasup_images_cache");
        } else {
            cacheDir = context.getCacheDir();
        } if(!cacheDir.exists()){
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        //String filename = String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
    }

    public void clear(){
        File[] files = cacheDir.listFiles();
        if(files==null) {
            return;
        }

        for(File f:files) {
            f.delete();
        }
    }
}
