package com.example.deliveryapp.dummy;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.deliveryapp.Delivery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class DeliveryContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DeliveryItem> ITEMS = new ArrayList<DeliveryItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DeliveryItem> ITEM_MAP = new HashMap<String, DeliveryItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

        Random rand = new Random();

        LocalDateTime dateTime =  LocalDateTime.now();
        for (int i = 1; i <= COUNT; i++) {
            addItem(new DeliveryContent.DeliveryItem(i+ " collection address, town, county, postcode",
                    i+ " Delivery address, town, county, postcode" ,
                    rand.nextInt(51) % 2 == 0?"food" : "Sanitaries",
                    dtf.format(dateTime) ));
        }
    }




    private static void addItem(DeliveryItem item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

//    private static DeliveryItem createDummyItem(int position) {
//        return new DeliveryItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }
//
//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DeliveryItem {
        private Delivery delivery;
        public final String collectionAddress;
        public final String deliveryAddress;
        public final String deliveryType;
        public final String expiryDate;

        public DeliveryItem(String collectionAddress, String deliveryAddress, String deliveryType,
                            String expiryDate) {
            this.collectionAddress = collectionAddress;
            this.deliveryAddress = deliveryAddress;
            this.deliveryType = deliveryType;
            this.expiryDate = expiryDate;
        }

        public DeliveryItem(Delivery delivery){

            this.delivery = delivery;
            this.collectionAddress = delivery.getCollectionAddress();
            this.deliveryAddress = delivery.getDeliveryAddress();
            this.deliveryType = delivery.getDeliveryType();
            this.expiryDate = delivery.getExpiryDate();
        }

        public Delivery getDelivery() {
            return delivery;
        }

        @Override
        public String toString() {
            return "DeliveryItem{" +
                    "collectionAddress='" + collectionAddress + '\'' +
                    ", deliveryAddress='" + deliveryAddress + '\'' +
                    ", deliveryType='" + deliveryType + '\'' +
                    ", expiryDate='" + expiryDate + '\'' +
                    '}';
        }
    }
}
