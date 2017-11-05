package com.example.anonymous.librarian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ANONYMOUS on 04-Nov-17.
 */

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookRecyclerViewHolder> {

    public static ArrayList<BookRecyclerViewItem> mBooks;
    Context context;

    final String ID = "Book Id : ";
    final String ISSUED = "Is Issued : ";
    final String NUMBER_OF_COPIES = "Number of Copies : ";

    public BookRecyclerViewAdapter(ArrayList<BookRecyclerViewItem> mBooks, Context context) {
        this.mBooks = mBooks;
        this.context = context;
    }

    @Override
    public BookRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.books_list_custom_card_view, parent, false);

        return new BookRecyclerViewHolder(listItemView, this.context, this.mBooks);

    }

    @Override
    public void onBindViewHolder(BookRecyclerViewHolder holder, int position) {

        BookRecyclerViewItem currentBook = mBooks.get(position);

        holder.bookName.setText(currentBook.getmBookName());
        holder.bookId.setText(ID + currentBook.getmBookId());

        if(Integer.parseInt(currentBook.getmIsBookIssued()) == 0){

            // this means that the book is'nt issued to anybody
            String isIssued = ISSUED + "False";
            holder.bookIsIssued.setText(isIssued);

        } else if(Integer.parseInt(currentBook.getmIsBookIssued()) == 1){

            String isIssued = "True";
            holder.bookIsIssued.setText(isIssued);

        }

        holder.bookNumberOfCopies.setText(NUMBER_OF_COPIES + currentBook.getmBookNumberOfCopies());

    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class BookRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView bookName, bookId, bookIsIssued, bookNumberOfCopies;
        Context context;
        ArrayList<BookRecyclerViewItem> books = new ArrayList<BookRecyclerViewItem>();

        public BookRecyclerViewHolder(View itemView, Context context, ArrayList<BookRecyclerViewItem> books) {

            super(itemView);

            this.context = context;
            this.books = books;

            bookName = itemView.findViewById(R.id.book_name);
            bookId = itemView.findViewById(R.id.book_id);
            bookIsIssued = itemView.findViewById(R.id.is_book_issued);
            bookNumberOfCopies = itemView.findViewById(R.id.book_number_of_copies);
        }
    }

    public void setFilter(ArrayList<BookRecyclerViewItem> newList){

        mBooks = new ArrayList<BookRecyclerViewItem>();
        mBooks.addAll(newList);
        notifyDataSetChanged();

    }

}
