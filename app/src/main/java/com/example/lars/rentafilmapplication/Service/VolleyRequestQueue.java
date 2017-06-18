package com.example.lars.rentafilmapplication.Service;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Lars on 15-6-2017.
 */

// De requestqueue klasse wordt gemaakt als singleton om te zorgen dat hij blijft runnen zolang de app open staat.
// Wanneer je maar één maal de request hoeft te maken, kun je de requestqueue aanroepen waar je wil, en hem
// vervolgens stoppen met stop() wanneer je een response hebt gekregen.
public class VolleyRequestQueue {

    private static VolleyRequestQueue myInstance;
    private RequestQueue myRequestQueue;
    private static Context myContext;

    // private constructor ; kan niet worden aangeroepen van buiten af, hoort bij singleton pattern.
    // Dit wil zeggen dat er maar een instantie van een klasse kan zijn.
    private VolleyRequestQueue(Context context){
        myContext = context;
        myRequestQueue = getRequestQueue();
    }

    // static methode die één instantie van deze klasse beheert.
    public static synchronized VolleyRequestQueue getInstance(Context context){
        if (myInstance == null){
            myInstance = new VolleyRequestQueue(context);
        }
        return myInstance;
    }

    public RequestQueue getRequestQueue() {
        if (myRequestQueue == null) {

            // instantieer de cache
            Cache cache = new DiskBasedCache(myContext.getCacheDir(), 10 * 1024 * 1024);
            // Zet het netwerk op om HttpURLConnection te gebruiken als HTTP client.
            Network network = new BasicNetwork(new HurlStack());
            // instantieer de requestqueue met de cache en het netwerk
            myRequestQueue = new RequestQueue(cache, network);
            // start de queue
            myRequestQueue.start();
        }
        return myRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}
