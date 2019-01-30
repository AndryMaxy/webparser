package by.epam.akulich.webparser.servlet;

import by.epam.akulich.webparser.bean.Drug;
import by.epam.akulich.webparser.bean.Firm;
import by.epam.akulich.webparser.factory.ParserFactory;
import by.epam.akulich.webparser.generator.FileGenerator;
import by.epam.akulich.webparser.parser.DOMParser;
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
        InputStream fileContent = filePart.getInputStream();

        IParser parser = ParserFactory.getInstance().getParser(parserName);
        List<Drug> drugs = parser.parse(fileContent);
        for (Drug drug : drugs) {
            System.out.println(drug.getName());
            System.out.println(drug.getProducer());
            System.out.println(drug.getGroup());
            System.out.println(drug.getAnalogs());
            for (Firm f : drug.getVersions()) {
                System.out.println(f.getName());
                System.out.println(f.getCertificate().getNumber());
                System.out.println(f.getCertificate().getIssueDate());
                System.out.println(f.getCertificate().getExpirationDate());
                System.out.println(f.getCertificate().getRegister());
                System.out.println(f.getFormType());
                System.out.println(f.getDrugPackage());
                System.out.println(f.getDosage());
            }
        }
        req.setAttribute("drugs", drugs);
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }
}
