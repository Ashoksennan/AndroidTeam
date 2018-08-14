package chennaicitytrafficapplication.prematix.com.etownpublic.common;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Admin on 8/3/2018.
 */

public interface RetrofitInterface {
    @GET("EPSGetDistrictDetails")
    Call<Object>getDistricts(@Header("accessToken") String accessToken);
    @GET("EPSGetPanchayatDetails")
    Call<Object>getPanchayat(@Header("accessToken") String accessToken,
                             @Query("DistrictId") int DistrictId);
    @GET("EPSTaxMasterDetails")
    Call<Object>getMasterDetails(@Header("accessToken") String accessToken,
                                 @Query("Type") String Type,
                                 @Query("Input") String Input,
                                 @Query("District") String District,
                                 @Query("Panchayat") String Panchayat
    );
    @GET("EPSTrackingRequest")
    Call<Object>getTrackingDetails(@Header("accessToken") String accessToken,
                                   @Query("Type") String Type,
                                   @Query("MobileNo") String MobileNo,
                                   @Query("RequestNo") String RequestNo,
                                   @Query("RequestDate") String RequestDate,
                                   @Query("District") String District,
                                   @Query("Panchayat") String Panchayat
    );
    @GET("EPSApplyBirthRequest")
    Call<Object>saveBirthDetails(@Header("accessToken") String accessToken,
                                 @Query("District") String District,
                                 @Query("Panchayat") String Panchayat,
                                 @Query("MobileNo") String MobileNo,
                                 @Query("EmailId") String EmailId,
                                 @Query("DOB") String DOB,
                                 @Query("Gender") String Gender,
                                 @Query("ChildName") String ChildName,
                                 @Query("FatherName") String FatherName,
                                 @Query("MotherName") String MotherName,
                                 @Query("FatherAadhaarNo") String FatherAadhaarNo,
                                 @Query("MotherAadhaarNo") String MotherAadhaarNo,
                                 @Query("PerAddress") String PerAddress,
                                 @Query("PerPincode") String PerPincode,
                                 @Query("BirthAddress") String BirthAddress,
                                 @Query("BirthPincode") String BirthPincode,
                                 @Query("BirthPlace") String BirthPlace,
                                 @Query("HospitalCode") String HospitalCode,
                                 @Query("HospitalName") String HospitalName,
                                 @Query("DoorNo") String DoorNo,
                                 @Query("WardNo") String WardNo,
                                 @Query("StreetCode") String StreetCode,
                                 @Query("StreetName") String StreetName,
                                 @Query("StateName") String StateName,
                                 @Query("DistrictName") String DistrictName,
                                 @Query("TownVillage") String TownVillage,
                                 @Query("TownVillageName") String TownVillageName,
                                 @Query("Religion") String Religion,
                                 @Query("ReligionOthers") String ReligionOthers,
                                 @Query("FatherEducation") String FatherEducation,
                                 @Query("MotherEducation") String MotherEducation,
                                 @Query("FatherOccupation") String FatherOccupation,
                                 @Query("MotherOccupation") String MotherOccupation,
                                 @Query("MotherMarriageAge") String MotherMarriageAge,
                                 @Query("MotherChildBirthAge") String MotherChildBirthAge,
                                 @Query("ChildBornAlive") String ChildBornAlive,
                                 @Query("ChildWeight") String ChildWeight,
                                 @Query("MethodofDelivery") String MethodofDelivery,
                                 @Query("TypeAttentionDelivery") String TypeAttentionDelivery,
                                 @Query("PregnancyDuration") String PregnancyDuration,
                                 @Query("EntryType") String EntryType
    );
    @GET("EPSApplyDeathRequest")
    Call<Object>saveDeathDetails(@Header("accessToken") String accessToken,
                                 @Query("District") String District,
                                 @Query("Panchayat") String Panchayat,
                                 @Query("MobileNo") String MobileNo,
                                 @Query("EmailId") String EmailId,
                                 @Query("DOD") String DOD,
                                 @Query("Gender") String Gender,
                                 @Query("Age") String Age,
                                 @Query("AgeType") String AgeType,
                                 @Query("DeceasedName") String DeceasedName,
                                 @Query("HusbandWifeName") String HusbandWifeName,
                                 @Query("FatherName") String FatherName,
                                 @Query("MotherName") String MotherName,
                                 @Query("PerAddress") String PerAddress,
                                 @Query("PerPincode") String PerPincode,
                                 @Query("DeathAtAddress") String DeathAtAddress,
                                 @Query("DeathAtPincode") String DeathAtPincode,
                                 @Query("DeathPlace") String DeathPlace,
                                 @Query("HospitalCode") String HospitalCode,
                                 @Query("HospitalName") String HospitalName,
                                 @Query("DoorNo") String DoorNo,
                                 @Query("WardNo") String WardNo,
                                 @Query("StreetCode") String StreetCode,
                                 @Query("StreetName") String StreetName,
                                 @Query("OtherAddress") String OtherAddress,
                                 @Query("OtherPincode") String OtherPincode,
                                 @Query("StateName") String StateName,
                                 @Query("DistrictName") String DistrictName,
                                 @Query("TownVillage") String TownVillage,
                                 @Query("TownVillageName") String TownVillageName,
                                 @Query("Religion") String Religion,
                                 @Query("ReligionOthers") String ReligionOthers,
                                 @Query("OccupationDeceased") String OccupationDeceased,
                                 @Query("TypeMedicalAttention") String TypeMedicalAttention,
                                 @Query("MedicalCertified") String MedicalCertified,
                                 @Query("CauseDisease") String CauseDisease,
                                 @Query("DeathOccurPregnancy") String DeathOccurPregnancy,
                                 @Query("HabituallySmoke") String HabituallySmoke,
                                 @Query("HabituallySmokeYears") String HabituallySmokeYears,
                                 @Query("HabituallyTobacco") String HabituallyTobacco,
                                 @Query("HabituallyTobaccoYears") String HabituallyTobaccoYears,
                                 @Query("HabituallyChewArecaunt") String HabituallyChewArecaunt,
                                 @Query("HabituallyChewArecauntYears") String HabituallyChewArecauntYears,
                                 @Query("HabituallyDrinkAlcohol") String HabituallyDrinkAlcohol,
                                 @Query("HabituallyDrinkAlcoholYears") String HabituallyDrinkAlcoholYears,
                                 @Query("EntryType") String EntryType
    );
    @GET("EPSNonTaxRequest")
    Call<Object> saveNonTaxDetails(@Header("accessToken") String accessToken,
                                   @Query("District") String District,
                                   @Query("Panchayat") String Panchayat,
                                   @Query("Name") String Name,
                                   @Query("MobileNo") String MobileNo,
                                   @Query("EmailId") String EmailId,
                                   @Query("LeaseName") String LeaseName,
                                   @Query("DoorNo") String DoorNo,
                                   @Query("WardNo") String WardNo,
                                   @Query("StreetCode") String StreetCode,
                                   @Query("StreetName") String StreetName,
                                   @Query("EntryType") String EntryType
    );











