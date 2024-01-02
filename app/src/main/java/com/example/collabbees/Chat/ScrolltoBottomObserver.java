package com.example.collabbees.Chat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collabbees.databinding.ViewholderMessageSenderBinding;

//public class ScrolltoBottomObserver extends RecyclerView.AdapterDataObserver {
//
//    private RecyclerView recycler;
//    private MessageAdapter adapter;
//    private LinearLayoutManager manager;
//
//    public ScrolltoBottomObserver(RecyclerView recycler, MessageAdapter adapter, LinearLayoutManager manager) {
//        this.recycler = recycler;
//        this.adapter = adapter;
//        this.manager = manager;
//    }
//
//    @Override
//    public void onItemRangeInserted(int positionStart, int itemCount) {
//        super.onItemRangeInserted(positionStart, itemCount);
//        int count = adapter.getItemCount();
//        int lastVisiblePosition = manager.findLastCompletelyVisibleItemPosition();
//
//        // If the recycler view is initially being loaded or the
//        // user is at the bottom of the list, scroll to the bottom
//        // of the list to show the newly added message.
//        boolean loading = lastVisiblePosition == -1;
//        boolean atBottom = positionStart >= count - 1 && lastVisiblePosition == positionStart - 1;
//
//        if (loading || atBottom) {
//            recycler.scrollToPosition(positionStart);
//        }
//    }
//
//}
