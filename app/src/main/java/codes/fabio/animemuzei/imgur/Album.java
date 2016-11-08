package codes.fabio.animemuzei.imgur;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.List;

@AutoValue abstract class Album {
  public abstract String id();

  @Nullable abstract String title();

  @Nullable abstract String description();

  abstract List<Image> images();

  @SuppressWarnings("WeakerAccess") public static TypeAdapter<Album> typeAdapter(Gson gson) {
    return new AutoValue_Album.GsonTypeAdapter(gson);
  }
}
