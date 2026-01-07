package model;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable {

    private static final long serialVersionUID = 1L;
    private String scoreId;
    private String stuId;
    private String stuName;
    private String courseId;
    private String courseName;
    private Integer score;
    private String semester;
    private String academicYear;
    private Date createTime;
    private Date updateTime;

    public Score() {
    }

    public Score(String scoreId, String stuId, String courseId, String courseName, Integer score, String semester, String academicYear) {
        this.scoreId = scoreId;
        this.stuId = stuId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.score = score;
        this.semester = semester;
        this.academicYear = academicYear;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreId='" + scoreId + '\'' +
                ", stuId='" + stuId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", score=" + score +
                ", semester='" + semester + '\'' +
                ", academicYear='" + academicYear + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
