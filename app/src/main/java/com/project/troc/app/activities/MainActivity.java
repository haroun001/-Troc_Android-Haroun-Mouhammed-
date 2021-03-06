package com.project.troc.app.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.troc.R;
import com.project.troc.app.adapters.CustomListAdapter;
import com.project.troc.app.controllers.AnnonceController;
import com.project.troc.app.models.Annonce;


import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity {

    private int currentView;
    private Context mainContext = this;
    private ModalActivity modalActivity;

    public int getCurrentView() {
        return currentView;
    }

    public void setCurrentView(int currentView) {
        this.currentView = currentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentView = R.layout.activity_main;
        setContentView(currentView);

        try {
            buildMainView();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Toast.makeText(mainContext, "Unexpected error. (status: 0012)", Toast.LENGTH_SHORT).show();
            System.exit(0012);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mainContext, "Unexpected error. (status: 0014)", Toast.LENGTH_SHORT).show();
            System.exit(0014);
        }
    }

    public void buildMainView() throws NoSuchMethodException {
        callGetAll();

        final BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);

        final FloatingActionButton floatingActionButton = findViewById(R.id.button);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        final ImageButton imageButton = (ImageButton) findViewById(R.id.searchButton);

        final View search = (View) findViewById(R.id.search);

        final EditText editText = (EditText) findViewById(R.id.searchCode);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.containerSearch);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    buildMainView();
                    swipeRefreshLayout.setRefreshing(false);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    Toast.makeText(mainContext, "Unexpected error. (status: 0012)", Toast.LENGTH_SHORT).show();
                    System.exit(0012);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mainContext, "Unexpected error. (status: 0014)", Toast.LENGTH_SHORT).show();
                    System.exit(0014);
                }
            }
        });

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Method method = null;
                try {
                    method = MainActivity.class.getDeclaredMethod("showDeleteAllDialog");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    Toast.makeText(mainContext, "Unexpected error. (status: 0012)", Toast.LENGTH_SHORT).show();
                    System.exit(0012);
                }
                modalActivity = new ModalActivity(MainActivity.this, MainActivity.this, method);
                modalActivity.show(getSupportFragmentManager(), modalActivity.getTag());
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentView = R.layout.add_layout;
                setContentView(currentView);
                buildRegisterView();
            }
        });

        coordinatorLayout.setVisibility(View.INVISIBLE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coordinatorLayout.getVisibility() == View.INVISIBLE) {
                    coordinatorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    coordinatorLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().trim().equals("")){
                    Method method = null;
                    try {
                        method = MainActivity.class.getDeclaredMethod("setStudentResult");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        Toast.makeText(mainContext, "Unexpected error. (status: 0012)", Toast.LENGTH_SHORT).show();
                        System.exit(0012);
                    }

                    AnnonceController.getStudent(editText.getText().toString().trim(), mainContext, MainActivity.this, method);
                }
            }
        });
    }

    public void setStudentResult() {
        if(AnnonceController.getAnnonce().get(0) == null)
            Toast.makeText(mainContext, "Student not found", Toast.LENGTH_SHORT).show();
        else {
            ListView listView = findViewById(R.id.listView);

            listView.setAdapter(null);

            CustomListAdapter listViewAdapter = new CustomListAdapter(AnnonceController.getAnnonceList(), mainContext, this);

            listView.setAdapter(listViewAdapter);

            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.containerSearch);
            coordinatorLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void callGetAll() throws NoSuchMethodException {
        Method method = MainActivity.class.getDeclaredMethod("setContentOnListView");
        AnnonceController.getAllStudentsRetrofit(mainContext, this, method);
    }

    public void setContentOnListView() {

        if(AnnonceController.getAnnonceList().get(0) == null) {
            Toast.makeText(mainContext, "Student not found", Toast.LENGTH_SHORT).show();
        }
        else {
            ListView listView = findViewById(R.id.listView);

            listView.setAdapter(null);

            CustomListAdapter listViewAdapter = new CustomListAdapter(AnnonceController.getAnnonceList(), mainContext, this);

            listView.setAdapter(listViewAdapter);
        }
    }

    public void buildRegisterView() {
        final ImageButton imageButton = (ImageButton) findViewById(R.id.insert_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final EditText code = (EditText) findViewById(R.id.studentCode);
            final EditText name = (EditText) findViewById(R.id.studentName);
            final EditText email = (EditText) findViewById(R.id.studentEmail);

            if (name.getText().toString().trim().length() == 0 || email.getText().toString().trim().length() == 0) {
                Toast.makeText(mainContext, "Data cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (code.getText().toString().trim().length() < 5) {
                Toast.makeText(mainContext, "Code have to be 5 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            AnnonceController.insertStudent(mainContext, code.getText().toString().trim(), name.getText().toString().trim(), email.getText().toString().trim());

            code.setText("");
            name.setText("");
            email.setText("");
            }
        });
    }

    public void buildUpdateView(Annonce student) {
        final ImageButton imageButton = (ImageButton) findViewById(R.id.update_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText code = (EditText) findViewById(R.id.studentCodeUpdate);
                final EditText name = (EditText) findViewById(R.id.studentNameUpdate);
                final EditText email = (EditText) findViewById(R.id.studentEmailUpdate);

                if (name.getText().toString().trim().length() == 0 || email.getText().toString().trim().length() == 0) {
                    Toast.makeText(mainContext, "Data cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                AnnonceController.updateStudent(mainContext, code.getText().toString().trim(), name.getText().toString().trim(), email.getText().toString().trim());

                currentView = R.layout.activity_main;
                setContentView(currentView);

                try {
                    buildMainView();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    Toast.makeText(mainContext, "Unexpected error. (status: 0012)", Toast.LENGTH_SHORT).show();
                    System.exit(0012);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mainContext, "Unexpected error. (status: 0014)", Toast.LENGTH_SHORT).show();
                    System.exit(0014);
                }
            }
        });

        final EditText code = (EditText) findViewById(R.id.studentCodeUpdate);
        code.setText(student.getCode());

        final EditText name = (EditText) findViewById(R.id.studentNameUpdate);
        name.setText(student.getName());

        final EditText email = (EditText) findViewById(R.id.studentEmailUpdate);
        email.setText(student.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_app_bar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (currentView == R.layout.add_layout || currentView == R.layout.update_layout) {
            currentView = R.layout.activity_main;
            setContentView(currentView);

            try {
                buildMainView();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                Toast.makeText(mainContext, "Unexpected error. (status: 0012)", Toast.LENGTH_SHORT).show();
                System.exit(0012);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mainContext, "Unexpected error. (status: 0014)", Toast.LENGTH_SHORT).show();
                System.exit(0014);
            }
        }
        else {
            super.onBackPressed();
        }
    }

    public void showDeleteDialog(final Annonce student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainContext);
        builder.setMessage("Are you sure you want to delete this student? This action can not be undone.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AnnonceController.deleteStudent(mainContext, student.getCode());
                        try {
                            buildMainView();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                            Toast.makeText(mainContext, "Unexpected error. (status: 0012)", Toast.LENGTH_SHORT).show();
                            System.exit(0012);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mainContext, "Unexpected error. (status: 0014)", Toast.LENGTH_SHORT).show();
                            System.exit(0014);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .show();
    }

    public void showDeleteAllDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainContext);
        builder.setMessage("Are you sure you want to delete all students? This action can not be undone.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modalActivity.dismiss();
                        AnnonceController.deleteAllStudents(mainContext);
                        try {
                            buildMainView();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                            Toast.makeText(mainContext, "Unexpected error. (status: 0012)", Toast.LENGTH_SHORT).show();
                            System.exit(0012);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mainContext, "Unexpected error. (status: 0014)", Toast.LENGTH_SHORT).show();
                            System.exit(0014);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modalActivity.dismiss();
                    }
                })
                .show();
    }
}