    @GET("EPSGetDistrictDetails")
    Call<Object> getDistrict(
            @Header("accessToken") String accessToken
    );


//    @GET("EPSGetPanchayatDetails")
//    Call<Object> getPanchayat(
//            @Header("accessToken") String accessToken,
//            @Query("DistrictId") int districtId
//    );

    @GET("EPSBirthDeathAbstract")
    Call<Object> getBirthDetailsDatas(
            @Header("accessToken") String accessToken,
            @Query("Type") String Type,
            @Query("Year") String Year,
            @Query("WardNo") String WardNo,
            @Query("District") String District,
            @Query("Panchayat") String Panchayat
    );

    @GET("EPSBirthDeathSearch")
    Call<Object> getBirthCertificateSearchDetails(
            @Header("accessToken") String accessToken,
            @Query("Type") String Type,
            @Query("Date") String Date,
            @Query("Gender") String Gender,
            @Query("District") String District,
            @Query("Panchayat") String Panchayat
    );

    @GET("EPSTaxMasterDetails")
    Call<Object> getWard_Street(
            @Header("accessToken") String accessToken,
            @Query("Type") String Type,
            @Query("Input") String Input,
            @Query("District") String District,
            @Query("Panchayat") String Panchayat
    );

    @GET("EPSGrievancesRegister")
    Call<Object> submitGrievances(
            @Header("accessToken") String accessToken,
            @Query("District") String District,
            @Query("Panchayat") String Panchayat,
            @Query("CType") String CType,
            @Query("Description") String Description,
            @Query("Name") String Name,
            @Query("DoorNo") String DoorNo,
            @Query("WardNo") String WardNo,
            @Query("StreetName") String StreetName,
            @Query("City") String City,
            @Query("MobileNo") String MobileNo,
            @Query("EmailId") String EmailId,
            @Query("EntryType") String EntryType
    );

    //responsebody for string response using okhttp3
    @GET("SendSMS")
    Call<ResponseBody> sendSMS(

            @Query("SMSMobileNo") String SMSMobileNo,
            @Query("SMSMessage") String SMSMessage
    );


    @GET("/Etown/EPSGrievancesTracking")
    Call<Object> getGrievance(@Header("accessToken")String accessToken,
                              @Query("GrievancesNo")String GrievancesNo,
                              @Query("FromDate")String FromDate,
                              @Query("ToDate")String ToDate


    );
    @GET("/Etown/EPSSingleGrievance")
    Call<Object> getSingleGrievance(@Header("accessToken")String accessToken,
                                    @Query("GrievancesNo")String GrievancesNo,
                                    @Query("District")String District,
                                    @Query("Panchayat")String Panchayat
    );




    @GET("EPSPanchayatContact")
    Call<Object> getEODetails(
            @Header("accessToken") String accessToken,
            @Query("District") String District,
            @Query("Panchayat") String Panchayat
    );



}
