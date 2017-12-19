package retrofit;

import com.rubysoftware.listCheckin.models.LogutMessage;
import com.rubysoftware.listCheckin.models.checkin.Checkin22;
import com.rubysoftware.listCheckin.models.syncccheckin.data.CheckinSyncDataModel;

import java.util.HashMap;
import java.util.List;

import create.checkin.models.EmployeesJSON;
import create.checkin.models.dynamic.checkintemplate.DynamicTemplate;
import okhttp3.RequestBody;
import report.checkin.model.CheckinReportCount;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import show.checkin.model.activity.CheckinActivtyObject;
import show.checkin.model.comment.CheckinComment1;
import show.checkin.model.comment.CommentOuterJSON;
import show.checkin.model.milestone.Milestones;
import show.checkin.model.show.AssignedEmployee;
import show.checkin.model.show.CheckinJSONShow;
import show.checkin.model.update.CheckinUpdate1;
import show.checkin.model.update.UpdateOuterJSON;

/**
 * Created by rmed177lt on 2/9/16.
 */

public interface ApiInterface {


    //checkin report
    @GET("checkins/summary_counts")
    Call<CheckinReportCount> getCheckinReportCounts();



    @HTTP(method = "DELETE", path = "sessions/{id}", hasBody = true)
    Call<LogutMessage> logoutUser(@Path("id") int id, @Body HashMap<String, String> body);


    //Show a specific checkin
    @GET("checkins/{id}")
    Call<CheckinJSONShow> getSingleCheckin(
            @Path("id") int id);

    //List checkin for an employee or manager
    @GET("checkins?")
    Call <Checkin22> getEmployeeOrManagerCheckin(@Query("assigned_to") int id,
                                                 @Query("limit") int limit,
                                                 @Query("page") int page_number,
                                                 @Query("type") String type,
                                                 @Query("search") String search);
//    @GET("checkins?")
//    Call <Checkin22> getEmployeeOrManagerCheckin(@Header("Authorization") String token,
//                                                 @Header("Content-Type") String token2 ,
//                                                 @Query("assigned_to") int id ,
//                                                 @Query("limit") int limit,
//                                                 @Query("page_checkin_id") int page_checkin_id,
//                                                 @Query("page_type") String page_type);



   //List assigned employees checkin for a manager //Teams
    @GET("checkins?")
    Call<Checkin22>  getManagerAssignedEmployeeCheckin(@Query("manager_id") int id,
                                                       @Query("limit") int limit,
                                                       @Query("page") int page_number,
                                                       @Query("type") String type,
                                                       @Query("search") String search);
//    @GET("checkins?")
//    Call<Checkin22>  getManagerAssignedEmployeeCheckin(@Header("Authorization") String token,
//                                                       @Header("Content-Type") String token2 ,
//                                                       @Query("manager_id") int id ,
//                                                       @Query("limit") int limit,
//                                                       @Query("page_checkin_id") int page_checkin_id,
//                                                       @Query("page_type") String page_typed);




    //Fetch checkin activities thats updates comment
    @GET("checkins/{id}/checkin_activities?")
    Call<CheckinActivtyObject> getActvitiesOfCheckin(@Path("id") int id,
                                                     @Query("page_activity_id") int page_checkin_id,
                                                     @Query("page_type") String page_typed,
                                                     @Query("activity_limit") int limit
    );


    //List checkin updates
    @GET("checkin_updates")
    Call<List<CheckinUpdate1>> getCheckinUpdates(@Query("checkin_id") int id);


    //List checkin Comments
    @GET("checkin_comments")
    Call<List<CheckinComment1>> getCheckinComments(@Query("checkin_id") int id);

    //create checkin comment
    @POST("checkins/{id}/checkin_comments")
    Call<CommentOuterJSON> createCheckinComment(@Path("id") int id,
                                                @Body RequestBody body);

    //create checkin update
    @POST("checkins/{id}/checkin_updates")
    Call<UpdateOuterJSON> createCheckinUpdate(@Path("id") int id,
                                              @Body RequestBody body);

    //
    @GET("employees")
    Call<EmployeesJSON> getAssignedEmployeesToCreateCheckin();

    //
    @GET("checkins/{id}/milestone_labels")
    Call<Milestones> getAssignedMilestones(@Path("id") int id);


    //create a checkin
    @POST("checkins")
    Call<CheckinJSONShow> createCheckin(@Body RequestBody body);


    //edit a checkin
    @PUT("checkins/{id}")
    Call<CheckinJSONShow> editCheckin(@Body RequestBody body,
                                      @Path("id") String id);


    //delete a checkin
    @DELETE("checkins/{id}")
    Call<AssignedEmployee> deleteCheckin(@Body RequestBody body,
                                         @Path("id") int id);

    //fetch for team checkin sync data  //manager
    @POST("checkins/checkin_list")
    Call<CheckinSyncDataModel> getMetaDataOfTopCheckins(@Query("manager_id") int id,
                                                        @Body RequestBody body);

 //fetch for my checkin sync data
 @POST("checkins/checkin_list")
 Call<CheckinSyncDataModel> getMetaDataOfTopCheckinsMy(@Query("assigned_to") int id,
                                                       @Body RequestBody body);

    //fetch detail of specific checins
    @POST("checkins/checkin_list")
    Call<Checkin22> getSpecificCheckinDeatils(@Body RequestBody body);



    //milestone update
    @PUT("checkins/{checkin_id}/checkin_updates/{milestone_id}")
    Call<UpdateOuterJSON> updateMilestone(@Body RequestBody body,
                                          @Path("checkin_id") int checkin_id,
                                          @Path("milestone_id") int milestone_id);



    //List MIsssed checkin
    @GET("checkins/missed_checkins")
    Call <Checkin22> getMissedCheckin(@Query("assigned_to") int a_id,
                                      @Query("limit") int limit,
                                      @Query("page") int page_number);
    //List MIsssed checkin
    @GET("checkins/missed_checkins")
    Call <Checkin22> getMissedCheckinManger(@Query("manager_id") int m_id,
                                            @Query("limit") int limit,
                                            @Query("page") int page_number);



    //List MIsssed milestone
    @GET("checkins/missed_milestones")
    Call <Checkin22> getMissedMilestone(@Query("assigned_to") int a_id,
                                        @Query("limit") int limit,
                                        @Query("page") int page_number);
    //List MIsssed milestone
    @GET("checkins/missed_milestones")
    Call <Checkin22> getMissedMilestoneManager(
            @Query("manager_id") int m_id,
            @Query("limit") int limit,
            @Query("page") int page_number);


    //DrillDown
    @GET("checkins/summary_checkins")
    Call <Checkin22> getDrillDownResult(@Query("code_name") String code_name,
                                        @Query("type") String type,
                                        @Query("limit") int limit,
                                        @Query("page") int page_number);


    //Dynamic checkin render page details
    @GET("checkin_templates")
    Call <DynamicTemplate> getCheckinTemplates(@Query("id") String template_id,
                                               @Query("checkin_id") String checkin_id);



}