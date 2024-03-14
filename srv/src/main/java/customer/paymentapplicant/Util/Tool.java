package customer.paymentapplicant.Util;

// import java.text.SimpleDateFormat;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.UUID;

// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import java.text.SimpleDateFormat;
// import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {
    public static String getNowDate_YYYYMMDDHHMMSS(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

        public static Map<String, String> payloadMap(String json) {
        try {
            Map<String, String> event = new ObjectMapper().readValue(json, new TypeReference<Map<String, String>>() {});
            return event;
        } catch (JsonProcessingException e) {
            return new HashMap<>();
        }


    // public static UUID getUUID() {
    //     return UUID.randomUUID();
    // }
    }

}
