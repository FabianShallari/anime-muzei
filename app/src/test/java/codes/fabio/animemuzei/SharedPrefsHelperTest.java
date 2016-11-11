package codes.fabio.animemuzei;

import android.content.SharedPreferences;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class) @Config(constants = BuildConfig.class, sdk = 23)
public class SharedPrefsHelperTest {

  @Mock SharedPreferences sharedPreferences;
  private SharedPrefsHelper sharedPrefsHelper;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
    sharedPrefsHelper = new SharedPrefsHelper(sharedPreferences);
  }

  @Test public void isNsfwEnabled() throws Exception {

  }

  @Test public void setNsfwEnabled() throws Exception {

  }

  @Test public void getUpdateIntervalMillis() throws Exception {

  }

  @Test public void setUpdateIntervalMillis() throws Exception {

  }
}