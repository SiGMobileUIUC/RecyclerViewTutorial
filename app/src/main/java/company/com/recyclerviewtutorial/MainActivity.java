package company.com.recyclerviewtutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    Using split "" will give us a leading ""
//    Stores the words we want to display
    public static final String[] WORD = ((String)"SIGMOBILE").split("");
//    The recycler view
    RecyclerView mRecyclerView;
//    And its adapter
    MainRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        get the reference to the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        You can use either vertical or horizontal. it tells the recycler view the orientation to render items.
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);

//        Set up the colors
        List<Integer> mColorList = new ArrayList<>();
        mColorList.add(R.color.blue);
        mColorList.add(R.color.purple);
        mColorList.add(R.color.green);
        mColorList.add(R.color.red);
        mColorList.add(R.color.orange);
//        Set up the words
        List<String> mWords = new ArrayList<>();
//        50 words ----> 50 cells
        for (int i = 0; i < 50; i++) {
//            the words go like S->I->G->M->O->B->I->L->E->S->I....
//            keep in mind the leading ""
            mWords.add(WORD[i % (WORD.length - 1) + 1]);
        }
//        Set up the adapter, using the function we wrote, pass in the colors and words
        mRecyclerAdapter = new MainRecyclerAdapter(this, mWords, mColorList);
//        Setting up the on click event
//        Its usually a good practice to handle click events in an activity rather in the adapter
        mRecyclerAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mRecyclerAdapter);
//        This tells the recycler adapter that we want to change the data in the recycler view,
//        And the adapter will reload everything.
        mRecyclerAdapter.notifyDataSetChanged();
    }

//    The onclick listener
    MainRecyclerAdapter.OnItemClickListener onItemClickListener = new MainRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            mRecyclerAdapter.deleteEntry(position);
        }
    };
}

/*
Things changed:
build.gradle(Module:app)
res/values/colors
res/layout/activity_main.xml
res/layout/recycler_item_main.xml
java/(.)/MainActivity
java/(.)/MainRecyclerAdapter
 */
