package codes.fabio.animemuzei.settings;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import codes.fabio.animemuzei.R;

class UpdateIntervalViewHolder {

  private TextView updateIntervalText;

  UpdateIntervalViewHolder(View convertView) {
    updateIntervalText = (TextView) convertView.findViewById(R.id.updateIntervalText);
  }

  void bind(UpdateInterval updateInterval) {
    Context context = updateIntervalText.getContext();
    updateIntervalText.setText(context.getString(R.string.update_interval, updateInterval.amount(),
        updateInterval.timeUnit().toString().toLowerCase()));
  }
}
