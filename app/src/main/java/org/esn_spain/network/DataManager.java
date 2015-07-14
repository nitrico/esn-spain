package org.esn_spain.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import org.esn_spain.model.simple.Data;
import org.esn_spain.model.simple.esn.Galaxy;
import org.esn_spain.model.web.Events;
import org.esn_spain.model.web.Members;
import org.esn_spain.model.web.Partners;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.io.Reader;
import java.io.StringReader;

public class DataManager {

  private static final String TAG = DataManager.class.getSimpleName();
  private static final String GALAXY_URL = "https://dl.dropboxusercontent.com/u/430853/app/galaxy.xml";
  private static DataManager mInstance;
  private static Context mContext;

  private String mUrl = "https://dl.dropboxusercontent.com/u/430853/app/data.xml";
    // "https://dl.dropboxusercontent.com/u/430853/app/data.xml";
  private Galaxy mGalaxy;
  private Data mData;
  private Partners mPartners;
  private Events mEvents;
  private RequestQueue mRequestQueue;
  private Serializer mSerializer;

  private DataManager(Context context) {
    mContext = context;
    mRequestQueue = getRequestQueue();
    mSerializer = new Persister();
  }

  public static synchronized DataManager from(Context context) {
    if (mInstance == null) mInstance = new DataManager(context);
    return mInstance;
  }

