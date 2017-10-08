package company.com.recyclerviewtutorial;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// A child class of adapter
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private OnItemClickListener mItemClickListener;
    private List<String> mWords;
    private List<Integer> mColorList;
    private List<Integer> mColors;
    private Context mContext;
    private Random rnd;

    public MainRecyclerAdapter(Context context, List<String> words, List<Integer> colorList) {
//        A random number generator
        rnd = new Random();
//        The context of the activity that holds the recycler view
//        Useful when styles/themes are needed, i.e. when you need to get color from your resources
        mContext = context;
//        Deep copy, can use shallow copy as well
        mWords = new ArrayList<>(words);
        mColorList = new ArrayList<>(colorList);

        generateColors();
    }
//    generating a list of random colors, the size of the list == the size of words
    public void generateColors(){
        if (mColorList == null || mColorList.size() <= 0 || mWords == null || mWords.size() <= 0) return;
        int length = getItemCount();
        mColors = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            mColors.add(mColorList.get(rnd.nextInt(mColorList.size())));
        }
    }

//    Trying to delete an entry
    public void deleteEntry(int entry){
//        make sure that the entry is within our range
        if (getItemCount() > entry){
//            remove the word and the color
            mWords.remove(entry);
            mColors.remove(entry);
//            Call this function to notify the adapter(this class) to regenerate that cell and all cells below it
//            this.notifyDataSetChanged() also works, but more costly
            this.notifyItemRemoved(entry);
        }
    }
//    Before every cell is created tell them which item view to use
//    View type can be used when you want to have multiple types of views in your recycler view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item_main, parent, false);
        return new ViewHolder(view);
    }

//    When a cell comes visible, fill in the text views
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText( mWords.get(position) );
        holder.textView.setTextColor( ContextCompat.getColor(mContext, mColors.get(position)) );
    }

//    This function is not only used by ourselves, lots of other functions in the adapter needs it
//    Should return the size of your recycler view----how many cells you want
    @Override
    public int getItemCount() {
        return mWords.size();
    }

//    The view holder specifies what views we have in our item view
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.recycler_item_text);
//            This class(the onClick function) will receive the onClick call from textView
            textView.setOnClickListener(this);
        }
//        The onClick function
        @Override
        public void onClick(View v) {
//            There may be clicks on other views that are also passed to this function(If you do pass them here)
            switch (v.getId()){
                case R.id.recycler_item_text:
//                    calls the function of the interface we wrote
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(itemView, getAdapterPosition());
                    }
                    break;
                default:
            }

        }
    }

//    Set up the listener(Called from an activity, usually the parent activity of the recycler view)
    public void setOnItemClickListener(final OnItemClickListener mOnImageClickListener) {
        this.mItemClickListener = mOnImageClickListener;
    }

//    This interface specifies a on click listener that has a function onItemClick(View view, int position)
//    Any class can therefore initiate such an listener
//    The listener can be passed to this adapter through setOnItemClickListener, and handle the onClick events
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
