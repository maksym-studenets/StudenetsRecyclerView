package com.mstudenets.studenetsrecyclerview.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mstudenets.studenetsrecyclerview.R;
import com.mstudenets.studenetsrecyclerview.controller.FruitAdapter;
import com.mstudenets.studenetsrecyclerview.controller.JsonManager;
import com.mstudenets.studenetsrecyclerview.model.Fruit;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ArrayList<Fruit> fruitList;
    private FruitAdapter adapter;
    private AlertDialog.Builder alertDialog;
    private View view;
    private EditText fruitNameEdit, fruitCountryEdit, fruitPriceEdit;

    private int editPosition;
    private boolean add = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                removeView();
                add = true;
                alertDialog.setTitle("Add fruit");
                alertDialog.show();
            }
        });

        JsonManager manager = new JsonManager(getApplicationContext());

        manager.parseJson(manager.getJsonRead());
        fruitList = manager.getFruitList();



        initializeRecyclerView();
        initializeDialog();
        initializeSwipeAction();
    }
    // TODO: Save changes to JSON file on activity destroy
    /*
    protected void onDestroy() {
        super.onDestroy();
        manager.updateJson();
    }
    */

    private void initializeRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.fruitRecyclerView);
        adapter = new FruitAdapter(fruitList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        adapter.createHelperCallback();
    }

    private void initializeSwipeAction() {
        ItemTouchHelper.SimpleCallback touchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    removeItem(position);
                }
                else {
                    removeView();
                    editPosition = position;
                    alertDialog.setTitle("Edit fruit");
                    fruitNameEdit.setText(fruitList.get(editPosition).getName());
                    fruitCountryEdit.setText(fruitList.get(editPosition).getCountry());
                    fruitPriceEdit.setText(String.valueOf(fruitList.get(editPosition).getPrice()));
                    alertDialog.show();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeView() {
        if (view.getParent() != null)
            ((ViewGroup) view.getParent()).removeView(view);
    }

    public void removeItem(int position) {
        fruitList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void initializeDialog() {
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alertDialog.setView(view);

        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = fruitNameEdit.getText().toString();
                String country = fruitCountryEdit.getText().toString();
                double price = Double.parseDouble(fruitPriceEdit.getText().toString());

                if (add) {
                    add = false;
                    adapter.addItem(name, country, price);
                    dialogInterface.dismiss();
                }
                else {
                    fruitList.set(editPosition, new Fruit(name, country, price));
                    adapter.notifyDataSetChanged();
                    dialogInterface.dismiss();

                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        fruitNameEdit = (EditText) view.findViewById(R.id.dialogNameEdit);
        fruitCountryEdit = (EditText) view.findViewById(R.id.dialogCountryEdit);
        fruitPriceEdit = (EditText) view.findViewById(R.id.dialogPriceEdit);
    }
}
