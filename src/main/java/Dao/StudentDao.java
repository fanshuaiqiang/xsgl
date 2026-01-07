package Dao;

import model.Student;

import java.util.List;

public interface StudentDao {
    boolean addStudent(Student student);
    boolean deleteStudent(String stuId);
    List<Student> getStudents();
    List<Student> getStudentsByPage(int page, int pageSize);
    List<Student> searchStudents(String keyword, int page, int pageSize);
    int getTotalStudentCount();
    int getSearchStudentCount(String keyword);
    boolean updateStudentphone(String stuId, String newPhone);
    boolean updateStudentPhoneAndEmail(String stuId, String phone, String email);
    boolean updateStudent(Student student);
    Student getStudentById(String stuId);
}
