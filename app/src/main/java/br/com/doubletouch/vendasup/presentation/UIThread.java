package br.com.doubletouch.vendasup.presentation;


import android.os.Handler;
import android.os.Looper;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;

/**
 * MainThread (UI Thread) implementation based on a Handler instantiated with the main
 * application Looper.
 * Created by LADAIR on 12/02/2015.
 */
public class UIThread implements PostExecutionThread {

    private static class LazyHolder {
        private static final UIThread INSTANCE = new UIThread();
    }

    public static UIThread getInstance() {
        return LazyHolder.INSTANCE;
    }

    private final Handler handler;

    private UIThread() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    /**
     * Causes the Runnable r to be added to the message queue.
     * The runnable will be run on the main thread.
     *
     * @param runnable {@link Runnable} to be executed.
     */
    @Override public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
