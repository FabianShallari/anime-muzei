package codes.fabio.animemuzei;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Util {

  private static final Random random = new Random();
  private static final HashMap<String, Typeface> typefaces = new HashMap<>();


  private Util() {
    //no instance
  }

  public static Typeface getTypeFace(Context context, String name) {
    if (!typefaces.containsKey(name)) {
      typefaces.put(name, Typeface.createFromAsset(context.getAssets(), "fonts/" + name));
    }

    return typefaces.get(name);
  }

  public static <T> T randomFromList(List<T> list) {
    random.setSeed(System.currentTimeMillis());
    return list.get(random.nextInt(list.size()));
  }

  public static Intent viewIntent(String link) {
    return new Intent(Intent.ACTION_VIEW, Uri.parse(link));
  }

  public static Intent shareImageIntent(String message) {
    Intent shareImageIntent = new Intent(Intent.ACTION_SEND);
    shareImageIntent.putExtra(Intent.EXTRA_TEXT, message);
    shareImageIntent.setType("text/plain");
    return Intent.createChooser(shareImageIntent, "Share Image Via")
        .addFlags(FLAG_ACTIVITY_NEW_TASK);
  }


}
