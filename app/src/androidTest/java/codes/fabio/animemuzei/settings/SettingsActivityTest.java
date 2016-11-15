package codes.fabio.animemuzei.settings;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import codes.fabio.animemuzei.AppSettings;
import codes.fabio.animemuzei.R;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static codes.fabio.animemuzei.Util.randomFromList;
import static java.util.Collections.unmodifiableList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class) public class SettingsActivityTest {

  private AppSettings appSettings;
  private String appName;
  private List<UpdateInterval> updateIntervalChoices;

  @Rule public ActivityTestRule<SettingsActivity> activityActivityTestRule =
      new ActivityTestRule<>(SettingsActivity.class);

  @Before public void setUp() {
    appName = getTargetContext().getString(R.string.app_name);
    appSettings = activityActivityTestRule.getActivity().appSettings;
    updateIntervalChoices = getUpdateIntervalChoices(
        activityActivityTestRule.getActivity().updateIntervalSpinnerAdapter);
  }

  @Test public void appName_shouldBeDisplayed() {
    onView(withId(R.id.appName)).check(matches(withText(equalToIgnoringCase(appName))));
  }

  @Test public void nsfwCheckboxState_byDefault_shouldBeSameAsAppSettings() {
    boolean defaultNSFW = appSettings.isNsfwEnabled();
    onView(withId(R.id.nsfw)).check(matches(defaultNSFW ? isChecked() : isNotChecked()));
  }

  @Test public void nsfwChecboxClick_shouldToggleCheckedState() {
    boolean wasChecked = activityActivityTestRule.getActivity().nsfwCheckBox.isChecked();

    onView(withId(R.id.nsfw)).perform(click());
    onView(withId(R.id.nsfw)).check(matches(wasChecked ? isNotChecked() : isChecked()));
  }

  @Test public void nsfwCheckBoxClick_shouldToggleAppSettings() {
    boolean wasEnabled = appSettings.isNsfwEnabled();
    onView(withId(R.id.nsfw)).perform(click());
    assertThat(appSettings.isNsfwEnabled(), is(!wasEnabled));
  }

  @Test public void spinner_shouldBeDisplayed() {
    onView(withId(R.id.updateInterval)).check(matches(isDisplayed()));
  }

  @Test public void spinnerSelection_shouldShowNewUpdateInterval() {
    UpdateInterval spinnerItemToSelect = differentChoice();

    onView(withId(R.id.updateInterval)).perform(click());
    onData(allOf(is(instanceOf(UpdateInterval.class)), is(spinnerItemToSelect))).perform(click());
    onView(withId(R.id.updateInterval)).check(
        matches(withSpinnerText(spinnerItemToSelect.toString())));
  }

  @Test public void spinnerSelection_shouldUpdateAppSettings() {
    UpdateInterval spinnerItemToSelect = differentChoice();

    onView(withId(R.id.updateInterval)).perform(click());
    onData(allOf(is(instanceOf(UpdateInterval.class)), is(spinnerItemToSelect))).perform(click());
    assertThat(appSettings.getUpdateIntervalMillis(), is(spinnerItemToSelect.toMillis()));
  }

  private UpdateInterval differentChoice() {
    List<UpdateInterval> differentChoices = new ArrayList<>(updateIntervalChoices);
    differentChoices.remove(getCurrentChoice());
    return randomFromList(differentChoices);
  }

  private UpdateInterval getCurrentChoice() {
    return ((UpdateInterval) activityActivityTestRule.getActivity().updateIntervalSpinner.getSelectedItem());
  }

  private List<UpdateInterval> getUpdateIntervalChoices(
      UpdateIntervalSpinnerAdapter updateIntervalSpinnerAdapter) {

    List<UpdateInterval> choices = new ArrayList<>();
    for (int i = 0; i < updateIntervalSpinnerAdapter.getCount(); i++) {
      choices.add((UpdateInterval) updateIntervalSpinnerAdapter.getItem(i));
    }
    return unmodifiableList(choices);
  }
}