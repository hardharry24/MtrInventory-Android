package services;

import models.Brand;
import models.Model;
import models.Motorcycle;
import models.MyOrder;
import models.Order;
import models.OrderDetails;
import models.Payment;
import models.Remaining;
import models.Response;
import models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIservice {
    /******USER API*****/
    @FormUrlEncoded
    @POST("APIs/UserLogin.php")
    Call<Response> UserLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("APIs/UserCreate.php")
    Call<Response> UserRegistration(@Field("lastname") String lastname, @Field("firstname") String firstname, @Field("mi") String mi, @Field("username") String username, @Field("password") String password, @Field("role") String role,@Field("code") String code);

    @FormUrlEncoded
    @POST("APIs/UserCodeVerification.php")
    Call<Response> UserVerification(@Field("username") String username, @Field("code") String code);

    @FormUrlEncoded
    @POST("APIs/UserGetUser.php")
    Call<User> UserGetCurrent(@Field("username") String username);

    @FormUrlEncoded
    @POST("APIs/OrderMethods.php")
    Call<Response> UserOrder(@Field("action") String action,@Field("orderMotorId") String orderMotorId,@Field("orderQty") String orderQty,@Field("orderUsername") String orderUsername);

    @FormUrlEncoded
    @POST("APIs/UtilsGetMethod.php")
    Call<Order> UserGetOrder(@Field("action") String action,@Field("username") String username);

    @FormUrlEncoded
    @POST("APIs/OrderMethods.php")
    Call<Response> OrderRemove(@Field("action") String action,@Field("orderId") String orderId);

    @FormUrlEncoded
    @POST("APIs/OrderMethods.php")
    Call<Response> OrderCheckOut(@Field("action") String action,@Field("ordertbMasterId") String ordertbMasterId,@Field("orderMstrDateTime") String orderMstrDateTime,@Field("orderMstrUsername") String username);

    @FormUrlEncoded
    @POST("APIs/OrderMethods.php")
    Call<Response> UpdateMotorDetails(@Field("action") String action,@Field("mtrId") String mtrId,@Field("orderQty") String orderQty);

    @FormUrlEncoded
    @POST("APIs/PaymentMethod.php")
    Call<Response> OrderPayments(@Field("action") String action,@Field("pOrderMsterId") String pOrderMsterId,@Field("pAmount") String pAmount,@Field("pTotal") String pTotal,@Field("pChange") String pChange,@Field("pUsername") String pUsername,@Field("pDateTime") String pDateTime);

    @FormUrlEncoded
    @POST("APIs/UtilsGetMethod.php")
    Call<MyOrder> getMyOrder(@Field("action") String action, @Field("username") String username);

    @FormUrlEncoded
    @POST("APIs/UtilsGetMethod.php")
    Call<Payment> getPayment(@Field("action") String action, @Field("username") String username);

    @FormUrlEncoded
    @POST("APIs/UtilsGetMethod.php")
    Call<OrderDetails> getOrderDetails(@Field("action") String action, @Field("orderId") String orderId);

    /**************************         ADMIN API         *****************************/


    @FormUrlEncoded
    @POST("APIs/UtilsGetMethod.php")
    Call<Brand> getAllBrand(@Field("action") String action);

    @FormUrlEncoded
    @POST("APIs/UtilsGetMethod.php")
    Call<Model> getAllModel(@Field("action") String action,@Field("mtrBrand") String mtrBrand);

    @FormUrlEncoded
    @POST("APIs/MotorcycleMethods.php")
    Call<Response> adminSaveMtr(@Field("action") String action, @Field("mtrBrand") String mtrBrand,@Field("mtrModel") String mtrModel,@Field("mtrQty") int mtrQty,@Field("mtrPrice") String mtrPrice,@Field("mtrDateTime") String mtrDateTime,@Field("mtrImg") String mtrImg);

    @FormUrlEncoded
    @POST("APIs/UtilsGetMethod.php")
    Call<Motorcycle> getMotorcycles(@Field("action") String action);

    @FormUrlEncoded
    @POST("APIs/UtilsGetMethod.php")
    Call<Remaining> getRemainingMtr(@Field("action") String action);

    @FormUrlEncoded
    @POST("APIs/UtilsGetMethod.php")
    Call<Remaining> getSoldMtrBy(@Field("action") String action,@Field("type") String type,@Field("date") String date);

}
