package com.example.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private ImageView img;
    private Button btnAdd;
    private EditText etTitle, etAuthor;
    private RecyclerView recyclerView;
    private List<Post> list = new ArrayList<>();
    RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        img = (ImageView)findViewById(R.id.imageView);

        btnAdd = (Button)findViewById(R.id.button);
        btnAdd.setOnClickListener(this);

        etTitle = (EditText)findViewById(R.id.editText);
        etAuthor = (EditText)findViewById(R.id.editText2);

        adapter = new RecyclerAdapter(list);
        recyclerView.setAdapter(adapter);
        getPost();
        getPhoto();

    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.button){
            User user = new User(etTitle.getText().toString(), etAuthor.getText().toString());
            Call<User> call = RestClient.postRequest().updateUser(user.getTitle(), user.getAuthor());
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful())
                        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "FailBR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getPhoto() {
        Call<Photo> call = RestClient.request().getPhotoItem();
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                Log.d("MC", response.body().getThumbnail()+"");
                if(response.isSuccessful()){
                    Picasso.with(getApplicationContext()).load(response.body().getThumbnail()).into(img);
                }

            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Log.d("MC", "Kate");

            }
        });
    }


    public void getPost(){
        Call<List<Post>> call = RestClient.request().getPostList();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d("MC", ""+response.code() );
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    Log.d("MC", "ASD");
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Log.d("MC", "Err");
            }
        });
    }



    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PostViewHolder>{


        List<Post> postList;

        public RecyclerAdapter(List<Post> postList) {
            this.postList = postList;
        }

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_item, parent, false);

            return new PostViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PostViewHolder holder, int position) {
            holder.textView.setText(postList.get(position).getBody());


        }

        @Override
        public int getItemCount() {
            return postList.size();
        }

        public class PostViewHolder extends RecyclerView.ViewHolder{

            private TextView textView;

            public PostViewHolder(View itemView) {
                super(itemView);
                textView = (TextView)itemView.findViewById(android.R.id.text1);
            }




        }
    }
}
