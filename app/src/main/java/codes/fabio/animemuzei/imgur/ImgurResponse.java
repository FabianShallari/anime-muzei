package codes.fabio.animemuzei.imgur;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue abstract class ImgurResponse {

  abstract Album data();

  abstract boolean success();

  abstract int status();

  static ImgurResponse create(Album data, boolean success, int status) {
    return new AutoValue_ImgurResponse(data, success, status);
  }

  @SuppressWarnings("WeakerAccess")
  public static TypeAdapter<ImgurResponse> typeAdapter(Gson gson) {
    return new AutoValue_ImgurResponse.GsonTypeAdapter(gson);
  }
}
