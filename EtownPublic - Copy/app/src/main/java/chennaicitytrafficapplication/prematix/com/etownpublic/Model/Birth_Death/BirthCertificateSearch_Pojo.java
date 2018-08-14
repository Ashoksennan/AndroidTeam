package chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death;

public class BirthCertificateSearch_Pojo  {

    int Sno,RegNo,RegYear;

    String DivNo,RegDate,NameRegDate,ChildName,DateOfBirth,Sex,FatherName,MotherName,BirthType;


    public BirthCertificateSearch_Pojo(int sno, int regNo, int regYear, String divNo, String regDate, String nameRegDate, String childName, String dateOfBirth, String sex, String fatherName, String motherName, String birthType) {
        Sno = sno;
        RegNo = regNo;
        RegYear = regYear;
        DivNo = divNo;
        RegDate = regDate;
        NameRegDate = nameRegDate;
        ChildName = childName;
        DateOfBirth = dateOfBirth;
        Sex = sex;
        FatherName = fatherName;
        MotherName = motherName;
        BirthType = birthType;
    }

    public int getSno() {
        return Sno;
    }

    public void setSno(int sno) {
        Sno = sno;
    }

    public int getRegNo() {
        return RegNo;
    }

    public void setRegNo(int regNo) {
        RegNo = regNo;
    }

    public int getRegYear() {
        return RegYear;
    }

    public void setRegYear(int regYear) {
        RegYear = regYear;
    }

    public String getDivNo() {
        return DivNo;
    }

    public void setDivNo(String divNo) {
        DivNo = divNo;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
    }

    public String getNameRegDate() {
        return NameRegDate;
    }

    public void setNameRegDate(String nameRegDate) {
        NameRegDate = nameRegDate;
    }

    public String getChildName() {
        return ChildName;
    }

    public void setChildName(String childName) {
        ChildName = childName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String motherName) {
        MotherName = motherName;
    }

    public String getBirthType() {
        return BirthType;
    }

    public void setBirthType(String birthType) {
        BirthType = birthType;
    }
}
