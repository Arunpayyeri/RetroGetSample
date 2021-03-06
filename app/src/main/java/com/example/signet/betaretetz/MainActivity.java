package com.example.signet.betaretetz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.signet.betaretetz.POJO.EmpListPOJO;
import com.example.signet.betaretetz.POJO.Employee;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    List<Employee> list_emp;  // Hold the list of car companies
    ListView lv;
    Stethotest api;

    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        lv=(ListView) findViewById(R.id.listView);
        api= (Stethotest) getApplication();
        restCall();
    }

    private void restCall() {

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(REST.BASEURL)
                .build();
        REST.api_carCompanies api = adapter.create(REST.api_carCompanies.class);
        //app.httpClient.newCall(request).enqueue(new okhttp3.Callback() {
        //Defining the method

       api.getData(new Callback<EmpListPOJO>() {
            @Override
            public void success(EmpListPOJO car_list_response , Response response) {
                if (car_list_response != null){

                    list_emp = car_list_response.getEmployees();  // Takes list of car from Response

                    //Loads List View
                    Adapter arrayAdapter = new Adapter(getBaseContext(), list_emp);
                    lv.setAdapter(arrayAdapter);


                    mShimmerViewContainer.stopShimmerAnimation();
                   // mShimmerViewContainer.setVisibility(View.GONE);

                }
            }

            @Override
            public void failure(RetrofitError error) {
               // mShimmerViewContainer.stopShimmerAnimation();
             //   mShimmerViewContainer.setVisibility(View.GONE);
                Log.e("Failed to Connect REST",""+error.getCause());
            }
        });

        //---------------------*** END REST ***-----------------------------------------------------//


    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }
    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

}
