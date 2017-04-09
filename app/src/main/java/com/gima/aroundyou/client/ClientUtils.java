package com.gima.aroundyou.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class contains some utility classes required for indexing
 */

public class ClientUtils {

    public static List<Map<String, Object>> getLocationDataAsArray(List<IndexInputDocument> documents) {
        List<Map<String, Object>> locationData = new ArrayList<>();
        for (IndexInputDocument document : documents) {
            locationData.add(document.getFields());
        }
        return locationData;
    }
}
