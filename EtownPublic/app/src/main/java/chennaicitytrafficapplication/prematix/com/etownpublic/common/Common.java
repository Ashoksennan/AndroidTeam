package chennaicitytrafficapplication.prematix.com.etownpublic.common;

import android.content.Context;
import android.net.ConnectivityManager;

public class Common {

  public static String  baseUrl="http://www.predemos.com/Etown/";

  public static String  API_DISTRICT_DETAILS=baseUrl+"EPSGetDistrictDetails";
  public static String  ACCESS_TOKEN="eyJhbGciOiJIUzI1NiJ9.UHJlbWF0aXg.VnYf5L2bruAL3IhIbhOCnqW3SADSM2qjWrZAV0yrB94";


  public static String  API_TOWNPANCHAYAT=baseUrl+"EPSGetPanchayatDetails?DistrictId=";

  public static String API_ASSBY_NO="http://www.predemos.com/Etown/EPSTaxBalancePaymentDetails?";
  public static String API_ADD_NEW_CONNECTION = "http://www.predemos.com/Etown/EPSWaterNewConnection?";

  public static String  API_TAXBALANCEPAYMENTDETAILS=baseUrl+"EPSTaxBalancePaymentDetails?";

  public static String  API_FinancialDates=baseUrl+"EPSTaxMasterDetails?Type=FinYear&Input=WD-04&District=Krishnagiri&Panchayat=Mathigiri";

      public static String API_VIEW_DCB="http://www.etownpanchayat.com/PublicServices/WebView/LandingPage.aspx?RType=DCB&";

  public static String API_NEW_TRACK_ASSESSMENTORCONNECTION=baseUrl+"EPSTrackingRequest?";

  public static String API_TAX_MASTER_DETAILS ="http://www.predemos.com/Etown/EPSTaxMasterDetails?";
public static String API_GET_STREET ="http://www.predemos.com/Etown/EPSTaxMasterDetails?";

public static String API_NEW_PROFESSIONASS="http://www.predemos.com/Etown/EPSProfessionNewAssessment?";


  public static String API_PROPERTYTAXCALCULATION=baseUrl+"EPSPropertyTaxCalcuation?";
  public static String API_PROPERTYTAXMASTERDETAILS=baseUrl+"EPSTaxMasterDetails?";

  public static String GET_ASSESSMENT_PROP=baseUrl+"EPSTaxBalancePaymentDetails?Type=PSearch&TaxNo=";

  public static String  uploadUrl="http://192.168.1.11:2020/getdata/";

  public static String API_ORGANISATIONS=baseUrl+"EPSTaxMasterDetails?Type=Organization&Input=&District=";

  public static String API_GET_PROFESSION_DEMAND=baseUrl+"EPSProfessionDemand?District=";

  public static String API_ONLINE_FILING_SAVE=baseUrl+"EPSProfessionOnline?District=";

  public static String TRACKNEWASSESSMENT=baseUrl+"EPSTrackingRequest?";




  public static String param_distrct ="district";
  public static String param_panchayat ="panchayat";
  public static String param_mobileNo ="mobileNo";
  public static String param_emailId ="emailId";
  public static String param_finyear ="finyear";
  public static String param_name="name";
  public static String Type ="type";




  public static boolean isNetworkAvailable(final Context context) {
    final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
  }

}
