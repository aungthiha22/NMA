package com.rebook.nma.sync;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.File;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by YDN on 7/3/2017.
 */
public interface SyncPostService {

    @Headers({"X-Authorization:a01bd6c00469ee105a1b1ec86c0a456ba0ce95bb"})
    @GET("/product/latest")void getProductLatest(@Query("page") int page,
                                         Callback<JsonArray> jsonArrayCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/sliders")void getSlider(@Query("page") int page,
                                                 Callback<JsonArray> jsonArrayCallback);


    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/courses")void getCourses(@Query("page") int page,
                                                 Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/all_courses")void getAllCourses(@Query("page") int page,
                                    Callback<JsonObject> jsonObjectCallback);


    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/courses/detail/{id}")void getCourseDetail(@Path("id") String id,
                                                      Callback<JsonObject> jsonArrayCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @Multipart @POST("/authenticate") void getLogin(
            @Part("email")String email,
            @Part("password") String password,
            Callback<JsonObject> response);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/events")void getEvents(@Query("page") int page,
                                                     Callback<JsonObject> jsonArrayCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/events/detail/{id}")void getEventDetail(@Path("id") String id,
                                                     Callback<JsonObject> jsonArrayCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/jobs")void  getJobs(@Query("page") int page,
                                  Callback<JsonObject> jsonArrayCallback);
    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/blogs")void  getBlogs(@Query("page") int page,
                               Callback<JsonObject> jsonArrayCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/jobs/detail/{id}")void getjobDetail(@Path("id") String id,
                                                   Callback<JsonObject> jsonArrayCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/current_courses/{token}")void getCurrentCourse(@Path("token") String token,
                                               Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/assignments/{token}")void getAssignment(@Path("token") String token,
                                                    Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @Multipart @POST("/assignments/create/{token}") void getUploadAssignment(
            @Part("answer_file") TypedFile file,
            @Part("description") String description,
            @Path("token") String token,
            Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/assignments/detail/{id}/{token}")void getAssignmentDetail(@Path("id") String id,
                                                                         @Path("token") String token,
                                                   Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/announcements/{token}")void getAnnouncement(@Path("token") String token,
                                                   Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/announcements/detail/{id}/{token}")void getAnnouncementsDetail(@Path("id") String id,
                                                                     @Path("token") String token,
                                                                     Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/marks/{token}")void getMarks(@Path("token") String token,
                                                       Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/marks/detail/{id}/{token}")void getMarkDetail(@Path("id") String id,
                                                                          @Path("token") String token,
                                                                          Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @GET("/attendances/{id}/{token}")void getAttendanceList(@Path("id") String id,
                                                         @Path("token") String token,
                                                         Callback<JsonObject> jsonObjectCallback);

    @Headers({"X-Authorization:4464575fedb36812fa7bddf836e2fb87bb49cead"})
    @Multipart @POST("/enroll") void getEnroll(
            @Part("course_id")String courseId,
            @Part("batch_id") String batchId,
            @Part("student_id") String studentId,
            Callback<JsonObject> response);



}