package controller;

import Dao.StudentDao;
import Dao.impl.StudentDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Student;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/studentManage/*")
public class StudentServlet extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(StudentServlet.class);
    
    private boolean checkAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user != null && ("admin".equals(user.getRole()) || "teacher".equals(user.getRole()));
    }
    
    private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("=== 开始添加学生 ===");
        
        if (!checkAdmin(req)) {
            logger.warn("添加学生失败：无权限操作");
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        String stuId = req.getParameter("stuId");
        String stuName = req.getParameter("stuName");
        String gender = req.getParameter("gender");
        String ageStr = req.getParameter("age");
        String phone = req.getParameter("phone");
        String className = req.getParameter("className");
        String email = req.getParameter("email");
        
        logger.info("添加学生参数 - stuId: {}, stuName: {}, gender: {}, age: {}, phone: {}, className: {}, email: {}", 
                   stuId, stuName, gender, ageStr, phone, className, email);
        
        // 参数验证（学号自动生成，无需验证；年龄可选）
        if (stuName == null || stuName.trim().isEmpty()) {
            logger.error("添加学生失败：姓名不能为空");
           // req.setAttribute("msg", "请填写完整的学生信息");
            req.getRequestDispatcher("/admin/student/add.jsp").forward(req, resp);
            return;
        }

        int age = 0;
        if (ageStr != null && !ageStr.trim().isEmpty()) {
            try {
                age = Integer.parseInt(ageStr);
                if (age < 1 || age > 100) {
                    logger.error("添加学生失败：年龄不合法 - {}", age);
                    req.setAttribute("msg", "年龄必须在1-100之间");
                    req.getRequestDispatcher("/admin/student/add.jsp").forward(req, resp);
                    return;
                }
            } catch (NumberFormatException e) {
                logger.error("添加学生失败：年龄格式错误 - {}", ageStr, e);
                req.setAttribute("msg", "年龄必须是数字");
                req.getRequestDispatcher("/admin/student/add.jsp").forward(req, resp);
                return;
            }
        }
        
        StudentDao studentDao = new StudentDaoImpl();
        Student student = new Student();
        student.setStuId(stuId);
        student.setStuName(stuName);
        student.setGender(gender);
        student.setAge(age);
        student.setPhone(phone);
        student.setClassName(className);
        student.setEmail(email);
        
        try {
            if (studentDao.addStudent(student)) {
                logger.info("添加学生成功 - stuId: {}", stuId);
                req.getSession().setAttribute("successMsg", "添加学生成功");
                resp.sendRedirect(req.getContextPath() + "/studentManage/list");
            } else {
                logger.error("添加学生失败 - stuId: {}", stuId);
                req.setAttribute("msg", "添加学生失败");
                req.getRequestDispatcher("/admin/student/add.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("添加学生异常 - stuId: " + stuId, e);
            req.setAttribute("msg", "添加学生失败：" + e.getMessage());
            req.getRequestDispatcher("/admin/student/add.jsp").forward(req, resp);
        }
    }
    
    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("=== 开始删除学生 ===");
        if (!checkAdmin(req)) {
            logger.warn("删除学生失败：无权限操作");
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        String stuId = req.getParameter("id");
        logger.info("删除学生请求参数 - stuId: {}", stuId);
        StudentDao studentDao = new StudentDaoImpl();
        
        if (studentDao.deleteStudent(stuId)) {
            logger.info("删除学生成功 - stuId: {}", stuId);
            req.getSession().setAttribute("successMsg", "删除学生成功");
            resp.sendRedirect(req.getContextPath() + "/studentManage/list");
        } else {
            logger.error("删除学生失败 - stuId: {}", stuId);
            req.getSession().setAttribute("msg", "删除学生失败");
            resp.sendRedirect(req.getContextPath() + "/studentManage/list");
        }
    }
    
    private void adminUpdateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("=== 开始管理员更新学生信息 ===");
        
        if (!checkAdmin(req)) {
            logger.warn("更新学生失败：无权限操作");
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        String stuId = req.getParameter("stuId");
        String stuName = req.getParameter("stuName");
        String gender = req.getParameter("gender");
        String ageStr = req.getParameter("age");
        String phone = req.getParameter("phone");
        String className = req.getParameter("className");
        String email = req.getParameter("email");
        
        logger.info("更新学生参数 - stuId: {}, stuName: {}, gender: {}, age: {}, phone: {}, className: {}, email: {}", 
                   stuId, stuName, gender, ageStr, phone, className, email);
        
        if (stuId == null || stuId.trim().isEmpty()) {
            logger.error("更新学生失败：学号不能为空");
            req.setAttribute("msg", "学号不能为空");
            req.getRequestDispatcher("/studentManage/list").forward(req, resp);
            return;
        }
        
        int age = 0;
        if (ageStr != null && !ageStr.trim().isEmpty()) {
            try {
                age = Integer.parseInt(ageStr);
                if (age < 1 || age > 100) {
                    logger.error("更新学生失败：年龄不合法 - {}", age);
                    req.setAttribute("msg", "年龄必须在1-100之间");
                    req.getRequestDispatcher("/studentManage/list").forward(req, resp);
                    return;
                }
            } catch (NumberFormatException e) {
                logger.error("更新学生失败：年龄格式错误 - {}", ageStr, e);
                req.setAttribute("msg", "年龄必须是数字");
                req.getRequestDispatcher("/studentManage/list").forward(req, resp);
                return;
            }
        }
        
        StudentDao studentDao = new StudentDaoImpl();
        Student student = new Student();
        student.setStuId(stuId);
        student.setStuName(stuName);
        student.setGender(gender);
        student.setAge(age);
        student.setPhone(phone);
        student.setClassName(className);
        student.setEmail(email);
        
        try {
            if (studentDao.updateStudent(student)) {
                logger.info("更新学生成功 - stuId: {}", stuId);
                resp.sendRedirect(req.getContextPath() + "/studentManage/list");
            } else {
                logger.error("更新学生失败 - stuId: {}", stuId);
                req.setAttribute("msg", "更新学生失败");
                req.getRequestDispatcher("/studentManage/list").forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("更新学生异常 - stuId: " + stuId, e);
            req.setAttribute("msg", "更新学生失败：" + e.getMessage());
            req.getRequestDispatcher("/studentManage/list").forward(req, resp);
        }
    }
    
    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("=== 开始修改学生个人信息 ===");
        
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        
        if (user == null) {
            logger.warn("修改失败：用户未登录");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        
        logger.info("当前登录用户: userId={}, username={}", user.getUserId(), user.getUsername());
        
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        
        logger.info("修改请求 - phone={}, email={}", phone, email);
        
        if (phone == null || phone.trim().isEmpty()) {
            logger.warn("修改失败：联系电话不能为空");
            req.setAttribute("msg", "联系电话不能为空");
            req.setAttribute("msgType", "error");
            myInfo(req, resp);
            return;
        }
        
        if (email == null || email.trim().isEmpty()) {
            logger.warn("修改失败：邮箱不能为空");
            req.setAttribute("msg", "邮箱不能为空");
            req.setAttribute("msgType", "error");
            myInfo(req, resp);
            return;
        }
        
        StudentDao studentDao = new StudentDaoImpl();
        // 优先通过用户名（学号）查找学生，因为 userId 可能是 UUID
        Student student = studentDao.getStudentById(user.getUsername());
        if (student == null) {
            // 如果通过用户名查不到，再尝试通过用户ID查找
            logger.info("通过username(学号)未查到学生，尝试通过userId查询: {}", user.getUserId());
            student = studentDao.getStudentById(user.getUserId());
        }
        
        if (student == null) {
            logger.error("修改失败：找不到对应的学生记录 - 用户: {}", user.getUsername());
            req.setAttribute("msg", "找不到对应的学生记录");
            req.setAttribute("msgType", "error");
            myInfo(req, resp);
            return;
        }
        
        String stuId = student.getStuId();
        logger.info("确定学号为: {}", stuId);
        
        if (studentDao.updateStudentPhoneAndEmail(stuId, phone, email)) {
            logger.info("修改成功 - 用户: {}", user.getUsername());
            session.setAttribute("successMsg", "个人信息修改成功");
            resp.sendRedirect(req.getContextPath() + "/studentManage/myInfo");
        } else {
            logger.error("修改失败 - 数据库更新操作失败 - 用户: {}", user.getUsername());
            req.setAttribute("msg", "修改失败，请稍后再试");
            req.setAttribute("msgType", "error");
            myInfo(req, resp);
        }
    }
    
    private void editStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req)) {
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        String stuId = req.getParameter("id");
        StudentDao studentDao = new StudentDaoImpl();
        Student student = studentDao.getStudentById(stuId);
        
        if (student != null) {
            req.setAttribute("student", student);
            req.getRequestDispatcher("/admin/student/edit.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/studentManage/list");
        }
    }
    
    private void listStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("=== 开始查询学生列表 ===");
        
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        
        if (user == null) {
            logger.warn("查询学生列表失败：用户未登录");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        
        logger.info("当前用户: {}, 角色: {}", user.getUsername(), user.getRole());
        
        String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("pageSize");
        String keyword = req.getParameter("keyword");
        
        int page = 1;
        int pageSize = 10;
        
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                logger.warn("页码格式错误: {}", pageStr);
                page = 1;
            }
        }
        
        if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
            try {
                pageSize = Integer.parseInt(pageSizeStr);
                if (pageSize < 1) {
                    pageSize = 10;
                }
            } catch (NumberFormatException e) {
                logger.warn("每页条数格式错误: {}", pageSizeStr);
                pageSize = 10;
            }
        }
        
        logger.info("查询参数 - page: {}, pageSize: {}, keyword: {}", page, pageSize, keyword);
        
        StudentDao studentDao = new StudentDaoImpl();
        List<Student> studentList;
        int totalCount;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            logger.info("执行搜索查询");
            studentList = studentDao.searchStudents(keyword, page, pageSize);
            totalCount = studentDao.getSearchStudentCount(keyword);
            req.setAttribute("keyword", keyword);
        } else {
            logger.info("执行普通分页查询");
            studentList = studentDao.getStudentsByPage(page, pageSize);
            totalCount = studentDao.getTotalStudentCount();
        }
        
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        
        logger.info("查询结果 - 总记录数: {}, 总页数: {}, 当前页数据条数: {}", totalCount, totalPages, studentList.size());
        
        req.setAttribute("students", studentList);
        req.setAttribute("currentPage", page);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/admin/student/list.jsp").forward(req, resp);
    }
    
    private void myInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        
        StudentDao studentDao = new StudentDaoImpl();
        // 优先通过用户名（学号）查找学生，因为 userId 可能是 UUID
        Student student = studentDao.getStudentById(user.getUsername());
        if (student == null) {
            // 如果通过用户名查不到，再尝试通过用户ID查找
            logger.info("myInfo: 通过username(学号)未查到学生，尝试通过userId查询: {}", user.getUserId());
            student = studentDao.getStudentById(user.getUserId());
        }
        
        if (student == null) {
            logger.warn("myInfo: 找不到对应的学生记录 - 用户: {}", user.getUsername());
        } else {
            logger.info("myInfo: 成功获取学生信息 - 学号: {}", student.getStuId());
        }
        
        req.setAttribute("student", student);
        req.getRequestDispatcher("/student/info.jsp").forward(req, resp);
    }
    
    private void exportStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        
        if (!checkAdmin(req)) {
            resp.getWriter().write("无权限操作！");
            return;
        }
        
        StudentDao studentDao = new StudentDaoImpl();
        List<Student> studentList = studentDao.getStudents();
        
        resp.setContentType("text/csv;charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=students.csv");
        
        java.io.PrintWriter out = resp.getWriter();
        out.println("学号,姓名,性别,年龄,联系电话,班级,邮箱");
        
        for (Student student : studentList) {
            out.println(student.getStuId() + "," + student.getStuName() + "," + 
                       student.getGender() + "," + student.getAge() + "," + 
                       student.getPhone() + "," + student.getClassName() + "," + 
                       student.getEmail());
        }
        
        out.flush();
        out.close();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String path = req.getPathInfo();
        
        if (path == null) {
            path = "/list";
        }
        
        switch (path) {
            case "/add":
                addStudent(req, resp);
                break;
            case "/delete":
                deleteStudent(req, resp);
                break;
            case "/edit":
                editStudent(req, resp);
                break;
            case "/adminUpdate":
                adminUpdateStudent(req, resp);
                break;
            case "/update":
                updateStudent(req, resp);
                break;
            case "/list":
                listStudent(req, resp);
                break;
            case "/myInfo":
                myInfo(req, resp);
                break;
            case "/export":
                exportStudent(req, resp);
                break;
            default:
                resp.getWriter().write("无效请求");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
