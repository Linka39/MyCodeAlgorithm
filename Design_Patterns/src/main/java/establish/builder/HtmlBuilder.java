package establish.builder;

import establish.builder.component.HeadingBuilder;

public class HtmlBuilder {
    private HeadingBuilder headingBuilder = new HeadingBuilder();

    public String toHtml(String md) {
        StringBuilder sb = new StringBuilder();
        sb.append(headingBuilder.buildHeading(md)).toString();
        return sb.toString();
    }
}
