package ru.itmo.wp.util;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class CaptchaFilter extends HttpFilter {
    static final int minRng = 100;
    static final int maxRng = 999;
    static final String captchaURI
            = "C:/Users/Public/Documents/Programming/Web 3rd semester/hw3/src/main/webapp/static/captcha.html";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (request.getMethod().equals("GET")) {
            session.setAttribute("originalUri", request.getRequestURI());
        }
        System.out.println(session.getAttribute("originalUri"));

        if (session.getAttribute("captchaPassed") == null) {
            final String reqCapNumber = Objects.requireNonNullElse(request.getParameter("captchaNumber"), "");
            if (session.getAttribute("expectedNumber") == null) {
                session.setAttribute("expectedNumber", ThreadLocalRandom.current().nextInt(minRng, maxRng + 1));
            }
            final String sesCapNumber = session.getAttribute("expectedNumber").toString();

            if (reqCapNumber.equals(sesCapNumber)) {
                session.setAttribute("captchaPassed", true);
                response.sendRedirect(session.getAttribute("originalUri").toString());
            } else {
                if (!reqCapNumber.isEmpty()) {
                    session.setAttribute("expectedNumber", ThreadLocalRandom.current().nextInt(minRng, maxRng + 1));
                }

                byte[] imageBuffer = Base64.getEncoder().encode(ImageUtils.toPng(
                        session.getAttribute("expectedNumber").toString()));

                try (OutputStream outputStream = response.getOutputStream()) {
                    File file = new File(captchaURI);
                    String out = "<img src='data:image/png;base64, " + new String(imageBuffer) + "'" +
                            " alt='" + session.getAttribute("expectedNumber") + "' />";

                    Files.copy(file.toPath(), outputStream);
                    outputStream.write(out.getBytes(StandardCharsets.UTF_8));
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}