package codes.fabio.animemuzei.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import codes.fabio.animemuzei.AnimeMuzeiRoboelectricTestRunner;
import codes.fabio.animemuzei.R;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.RuntimeEnvironment.application;

@RunWith(AnimeMuzeiRoboelectricTestRunner.class) public class UpdateIntervalSpinnerAdapterTest {

  private List<UpdateInterval> updateIntervals;
  private LayoutInflater layoutInflater;
  private UpdateIntervalSpinnerAdapter updateIntervalSpinnerAdapter;

  @Before public void setUp() {
    updateIntervals = getDummyUpdateIntervals();
    layoutInflater = mock(LayoutInflater.class);
    updateIntervalSpinnerAdapter =
        new UpdateIntervalSpinnerAdapter(layoutInflater, updateIntervals);
  }

  @Test public void getCount_returnsListSize() throws Exception {
    assertThat(updateIntervalSpinnerAdapter.getCount(), is(updateIntervals.size()));
  }

  @Test public void getItem_returnsItemFromList() throws Exception {
    for (int i = 0; i < updateIntervals.size(); i++) {
      UpdateInterval updateInterval = ((UpdateInterval) updateIntervalSpinnerAdapter.getItem(i));
      assertThat(updateInterval, is(sameInstance(updateIntervals.get(i))));
    }
  }

  @Test public void getItemId_returnsItemsPositionsInList() throws Exception {
    for (int i = 0; i < updateIntervals.size(); i++) {
      assertThat(updateIntervalSpinnerAdapter.getItemId(i), is((long) i));
    }
  }

  @Test public void getView_shouldBindDataAndReturnViewWithoutConvertView() {
    ViewGroup container = mock(ViewGroup.class);

    for (int position = 0; position < updateIntervals.size(); position++) {
      View view = mock(View.class);
      TextView updateIntervalTextView = mock(TextView.class);

      when(layoutInflater.inflate(R.layout.update_interval_view, container, false)).thenReturn(
          view);
      when(updateIntervalTextView.getContext()).thenReturn(application.getApplicationContext());
      when(view.findViewById(R.id.updateIntervalText)).thenReturn(updateIntervalTextView);

      assertThat(updateIntervalSpinnerAdapter.getView(position, null, container),
          is(sameInstance(view)));
      verify(updateIntervalTextView).setText(anyString());
    }
  }

  @Test public void getView_shouldBindDataAndReturnViewWithConvertView() {
    ViewGroup container = mock(ViewGroup.class);

    for (int position = 0; position < updateIntervals.size(); position++) {
      View view = mock(View.class);
      UpdateIntervalViewHolder viewHolder = mock(UpdateIntervalViewHolder.class);

      when(view.getTag()).thenReturn(viewHolder);

      assertThat(updateIntervalSpinnerAdapter.getView(position, view, container),
          is(sameInstance(view)));
      verify(viewHolder).bind(updateIntervals.get(position));
    }
  }

  private List<UpdateInterval> getDummyUpdateIntervals() {
    return asList(UpdateInterval.create(5, MINUTES), UpdateInterval.create(10, DAYS));
  }
}