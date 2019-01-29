package by.epam.akulich.webparser.servlet;

import by.epam.akulich.webparser.bean.Drug;
import by.epam.akulich.webparser.factory.ParserFactory;
import by.epam.akulich.webparser.parser.DOMParser;
import by.epam.akulich.webparser.parser.IParser;
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
import java.util.ArrayList;
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
        drugs.clear();
        Drug drug = new Drug();
        drug.setName("kek");
        System.out.println(drug.getName());
        List<Drug> drugs1 = new ArrayList<>();
        drugs.add(drug);
//        List<String> drugs1 = new ArrayList<>();
//        drugs1.add("result");
        req.setAttribute("drugs", drugs1);
        req.setAttribute("hmmm", "ples");
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }
}
