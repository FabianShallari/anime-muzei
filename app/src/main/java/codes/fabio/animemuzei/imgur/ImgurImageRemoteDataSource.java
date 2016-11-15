package codes.fabio.animemuzei.imgur;

import android.net.Uri;
import codes.fabio.animemuzei.AppSettings;
import com.google.android.apps.muzei.api.Artwork;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import timber.log.Timber;

import static codes.fabio.animemuzei.Util.randomFromList;
import static codes.fabio.animemuzei.Util.viewIntent;
import static java.util.Collections.unmodifiableList;
import static rx.Observable.just;

@Singleton public class ImgurImageRemoteDataSource {

  @SuppressWarnings("WeakerAccess") final ImgurApi imgurApi;
  private final List<String> sfwAlbumIds;
  private final List<String> nsfwAlbumIds;
  @SuppressWarnings("WeakerAccess") final AppSettings appSettings;

  @Inject ImgurImageRemoteDataSource(ImgurApi imgurApi, @Sfw List<String> sfwAlbumIds,
      @Nsfw List<String> nsfwAlbumIds, AppSettings appSettings) {

    this.imgurApi = imgurApi;
    this.sfwAlbumIds = sfwAlbumIds;
    this.nsfwAlbumIds = nsfwAlbumIds;
    this.appSettings = appSettings;
  }

  public Observable<Artwork> nextImage() {
    return Observable.zip(just(sfwAlbumIds), just(nsfwAlbumIds),
        new Func2<List<String>, List<String>, List<String>>() {
          @Override public List<String> call(List<String> sfwAlbumIds, List<String> nsfwAlbumIds) {
            List<String> albumIds = new ArrayList<>(sfwAlbumIds);
            Timber.d("nsfwEnabled: %s", appSettings.isNsfwEnabled());
            if (appSettings.isNsfwEnabled()) {
              albumIds.addAll(nsfwAlbumIds);
            }
            return unmodifiableList(albumIds);
          }
        }).flatMap(new Func1<List<String>, Observable<ImgurResponse>>() {
      @Override public Observable<ImgurResponse> call(List<String> strings) {
        return imgurApi.album(randomFromList(strings));
      }
    }).map(new Func1<ImgurResponse, Album>() {
      @Override public Album call(ImgurResponse imgurResponse) {
        return imgurResponse.data();
      }
    }).map(new Func1<Album, Image>() {
      @Override public Image call(Album album) {
        return randomFromList(album.images());
      }
    }).map(new Func1<Image, Artwork>() {
      @Override public Artwork call(Image image) {
        Artwork.Builder artworkBuilder = new Artwork.Builder();
        artworkBuilder.imageUri(Uri.parse(image.link()));
        artworkBuilder.token(image.id());
        artworkBuilder.viewIntent(viewIntent(image.link()));
        if (image.title() != null) {
          artworkBuilder.title(image.title());
        }
        if (image.description() != null) {
          artworkBuilder.byline(image.description());
        }
        return artworkBuilder.build();
      }
    });
  }
}
