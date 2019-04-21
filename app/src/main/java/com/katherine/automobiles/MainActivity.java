package com.katherine.automobiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.katherine.automobiles.Database.DatabaseAdapter;
import com.katherine.automobiles.Presenters.MainActivityPresenter;
import com.katherine.automobiles.Views.ActivityView;

import java.net.ContentHandler;

public class MainActivity extends AppCompatActivity implements ActivityView {

    private FloatingActionButton addFab;
    private TextView filterAndSortTextView;
    private LinearLayout filterAndSortLayout;
    private CheckBox priceCheckBox;
    private CheckBox brandsCheckBox;
    private CheckBox manufacturersCheckBox;
    private RecyclerView brandsRecyclerView;
    private RecyclerView manufacturersRecyclerView;
    private RecyclerView autoesRecyclerView;

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DatabaseAdapter databaseAdapter = DatabaseAdapter.getInstance();
        databaseAdapter.setDbHelper(this);

        presenter = new MainActivityPresenter(this);
      //  presenter.attachView(this);

        initActivity();


    }

    private void initActivity(){
        addFab = (FloatingActionButton)findViewById(R.id.addFab);
        filterAndSortTextView = (TextView)findViewById(R.id.filterAndSortTextView);
        filterAndSortLayout = (LinearLayout)findViewById(R.id.filterAndSortLayout);
        priceCheckBox = (CheckBox)findViewById(R.id.priceCheckBox);
        brandsCheckBox = (CheckBox)findViewById(R.id.brandsCheckBox);
        manufacturersCheckBox = (CheckBox)findViewById(R.id.manufacturesCheckBox);
        brandsRecyclerView = (RecyclerView)findViewById(R.id.brandsRecyclerView);
        manufacturersRecyclerView = (RecyclerView)findViewById(R.id.manufacturesRecyclerView);
        autoesRecyclerView = (RecyclerView)findViewById(R.id.autoesRecyclerView);

        filterAndSortTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeViewVisibility(filterAndSortLayout);
            }
        });

        brandsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.changeViewVisibility(brandsRecyclerView);
            }
        });

        manufacturersCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.changeViewVisibility(manufacturersRecyclerView);
            }
        });

        priceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    ((CardAdapter)autoesRecyclerView.getAdapter()).setContents(presenter.getCardContentFilter(MainActivityPresenter.MAPPERS.AUTOMOBILE, MainActivityPresenter.FILTERS.PRICE));
                else
                    ((CardAdapter)autoesRecyclerView.getAdapter()).setContents(presenter.getCardContent(MainActivityPresenter.MAPPERS.AUTOMOBILE));
                autoesRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewAutoActivity.class);
                startActivity(intent);
            }
        });


        CardsTextCheckAdapter cardsTextCheckAdapterBrands = new CardsTextCheckAdapter(presenter.getCardContent(MainActivityPresenter.MAPPERS.BRAND));
        brandsRecyclerView.setAdapter(cardsTextCheckAdapterBrands);
        GridLayoutManager gridLayoutManagerBrands = new GridLayoutManager(this, 2);
        brandsRecyclerView.setLayoutManager(gridLayoutManagerBrands);

        CardsTextCheckAdapter cardsTextCheckAdapterMan = new CardsTextCheckAdapter(presenter.getCardContent(MainActivityPresenter.MAPPERS.MANUFACTURER));
        manufacturersRecyclerView.setAdapter(cardsTextCheckAdapterMan);
        GridLayoutManager gridLayoutManagerMan = new GridLayoutManager(this, 2);
        manufacturersRecyclerView.setLayoutManager(gridLayoutManagerMan);

        CardAutoAdapter cardAutoAdapter = new CardAutoAdapter(presenter.getCardContent(MainActivityPresenter.MAPPERS.AUTOMOBILE));
        autoesRecyclerView.setAdapter(cardAutoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        autoesRecyclerView.setLayoutManager(linearLayoutManager);
        registerForContextMenu(autoesRecyclerView);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item =  menu.findItem(R.id.SearchItem);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((CardAdapter)autoesRecyclerView.getAdapter()).setContents(presenter.searchQuery(query));
                autoesRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((CardAdapter)autoesRecyclerView.getAdapter()).setContents(presenter.searchQuery(newText));
                autoesRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position;

        switch (item.getItemId()){
            case 1:

                position = ((CardAdapter)autoesRecyclerView.getAdapter()).getPosition();

                presenter.removeItem(((CardAdapter)autoesRecyclerView.getAdapter()).getContents().get(position)[0]);
                ((CardAdapter)autoesRecyclerView.getAdapter()).getContents().remove(position);

                // уведомляем, что данные изменились
                autoesRecyclerView.getAdapter().notifyDataSetChanged();
                return true;
            default:
                position = ((CardAdapter)autoesRecyclerView.getAdapter()).getPosition();
                //   Recipe recipe = (Recipe) recipeMapper.find(DataMapper.ID, String.valueOf(((Recipe)entities.get(position)).getId()));
                Intent intent = new Intent(this,NewAutoActivity.class);
                intent.putExtra(NewAutoActivity.AUTO_ID, (((CardAdapter)autoesRecyclerView.getAdapter()).getContents().get(position)[0]));
                startActivity(intent);
                //recipeMapper.update();*/
                return true;
        }

        // return super.onContextItemSelected(item);
    }
}
