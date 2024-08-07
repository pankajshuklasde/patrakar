package com.patrakar.patrakar.model.repository.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsLetterDto {
    List<NewsItemDto> newsItemDto;
    public String asHtml(){
        StringBuilder htmlBuilder=new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>News Letter</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f0f8ff;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 800px;\n" +
                "            margin: 0 auto;\n" +
                "            background: #fff;\n" +
                "            padding: 30px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 40px;\n" +
                "        }\n" +
                "        .header h1 {\n" +
                "            color: #4682b4;\n" +
                "        }\n" +
                "        .news-item {\n" +
                "            margin-bottom: 30px;\n" +
                "            padding: 20px;\n" +
                "            border-left: 4px solid #4682b4;\n" +
                "            background: #e6f2ff;\n" +
                "            border-radius: 8px;\n" +
                "        }\n" +
                "        .news-item h2 {\n" +
                "            margin: 0 0 15px;\n" +
                "            color: #4682b4;\n" +
                "        }\n" +
                "        .news-item ul {\n" +
                "            padding-left: 20px;\n" +
                "            list-style-type: none;\n" +
                "        }\n" +
                "        .news-item ul li {\n" +
                "            margin-bottom: 10px;\n" +
                "            padding: 10px;\n" +
                "            background: #fff;\n" +
                "            border: 1px solid #ddd;\n" +
                "            border-radius: 4px;\n" +
                "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                "            transition: background-color 0.3s, border-color 0.3s;\n" +
                "        }\n" +
                "        .news-item ul li:hover {\n" +
                "            background-color: #f0f8ff;\n" +
                "            border-color: #4682b4;\n" +
                "        }\n" +
                "        .news-item p {\n" +
                "            margin: 15px 0 0;\n" +
                "            font-family: 'Georgia', serif;\n" +
                "            font-size: 1.1em;\n" +
                "            font-weight: bold;\n" +
                "            color: #555;\n" +
                "            background: #fff;\n" +
                "            padding: 10px;\n" +
                "            border-radius: 4px;\n" +
                "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            margin-top: 50px;\n" +
                "            font-size: 0.9em;\n" +
                "            color: #555;\n" +
                "        }\n" +
                "    </style>"+
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Mstack News Letter</h1>\n" +
                "        </div>");
        newsItemDto.forEach(item->{
            htmlBuilder.append(generateItemHtml(item));

        });
        htmlBuilder.append("<div class=\"footer\">\n" +
                "            <p>&copy; 2024 Mstack News Letter. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n");
        return htmlBuilder.toString();

    }

    private String generateItemHtml(NewsItemDto item) {
        StringBuilder html=new StringBuilder();
        html.append("<div class=\"news-item\">\n" +
                "            <h2>"+item.getTopic()+"</h2>\n" +
                "            <ul>");
        item.getTopicNews().forEach(news->{
            html.append("<li>").append(news).append("</li>");
        });
        html.append("<p> Summary: ").append(item.getSummary()).append("</p>");
        html.append("</div>");
        return html.toString();
    }
}
