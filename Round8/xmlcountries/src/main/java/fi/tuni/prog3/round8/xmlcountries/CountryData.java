package fi.tuni.prog3.round8.xmlcountries;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountryData
{
    public static List<Country> readFromXmls(String areaFile, String populationFile, String gdpFile) throws IOException, JDOMException
    {
        HashMap<String, Map<String, String>> countries = new HashMap<>();

        var sax = new SAXBuilder();
        sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        var areaXml = sax.build(new File(areaFile));
        var populationXml = sax.build(new File(populationFile));
        var gdpXml = sax.build(new File(gdpFile));

        for (var child : areaXml.getRootElement().getChild("data").getChildren())
        {
            var children = child.getChildren();
            var key = children.get(0).getAttribute("key").getValue();
            var name = children.get(0).getContent(0).getValue();
            var area = children.get(2).getContent(0).getValue();

            countries.put(key, new HashMap<>(Map.ofEntries(
                    new AbstractMap.SimpleEntry<>("name", name),
                    new AbstractMap.SimpleEntry<>("area", area),
                    new AbstractMap.SimpleEntry<>("population", ""),
                    new AbstractMap.SimpleEntry<>("gdp", ""))));
        }

        for (var child : populationXml.getRootElement().getChild("data").getChildren())
        {
            var children = child.getChildren();
            var key = children.get(0).getAttribute("key").getValue();
            var population = children.get(2).getContent(0).getValue();

            countries.get(key).put("population", population);
        }

        for (var child : gdpXml.getRootElement().getChild("data").getChildren())
        {
            var children = child.getChildren();
            var key = children.get(0).getAttribute("key").getValue();
            var gdp = children.get(2).getContent(0).getValue();

            countries.get(key).put("gdp", gdp);
        }

        return countries.values().stream()
                .map(x -> new Country(x.get("name"),
                                      Double.parseDouble(x.get("area")),
                                      Long.parseLong(x.get("population")),
                                      Double.parseDouble(x.get("gdp"))))
                .collect(Collectors.toList());
    }

    public static void writeToXml(List<Country> countries, String countryFile) throws IOException
    {
        var root = new Element("counties");
        var document = new Document(root);

        for (var country : countries)
        {
            var countryElem = new Element("country");
            var nameElem = new Element("name");
            var areaElem = new Element("area");
            var populationElem = new Element("population");
            var gdpElem = new Element("gdp");

            nameElem.setText(country.getName());
            areaElem.setText(String.format("%.1f", country.getArea()));
            populationElem.setText(country.getPopulation() + "");
            gdpElem.setText(country.getGdp() + "");

            countryElem.addContent(nameElem);
            countryElem.addContent(areaElem);
            countryElem.addContent(populationElem);
            countryElem.addContent(gdpElem);

            root.addContent(countryElem);
        }

        var xout = new XMLOutputter(Format.getPrettyFormat());
        xout.output(document, new FileOutputStream(countryFile));
    }
}
