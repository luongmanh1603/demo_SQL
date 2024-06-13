package com.example.demosql.controllers;

import com.example.demosql.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "studentController", value = "/students")
public class StudentController extends HttpServlet {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //lay sinh vien can edit
        String id = req.getParameter("id");
        String action = req.getParameter("action");

        //delete student
        if (id != null && action != null && action.equals("delete")) {
            // If the action is "delete", delete the student
            Student student = entityManager.find(Student.class, Long.parseLong(id));
            if (student != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(student);
                entityManager.getTransaction().commit();
                req.setAttribute("message", "Student deleted successfully");
            }
            resp.sendRedirect(req.getContextPath() + "/students");
            return;
        }
        //create and edit student
        if (id != null ) {
            Student student = entityManager.find(Student.class, Long.parseLong(id));
            req.setAttribute("student", student);
            req.getRequestDispatcher("/WEB-INF/views/edit-student.jsp").forward(req, resp);
            return;
        }
        //lay danh sach sinh vien
        var students = entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        req.setAttribute("students", students);
        req.getRequestDispatcher("/WEB-INF/views/student.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //lay du lieu tu form
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));

        //edit student
        Student student;
        if (entityManager.find(Student.class, (long) id) != null) {
            //neu sinh vien da ton tai thi cap nhat lai thong tin
            student = entityManager.find(Student.class, (long) id);
            student.setName(name);
            student.setAge(age);
        } else {
            //neu sinh vien chua ton tai thi tao moi
            student = new Student((long) id, name, age);
        }



        // luu doi Student vao database
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(student);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
//thong bao luu thanh cong
        req.setAttribute("message", "Student created successfully");
        //chuyen huong ve trang danh sach student
        resp.sendRedirect(req.getContextPath() + "/students");
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
