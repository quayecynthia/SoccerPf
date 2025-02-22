package ck.edu.com.soccerproject.ui.allgames;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ck.edu.com.soccerproject.model.DatabaseHelper;
import ck.edu.com.soccerproject.model.Game;
import ck.edu.com.soccerproject.model.ListGames;
import ck.edu.com.soccerproject.R;

//Fragment display all matches with the RecyclerView
public class AllgamesFragment extends Fragment {
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper myDatabase;
    Cursor cursor;

    private RecyclerView recyclerView;
    private ArrayList<Game> gamesTable;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_allgames, container, false);
        recyclerView = root.findViewById(R.id.recycler_allgames);
        gamesTable = new ArrayList<Game>();
        ListGames listGames = new ListGames (getContext(), gamesTable);
        //Set the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listGames);

        //instantiate the database
        myDatabase = new DatabaseHelper(getActivity());
        sqLiteDatabase = myDatabase.getReadableDatabase();

        //retrieve all data from cursor
        cursor = myDatabase.getData();
        if(cursor.moveToFirst()){
            do{
                String first_team, second_team, score, date, location;
                byte[] image;
                first_team = cursor.getString(1);
                second_team = cursor.getString(2);
                score = cursor.getString(3);
                date = cursor.getString(4);
                location = cursor.getString(5);
                image = cursor.getBlob(6);
                //Add a new game in the list of games
                Game game = new Game(first_team, second_team, score, date, location, image);
                gamesTable.add(game);

            }while(cursor.moveToNext());
        }
        return root;
    }

}