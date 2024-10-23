package com.cova.securitygatepass.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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
import com.cova.securitygatepass.Repository.AppRepository;

import java.util.List;

public class AppViewModel extends AndroidViewModel {

    private final AppRepository appRepository;
    private LiveData<LoginModel> loginModelLiveData;
    private LiveData<HeaderBarcodeModel> headerBarcodeModelLiveData;
    private LiveData<BarcodeListModel> barcodeListModelLiveData;
    private LiveData<PostHeaderModel> headerPostDataLiveData;
    private LiveData<PostListModel> listPostDataLiveData;

    public AppViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }

    public LiveData<LoginModel> getTokenLiveData() {
        if (loginModelLiveData == null) {
            loginModelLiveData = new MutableLiveData<>();
        }
        loginModelLiveData = appRepository.getTokenLiveData();
        return loginModelLiveData;
    }

    public void getToken(LoginRequest body) {
        appRepository.getToken(body);
    }

    public LiveData<HeaderBarcodeModel> getHeaderBarCodeLiveData() {
        if (headerBarcodeModelLiveData == null) {
            headerBarcodeModelLiveData = new MutableLiveData<>();
        }
        headerBarcodeModelLiveData = appRepository.getHeaderBarCodeLiveData();
        return headerBarcodeModelLiveData;
    }

    public void getHeaderBarCode(String docNum) {
        appRepository.getHeaderBarCode(docNum);
    }

    public LiveData<BarcodeListModel> getBarCodeListLiveData() {
        if (barcodeListModelLiveData == null) {
            barcodeListModelLiveData = new MutableLiveData<>();
        }
        barcodeListModelLiveData = appRepository.getBarCodeListLiveData();
        return barcodeListModelLiveData;
    }

    public void getBarCodeList(String docEntry, Integer groupnum) {
        appRepository.getBarCodeList(docEntry, groupnum);
    }

    public LiveData<PostHeaderModel> getHeaderPostDataLiveData() {
        if (headerPostDataLiveData == null) {
            headerPostDataLiveData = new MutableLiveData<>();
        }
        headerPostDataLiveData = appRepository.getHeaderPostDataLiveData();
        return headerPostDataLiveData;
    }

    public void sendHeaderData(Result body) {
        appRepository.sendHeaderData(body);
    }

    public LiveData<PostListModel> getListPostDataLiveData() {
        if (listPostDataLiveData == null) {
            listPostDataLiveData = new MutableLiveData<>();
        }
        listPostDataLiveData = appRepository.getListPostDataLiveData();
        return listPostDataLiveData;
    }

    public void sendBarCodeList(List<ResultItem> resultItemList, Integer groupnum) {
        appRepository.sendBarCodeList(resultItemList, groupnum);
    }
}
