package codes.fabio.animemuzei.settings;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import codes.fabio.animemuzei.R;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdateIntervalViewHolderTest {

  private Context context;
  private TextView updateIntervalTextView;
  private UpdateIntervalViewHolder viewHolder;
  private UpdateInterval updateInterval;

  @Before public void setUp() throws Exception {

    View itemView = mock(View.class);
    updateIntervalTextView = mock(TextView.class);
    updateInterval = mock(UpdateInterval.class);
    context = mock(Context.class);

    when(itemView.findViewById(R.id.updateIntervalText)).thenReturn(updateIntervalTextView);
    when(updateIntervalTextView.getContext()).thenReturn(context);
    when(updateInterval.format(context)).thenReturn("Test Format String");

    viewHolder = new UpdateIntervalViewHolder(itemView);
  }

  @Test public void bind() throws Exception {

    viewHolder.bind(updateInterval);
    verify(updateIntervalTextView).setText(updateInterval.format(context));
  }
}