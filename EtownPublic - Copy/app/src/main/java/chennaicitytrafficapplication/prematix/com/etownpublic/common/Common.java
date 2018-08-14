package chennaicitytrafficapplication.prematix.com.etownpublic.common;

import android.content.Context;
import android.net.ConnectivityManager;

public class Common {

  public static String  baseUrl="http://www.predemos.com/Etown/";

  public static String  API_DISTRICT_DETAILS=baseUrl+"EPSGetDistrictDetails";
  public static String  ACCESS_TOKEN="eyJhbGciOiJIUzI1NiJ9.UHJlbWF0aXg.VnYf5L2bruAL3IhIbhOCnqW3SADSM2qjWrZAV0yrB94";


  public static String  API_TOWNPANCHAYAT=baseUrl+"EPSGetPanchayatDetails?DistrictId=";



  public static String  API_TAXBALANCEPAYMENTDETAILS=baseUrl+"EPSTaxBalancePaymentDetails?";

  public static String  API_FinancialDates=baseUrl+"EPSTaxMasterDetails?Type=FinYear&Input=WD-04&District=Krishnagiri&Panchayat=Mathigiri";

      public static String API_VIEW_DCB="http://www.etownpanchayat.com/PublicServices/WebView/LandingPage.aspx?RType=DCB&";

  public static String API_NEW_TRACK_ASSESSMENTORCONNECTION=baseUrl+"EPSTrackingRequest?";



  public static String API_PROPERTYTAXCALCULATION=baseUrl+"EPSPropertyTaxCalcuation?";
  public static String API_PROPERTYTAXMASTERDETAILS=baseUrl+"EPSTaxMasterDetails?";



  public static boolean isNetworkAvailable(final Context context) {
    final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
  }

}
