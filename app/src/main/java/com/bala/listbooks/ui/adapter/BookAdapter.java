package com.bala.listbooks.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.bala.listbooks.R;
import com.bala.listbooks.ui.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> implements Filterable {

    private List<Book> localBookList;
    private List<Book> bookFiltered;

    public BookAdapter(List<Book> bookList) {
        localBookList = new ArrayList<>();
        localBookList = bookList;
        bookFiltered = localBookList;
    }

    public void addItems(List<Book> bookList){
        localBookList.clear();
        bookFiltered.clear();
        bookFiltered.addAll(bookList);
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder viewHolder, final int position) {

        Book book = bookFiltered.get(position);
        viewHolder.getBookTitleView().setText(book.getTitle());
        viewHolder.getBookAuthor().setText(book.getAuthor());
        viewHolder.getBookPublisher().setText(book.getPublisher());
        viewHolder.getBookContributor().setText(book.getContributor());
        viewHolder.getBookDescription().setText(book.getDescription());
    }

    @Override
    public int getItemCount() {
        return bookFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    bookFiltered = localBookList;
                } else {
                    List<Book> filteredList = new ArrayList<>();
                    for (Book row : localBookList) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || row.getAuthor().toLowerCase().contains(charSequence)
                                || row.getContributor().toLowerCase().contains(charSequence)
                                || row.getDescription().toLowerCase().contains(charSequence)
                                || row.getPublisher().toLowerCase().contains(charSequence)
                        ) {
                            filteredList.add(row);
                        }
                    }

                    bookFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = bookFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                bookFiltered = (List<Book>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}