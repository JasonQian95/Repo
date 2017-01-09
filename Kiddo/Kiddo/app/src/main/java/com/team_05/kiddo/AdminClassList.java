package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
public class AdminClassList extends AppCompatActivity{
//public class AdminClassList extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SearchView classSearch;
    private ListView classListView;
    private ArrayAdapter<String> studentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_class_list);

        Button backButton = (Button)(findViewById(R.id.backButton));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminClassList.this, AdminViewClass.class);
                finish();
                startActivity(intent);
            }

        });

        Button deleteButton = (Button)(findViewById(R.id.deleteClassButton));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminClassList.this, AdminManageClasses.class);
                finish();
                startActivity(intent);
            }

        });

        // Create class list ListView
        classListView = (ListView)findViewById(R.id.classListView);
        ArrayList<String> students = new ArrayList<String>();
        students.add("Jessica Alberts");
        students.add("Jim Cole");
        students.add("Ben Green");
        students.add("Milla Ford");
        students.add("Mary Hilbert");
        students.add("Liam Jones");
        students.add("Steve Kim");
        students.add("Rachel Williams");


        studentsAdapter = new ArrayAdapter<String>(AdminClassList.this, android.R.layout.simple_list_item_1, students);
        classListView.setAdapter(studentsAdapter);

       /* classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> student, View view, int position, long id) {
                Intent intent = new Intent(AdminClassList.this, AdminViewStudent.class);
                startActivity(intent);
            }
        }); */

   /*     // Create class list SearchView
        classSearch = (SearchView)findViewById(R.id.search);
        classSearch.setAdapter(studentsAdapter);

        classSearch.setTextFilterEnabled(true);
        setupSearchView();

    }

    private void setupSearchView()
    {
        classSearch.setIconifiedByDefault(false);
        classSearch.setOnQueryTextListener(this);
        classSearch.setSubmitButtonEnabled(true);
        classSearch.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        if (TextUtils.isEmpty(newText)) {
            classSearch.clearTextFilter();
        } else {
            classSearch.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }*/
}}
