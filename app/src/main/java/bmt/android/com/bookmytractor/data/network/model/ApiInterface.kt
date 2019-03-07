package bmt.android.com.bookmytractor.data.network.model

import bmt.android.com.bookmytractor.data.model.BookMachine.BookMachineResponse
import bmt.android.com.bookmytractor.data.model.BookMachine.SuccessResponse
import bmt.android.com.bookmytractor.data.model.CreateAccountPojo.SignUpResponse
import bmt.android.com.bookmytractor.data.model.CropListPojo.CropListResponse
import bmt.android.com.bookmytractor.data.model.ForgotPasswordPojo.ForgotPwdResponse
import bmt.android.com.bookmytractor.data.model.MachinesListPojo.MachinesListResponse
import bmt.android.com.bookmytractor.data.model.UserBookingsPojo.UserBookingsListResponse
import bmt.android.com.bookmytractor.data.model.VerifyOtpPojo.VerifyOtpResponse
import retrofit2.http.*

interface ApiInterface
{
    @POST("login")
    @FormUrlEncoded
    fun getLoginResponse(@Field("mobile") param1: String?, @Field("password") param2: String?): io.reactivex.Observable<SignUpResponse>

    @POST("signup")
    @FormUrlEncoded
    fun getSignUpResponse(@Field("name") userName: String, @Field("email") userEmail: String, @Field("mobile") userMobile: String, @Field("password") userPwd: String): io.reactivex.Observable<SignUpResponse>


    @POST("verify-otp")
    @FormUrlEncoded
    fun getOtpVerificationResponse(@Field("otp") param1: String, @Field("user_id") param2: Long): io.reactivex.Observable<VerifyOtpResponse>

    @POST("resend-sms")
    @FormUrlEncoded
    fun getResendSmsResponse(@Field("mobile") param1: String, @Field("password") param2:String): io.reactivex.Observable<SignUpResponse>

    @POST("add-password")
    @FormUrlEncoded
    fun getAddPwdResponse(@Field("user_id") param1: Long?,@Field("new") param2: String): io.reactivex.Observable<SignUpResponse>

    @POST("change-password")
    @FormUrlEncoded
    fun getChangePwdResponse(@Field("user_id") userId: String, @Field("old") oldPwd:String,@Field("new") newPws:String,@Field("access_token") param3: String) : io.reactivex.Observable<SignUpResponse>

    @POST("forgot-password")
    @FormUrlEncoded
    fun getForgotPwdResponse(@Field("mobile") param1: String): io.reactivex.Observable<ForgotPwdResponse>

    @GET("crops")
    fun getCropList(@Query("page") param1: Int, @Query("user_id") param2: Long, @Query("key") param3: String): io.reactivex.Observable<CropListResponse>

    @GET("machines")
    fun getMachinesList(@Query("user_id") param2: Long, @Query("crop") cropName: String,@Query("page") param1: Int): io.reactivex.Observable<MachinesListResponse>

    @POST("machines/{machineId}/book")
    @FormUrlEncoded
    fun toBookMachine(@Path("machineId") machineId:String,
                      @Field("mobile") mobile: String,
                      @Field("user_id") userId: String,
                      @Field("date_from") dateFrom: String,
                      @Field("date_to") dateTo: String,
                      @Field("type_id") typeId: String,
                      @Field("email") email: String,
                      @Field("full_address") fullAddress: String,
                      @Field("location") location: String,
                      @Field("sqf_area") sqfArea: String): io.reactivex.Observable<BookMachineResponse>


    @POST("machines/{machineId}/check")
    @FormUrlEncoded
    fun getMachinesAvailability(@Field("user_id") userId: String,@Field("date_from") dateFrom: String,@Field("date_to") dateTo: String): io.reactivex.Observable<SuccessResponse>


    @POST("machines/{machineId}/success")
    @FormUrlEncoded
    fun getMachineBookSuccessResponse(@Path("machineId") machineId:String,@Field("user_id") userId: String,@Field("payment_id") dateFrom: String): io.reactivex.Observable<SuccessResponse>

    @POST("machines/{machineId}/failure")
    @FormUrlEncoded
    fun getMachineBookFailureResponse(@Path("machineId") machineId:String,@Field("user_id") userId: String,@Field("payment_id") paymentId: String): io.reactivex.Observable<SuccessResponse>

    @GET("bookings")
    fun getUserBookingsList(@Field("user_id") userId: String,@Field("page") page: String): io.reactivex.Observable<UserBookingsListResponse>





}