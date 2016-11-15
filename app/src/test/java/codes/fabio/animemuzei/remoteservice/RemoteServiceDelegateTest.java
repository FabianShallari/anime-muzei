package codes.fabio.animemuzei.remoteservice;

import codes.fabio.animemuzei.AppSettings;
import codes.fabio.animemuzei.imgur.ImgurImageRemoteDataSource;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.android.apps.muzei.api.UserCommand;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RemoteServiceDelegateTest {

  private RemoteServiceDelegate delegate;
  private List<UserCommand> userCommandList;
  private AppSettings appSettings;
  private final int MOCK_REASON = 1;
  private ImgurImageRemoteDataSource imgurImageRemoteDataSource;
  private AnimeMuzeiRemoteSourceService animeMuzeiRemoteSourceService;

  @Before public void setUp() {
    UserCommand userCommand1 = mock(UserCommand.class);
    UserCommand userCommand2 = mock(UserCommand.class);

    userCommandList = asList(userCommand1, userCommand2);
    appSettings = mock(AppSettings.class);
    imgurImageRemoteDataSource = mock(ImgurImageRemoteDataSource.class);
    animeMuzeiRemoteSourceService = mock(AnimeMuzeiRemoteSourceService.class);

    when(appSettings.getUpdateIntervalMillis()).thenReturn(1000L);

    delegate = new RemoteServiceDelegate(userCommandList, appSettings, imgurImageRemoteDataSource);
  }

  @Test public void attachService_storesServiceFieldInValue() throws Exception {
    delegate.attachService(animeMuzeiRemoteSourceService);
    assertThat(delegate.getService(), is(animeMuzeiRemoteSourceService));
  }

  @Test public void attachService_setsCustomUserCommands() {
    delegate.attachService(animeMuzeiRemoteSourceService);
    verify(delegate.getService()).setCustomCommands(userCommandList);
  }

  @Test(expected = IllegalStateException.class)
  public void accessingServiceAfter_detachService_throwsException() {
    delegate.attachService(animeMuzeiRemoteSourceService);
    delegate.detachService();
    delegate.getService();
  }

  @Test public void rescheduleOnly() throws Exception {
    delegate.attachService(animeMuzeiRemoteSourceService);
    delegate.rescheduleOnly();
    verify(delegate.getService()).setNextUpdate(anyLong());
  }

  @Test(expected = RemoteMuzeiArtSource.RetryException.class)
  public void tryUpdate_throwsRetryException_InCaseImageResponseThrowsException() throws Exception {

    when(imgurImageRemoteDataSource.nextImage()).thenThrow(new RuntimeException());

    delegate.attachService(animeMuzeiRemoteSourceService);
    delegate.tryUpdate(MOCK_REASON);
  }

  @Test public void tryUpdate_setsArtwork_andReschedules() throws Exception {
    Artwork artwork = mock(Artwork.class);
    when(imgurImageRemoteDataSource.nextImage()).thenReturn(Observable.just(artwork));

    delegate.attachService(animeMuzeiRemoteSourceService);
    delegate.tryUpdate(MOCK_REASON);
    verify(delegate.getService()).setArtwork(artwork);
    verify(delegate.getService()).setNextUpdate(anyLong());
  }
}