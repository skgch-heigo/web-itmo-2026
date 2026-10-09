package ru.itmo.wp.servlet;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    private static final String SRC_STATIC = "src/main/webapp/static";


    private boolean fileInDirectory(File file, String path) {
        if (file == null || path == null) {
            return false;
        }
        try {
            return file.getCanonicalPath().startsWith(new File(path).getCanonicalPath() + File.separator);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] allFilesUri = request.getRequestURI().split("\\+");
        boolean setContentFlag = true;
        File[] files = new File[allFilesUri.length];
        for (int i = 0; i < allFilesUri.length; i++) {
            String uri = allFilesUri[i];
            if (!uri.startsWith("/")) {
                uri = "/" + uri;
            }
            File file = new File(SRC_STATIC + uri);
            String sourcePath = SRC_STATIC;
            if (!file.isFile()) {
                file = new File(getServletContext().getRealPath("/static" + uri));
                sourcePath = getServletContext().getRealPath("/static");
            }
            if (file.isFile() && fileInDirectory(file, sourcePath)) {
                if (setContentFlag) {
                    response.setContentType(getServletContext().getMimeType(file.getName()));
                    setContentFlag = false;
                }
                files[i] = file;
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
        try (OutputStream outputStream = response.getOutputStream()) {
            for (File file : files) {
                Files.copy(file.toPath(), outputStream);
            }
        }
    }
}
