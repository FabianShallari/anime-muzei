package codes.fabio.animemuzei.settings;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import codes.fabio.animemuzei.R;
import java.util.concurrent.TimeUnit;

import static codes.fabio.animemuzei.Util.quantityStringForTimeUnit;

class UpdateIntervalViewHolder {

  private TextView updateIntervalText;

  UpdateIntervalViewHolder(View convertView) {
    updateIntervalText = (TextView) convertView.findViewById(R.id.updateIntervalText);
  }

  void bind(UpdateInterval updateInterval) {
    Context context = updateIntervalText.getContext();

    int amount = updateInterval.amount();
    TimeUnit timeUnit = updateInterval.timeUnit();

    String timeUnitString = context.getResources()
        .getQuantityString(quantityStringForTimeUnit(timeUnit), amount, amount);

    updateIntervalText.setText(timeUnitString);
  }
}
