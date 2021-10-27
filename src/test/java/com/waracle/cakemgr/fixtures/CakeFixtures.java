package com.waracle.cakemgr.fixtures;

import com.waracle.cakemgr.domain.Cake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public final class CakeFixtures {

    public static final String VICTORIA_SPONGE_TITLE = "victoria sponge";
    public static final String VICTORIA_SPONGE_DESC = "sponge with jam";
    public static final String VICTORIA_SPONGE_IMAGE = "http://victoriasponge.jpg";
    public static final String LEMON_CHEESECAKE_TITLE = "Lemon cheesecake";
    public static final String LEMON_CHEESECAKE_DESC = "A cheesecake made of lemon";
    public static final String LEMON_CHEESECAKE_IMAGE = "http://lemoncheesecake.jpg";
    public static final String CARROT_CAKE_TITLE = "Carrot cake";
    public static final String CARROT_CAKE_DESC = "Bugs bunnys favourite";
    public static final String CARROT_CAKE_IMAGE = "http://carrotcake.jpg";
    public static final String BANANA_CAKE_TITLE = "Banana cake";
    public static final String BANANA_CAKE_DESC = "Donkey kongs favourite";
    public static final String BANANA_CAKE_IMAGE = "http://bananacake.jpg";
    public static final String BIRTHDAY_CAKE_TITLE = "Birthday cake";
    public static final String BIRTHDAY_CAKE_DESC = "a yearly treat";
    public static final String BIRTHDAY_CAKE_IMAGE = "http://birthdaycake.jpg";

    private CakeFixtures() {}

    public static Cake victoriaSponge() {
        return Cake.builder()
                .title(VICTORIA_SPONGE_TITLE)
                .desc(VICTORIA_SPONGE_DESC)
                .image(VICTORIA_SPONGE_IMAGE)
                .build();
    }

    public static JSONObject victoriaSpongeJson() {
        return cakeJson(VICTORIA_SPONGE_TITLE,
                VICTORIA_SPONGE_DESC,
                VICTORIA_SPONGE_IMAGE);
    }

    public static Cake lemonCheeseCake() {
        return Cake.builder()
                .title(LEMON_CHEESECAKE_TITLE)
                .desc(LEMON_CHEESECAKE_DESC)
                .image(LEMON_CHEESECAKE_IMAGE)
                .build();
    }

    public static JSONObject lemonCheeseCakeJson() {
        return cakeJson(LEMON_CHEESECAKE_TITLE,
                LEMON_CHEESECAKE_DESC,
                LEMON_CHEESECAKE_IMAGE);
    }

    public static Cake carrotCake() {
        return Cake.builder()
                .title(CARROT_CAKE_TITLE)
                .desc(CARROT_CAKE_DESC)
                .image(CARROT_CAKE_IMAGE)
                .build();
    }

    public static JSONObject carrotCakeJson() {
        return cakeJson(CARROT_CAKE_TITLE,
                CARROT_CAKE_DESC,
                CARROT_CAKE_IMAGE);
    }

    public static Cake bananaCake() {
        return Cake.builder()
                .title(BANANA_CAKE_TITLE)
                .desc(BANANA_CAKE_DESC)
                .image(BANANA_CAKE_IMAGE)
                .build();
    }

    public static JSONObject bananaCakeJson() {
        return cakeJson(BANANA_CAKE_TITLE,
                BANANA_CAKE_DESC,
                BANANA_CAKE_IMAGE);
    }

    public static Cake birthdayCake() {
        return Cake.builder()
                .title(BIRTHDAY_CAKE_TITLE)
                .desc(BIRTHDAY_CAKE_DESC)
                .image(BIRTHDAY_CAKE_IMAGE)
                .build();
    }

    public static JSONObject birthdayCakeJson() {
        return cakeJson(BIRTHDAY_CAKE_TITLE,
                BIRTHDAY_CAKE_DESC,
                BIRTHDAY_CAKE_IMAGE);
    }

    public static List<Cake> allCakes() {
        return List.of(victoriaSponge(), lemonCheeseCake(),
        carrotCake(), bananaCake(), birthdayCake());
    }

    public static JSONArray allCakesJson() {
        return new JSONArray()
                .put(victoriaSpongeJson())
                .put(lemonCheeseCakeJson())
                .put(carrotCakeJson())
                .put(bananaCakeJson())
                .put(birthdayCakeJson());
    }

    public static JSONObject cakeJson(String title, String desc, String image) {
        JSONObject cakeJson = null;
        try {
            cakeJson = new JSONObject()
                    .put("title", title)
                    .put("desc", desc)
                    .put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cakeJson;
    }

}