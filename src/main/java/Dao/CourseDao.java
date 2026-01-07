package Dao;

import model.Course;
import java.util.List;

// 课程/成绩操作接口：管理员增删改查，普通用户仅查询
public interface CourseDao {
        // 管理员专属：新增成绩/课程
        boolean addCourse(Course course);
        // 管理员专属：删除成绩/课程
        boolean deleteCourse(String courseId);
        // 管理员专属：修改成绩
        boolean updateCourseScore(String courseId, Integer newScore);
        // 所有人：查询所有成绩/课程
        List<Course> getAllCourses();
        // 辅助：按课程号查单个课程
        Course getCourseById(String courseId);
}
