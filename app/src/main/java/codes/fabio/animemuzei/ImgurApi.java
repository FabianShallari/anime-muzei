package codes.fabio.animemuzei;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fabian on 11/4/16.
 */

interface ImgurApi {
  @GET("gallery/album/{albumId}") Call<ResponseBody> album(@Path("albumId") String albumId);
}