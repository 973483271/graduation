package com.bysj.sys.entity;

/**
 * 出题情况统计 实体
 */
public class AssignTopicSituation {
//    存储查看所有专业出题详情
    private Integer eduId; //专业id
    private String education;  //专业名称
    private String college;//学院名称
    private Integer studentNum; //专业人数
    private Integer topicNum;   //通过审核的题目数
    private Integer resultNum; //结果多/少题目数
//存储查看指定专业导师出题详情
    private String teacherId; //教师Id(指导、审题)
    private String teacherName; //教师名称(指导、审题)
    private Integer teacherTopicNum; //出题数
    private Integer teacherPassNum; //过审题数
    private Double teacherPassingRate; //过审率
    //存储审题情况数据
    private Integer examinTopicNum;  //审题总数
    private Integer examinTopicPassingNum; //过审题数
    private Double examinTopicPassingRate ; //审核通过率
    private Integer examinTopicNumByExaminTea;//每个审题老师审核题目总数
    private Integer examinTopicPassingNumByExaminTea; //每个审题老师过审题数
    private Double examinTopicPassingRateByExaminTea;//每个审题老师审核题目通过率

    public Integer getExaminTopicPassingNum() {
        return examinTopicPassingNum;
    }

    public void setExaminTopicPassingNum(Integer examinTopicPassingNum) {
        this.examinTopicPassingNum = examinTopicPassingNum;
    }

    public Integer getExaminTopicPassingNumByExaminTea() {
        return examinTopicPassingNumByExaminTea;
    }

    public void setExaminTopicPassingNumByExaminTea(Integer examinTopicPassingNumByExaminTea) {
        this.examinTopicPassingNumByExaminTea = examinTopicPassingNumByExaminTea;
    }

    public Integer getExaminTopicNum() {
        return examinTopicNum;
    }

    public void setExaminTopicNum(Integer examinTopicNum) {
        this.examinTopicNum = examinTopicNum;
    }

    public Double getExaminTopicPassingRate() {
        return examinTopicPassingRate;
    }

    public void setExaminTopicPassingRate(Double examinTopicPassingRate) {
        this.examinTopicPassingRate = examinTopicPassingRate;
    }

    public Integer getExaminTopicNumByExaminTea() {
        return examinTopicNumByExaminTea;
    }

    public void setExaminTopicNumByExaminTea(Integer examinTopicNumByExaminTea) {
        this.examinTopicNumByExaminTea = examinTopicNumByExaminTea;
    }

    public Double getExaminTopicPassingRateByExaminTea() {
        return examinTopicPassingRateByExaminTea;
    }

    public void setExaminTopicPassingRateByExaminTea(Double examinTopicPassingRateByExaminTea) {
        this.examinTopicPassingRateByExaminTea = examinTopicPassingRateByExaminTea;
    }

    private String xAxisData; //统计图 x轴数据
    private String yAxisData;//统计图 y轴数据

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(Integer topicNum) {
        this.topicNum = topicNum;
    }

    public Integer getResultNum() {
        return resultNum;
    }

    public void setResultNum(Integer resultNum) {
        this.resultNum = resultNum;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getTeacherTopicNum() {
        return teacherTopicNum;
    }

    public void setTeacherTopicNum(Integer teacherTopicNum) {
        this.teacherTopicNum = teacherTopicNum;
    }

    public Integer getTeacherPassNum() {
        return teacherPassNum;
    }

    public void setTeacherPassNum(Integer teacherPassNum) {
        this.teacherPassNum = teacherPassNum;
    }

    public Double getTeacherPassingRate() {
        return teacherPassingRate;
    }

    public void setTeacherPassingRate(Double teacherPassingRate) {
        this.teacherPassingRate = teacherPassingRate;
    }

    public String getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(String xAxisData) {
        this.xAxisData = xAxisData;
    }

    public String getyAxisData() {
        return yAxisData;
    }

    public void setyAxisData(String yAxisData) {
        this.yAxisData = yAxisData;
    }

    @Override
    public String toString() {
        return "AssignTopicSituation{" +
                "eduId=" + eduId +
                ", education='" + education + '\'' +
                ", college='" + college + '\'' +
                ", studentNum=" + studentNum +
                ", topicNum=" + topicNum +
                ", resultNum=" + resultNum +
                ", teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherTopicNum=" + teacherTopicNum +
                ", teacherPassNum=" + teacherPassNum +
                ", teacherPassingRate=" + teacherPassingRate +
                ", examinTopicNum=" + examinTopicNum +
                ", examinTopicPassingNum=" + examinTopicPassingNum +
                ", examinTopicPassingRate=" + examinTopicPassingRate +
                ", examinTopicNumByExaminTea=" + examinTopicNumByExaminTea +
                ", examinTopicPassingNumByExaminTea=" + examinTopicPassingNumByExaminTea +
                ", examinTopicPassingRateByExaminTea=" + examinTopicPassingRateByExaminTea +
                ", xAxisData='" + xAxisData + '\'' +
                ", yAxisData='" + yAxisData + '\'' +
                '}';
    }
}
