package codes.fabio.animemuzei;

import android.content.SharedPreferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.MODE_PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.robolectric.RuntimeEnvironment.application;

@RunWith(AnimeMuzeiRoboelectricTestRunner.class)
public class AppSettingsTest {

  private SharedPreferences sharedPreferences;
  private AppSettings appSettings;

  @Before public void setUp() {
    sharedPreferences = application.getSharedPreferences("TEST_PREFS", MODE_PRIVATE);
    appSettings = new AppSettings(sharedPreferences);
  }

  @After public void tearDown() {
    sharedPreferences.edit().clear().apply();
  }

  @Test public void isNsfwEnabled() throws Exception {
    appSettings.setNsfwEnabled(true);
    assertThat(appSettings.isNsfwEnabled(), is(true));

    appSettings.setNsfwEnabled(false);
    assertThat(appSettings.isNsfwEnabled(), is(false));
  }

  @Test public void getUpdateIntervalMillis() throws Exception {
    appSettings.setUpdateIntervalMillis(2000L);
    assertThat(appSettings.getUpdateIntervalMillis(), is(2000L));
  }
}