package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView mShoppingListView;
    private CursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShoppingListView = (ListView) findViewById(R.id.shopping_list_view);

        Cursor cursor = ShoppingSQLiteOpenHelper.getInstance(MainActivity.this).getShoppingList();

        mCursorAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, cursor, new String[]{ShoppingSQLiteOpenHelper.COL_ITEM_NAME, ShoppingSQLiteOpenHelper.COL_ITEM_TYPE}, new int[]{R.id.item_name, R.id.item_type}, 0);
        mShoppingListView.setAdapter(mCursorAdapter);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor cursor =  ShoppingSQLiteOpenHelper.getInstance(MainActivity.this).searchFood(query);
            mCursorAdapter.swapCursor(cursor);

        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

}
