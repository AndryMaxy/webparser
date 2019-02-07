package by.epam.akulich.webparser.servlet;

import by.epam.akulich.webparser.bean.Medicine;
import by.epam.akulich.webparser.exception.ParserException;
import by.epam.akulich.webparser.factory.ParserFactory;
import by.epam.akulich.webparser.parser.IParser;
import by.epam.akulich.webparser.validator.XMLValidator;
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
import java.util.List;

@WebServlet("/parse")
@MultipartConfig
public class ParserServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(ParserServlet.class.getSimpleName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        String parserName = req.getParameter("parser");
        InputStream fileContent1 = filePart.getInputStream();
        InputStream fileContent2 = filePart.getInputStream();

        XMLValidator validator = new XMLValidator();
        boolean isValid = validator.validate(fileContent1);

        if (isValid) {
            IParser parser = ParserFactory.getInstance().getParser(parserName);
            List<Medicine> medicines;
            try {
                medicines = parser.parse(fileContent2);
            } catch (ParserException e) {
                LOGGER.error("Parser exception.", e);
                return;
            }
            req.setAttribute("medicines", medicines);
            req.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(req, resp);
        } else {
            LOGGER.info("XML file is not valid");
            req.getRequestDispatcher("/WEB-INF/jsp/notValid.jsp").forward(req,resp);
        }
    }
}
