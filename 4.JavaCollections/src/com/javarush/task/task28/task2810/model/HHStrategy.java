package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";
    private static final String URL_FORMAT_TEMP = "http://javarush.ru/testdata/big28data.html";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancyList = new ArrayList<>();
        Document document = null;
        int increment = 0;
        while (true) {
            document = getDocument(searchString, increment);
            increment++;
            Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
            if (elements.size() == 0) {
                break;
            }
            for (Element element : elements) {
                Vacancy vacancy = new Vacancy();
                vacancy.setTitle(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-title").text());
                vacancy.setCity(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-address").text());
                vacancy.setCompanyName(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-employer").text());
                vacancy.setUrl(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-title").attr("href"));
                vacancy.setSalary(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-compensation").text());
                vacancy.setSiteName(URL_FORMAT);
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
