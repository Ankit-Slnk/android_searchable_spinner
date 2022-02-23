package com.toptoche.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toptoche.sample.adapter.DialogRecyclerViewAdapter;
import com.toptoche.sample.listener.RecyclerViewListener;
import com.toptoche.sample.models.DataPolicyNumber;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LinearLayout llTagNo;
    TextView tvTagNo;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llTagNo = findViewById(R.id.llTagNo);
        tvTagNo = findViewById(R.id.tvTagNo);

        final ArrayList<DataPolicyNumber> dataPolicyNumbers = new ArrayList<>();

        dataPolicyNumbers.add(new DataPolicyNumber("1"));
        dataPolicyNumbers.add(new DataPolicyNumber("2"));
        dataPolicyNumbers.add(new DataPolicyNumber("3"));
        dataPolicyNumbers.add(new DataPolicyNumber("4"));
        dataPolicyNumbers.add(new DataPolicyNumber("5"));

        llTagNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.searchable_list_dialog);

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);

                dialog.getWindow().setLayout(width, height);

                final EditText etSearch = dialog.findViewById(R.id.etSearch);
                final ImageView imgClear = dialog.findViewById(R.id.imgClear);
                final RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
                final TextView tvClose = dialog.findViewById(R.id.tvClose);

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                final DialogRecyclerViewAdapter adapter = new DialogRecyclerViewAdapter(MainActivity.this, dataPolicyNumbers, new RecyclerViewListener() {
                    @Override
                    public void onItemViewTap(int index) {
                        tvTagNo.setText(dataPolicyNumbers.get(index).gettag_no());
                        dialog.dismiss();
                    }
                });

                recyclerView.setAdapter(adapter);

                imgClear.setVisibility(View.GONE);

                imgClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etSearch.setText("");
                        adapter.filter("");
                    }
                });

                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence cs, int start, int before, int count) {
                        String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                        if (text.isEmpty()) {
                            imgClear.setVisibility(View.GONE);
                        } else {
                            imgClear.setVisibility(View.VISIBLE);
                        }
                        adapter.filter(text);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}
