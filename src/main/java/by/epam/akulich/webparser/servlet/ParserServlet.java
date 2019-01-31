package by.epam.akulich.webparser.servlet;

import by.epam.akulich.webparser.bean.Drug;
import by.epam.akulich.webparser.factory.ParserFactory;
import by.epam.akulich.webparser.generator.FileGenerator;
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
import java.io.File;
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
        InputStream fileContent = filePart.getInputStream();
        //ДОБАВИТЬ ДЕФОЛ!!!!!!! В СВИТЧ
        File scheme = new File(ParserServlet.class.getResource("/medicine-scheme.xsd").getPath());
        FileGenerator generator = new FileGenerator();
        File xml = generator.generateTmpFile(fileContent);

        XMLValidator validator = new XMLValidator();
        boolean isValid = validator.validate(xml, scheme);

        if (isValid) {
            IParser parser = ParserFactory.getInstance().getParser(parserName);
            List<Drug> drugs = parser.parse(xml);
            req.setAttribute("drugs", drugs);
            req.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(req, resp);
        } else {
            LOGGER.info("XML file is not valid");
            resp.sendRedirect("/index.jsp");
        }
    }
}
