package com.example.anonymous.librarian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ANONYMOUS on 05-Nov-17.
 */

public class IssuedBookRecyclerViewAdapter extends RecyclerView.Adapter<IssuedBookRecyclerViewAdapter.IssuedBookRecyclerViewAdapterViewHolder> {

    Context context;
    ArrayList<IssuedBookListItem> issuedBooks;

    private final String BOOK_ID = "Book ID : ";
    private final String CURRENTLY_ISSUED_TO = "Currently issued to : ";
    private final String ISSUED_ON = "Issued on : ";

    public IssuedBookRecyclerViewAdapter(Context context, ArrayList<IssuedBookListItem> issuedBooks) {
        this.context = context;
        this.issuedBooks = issuedBooks;
    }

    @Override
    public IssuedBookRecyclerViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(context).inflate(R.layout.issued_books_list_custom_card_view, parent, false);
        return new IssuedBookRecyclerViewAdapterViewHolder(listItemView, context, issuedBooks);
    }

    @Override
    public void onBindViewHolder(IssuedBookRecyclerViewAdapterViewHolder holder, int position) {

        IssuedBookListItem currentIssuedBookItem = issuedBooks.get(position);

        holder.issuedBookName.setText(currentIssuedBookItem.getmIssuedBookName());
        holder.issuedBooksId.setText(BOOK_ID + currentIssuedBookItem.getmIssuedBookId());
        holder.issuedBookToName.setText(CURRENTLY_ISSUED_TO + currentIssuedBookItem.getmIssuedBookToName());
        holder.issuedBookOnDate.setText(ISSUED_ON + currentIssuedBookItem.getmIssuedBookOnDate());

    }

    @Override
    public int getItemCount() {
        return issuedBooks.size();
    }

    public class IssuedBookRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ArrayList<IssuedBookListItem> issuedBooks;

        TextView issuedBookName, issuedBooksId, issuedBookToName, issuedBookOnDate;

        public IssuedBookRecyclerViewAdapterViewHolder(View itemView, Context context, ArrayList<IssuedBookListItem> issuedBooks) {
            super(itemView);

            this.context = context;
            this.issuedBooks = issuedBooks;

            issuedBookName = itemView.findViewById(R.id.issued_book_name);
            issuedBooksId = itemView.findViewById(R.id.issued_book_id);
            issuedBookToName = itemView.findViewById(R.id.currently_issued_to);
            issuedBookOnDate = itemView.findViewById(R.id.issued_book_on);
        }

    }

    public void setIssuedBookFilter(ArrayList<IssuedBookListItem> newIssueList) {

        issuedBooks = new ArrayList<IssuedBookListItem>();
        issuedBooks.addAll(newIssueList);
        notifyDataSetChanged();
    }
}