  public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
    return mRequestQueue;
  }

  public <T> void addToRequestQueue(Request<T> req) {
    req.setTag(TAG);
    getRequestQueue().add(req);
  }

  @SuppressWarnings("unused")
  public <T> void addToRequestQueue(Request<T> req, String tag) {
    req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    getRequestQueue().add(req);
  }

  @SuppressWarnings("unused")
  public void cancelPendingRequests(Object tag) {
    if (mRequestQueue != null) mRequestQueue.cancelAll(tag);
  }

  @SuppressWarnings("unused")
  public void clearCache() {
    if (mRequestQueue != null) mRequestQueue.getCache().clear();
  }

  /*
  public List<? extends SimpleXmlObject> find(Criterion c) {
    List<SimpleXmlObject> list = new ArrayList<>();
    if (mData != null) for (SimpleXmlObject item: mData.get()) if (c.test(item)) list.add(item);
    return list;
  }
  */



  public Partners getPartners() {
    return mPartners;
  }

  public void loadPartners(String url, PartnersListener listener) {
    //mUrl = url;
    System.out.println("loadPartners CALLED");
    Cache.Entry cache = getRequestQueue().getCache().get(url);
    if (cache!=null && !cache.refreshNeeded()) {
      String cachedResponse = new String(getRequestQueue().getCache().get(url).data);
      Reader reader = new StringReader(cachedResponse);
      try {
        mPartners = mSerializer.read(Partners.class, reader);
        if (mPartners != null && listener != null) listener.onPartnersLoaded(mPartners);
      }
      catch (Exception e) { e.printStackTrace(); }
    }
    else addToRequestQueue(createPartnersRequest(url, listener));
  }

  public SimpleXmlRequest<Partners> createPartnersRequest(final String url, final PartnersListener listener) {
    System.out.println("createPartnersRequest CALLED");
    return new SimpleXmlRequest<>(Request.Method.GET, url, Partners.class,
            new Response.Listener<Partners>() {
              @Override
              public void onResponse(Partners response) {
                mPartners = response;
                if (mPartners != null && listener != null) listener.onPartnersLoaded(mPartners);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                System.out.println("Error al obtener el contenido de " + url);
                VolleyLog.e("Error: ", error.getMessage());
              }
            }
    );
  }

  public void loadEvents(String url, EventsListener listener) {
    //mUrl = url;
    System.out.println("loadEvents CALLED");
    Cache.Entry cache = getRequestQueue().getCache().get(url);
    if (cache!=null && !cache.refreshNeeded()) {
      String cachedResponse = new String(getRequestQueue().getCache().get(url).data);
      Reader reader = new StringReader(cachedResponse);
      try {
        mEvents = mSerializer.read(Events.class, reader);
        if (mEvents != null && listener != null) listener.onEventsLoaded(mEvents);
      }
      catch (Exception e) { e.printStackTrace(); }
    }
    else addToRequestQueue(createEventsRequest(url, listener));
  }

  public SimpleXmlRequest<Events> createEventsRequest(final String url, final EventsListener listener) {
    System.out.println("createEventsRequest CALLED");
    return new SimpleXmlRequest<>(Request.Method.GET, url, Events.class,
            new Response.Listener<Events>() {
              @Override
              public void onResponse(Events response) {
                mEvents = response;
                if (mEvents != null && listener != null) listener.onEventsLoaded(mEvents);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                System.out.println("Error al obtener el contenido de " + url);
                VolleyLog.e("Error: ", error.getMessage());
              }
            }
    );
  }


  private Members mMembers;

  public void loadMembers(String url, MembersListener listener) {
    //mUrl = url;
    System.out.println("loadMembers CALLED");
    Cache.Entry cache = getRequestQueue().getCache().get(url);
    if (cache!=null && !cache.refreshNeeded()) {
      String cachedResponse = new String(getRequestQueue().getCache().get(url).data);
      Reader reader = new StringReader(cachedResponse);
      try {
        mMembers = mSerializer.read(Members.class, reader);
        if (mMembers != null && listener != null) listener.onMembersLoaded(mMembers);
      }
      catch (Exception e) { e.printStackTrace(); }
    }
    else addToRequestQueue(createMembersRequest(url, listener));
  }

  public SimpleXmlRequest<Members> createMembersRequest(final String url, final MembersListener listener) {
    System.out.println("createMembersRequest CALLED");
    return new SimpleXmlRequest<>(Request.Method.GET, url, Members.class,
            new Response.Listener<Members>() {
              @Override
              public void onResponse(Members response) {
                mMembers = response;
                if (mMembers != null && listener != null) listener.onMembersLoaded(mMembers);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                System.out.println("Error al obtener el contenido de " + url);
                VolleyLog.e("Error: ", error.getMessage());
              }
            }
    );
  }













  public void loadSectionContent(String url, OnSectionContentLoadedListener listener) {
    mUrl = url;
    Cache.Entry cache = getRequestQueue().getCache().get(url);
    if (cache!=null && !cache.refreshNeeded()) {
      String cachedResponse = new String(getRequestQueue().getCache().get(url).data);
      Reader reader = new StringReader(cachedResponse);
      try {
        mData = mSerializer.read(Data.class, reader);
        if (mData != null && listener != null) listener.onSectionContentLoaded(mData);
      }
      catch (Exception e) { e.printStackTrace(); }
    }
    else addToRequestQueue(createSectionContentRequest(url, listener));
  }

  public SimpleXmlRequest<Data> createSectionContentRequest(final String url, final OnSectionContentLoadedListener listener) {
    return new SimpleXmlRequest<>(Request.Method.GET, url, Data.class,
            new Response.Listener<Data>() {
              @Override
              public void onResponse(Data response) {
                mData = response;
                if (mData != null && listener != null) listener.onSectionContentLoaded(mData);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                System.out.println("Error al obtener el contenido de " + url);
                VolleyLog.e("Error: ", error.getMessage());
              }
            }
    );
  }


  private SimpleXmlRequest<Galaxy> createGalaxyRequest(final OnGalaxyAvailableListener activity) {
    return new SimpleXmlRequest<>(Request.Method.GET, GALAXY_URL, Galaxy.class,
            new Response.Listener<Galaxy>() {
              @Override
              public void onResponse(Galaxy response) {
                System.out.println("Galaxy recibido en DataManager");
                mGalaxy = response;
                System.out.println("Enviando galaxy de DataManager a DebugMainActivity");
                if (activity != null) activity.onGalaxyAvailable(mGalaxy);
                System.out.println("Galaxy enviado de DataManager a DebugMainActivity");
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                System.out.println("Error al obtener galaxy en DataManager");
                Log.e("DataManager", "requestGalaxy ERROR");
                VolleyLog.e("Error: ", error.getMessage());
              }
            }
    );
  }

  public void requestGalaxy(OnGalaxyAvailableListener activity) {
    Cache.Entry cache = getRequestQueue().getCache().get(GALAXY_URL);
    if (cache!=null && !cache.refreshNeeded()) {
      String cachedResponse = new String(getRequestQueue().getCache().get(GALAXY_URL).data);
      Reader reader = new StringReader(cachedResponse);
      try {
        System.out.println("Enviando galaxy desde cache en DataManager");
        mGalaxy = mSerializer.read(Galaxy.class, reader);
        if (activity!=null) activity.onGalaxyAvailable(mGalaxy);
      }
      catch (Exception e) { e.printStackTrace(); }
    }
    else {
      System.out.println("Solicitando galaxy en DataManager");
      addToRequestQueue(createGalaxyRequest(activity));
    }
  }

  public Galaxy getGalaxy() {
    return mGalaxy;
  }

  public interface OnGalaxyAvailableListener {
    void onGalaxyAvailable(Galaxy galaxy);
  }

  public interface OnSectionContentLoadedListener {
    void onSectionContentLoaded(Data data);
  }

  public interface PartnersListener {
    void onPartnersLoaded(Partners partners);
  }

  public interface EventsListener {
    void onEventsLoaded(Events events);
  }

  public interface MembersListener {
    void onMembersLoaded(Members members);
  }

}



  /*
  public void refresh(BaseFragment from, boolean hardRefresh) { // hard refresh
    Cache.Entry cache = getRequestQueue().getCache().get(mUrl);
    if ((hardRefresh && hardCondition(cache)) || (!hardRefresh && softCondition(cache))) {
      String cachedResponse = new String(getRequestQueue().getCache().get(mUrl).data);
      Reader reader = new StringReader(cachedResponse);
      try {
        mData = mSerializer.read(Data.class, reader);
        if (from != null && from.isAdded()) from.refreshed();
      }
      catch (Exception e) { e.printStackTrace(); }
    }
    else addToRequestQueue(createRequest(from));
  }

  private boolean softCondition(Cache.Entry cache) {
    return cache!=null;
  }

  private boolean hardCondition(Cache.Entry cache) {
    return cache!=null && !cache.isExpired() && !cache.refreshNeeded();
  }

  private SimpleXmlRequest<Data> createRequest(final BaseFragment from) {
    return new SimpleXmlRequest<>(Request.Method.GET, mUrl, Data.class,
            new Response.Listener<Data>() {
              @Override
              public void onResponse(Data response) {
                DataManager.this.volleyHit(response, from);
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                DataManager.this.volleyFail(error, from);
              }
            }
    );
  }


  private void volleyHit(Object response, BaseFragment from) {
    if (response != null) mData = (Data) response;
    if (from != null && from.isAdded()) from.refreshed();
  }

  private void volleyFail(VolleyError error, BaseFragment from) {
    if (from != null && from.isAdded()) from.refreshed();
    VolleyLog.e("Error: ", error.getMessage());
  }
  */
