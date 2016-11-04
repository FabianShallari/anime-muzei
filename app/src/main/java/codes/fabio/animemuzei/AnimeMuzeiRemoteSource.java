package codes.fabio.animemuzei;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;

public class AnimeMuzeiRemoteSource extends RemoteMuzeiArtSource {

  private static final String SOURCE_NAME = "AnimeMuzeiArtSource";

  public AnimeMuzeiRemoteSource(String name) {
    super(name);
  }

  public AnimeMuzeiRemoteSource() {
    this(SOURCE_NAME);
  }

  @Override public void onCreate() {
    super.onCreate();
  }

  @Override protected void onTryUpdate(int reason) throws RetryException {

  }
}
