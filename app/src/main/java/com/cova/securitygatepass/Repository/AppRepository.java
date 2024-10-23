package com.cova.securitygatepass.Repository;

import static com.cova.securitygatepass.Config.Constant.SHRD_PREF;
import static com.cova.securitygatepass.Config.Constant.SHRD_PREF_USER_TOKEN;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cova.securitygatepass.Model.BarcodeList.BarcodeListModel;
import com.cova.securitygatepass.Model.BarcodeList.ResultItem;
import com.cova.securitygatepass.Model.HeaderModel.HeaderBarcodeModel;
import com.cova.securitygatepass.Model.HeaderModel.Result;
import com.cova.securitygatepass.Model.LoginModel.LoginModel;
import com.cova.securitygatepass.Model.LoginModel.LoginRequest;
import com.cova.securitygatepass.Model.PostHeader.PostHeaderModel;
import com.cova.securitygatepass.Model.PostList.PostListModel;
import com.cova.securitygatepass.Service.ApiService;
import com.cova.securitygatepass.Service.ApiUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    private final Context context;
    private final ApiService apiService;
    private final SharedPreferences sharedPreferences;
    private MutableLiveData<LoginModel> loginModelLiveData;
    private MutableLiveData<HeaderBarcodeModel> headerBarcodeModelLiveData;
    private MutableLiveData<BarcodeListModel> barcodeListModelLiveData;
    private MutableLiveData<PostHeaderModel> headerPostDataLiveData;
    private MutableLiveData<PostListModel> listPostDataLiveData;
    private String userToken = "";

    public AppRepository(Application application) {
        context = application.getApplicationContext();
        apiService = ApiUtils.getApiService(context);
        sharedPreferences = context.getSharedPreferences(SHRD_PREF, Context.MODE_PRIVATE);
        userToken = sharedPreferences.getString(SHRD_PREF_USER_TOKEN, "");
    }

    public LiveData<LoginModel> getTokenLiveData() {
        if (loginModelLiveData == null) {
            loginModelLiveData = new MutableLiveData<>();
        }
        return loginModelLiveData;
    }

    public LiveData<HeaderBarcodeModel> getHeaderBarCodeLiveData() {
        if (headerBarcodeModelLiveData == null) {
            headerBarcodeModelLiveData = new MutableLiveData<>();
        }
        return headerBarcodeModelLiveData;
    }

    public LiveData<BarcodeListModel> getBarCodeListLiveData() {
        if (barcodeListModelLiveData == null) {
            barcodeListModelLiveData = new MutableLiveData<>();
        }
        return barcodeListModelLiveData;
    }

    public LiveData<PostHeaderModel> getHeaderPostDataLiveData() {
        if (headerPostDataLiveData == null) {
            headerPostDataLiveData = new MutableLiveData<>();
        }
        return headerPostDataLiveData;
    }

    public LiveData<PostListModel> getListPostDataLiveData() {
        if (listPostDataLiveData == null) {
            listPostDataLiveData = new MutableLiveData<>();
        }
        return listPostDataLiveData;
    }

    public void getToken(LoginRequest body) {

        apiService.getToken(body).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                try {
                    if (loginModelLiveData == null) {
                        getTokenLiveData();
                    }
                    if (response.isSuccessful()) {
                        loginModelLiveData.postValue(response.body());
                    } else {
                        Gson gson = new Gson();
                        LoginModel model = gson.fromJson(response.errorBody().charStream(), LoginModel.class);
                        loginModelLiveData.postValue(model);
                    }
                } catch (Exception e) {
                    loginModelLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                loginModelLiveData.postValue(null);
            }
        });
    }

    public void getHeaderBarCode(String docNum) {

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);

        apiService.getHeaderBarCode(map, docNum).enqueue(new Callback<HeaderBarcodeModel>() {
            @Override
            public void onResponse(Call<HeaderBarcodeModel> call, Response<HeaderBarcodeModel> response) {
                try {
                    if (headerBarcodeModelLiveData == null) {
                        getHeaderBarCodeLiveData();
                    }
                    if (response.isSuccessful()) {
                        headerBarcodeModelLiveData.postValue(response.body());
                    } else {
                        Gson gson = new Gson();
                        HeaderBarcodeModel model = gson.fromJson(response.errorBody().charStream(), HeaderBarcodeModel.class);
                        headerBarcodeModelLiveData.postValue(model);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Something went wrong.!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HeaderBarcodeModel> call, Throwable t) {
                headerBarcodeModelLiveData.postValue(null);
            }
        });
    }

    public void getBarCodeList(String docEntry, Integer groupnum) {

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);

        apiService.getBarCodeList(map, docEntry, groupnum).enqueue(new Callback<BarcodeListModel>() {
            @Override
            public void onResponse(Call<BarcodeListModel> call, Response<BarcodeListModel> response) {
                try {
                    if (barcodeListModelLiveData == null) {
                        getBarCodeListLiveData();
                    }
                    if (response.isSuccessful()) {
                        barcodeListModelLiveData.postValue(response.body());
                    } else {
                        Gson gson = new Gson();
                        BarcodeListModel model = gson.fromJson(response.errorBody().charStream(), BarcodeListModel.class);
                        barcodeListModelLiveData.postValue(model);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Something went wrong.!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BarcodeListModel> call, Throwable t) {
                barcodeListModelLiveData.postValue(null);
            }
        });
    }

    public void sendHeaderData(Result body) {

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);
        Log.e("TAG01", "01");
        Log.e("TAG02", ""+body);
        apiService.sendHeaderData(map, body).enqueue(new Callback<PostHeaderModel>() {
            @Override
            public void onResponse(Call<PostHeaderModel> call, Response<PostHeaderModel> response) {
                try {
                    Log.e("TAG01", "02");
                    if (headerPostDataLiveData == null) {
                        getHeaderPostDataLiveData();
                    }
                    if (response.isSuccessful()) {
                        Log.e("TAG01", "03 "+response);
                        headerPostDataLiveData.postValue(response.body());
                    } else {
                        Log.e("TAG01", "04 "+response.body());
                        Gson gson = new Gson();
                        PostHeaderModel model = gson.fromJson(response.errorBody().charStream(), PostHeaderModel.class);
                        headerPostDataLiveData.postValue(model);
                    }
                } catch (Exception e) {
                    Log.e("TAG01", "04 "+e.getMessage());
                    Toast.makeText(context, "Something went wrong.!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostHeaderModel> call, Throwable t) {
                barcodeListModelLiveData.postValue(null);
            }
        });
    }

    public void sendBarCodeList(List<ResultItem> resultItemList, Integer groupnum) {

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);

        apiService.sendBarCodeList(map, resultItemList, groupnum).enqueue(new Callback<PostListModel>() {
            @Override
            public void onResponse(Call<PostListModel> call, Response<PostListModel> response) {
                try {
                    if (listPostDataLiveData == null) {
                        getListPostDataLiveData();
                    }
                    if (response.isSuccessful()) {
                        listPostDataLiveData.postValue(response.body());
                    } else {
                        Gson gson = new Gson();
                        PostListModel model = gson.fromJson(response.errorBody().charStream(), PostListModel.class);
                        listPostDataLiveData.postValue(model);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Something went wrong.!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostListModel> call, Throwable t) {
                listPostDataLiveData.postValue(null);
            }
        });
    }

}
