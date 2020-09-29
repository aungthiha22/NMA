package com.rebook.nma.util;

import android.content.Context;
import android.util.DisplayMetrics;
import com.google.gson.JsonObject;
import retrofit.Callback;

public class Utils {

  public static int getPx(Context context, int requiredDp) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return (int) ((requiredDp * displayMetrics.density) + 0.5);

  }
  public static void AddToFavorite(Context context, String type, String cat, String id,
                                   Callback<JsonObject> callback) { }
}
