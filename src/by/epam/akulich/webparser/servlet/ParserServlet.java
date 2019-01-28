package by.epam.akulich.webparser.servlet;

import by.epam.akulich.webparser.parser.DOMParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/parse")
@MultipartConfig
public class ParserServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(ParserServlet.class.getSimpleName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        String parser = req.getParameter("parser");
        InputStream fileContent = filePart.getInputStream();
        String result = "";
        if (parser.equals("DOM")) {
           result = new DOMParser().parse(fileContent);
        } else {
            LOGGER.info("fail");
        }
        resp.getWriter().println(result);
    }
}
