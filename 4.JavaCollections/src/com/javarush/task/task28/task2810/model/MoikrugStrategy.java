package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy{

    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";
    private static final String URL_FORMAT_TEMP = "http://javarush.ru/testdata/big28data2.html";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        String siteName = "https://moikrug.ru";
        List<Vacancy> vacancyList = new ArrayList<>();
        Document document = null;
        int increment = 0;
        while (true) {
            document = getDocument(searchString, increment);
            increment++;
            Elements elements = document.getElementsByClass("job");
            if (elements.size() == 0) {
                break;
            }
            for (Element element : elements) {
                Vacancy vacancy = new Vacancy();
                vacancy.setCompanyName(element.getElementsByClass("company_name").first().text());
                Element titleWithURL = element.getElementsByClass("title").first().getElementsByTag("a").first();
                vacancy.setTitle(titleWithURL.text());
                vacancy.setUrl(siteName + titleWithURL.attr("href"));
                Elements aTag = element.getElementsByClass("meta").first().getElementsByTag("a");
                vacancy.setCity(aTag.size() != 0 ? aTag.first().text() : "");
                vacancy.setSalary(element.getElementsByClass("salary").text());
                vacancy.setSiteName(siteName);
                vacancyList.add(vacancy);
            }
        }
        return vacancyList;
    }

    protected Document getDocument(String searchString, int page) {
        String fullString = String.format(URL_FORMAT, searchString, page);
        Document doc = null;
        try {
            doc = Jsoup.connect(fullString)
                    .userAgent("Chrome/85.0.4183.83 Safari/537.36")
                    .referrer("https://spb.hh.ru/applicant/resumes?from=header_new")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
