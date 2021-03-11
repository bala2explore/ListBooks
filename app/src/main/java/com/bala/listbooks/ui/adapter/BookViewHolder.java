package com.bala.listbooks.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bala.listbooks.R;

public class BookViewHolder extends RecyclerView.ViewHolder {
    private final TextView bookTitle;
    private final TextView bookAuthor;
    private final TextView bookPublisher;
    private final TextView bookContributor;
    private final TextView bookDescription;

    public BookViewHolder(View view) {
        super(view);
        bookTitle = (TextView) view.findViewById(R.id.book_title);
        bookAuthor = (TextView) view.findViewById(R.id.book_author);
        bookPublisher = (TextView) view.findViewById(R.id.book_publisher);
        bookContributor = (TextView) view.findViewById(R.id.book_contributor);
        bookDescription = (TextView) view.findViewById(R.id.book_description);
    }

    public TextView getBookTitleView() {
        return bookTitle;
    }

    public TextView getBookAuthor() {
        return bookAuthor;
    }

    public TextView getBookPublisher() {
        return bookPublisher;
    }

    public TextView getBookContributor() {
        return bookContributor;
    }

    public TextView getBookDescription() {
        return bookDescription;
    }
}
