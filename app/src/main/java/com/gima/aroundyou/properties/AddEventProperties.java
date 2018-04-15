package com.gima.aroundyou.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class works as a singleton which keep the user input provided for adding new events
 */

public class AddEventProperties {

    private Map<String, Object> properties;
    private static AddEventProperties addEventProperties = new AddEventProperties();

    private static final String EVENT_DESC = "event.desc";
    private static final String Event_TITLE = "event.title";
    private static final String EVENT_EXPIRY_DATE = "expiry.date";
    private static final String EVENT_LOCATION = "event.location";
    private static final String EVENT_CATEGORY = "event.category";

    public static AddEventProperties getInstance() {
        return addEventProperties;
    }
    private AddEventProperties() {
        properties = new HashMap<>();
    }

    public void setEventLocation(com.google.android.gms.maps.model.LatLng location) {
        properties.put(EVENT_LOCATION, location);
    }

    public void setEventDescription(String desc) {
        properties.put(EVENT_DESC, desc);
    }

    public void setEventTitle(String title) {
        properties.put(Event_TITLE, title);
    }

    public void setEventExpiryDate(Long date) {
        properties.put(EVENT_EXPIRY_DATE, date);
    }

    public String getEventDescription() {
        return (String)properties.get(EVENT_DESC);
    }

    public String getEventTitle() {
        return (String)properties.get(Event_TITLE);
    }

    public Object getEventExpiryDate() {
        return properties.get(EVENT_EXPIRY_DATE);
    }

    public Object getEventLocation() {
        return properties.get(EVENT_LOCATION);
    }

    public String getEventCategories() {

        List<String> tags = (List<String>) properties.get(EVENT_CATEGORY);
        String strTags = "";
        for (int i=0; i < tags.size(); i++) {
            if (i > 0) {
                strTags += ", ";
            }
            strTags += tags.get(i);
        }
        return strTags;
    }
    public void setEventCategories(String[] category) {
        if (category != null) {
            List<String> tags = (List<String>) properties.get(EVENT_CATEGORY);
            if (tags == null) {
                tags = new ArrayList<>();
            }
            tags.addAll(Arrays.asList(category));
            properties.put(EVENT_CATEGORY, tags);
        } else {
            properties.remove(EVENT_CATEGORY);
        }
    }
    public void reset() {
        properties.clear();

    }
}
