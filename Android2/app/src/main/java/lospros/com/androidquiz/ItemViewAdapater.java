package lospros.com.androidquiz;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewAdapater extends ArrayAdapter<String> {
    public ItemViewAdapater(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        /*private view holder class*/



    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    private void algo(){

        ViewHolder holder = null;
        holder = new ViewHolder();
        //holder.imageView.setImageBitmap();
    }
}
