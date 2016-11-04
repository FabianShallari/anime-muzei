package codes.fabio.animemuzei.imgur;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

interface ImgurApi {
  @GET("gallery/album/{albumId}") Observable<ImgurResponse> album(@Path("albumId") String albumId);
}