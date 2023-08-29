package lk.test.myapplication.managers;

import android.content.Context;

public class ContextManager {
    private static ContextManager singleton;

    private Context applicationContext;

    public static ContextManager getInstance() {
        if (singleton == null)
            singleton = new ContextManager();

        return singleton;
    }

    public void setApplicationContext(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    public Context getApplicationContext(){
        return applicationContext;
    }
}
