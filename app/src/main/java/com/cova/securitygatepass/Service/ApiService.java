package com.cova.securitygatepass.Service;

import com.cova.securitygatepass.Model.BarcodeList.BarcodeListModel;
import com.cova.securitygatepass.Model.BarcodeList.ResultItem;
import com.cova.securitygatepass.Model.HeaderModel.HeaderBarcodeModel;
import com.cova.securitygatepass.Model.HeaderModel.Result;
import com.cova.securitygatepass.Model.LoginModel.LoginModel;
import com.cova.securitygatepass.Model.LoginModel.LoginRequest;
import com.cova.securitygatepass.Model.PostHeader.PostHeaderModel;
import com.cova.securitygatepass.Model.PostList.PostListModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/auth/login")
    Call<LoginModel> getToken(@Body LoginRequest body);

    @GET("/api/admin/barcodeheader")
    Call<HeaderBarcodeModel> getHeaderBarCode(@HeaderMap Map<String, String> headers,
                                              @Query("docnum") String docNum);

    @GET("/api/admin/barcodelist")
    Call<BarcodeListModel> getBarCodeList(@HeaderMap Map<String, String> headers,
                                          @Query("docentry") String docEntry,
                                          @Query("groupnum") Integer groupnum);

    @POST("/api/admin/barcodeheader")
    Call<PostHeaderModel> sendHeaderData(@HeaderMap Map<String, String> headers, @Body Result result);

    @POST("/api/admin/barcodelist")
    Call<PostListModel> sendBarCodeList(@HeaderMap Map<String, String> headers,
                                        @Body List<ResultItem> resultItemList,
                                        @Query("groupnum") Integer groupNum);

}
