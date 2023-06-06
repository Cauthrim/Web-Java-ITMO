package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    static final String localPath
            = "C:/Users/Public/Documents/Programming/Web 3rd semester/hw3/src/main/webapp/static";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] uris = uri.split("\\+");
        try (OutputStream outputStream = response.getOutputStream()) {
            for (int i = 0; i < uris.length; i++) {
                String sep = "/";
                if (!uris[i].startsWith(sep)) {
                    uris[i] = sep + uris[i];
                }
                File file = new File(localPath + uris[i]);
                if (!file.isFile()) {
                    file = new File(getServletContext().getRealPath("/static" + uris[i]));
                }
                if (file.isFile()) {
                    if (i == 0) {
                        response.setContentType(getServletContext().getMimeType(file.getName()));
                    }
                    Files.copy(file.toPath(), outputStream);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
                }
            }
        }
    }
}
