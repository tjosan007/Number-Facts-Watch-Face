package com.tarwinderjosan.numberfactswatchface.util;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Helps updates the preferences when data is sent from the mobile,
 * and sends back a confirmation too.
 */
public class PreferenceUpdater {

    private static GoogleApiClient mClient;
    private static String nodeID = "";
    private static final int CONNECTION_TIME_OUT_MS = 100;

    /**
     * Called when a JSON formatted preference String is received from the mobile device.
     * @param json The preference formatted JSON String
     * @param context Application context
     */
    public static void update(String json, Context context) throws JSONException {
        initApiClient(context);
        JSONObject parent = new JSONObject(json);
        JSONArray objects = parent.getJSONArray("objects");
        for(int i=0;i<objects.length(); i++) {
            JSONObject obj = objects.getJSONObject(i);
            // Each obj has an identifier (pref key)
            String prefIdent = obj.getString("identifier");
            // Each obj has a value (t/f for cbprefs, n for lprefs prefs)
            String prefVal = obj.getString("value");
            // Update preferences with the new values

            // Store in watch preferences
            Prefs.init(context).putString(prefIdent, prefVal);
        }


    }

    private static void initApiClient(Context context) {
        mClient = getGoogleApiClient(context);
        retrieveDeviceNode();
    }

    private static void retrieveDeviceNode() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                mClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(mClient).await();
                List<Node> nodes = result.getNodes();
                if(nodes.size() > 0) {
                    nodeID = nodes.get(1).getId();
                    Log.d("TAG", "Node ID: " + nodeID);
                }
                mClient.disconnect();
            }
        }).start();
    }


    private static GoogleApiClient getGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();
    }
}
