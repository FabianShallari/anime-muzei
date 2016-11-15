package codes.fabio.animemuzei.settings;

import codes.fabio.animemuzei.AnimeMuzeiRoboelectricTestRunner;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.robolectric.RuntimeEnvironment.application;

@RunWith(AnimeMuzeiRoboelectricTestRunner.class) public class UpdateIntervalTest {

  private List<UpdateInterval> formattableUpdateIntervals;
  private List<UpdateInterval> nonFormattableUpdateIntervals;

  @Before public void setUp() throws Exception {
    formattableUpdateIntervals =
        asList(UpdateInterval.create(1, MINUTES), UpdateInterval.create(3, HOURS),
            UpdateInterval.create(1, DAYS));

    nonFormattableUpdateIntervals =
        asList(UpdateInterval.create(1, NANOSECONDS), UpdateInterval.create(1, MICROSECONDS),
            UpdateInterval.create(1, MILLISECONDS), UpdateInterval.create(1, SECONDS));
  }

  @Test public void equalsMillis() throws Exception {
    assertThat(UpdateInterval.create(1, DAYS).equalsMillis(DAYS.toMillis(1)), is(true));
  }

  @Test public void toMillis() throws Exception {
    assertThat(UpdateInterval.create(2, MINUTES).toMillis(), is(MINUTES.toMillis(2)));
  }

  @Test public void format_returnsCorrectFormatForValidUpdateInterval() throws Exception {
    for (UpdateInterval updateInterval : formattableUpdateIntervals) {
      assertThat(updateInterval.format(application), is(notNullValue()));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void format_throwsIllegalArgumentExcepationForInvalidUpdateInterval_nano()
      throws Exception {
    nonFormattableUpdateIntervals.get(0).format(application);
  }

  @Test(expected = IllegalArgumentException.class)
  public void format_throwsIllegalArgumentExcepationForInvalidUpdateInterval_micro()
      throws Exception {
    nonFormattableUpdateIntervals.get(1).format(application);
  }

  @Test(expected = IllegalArgumentException.class)
  public void format_throwsIllegalArgumentExcepationForInvalidUpdateInterval_milli()
      throws Exception {
    nonFormattableUpdateIntervals.get(2).format(application);
  }

  @Test(expected = IllegalArgumentException.class)
  public void format_throwsIllegalArgumentExcepationForInvalidUpdateInterval_second()
      throws Exception {
    nonFormattableUpdateIntervals.get(3).format(application);
  }
}