package sy.iyad.searcher.models;


import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sy.iyad.searcher.R;


public class Holder extends RecyclerView.ViewHolder {

    TextView textView;

    public Holder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textViewc);
    }
}
