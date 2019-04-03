package s2017s40.kr.hs.mirim.seoulapp_jsd;

public class XmlDTO {
    String shltrNm;//쉼터명
    String legaldongNm;//법정동명
    String shltrType;//쉼터유형
    int fanHoldCo;//선풍기보유대수
    int arcndtnHoldCo;//에어컨보유대수
    String nightExtnYn;//야간연장운영여부
    String wkendUseYn;//주말운영여부
    String rdnmadr;//소재지도로명주소
    String lnmadr;//소재지지번주소
    String phoneNumber;//관리기관전화번호
    String latitude;//위도
    String hardness;//경도
    XmlDTO(){}
    XmlDTO(String shltrNm, String rdnmadr, String latitude, String hardness){//Main RecyclerView를 위한 생성자
        this.shltrNm = shltrNm;
        this.rdnmadr = rdnmadr;
        this.latitude = latitude;
        this.hardness = hardness;
    }
    public void setArcndtnHoldCo(int arcndtnHoldCo) {
        this.arcndtnHoldCo = arcndtnHoldCo;
    }

    public void setFanHoldCo(int fanHoldCo) {
        this.fanHoldCo = fanHoldCo;
    }

    public void setLegaldongNm(String legaldongNm) {
        this.legaldongNm = legaldongNm;
    }

    public void setLnmadr(String lnmadr) {
        this.lnmadr = lnmadr;
    }

    public void setNightExtnYn(String nightExtnYn) {
        this.nightExtnYn = nightExtnYn;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRdnmadr(String rdnmadr) {
        this.rdnmadr = rdnmadr;
    }

    public void setShltrNm(String shltrNm) {
        this.shltrNm = shltrNm;
    }

    public void setShltrType(String shltrType) {
        this.shltrType = shltrType;
    }

    public void setWkendUseYn(String wkendUseYn) {
        this.wkendUseYn = wkendUseYn;
    }

    public void setHardness(String hardness) {
        this.hardness = hardness;
    }

    public int getFanHoldCo() {
        return fanHoldCo;
    }

    public int getArcndtnHoldCo() {
        return arcndtnHoldCo;
    }

    public String getLegaldongNm() {
        return legaldongNm;
    }

    public String getNightExtnYn() {
        return nightExtnYn;
    }

    public String getShltrNm() {
        return shltrNm;
    }

    public String getRdnmadr() {
        return rdnmadr;
    }

    public String getShltrType() {
        return shltrType;
    }

    public String getHardness() {
        return hardness;
    }

    public String getLnmadr() {
        return lnmadr;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getWkendUseYn() {
        return wkendUseYn;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
