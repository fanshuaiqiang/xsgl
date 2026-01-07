package model;

public class Course {
        private String CourseId;
        private String CourseName;
        private int Score;
        private String StuId;

        public String getCourseId() {
                return CourseId;
        }

        public void setCourseId(String courseId) {
                CourseId = courseId;
        }

        public String getCourseName() {
                return CourseName;
        }

        public void setCourseName(String courseName) {
                CourseName = courseName;
        }

        public int getScore() {
                return Score;
        }

        public void setScore(int score) {
                Score = score;
        }

        public String getStuId() {
                return StuId;
        }

        public void setStuId(String stuId) {
                StuId = stuId;
        }
        public Course(String courseId, String courseName, int score, String stuId) {
                this.CourseId = courseId;
                this.CourseName = courseName;
                this.Score = score;
                this.StuId = stuId;
        }
        public Course() {}
}
