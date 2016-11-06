package codes.fabio.animemuzei.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import codes.fabio.animemuzei.R;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton class UpdateIntervalSpinnerAdapter extends BaseAdapter {

  private final List<UpdateInterval> updateIntervals;

  @Inject UpdateIntervalSpinnerAdapter(List<UpdateInterval> updateIntervals) {
    this.updateIntervals = updateIntervals;
  }

  @Override public int getCount() {
    return updateIntervals.size();
  }

  @Override public Object getItem(int position) {
    return updateIntervals.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.update_interval_view, parent, false);
    }

    UpdateIntervalViewHolder viewHolder = ((UpdateIntervalViewHolder) convertView.getTag());

    if (viewHolder == null) {
      viewHolder = new UpdateIntervalViewHolder(convertView);
      convertView.setTag(viewHolder);
    }

    viewHolder.bind(((UpdateInterval) getItem(position)));

    return convertView;
  }
}
