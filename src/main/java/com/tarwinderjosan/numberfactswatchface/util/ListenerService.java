package com.tarwinderjosan.numberfactswatchface.util;

import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Handles data received from the mobile device.
 */
public class ListenerService extends WearableListenerService {
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);

        Log.d("TAG", "Received update");
        for(DataEvent event : dataEvents) {
            if(event.getType() == DataEvent.TYPE_CHANGED) {
                String path = event.getDataItem().getUri().getPath();
                switch(path) {

                    // Preferences updated
                    case "/update":
                        Log.d("TAG", "Is path /update");
                        DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                        String json = dataMapItem.getDataMap().getString("preference");
                        try {
                            PreferenceUpdater.update(json, this.getApplicationContext());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    // Fact pack images sent from device
                    case "/images":
                        DataMapItem dataMapItem2 = DataMapItem.fromDataItem(event.getDataItem());
                        ArrayList<byte[]> data = new ArrayList<>();
                        String pack = dataMapItem2.getDataMap().getString("factpackname");
                        int numImages = Integer.parseInt(dataMapItem2.getDataMap().getString("numberofimages"));

                        // Initial default "general" pack contains 60 images. 'a'
                        if(pack == "general60") {
                            for (int j = 0; j < 60; j++) {
                                byte[] imageContents = dataMapItem2.getDataMap().getByteArray("" + j);
                                data.add(imageContents);
                            }
                        } else {
                            // in the SQLite db, each column (minute) has 3 facts, so total of 180 facts (60 * 3)
                            // first fact, 'a' + minute, second fact, 'b' + minute, third fact, 'c' + minute

                            // 97 to 99 (both inclusive) are ascii values a, b, c
                            for (int i = 97; i <= 99; i++) {
                                int charVal = (char) i;
                                for (int j = ((i - 97) * 60); j < (((i - 97) + 1) * 60); j++) {
                                    byte[] imageContents = dataMapItem2.getDataMap().getByteArray("" + j);
                                    data.add(imageContents);
                                }
                            }
                        }
                        break;
                } // end switch
            }
        } // end for-each

    } // end method
} // end class3
