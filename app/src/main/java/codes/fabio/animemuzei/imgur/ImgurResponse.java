package codes.fabio.animemuzei.imgur;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by fabian on 11/4/16.
 */

@AutoValue abstract class ImgurResponse {

  abstract Album data();

  abstract boolean success();

  abstract int status();

  public static TypeAdapter<ImgurResponse> typeAdapter(Gson gson) {
    return new AutoValue_ImgurResponse.GsonTypeAdapter(gson);
  }
}
