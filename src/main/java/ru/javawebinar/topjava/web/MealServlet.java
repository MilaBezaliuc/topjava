package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealServlet extends HttpServlet {
    //private static final Logger LOG = LoggerWrapper.;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //LOG.info("getAll");
        request.setAttribute("mealList", MealsUtil.getMealWithExceeded(MealsUtil.MEAL_LIST, 2000));
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        //response.sendRedirect("mealList.jsp");
    }

    /*
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("mealList.jsp").forward(req, resp);
    }
    */
}