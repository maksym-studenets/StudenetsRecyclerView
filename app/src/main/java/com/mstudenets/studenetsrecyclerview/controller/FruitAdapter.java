package com.mstudenets.studenetsrecyclerview.controller;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mstudenets.studenetsrecyclerview.R;
import com.mstudenets.studenetsrecyclerview.model.Fruit;

import java.util.ArrayList;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.MyViewHolder>
{
    private ArrayList<Fruit> fruitList;
    private AlertDialog.Builder alertDialog;
    private View view;


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, country, price;
        ConstraintLayout fruitItem;
        //CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.fruitNameText);
            country = (TextView) view.findViewById(R.id.fruitCountryText);
            price = (TextView) view.findViewById(R.id.fruitPriceText);
            fruitItem = (ConstraintLayout) view.findViewById(R.id.itemLayoutContainer);
        }
    }

    public FruitAdapter(ArrayList<Fruit> moviesList) {
        this.fruitList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Fruit movie = fruitList.get(position);
        holder.name.setText(movie.getName());
        holder.country.setText(movie.getCountry());
        holder.price.setText(String.valueOf(movie.getPrice()));

        int current = position;
        holder.fruitItem.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                /*
                fruitList.remove(position);
                notifyItemRemoved(position);
                */

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    public ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        //Toast.makeText(MainActivity.this, "Moved", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        //Toast.makeText(MainActivity.this, "Moved", Toast.LENGTH_SHORT).show();
                        removeItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleCallback;
    }

    public void addItem(String name, String country, double price) {
        fruitList.add(new Fruit(name, country, price));
        notifyItemInserted(fruitList.size());
    }

    public void removeItem(int position) {
        fruitList.remove(position);
        this.notifyItemRemoved(position);
    }

    private void editItem(Context context, View view, int position) {
        alertDialog = new AlertDialog.Builder(context);

    }
}
