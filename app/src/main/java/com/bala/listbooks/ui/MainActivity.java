package com.bala.listbooks.ui;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bala.listbooks.R;
import com.bala.listbooks.data.Result;
import com.bala.listbooks.databinding.ActivityMainBinding;
import com.bala.listbooks.ui.adapter.BookAdapter;
import com.bala.listbooks.ui.adapter.MyDividerItemDecoration;
import com.bala.listbooks.ui.datepicker.DatePickerCallBack;
import com.bala.listbooks.ui.datepicker.DatePickerFragment;
import com.bala.listbooks.ui.model.Book;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DatePickerCallBack {

    private BookAdapter bookAdapter;
    private List<Book> bookList;
    ActivityMainBinding binding;
    MainActivityViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        model = new ViewModelProvider(this).get(MainActivityViewModel.class);

        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList);
        binding.bookRecyclerView.setAdapter(bookAdapter);
        binding.bookRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        binding.bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        showProgress(true);
        getBooks(buildURL(getTodayDate()));

        model.getBookData().observe(this, books -> {
            showProgress(false);
            bookAdapter.addItems(books);
        });

        model.getErrorData().observe(this, error -> {
            showProgress(false);
            showAlert("Error",((Result.Error) error).exceptionMessage);

        });

    }

    private void getBooks(String date){
        if(isNetworkAvailable(getApplication())){
            model.getBooks(date);
        }
        else{
            showProgress(false);
            showAlert("Internet not Available","Please check your internet connection");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                bookAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                bookAdapter.getFilter().filter(query);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionSearch) {
            return true;
        }
        else if(id == R.id.actionCalender){
            DialogFragment newFragment = new DatePickerFragment(this);
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }

        return super.onOptionsItemSelected(item);
    }

    private void showProgress(boolean value) {
        if (value) {
            binding.progressLayout.setVisibility(View.VISIBLE);
            binding.bookRecyclerView.setVisibility(View.GONE);
        } else {
            binding.progressLayout.setVisibility(View.GONE);
            binding.bookRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void pickDate(DatePicker view, int year, int month, int day) {
        //add month value with +1 - android default index issue
        month = month + 1;

        DecimalFormat df = new DecimalFormat("00");

        // Convert month value 3 to 03
        String monthStr = df.format(month);
        String dayStr = df.format(day);
        showProgress(true);
        String selectedDate = String.format("%d-%s-%s",year,monthStr,dayStr);
        getBooks(buildURL(selectedDate));
    }

    public String buildURL(String date){
        String apiUrl = "https://api.nytimes.com/svc/books/v2/lists/overview.json?published_date=%s&api-key=76363c9e70bc401bac1e6ad88b13bd1d";
        apiUrl = String.format(apiUrl, date);
        return apiUrl;
    }

    public String getTodayDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c);
    }

    public void showAlert(String title,String message){

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private Boolean isNetworkAvailable(Application application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }
}