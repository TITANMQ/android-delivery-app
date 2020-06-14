package com.example.deliveryapp.dummy;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
public class ReviewItemContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Review> REVIEWS = new ArrayList<Review>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Review> ITEM_MAP = new HashMap<String, Review>();

    private static final int COUNT = 12;

    static {
        // Add some sample items.

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");

        Random rand = new Random();

        LocalDate date =  LocalDate.now();
        for (int i = 1; i <= COUNT; i++) {
            addReview(new Review("user" + i, "Title of review" + i , dtf.format(date),
                    rand.nextInt(5) + 1 , "Review message" ));
        }
    }

    private static void addReview(Review review) {
        REVIEWS.add(review);
        ITEM_MAP.put(review.id, review);
    }

//    private static Review createReview(int position) {
//        return new Review(String.valueOf(position), "Item " + position, makeDetails(position));
//    }






    /**
     * A dummy item representing a piece of content.
     */
    public static class Review {
        public final String id;
        public final String title;
        public final String date;
        public final int stars;
        public final String message;

        public Review(String id, String title, String date, int stars, String message) {
            this.id = id;
            this.title = title;
            this.date = date;
            this.stars = stars;
            this.message = message;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public int getStars() {
            return stars;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "Review{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", date='" + date + '\'' +
                    ", stars=" + stars +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
