package com.nastyHaze.gimboslice.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class ThymeleafContextUtil {

    @Autowired
    private TemplateEngine templateEngine;

    public Context getContext(Map<String, Object> valueMap) {
        Context context = new Context();

        context.setVariables(valueMap);

        return context;
    }

    public String getParsedTemplate(String template, Context context) {
        return templateEngine.process(template, context);
    }
}
