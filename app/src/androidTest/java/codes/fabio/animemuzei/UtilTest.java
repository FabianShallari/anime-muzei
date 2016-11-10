package codes.fabio.animemuzei;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Intent.ACTION_CHOOSER;
import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.EXTRA_INTENT;
import static android.content.Intent.EXTRA_TEXT;
import static android.content.Intent.EXTRA_TITLE;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;

@RunWith(AndroidJUnit4.class) public class UtilTest {

  private static final String MOCK_LINK = "https://www.imgur.com/fakepic";
  private static final String MOCK_MESSAGE = "Mock Message";
  private static final String ALEGREYA_BLACK_ITALIC = "Alegreya-BlackItalic.ttf";
  private static final List<Integer> MOCK_LIST = Arrays.asList(1, 2, 3, 4, 5);

  @Test public void getTypeface_whenValidTypeFaceParameterIsPassed_returnsValidTypefaceObject()
      throws Exception {
    Typeface typeface = Util.getTypeFace(getTargetContext(), ALEGREYA_BLACK_ITALIC);
    assertThat(typeface, is(notNullValue()));
  }

  @Test(expected = RuntimeException.class)
  public void getTypeFace_whenInvalidTypefaceParameterIsPassed_throwsException() {
    Util.getTypeFace(getTargetContext(), "Invalid_TypeFace");
  }

  @Test public void getTypeFace_whenCalledTwice_shouldReturnCachedTypeFace() {
    Typeface typeface = Util.getTypeFace(getTargetContext(), ALEGREYA_BLACK_ITALIC);
    Typeface cachedTypeFace = Util.getTypeFace(getTargetContext(), ALEGREYA_BLACK_ITALIC);
    assertThat(typeface, is(sameInstance(cachedTypeFace)));
  }

  @Test public void randomFromList_returnsItemInArgumentList() throws Exception {
    assertThat(MOCK_LIST, hasItem(Util.randomFromList(MOCK_LIST)));
  }

  @Test public void viewIntent_returnsIntentWithCorrectActionAndData() throws Exception {
    Intent viewIntent = Util.viewIntent(MOCK_LINK);
    assertThat(viewIntent.getAction(), is(ACTION_VIEW));
    assertThat(viewIntent.getData(), is(Uri.parse(MOCK_LINK)));
  }

  @Test public void shareImageIntent_returnsChooserIntentWithCorrectTitle() throws Exception {
    Intent chooserIntent = Util.shareImageIntent(MOCK_MESSAGE);
    assertThat(chooserIntent.getAction(), is(ACTION_CHOOSER));
    assertThat(chooserIntent.getStringExtra(EXTRA_TITLE), is("Share Image Via"));
  }

  @Test public void shareImageIntent_returnsInnerChooserIntentWithCorrectActionAndData() {
    Intent chooserIntent = Util.shareImageIntent(MOCK_MESSAGE);
    Intent innerIntent = chooserIntent.getParcelableExtra(EXTRA_INTENT);
    assertThat(innerIntent.getAction(), is(ACTION_SEND));
    assertThat(innerIntent.getStringExtra(EXTRA_TEXT), is(MOCK_MESSAGE));
  }
}