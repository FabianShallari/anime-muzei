package codes.fabio.animemuzei.imgur;

import android.net.Uri;
import com.google.android.apps.muzei.api.Artwork;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.functions.Func1;

import static codes.fabio.animemuzei.imgur.Util.randomFromList;
import static codes.fabio.animemuzei.imgur.Util.viewIntent;

@Singleton public class ImgurImageRemoteDataSource {

  private final ImgurApi imgurApi;
  private final List<String> albums;

  @Inject ImgurImageRemoteDataSource(ImgurApi imgurApi, List<String> albums) {
    this.imgurApi = imgurApi;
    this.albums = albums;
  }

  public Observable<Artwork> nextImage() {
    return imgurApi.album(randomFromList(albums)).map(new Func1<ImgurResponse, Album>() {
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
