package codes.fabio.animemuzei.imgur;

import android.content.Intent;
import android.net.Uri;
import codes.fabio.animemuzei.AnimeMuzeiRoboelectricTestRunner;
import codes.fabio.animemuzei.AppSettings;
import com.google.android.apps.muzei.api.Artwork;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;
import rx.Observable;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AnimeMuzeiRoboelectricTestRunner.class) public class ImgurImageRemoteDataSourceTest {

  private BehaviorDelegate<ImgurApi> imgurApiBehaviorDelegate;
  private List<String> sfwAlbumIds;
  private List<String> nsfwAlbumIds;
  private AppSettings appSettings;
  private static final String MOCK_ALBUM_ID = "mock_album_id";

  @Before public void setup() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.testurl.com")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    NetworkBehavior networkBehavior = NetworkBehavior.create();
    MockRetrofit mockRetrofit =
        new MockRetrofit.Builder(retrofit).networkBehavior(networkBehavior).build();

    imgurApiBehaviorDelegate = mockRetrofit.create(ImgurApi.class);

    sfwAlbumIds = asList("aaa", "bbb", "ccc");
    nsfwAlbumIds = asList("ddd", "fff", "eee");
    appSettings = mock(AppSettings.class);

    when(appSettings.isNsfwEnabled()).thenReturn(true);
  }

  @Test public void nextImage_returnsArtworkMappedFromImageInResponse() throws Exception {
    Image mockImage = Image.create("id", "title", "description", "https://i.imgur.com/test");
    ImgurApi mockImgurApi = new MockImgurApi(imgurApiBehaviorDelegate, mockImage);

    ImgurImageRemoteDataSource imgurImageRemoteDataSource =
        new ImgurImageRemoteDataSource(mockImgurApi, sfwAlbumIds, nsfwAlbumIds, appSettings);

    Artwork artwork = imgurImageRemoteDataSource.nextImage().toBlocking().first();

    assertThat(artwork.getToken(), is(equalTo(mockImage.id())));
    assertThat(artwork.getTitle(), is(equalTo(mockImage.title())));
    assertThat(artwork.getByline(), is(equalTo(mockImage.description())));
    assertThat(artwork.getImageUri(), is(equalTo(Uri.parse(mockImage.link()))));
    assertThat(artwork.getViewIntent().getAction(), is(equalTo(Intent.ACTION_VIEW)));
    assertThat(artwork.getViewIntent().getData(), is(equalTo(Uri.parse(mockImage.link()))));
  }

  @Test public void nextImage_throwsIOException() throws Exception {

    ImgurApi failingMockImgurApi = new MockFailingImgurApi(imgurApiBehaviorDelegate);

    ImgurImageRemoteDataSource imgurImageRemoteDataSource =
        new ImgurImageRemoteDataSource(failingMockImgurApi, sfwAlbumIds, nsfwAlbumIds, appSettings);

    try {
      imgurImageRemoteDataSource.nextImage().toBlocking().first();
    } catch (Exception e) {
      assertThat(e.getCause(), is(instanceOf(IOException.class)));
    }

    fail();
  }

  @SuppressWarnings("WeakerAccess") ImgurResponse mockImgurResponseWithSingleImage(Image image) {
    Album album = Album.create("albumId", "Album title", "Album description", singletonList(image));
    return ImgurResponse.create(album, true, 200);
  }

  private class MockImgurApi implements ImgurApi {

    private final BehaviorDelegate<ImgurApi> imgurApiBehaviorDelegate;
    private final Image image;

    MockImgurApi(BehaviorDelegate<ImgurApi> imgurApiBehaviorDelegate, Image image) {
      this.imgurApiBehaviorDelegate = imgurApiBehaviorDelegate;
      this.image = image;
    }

    @Override public Observable<ImgurResponse> album(@Path("albumId") String albumId) {
      return imgurApiBehaviorDelegate.returningResponse(mockImgurResponseWithSingleImage(image))
          .album(albumId);
    }
  }

  private class MockFailingImgurApi implements ImgurApi {
    private final BehaviorDelegate<ImgurApi> imgurApiBehaviorDelegate;

    MockFailingImgurApi(BehaviorDelegate<ImgurApi> imgurApiBehaviorDelegate) {
      this.imgurApiBehaviorDelegate = imgurApiBehaviorDelegate;
    }

    @Override public Observable<ImgurResponse> album(@Path("albumId") String albumId) {
      return imgurApiBehaviorDelegate.returning(Calls.failure(new IOException()))
          .album(albumId);
    }
  }
}