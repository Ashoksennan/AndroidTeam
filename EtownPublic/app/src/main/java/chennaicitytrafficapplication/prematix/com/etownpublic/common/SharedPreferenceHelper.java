package chennaicitytrafficapplication.prematix.com.etownpublic.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

    public static final String PREF_ = "login";

    public static final String PREF_SELECTDISTRICT="select district";

    public static final String PREF_SELECTPANCHAYAT="select panchayat";





    private static Context context;
    public static SharedPreferenceHelper sharedPreferenceHelpher=null;
    public static final String sharedPreferenceName = "BIRTH_REGISTRATION";

    public SharedPreferenceHelper(Context context) {
        SharedPreferenceHelper.context = context;
    }
    public static SharedPreferenceHelper getInstance(Context context1){
        if(sharedPreferenceHelpher == null){
            sharedPreferenceHelpher = new SharedPreferenceHelper(context1);
        }
        return  sharedPreferenceHelpher;
    }
    public void putParentalInfo(String brth_reg_District,
                                String brth_reg_Panchayat,
                                String brth_reg_Mobno,
                                String brth_reg_Emailid,
                                String brth_reg_FatherName,
                                String brth_reg_MotherName,
                                String brth_reg_FatherAadharNo,
                                String brth_reg_MotherAadharNo,
                                String brth_reg_PermenantAddress,
                                String brth_reg_Pincode,
                                String brth_reg_per_PermenantAddress,
                                String brth_reg_per_Pincode,
                                String brth_reg_Dob,
                                String brth_reg_Gender,
                                String brth_reg_ChildName
    ){
        SharedPreferences birth_reg_shared_pref;
        birth_reg_shared_pref = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = birth_reg_shared_pref.edit();
        editor.putString("PREF_brth_reg_District",brth_reg_District);
        editor.putString("PREF_brth_reg_Panchayat",brth_reg_Panchayat);
        editor.putString("PREF_brth_reg_Mobno",brth_reg_Mobno);
        editor.putString("PREF_brth_reg_Emailid",brth_reg_Emailid);
        editor.putString("PREF_brth_reg_FatherName",brth_reg_FatherName);
        editor.putString("PREF_brth_reg_MotherName",brth_reg_MotherName);
        editor.putString("PREF_brth_reg_FatherAadharNo",brth_reg_FatherAadharNo);
        editor.putString("PREF_brth_reg_MotherAadharNo",brth_reg_MotherAadharNo);
        editor.putString("PREF_brth_reg_PermenantAddress",brth_reg_PermenantAddress);
        editor.putString("PREF_brth_reg_Pincode",brth_reg_Pincode);
        editor.putString("PREF_brth_reg_per_PermenantAddress",brth_reg_per_PermenantAddress);
        editor.putString("PREF_brth_reg_per_Pincode",brth_reg_per_Pincode);
        editor.putString("PREF_brth_reg_Dob",brth_reg_Dob);
        editor.putString("PREF_brth_reg_Gender",brth_reg_Gender);
        editor.putString("PREF_brth_reg_ChildName",brth_reg_ChildName);
        editor.commit();
    }
    public void putPersonalInfo(String death_reg_District,
                                String death_reg_Panchayat,
                                String death_reg_Mobno,
                                String death_reg_Emailid,
                                String death_reg_FatherName,
                                String death_reg_MotherName,
                                String death_reg_husband_wife_name,
                                String death_reg_PermenantAddress,
                                String death_reg_Pincode,
                                String death_reg_per_PermenantAddress,
                                String death_reg_per_Pincode,
                                String death_reg_DOD,
                                String death_reg_Gender,
                                String death_reg_Name,
                                String death_reg_age,
                                String death_reg_age_type
    ){
        SharedPreferences birth_reg_shared_pref;
        birth_reg_shared_pref = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = birth_reg_shared_pref.edit();
        editor.putString("PREF_death_reg_District",death_reg_District);
        editor.putString("PREF_death_reg_Panchayat",death_reg_Panchayat);
        editor.putString("PREF_death_reg_Mobno",death_reg_Mobno);
        editor.putString("PREF_death_reg_Emailid",death_reg_Emailid);
        editor.putString("PREF_death_reg_FatherName",death_reg_FatherName);
        editor.putString("PREF_death_reg_MotherName",death_reg_MotherName);
        editor.putString("PREF_death_reg_husband_wife_name",death_reg_husband_wife_name);
        editor.putString("PREF_death_reg_PermenantAddress",death_reg_PermenantAddress);
        editor.putString("PREF_death_reg_Pincode",death_reg_Pincode);
        editor.putString("PREF_death_reg_per_PermenantAddress",death_reg_per_PermenantAddress);
        editor.putString("PREF_death_reg_per_Pincode",death_reg_per_Pincode);
        editor.putString("PREF_death_reg_DOD",death_reg_DOD);
        editor.putString("PREF_death_reg_Gender",death_reg_Gender);
        editor.putString("PREF_death_reg_Name",death_reg_Name);
        editor.putString("PREF_death_reg_age",death_reg_age);
        editor.putString("PREF_death_reg_age_type",death_reg_age_type);
        editor.commit();
    }

    public String getSpecificValues(String key){
        SharedPreferences sharedPreferences1 = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);

        return  sharedPreferences1.getString(key,null);
    }
    public void putPlaceofBirth(String birthplace,
                                String hospital_code,
                                String hospitalName,
                                String doorNo,
                                String wardNo,
                                String streetcode,
                                String streetName
    ){
        SharedPreferences birth_reg_shared_pref;
        birth_reg_shared_pref = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = birth_reg_shared_pref.edit();
        editor.putString("PREF_birthplace",birthplace);
        editor.putString("PREF_hospital_code",hospital_code);
        editor.putString("PREF_hospitalName",hospitalName);
        editor.putString("PREF_doorNo",doorNo);
        editor.putString("PREF_wardNo",wardNo);
        editor.putString("PREF_streetName",streetName);
        editor.putString("PREF_streetcode",streetcode);
        editor.commit();
    }
    public void putPlaceofDeath(String deathplace,
                                String hospital_code,
                                String hospitalName,
                                String doorNo,
                                String wardNo,
                                String streetcode,
                                String streetName,
                                String other_address,
                                String other_pincode
    ){
        SharedPreferences birth_reg_shared_pref;
        birth_reg_shared_pref = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = birth_reg_shared_pref.edit();
        editor.putString("PREF_death_place",deathplace);
        editor.putString("PREF_death_hospital_code",hospital_code);
        editor.putString("PREF_death_hospitalName",hospitalName);
        editor.putString("PREF_death_doorNo",doorNo);
        editor.putString("PREF_death_wardNo",wardNo);
        editor.putString("PREF_death_streetName",streetName);
        editor.putString("PREF_death_streetcode",streetcode);
        editor.putString("PREF_death_other_address",other_address);
        editor.putString("PREF_death_other_pincode",other_pincode);
        editor.commit();
    }



    public void stepperOne(String district,String panchayat,String name,String mobileNo,String emailID)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("StepperPreference",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("pDistrict",district);
        editor.putString("pPanchayat",panchayat);
        editor.putString("pName",name);
        editor.putString("pMobileNo",mobileNo);
        editor.putString("pEmailId",emailID);

        editor.apply();
    }

    public void stepperProperty_Two(String bLicenseNo,String bLicenseDate,String BlockNo,String wardNo,String StreetCode,
                                    String streetName,String bZone,String bUsage,String bType,String totalArea)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("StepperPreference",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("bLicenseNo",bLicenseNo);
        editor.putString("bLicenseDate",bLicenseDate);
        editor.putString("BlockNo",BlockNo);
        editor.putString("wardNo",wardNo);
        editor.putString("streetCode",StreetCode);
        editor.putString("streetName",streetName);
        editor.putString("bZone",bZone);
        editor.putString("bUsage",bUsage);
        editor.putString("bType",bType);
        editor.putString("totalArea",totalArea);

        editor.apply();
    }



}
