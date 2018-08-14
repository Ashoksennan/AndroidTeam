package chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death;

public class BirthAbstract_BirthDetails_Pojo {

    String DivNo,RegDate,ChildName,DateOfBirth,Gender,FatherName,MotherName,PlaceofBirth,BirthType;
    int RegNo;

    public BirthAbstract_BirthDetails_Pojo(String divNo, String regDate, String childName, String dateOfBirth, String Gender, String fatherName, String motherName, String placeofBirth, String birthType, int regNo) {
        this. DivNo = divNo;
        this.RegDate = regDate;
        this.ChildName = childName;
        this.DateOfBirth = dateOfBirth;
        this.Gender = Gender;
        this.FatherName = fatherName;
        this.MotherName = motherName;
        this.PlaceofBirth = placeofBirth;
        this.BirthType = birthType;
        this.RegNo = regNo;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        Gender = Gender;
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

    public String getPlaceofBirth() {
        return PlaceofBirth;
    }

    public void setPlaceofBirth(String placeofBirth) {
        PlaceofBirth = placeofBirth;
    }

    public String getBirthType() {
        return BirthType;
    }

    public void setBirthType(String birthType) {
        BirthType = birthType;
    }

    public int getRegNo() {
        return RegNo;
    }

    public void setRegNo(int regNo) {
        RegNo = regNo;
    }
}
