package codes.fabio.animemuzei.imgur;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue abstract class Image {
  abstract String id();

  @Nullable abstract String title();

  @Nullable abstract String description();

  abstract String link();

  static Image create(String id, @Nullable String title, @Nullable String description,
      String link) {

    return new AutoValue_Image(id, title, description, link);
  }

  @SuppressWarnings("WeakerAccess") public static TypeAdapter<Image> typeAdapter(Gson gson) {
    return new AutoValue_Image.GsonTypeAdapter(gson);
  }
}