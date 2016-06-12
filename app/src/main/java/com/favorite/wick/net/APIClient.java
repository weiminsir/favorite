package com.favorite.wick.net;

/**
 * Created by Weimin on 4/7/2016.
 */
public interface APIClient {
    /**
     * BaseURL
     * APIInstance
//     */
//    @FormUrlEncoded
//    @POST("/fryms/api/login.php")
//    Observable<NObject<NUser>> login(@Field("username") String username,
//                                     @Field("password") String password);
//
//    @GET("/fryms/api/register.php")
//    Observable<NObject<NUser>> register(@Query("username") String username,
//                                        @Query("password") String password,
//                                        @Query("name") String name,
//                                        @Query("mobile") String mobile,
//                                        @Query("verify_code") String verify_code,
//                                        @Query("login") int login);
//
//    @FormUrlEncoded
//    @POST("/fryms/api/register_B.php")
//    Observable<NObject<NUser>> registerB();
//
//    @GET("/fryms/api/change_password.php")
//    Observable<NObject<Boolean>> changePassword(@Query("username") String username,
//                                                @Query("password") String password,
//                                                @Query("new_password") String new_password);
//
//    @GET("/fryms/api/check_verify_code.php")
//    Observable<NObject<String>> checkVerifyCode(@Query("mobile") String mobile,
//                                                @Query("verify_code") String verify_code);
//
//    @GET("/fryms/api/user_verify_code.php")
//    Observable<NObject<Boolean>> userVerifyCode(@Query("username") String username);
//
//    @GET("/fryms/api/send_verify_code.php")
//    Observable<NObject<Boolean>> sendVerifyCode(@Query("mobile") String mobile);
//
//    @GET("/fryms/api/reset_password.php")
//    Observable<NObject<Boolean>> resetPassword(@Query("username") String username,
//                                               @Query("password") String password,
//                                               @Query("verify_code") String verify_code);
//
//    @GET("/fryms/api/update_user_idcard.php")
//    Observable<NObject<String>> updateUserIdcard(@Query("idcard") String idcard);
//
//    @GET("/fryms/api/get_question_list.php")
//    Observable<NObject<NQuestion>> getQuestionList(@Query("page_no") int page_no,
//                                                   @Query("page_size") int page_size);
//
//    @GET("/fryms/api/get_question_list_v2.php")
//    Observable<NObjectList<NQuestion>> getQuestionListV2(@Query("index") int index,
//                                                         @Query("count") int count);
//
//    @GET("/fryms/api/get_my_qa.php")
//    Observable<NObject<NQuestion>> getMyQAList(@Query("page_no") int page_no,
//                                               @Query("page_size") int page_size,
//                                               @Query("type") int type);
//
//
//    @GET("/fryms/api/get_my_qa_v2.php")
//    Observable<NObjectList<NQuestion>> getMyQAListV2(@Query("index") int index,
//                                                     @Query("count") int count,
//                                                     @Query("filter") int filter);
//
//    // 获取我收藏的提问列表
//    @GET("/fryms/api/get_favorite_question.php")
//    Observable<NObject<NQuestion>> getFavoriteQuestion(@Query("page_no") int page_no,
//                                                       @Query("page_size") int page_size,
//                                                       @Query("type") int type);
//
//
//    @GET("/fryms/api/get_favorite_question_v2.php")
//    Observable<NObjectList<NQuestion>> getFavoriteQuestionV2(@Query("index") int index,
//                                                             @Query("count") int count,
//                                                             @Query("filter") int filter);
//
//    //2.15 获取回答列表   此处特殊
//    @GET("/fryms/api/get_answer_list.php")
//    Observable<NObject<NQuestion>> getAnswerList(@Query("question_id") long question_id,
//                                                 @Query("page_size") int page_size,
//                                                 @Query("type") int type);
//
//    @GET("/fryms/api/get_favorite_question_v2.php")
//    Observable<NObjectList<NQuestion>> getAnswerListV2(@Query("question_id") long question_id,
//                                                       @Query("answer_id") long answer_id,
//                                                       @Query("count") int count);
//
//    @GET("/fryms/api/get_answer_comment_list.php")
//    Observable<NObjectList<NQuestion>> getAnswerCommentList(@Query("question_id") long question_id,
//                                                            @Query("ac_id") long ac_id,
//                                                            @Query("count") int count);
//
//    //2.31  获取资讯列表
//    @GET("/fryms/api/get_information_list.php")
//    Observable<NObject<NInformation>> getInformationList(@Query("page_no") int page_no,
//                                                         @Query("page_size") int page_size);
//
//
//    @GET("/fryms/api/get_information_list_v2.php")
//    Observable<NObjectList<NInformation>> getInformationListV2(@Query("information_id") long information_id,
//                                                               @Query("count") int count);
//
//
//    @GET("/fryms/api/get_information_list_v3.php")
//    Observable<NObjectList<NInformation>> getInformationListV3(@Query("information_id") long information_id,
//                                                               @Query("count") int count);
//
//    //获取资讯详情
//    @GET("/fryms/api/get_information.php")
//    Observable<NObject<NInformation>> getInformation2(@Query("information_id") int information_id);
//
//    //2.49 获取首页信息（9条）
//    @GET("/fryms/api/get_i9.php")
//    Observable<NObjectList<NInformationData>> getInformation();
//
//    @GET("/fryms/api/get_information_list.php")
//    Observable<NObjectList<NInformationData>> getInformationHome();
//
//    @GET("/fryms/api/logout.php")
//    Observable<NObject<String>> logout();
//
//    //2.51 创建二维码信息
//    @GET("/fryms/api/creat_QR_Code.php")
//    Observable<NObject<NQRCode>> creatQRCode();
//
//    @GET("/fryms/api/login_QR.php")
//    Observable<NObject<NUser>> loginQR(@Query("username") String username,
//                                       @Query("verify_code") String verify_code);
//
//    @GET("/fryms/api/send_verify_code_E.php")
//    Observable<NObject<Boolean>> sendVerifyCodeE(@Query("Email") String Email,
//                                                 @Query("request") String request);
//
//    @GET("/fryms/api/register_Email_C.php")
//    Observable<NObject<NUser>> registerEmail(@Query("Email") String Email,
//                                             @Query("verify_code") String verify_code,
//                                             @Query("log_verify_code") String log_verify_code);
//
//    @GET("/fryms/api/reset_password_E.php")
//    Observable<NObject<Boolean>> resetPasswordE(@Query("username") String username,
//                                                @Query("password") String password,
//                                                @Query("verify_code") String verify_code);
//
//    @GET("/fryms/api/update_user_information.php")
//    Observable<NObject<NUser>> updateUserInformation(@Query("idcard") String idcard,
//                                                     @Query("name") String name);
//
//    @GET("/fryms/api/login_quick.php")
//    Observable<NObject<NUser>> loginQuick(@Query("mobile") String mobile,
//                                          @Query("verify_code") String verify_code);
//
//    @GET("/fryms/api/login_quick_E.php")
//    Observable<NObject<NUser>> loginQuickE(@Query("Email") String Email,
//                                           @Query("verify_code") String verify_code);
//
//    @GET("/fryms/api/get_mt_sample.php")
//    Observable<NObject<List<String>>> getMtSample(@Query("MtId") String MtId);
//
//    @FormUrlEncoded
//    @POST("/fryms/api/upload.php")
//    Observable<NObject<String>> Upload(@Field("file") String file);
//
//    @GET("/fryms/api/upload.php")
//    Observable<Response> acquirePNG(@Query("size") String size,
//                                    @Query("guid") String guid);
//
//    /**
//     * AFFAIR_URL
//     * APIInstance
//     */
//    //2.1 办事指南列表 	page 可选，页数，从1开始       每页显示记录数，默认5条
//    @GET("/shixiang/street.php")
//    Observable<NObjectList<NMatter>> affairGuide(@Query("depid") String depid,
//                                                 @Query("perpage") String perpage);
//
//    //2.1 办事指南列表 	page 可选，页数，从1开始       每页显示记录数，默认5条
//    @GET("/shixiang/street.php")
//    Observable<NObject<NMatter>> AffairGuideDetail(@Query("mtid") String mtid);
//
//    @GET("/shixiang/handle_search.php")
//    Observable<NObjectList<NMatter>> HandleSearch(@Query("titile") String titile);
//
//    @GET("/cailiao/mobile/banshi/matterssearch/{titile}/")
//    Observable<NObjectList<NMatter>> MattersSearch(@Query("titile") String titile);
//
//    @GET("/shixiang/user_handle_affairs.php")
//    Observable<NObjectList<NAffairState>> UserHandleAffairs(@Query("uid") String uid);
//
//    @GET("/shixiang/user_handle_affairs.php")
//    Observable<NObjectList<NMatterMaterial>> MatterMaterial(@Query("only_number") String only_number);
//
//    @GET("/get_only_number.php")
//    Observable<NObject<NOnlyNumber>> GetOnlyNumber(@Query("hs") String hs);
//
//    //获取所有事项
//    @GET("/shixiang/get_mt_matter.php")
//    Observable<NObject<NMtMatter>> GetMtMatter();
//
//    //获取所有材料
//    @GET("shixiang/get_mt_material.php")
//    Observable<NObjectList<NMtMaterial>> GetMtMaterial();
//
//    // 获取所有部门
//    @GET("/shixiang/get_dic_department.php")
//    Observable<List<NDepartment>> getDicDepartment();
//
//    //部门事项
//    @GET("/cailiao/mobile/banshi/getDepartmentMatter/{code}/")
//    Observable<NObjectList<NSectionMatter>> getDepartmentMatter(@Query("code") String code);
//
//    //办事记录列表
//    @GET("/shixiang/user_handle_affairs.php")
//    Observable<NObjectList<NAffairRecord>> affairRecord(@Query("uid") String uid);
//
//
//    @GET("/shixiang/user_handle_affairs.php")
//    Observable<NObjectList<NMatterMaterial>> affairMaterial(@Query("only_number") String only_number);
//
//    //办事指南事项详情
//    @GET("/shixiang/street.php")
//    Observable<NObject<NMatterDetail>> getMatter1(@Query("mtid") String mtid);
//
//    //办事指南事项详情
//    @GET("/cailiao/mobile/banshi/getmatter/{number}/")
//    Observable<NObject<NMatterDetail>> getMatter2(@Query("number") String number);
//
//    /***
//     * LINE_UP_URL
//     * APIInstance3
//     */
//    @GET("/bs/queue/site_login.php")
//    Observable<NObject<NPadUser>> SiteLogin(@Query("username") String username,
//                                            @Query("password") String password);
//
//    @GET("/bs/queue/get_system.php")
//    Observable<NObjectList<NWindowInfo>> getSystem(@Query("siteid") String siteid,
//                                                   @Query("log_verify_code") String log_verify_code);
//
//    // 查询当前排队人数
//    @GET("/bs/queue/check_queue.php")
//    Observable<NObject<NQueueNum>> checkQueue(@Query("system") String system,
//                                              @Query("log_verify_code") String log_verify_code);
//
//    @GET("/bs/queue/user_get.php")
//    Observable<NObject<NUserNumber>> userGetNum(@Query("mobile") String mobile,
//                                                @Query("system") String system,
//                                                @Query("log_verify_code") String log_verify_code);
//
//    @GET("/bs/queue/giveup.php")
//    Observable<NObject<String>> giveUpNum(@Query("system") String system,
//                                          @Query("mobile") String mobile,
//                                          @Query("log_verify_code") String log_verify_code);
//
//    @GET("/bs/queue/user_status.php")
//    Observable<NObject<NUserNumber>> queryUserStatus(@Query("queue_id") String queue_id,
//                                                     @Query("log_verify_code") String log_verify_code);
//
//    @GET("/bs/queue/win_call.php")
//    Observable<NObject<NUserNumber>> winCall(@Query("win") int win,
//                                             @Query("site_id") int site_id);
//
//    @GET("/bs/queue/win_start.php")
//    Observable<NObject<NUserNumber>> winStart(@Query("win") int win,
//                                              @Query("site_id") int site_id);
//
//    @GET("/bs/queue/win_end.php")
//    Observable<NObject<Boolean>> winEnd(@Query("win") int win,
//                                        @Query("site_id") int site_id);
//
//    @GET("/bs/queue/change_queue.php")
//    Observable<NObject<NUserNumber>> changeQueue(@Query("queue_id") int queue_id);
//
//    @GET("/bs/queue/all_status.php")
//    Observable<NObjectList<NUserNumber>> allStatus(@Query("system") String system);
//
//    //有待解决
//    @GET("/bs/queue/site_status.php")
//    Observable<NObjectList<NUserNumber>> siteStatus(@Query("site_id") int site_id);
//
//    //获取图片材料
//    @GET("/cailiao/api/fileapi/getimage")
//    Observable<NObject<NMultipleImage>> getShrinkImage(@Query("id") String id);
//
//
//    @FormUrlEncoded
//    @POST("/cailiao/api/fileapi/uploadfile")
//    Observable<NObject<String>> uploadImage(@Field("Type") String Type,
//                                            @Field("Id") String Id,
//                                            @Field("GuId") String GuId,
//                                            @Field("File") String File);
//
//    //删除图片材料一张
//    @GET("/cailiao/api/fileapi/deletefile")
//    Observable<NObject<String>> deleteOne(@Query("id") String id,
//                                          @Query("guid") String guid);
//
//    //删除多张图片材料
//    @GET("/cailiao/api/fileapi/deletefiles")
//    Observable<NObject<String>> deleteAll(@Query("id") String id);
//
//    //获取单张图片
//    @GET("/cailiao/api/fileapi/getfile")
//    Observable<NObject<NGetSingleImage>> getSingleImg(@Query("id") String id,
//                                                      @Query("guid") String guid);
//
//    //acauire notice Meassage
//    @GET("/result/get_notify_status.php")
//    Observable<NObjectList<NUserNoticeInfo>> getUserNoticeList(@Query("uid") String uid,
//                                                               @Query("list") String list);
//
//    @GET("/result/get_notify_status.php")
//    Observable<NObject<List>> getUserConfirm(@Query("uid") String uid,
//                                             @Query("mtid") String mtid);
//
//    @GET("/result/get_notify_status.php")
//    Observable<NObjectList<NUserNoticeInfo>> getUserNotice(@Query("uid") String uid);



}
