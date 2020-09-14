package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName()
            .replaceAll("\\.", "/") + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            updateFile(getUpdatedFileContent(vacancies));
            System.out.println(vacancies.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> list) {
        String result = null;
        try {
            Document doc = getDocument();
            Elements template = doc.getElementsByClass("template");
            Element clone = template.first().clone();
            clone.removeAttr("style");
            clone.removeClass("template");
            doc.removeClass("vacancy");
            doc.getElementsByClass("vacancy").stream()
                    .filter(element -> !element.hasClass("template")).forEach(Node::remove);
            for (Vacancy vacancy : list) {
                Element element = clone.clone();
                element.getElementsByClass("city").first().text(vacancy.getCity());
                element.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                element.getElementsByClass("salary").first().text(vacancy.getSalary());
                element.getElementsByTag("a").first().attr("href", vacancy.getUrl()).text(vacancy.getTitle());
                doc.getElementsByClass("vacancy").first().before(element.outerHtml());
            }
            result = doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            result = "Some exception occurred";
        }
        return result;
    }

    private void updateFile(String string) throws IOException {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(string);
        }
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "utf-8");
    }
}
