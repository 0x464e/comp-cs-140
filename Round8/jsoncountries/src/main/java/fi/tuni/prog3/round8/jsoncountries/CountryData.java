package fi.tuni.prog3.round8.jsoncountries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountryData
{
    public static List<Country> readFromJsons(String areaFile, String populationFile, String gdpFile) throws IOException
    {
        HashMap<String, Map<String, String>> countries = new HashMap<>();

        Gson gson = new Gson();
        var areaJson = gson.fromJson(new FileReader(areaFile), JsonObject.class);
        var populationJson = gson.fromJson(new FileReader(populationFile), JsonObject.class);
        var gdpJson = gson.fromJson(new FileReader(gdpFile), JsonObject.class);

        var areaRecords = areaJson.get("Root").getAsJsonObject()
                .get("data").getAsJsonObject()
                .get("record").getAsJsonArray();

        var populationRecords = populationJson.get("Root").getAsJsonObject()
                .get("data").getAsJsonObject()
                .get("record").getAsJsonArray();

        var gdpRecords = gdpJson.get("Root").getAsJsonObject()
                .get("data").getAsJsonObject()
                .get("record").getAsJsonArray();

        for (var country : areaRecords)
        {
            var fields = country.getAsJsonObject().get("field").getAsJsonArray();
            var key = fields.get(0).getAsJsonObject().get("attributes").getAsJsonObject().get("key").getAsString();
            var name = fields.get(0).getAsJsonObject().get("value").getAsString();
            var area = fields.get(2).getAsJsonObject().get("value").getAsString();

            countries.put(key, new HashMap<>(Map.ofEntries(
                    new AbstractMap.SimpleEntry<>("name", name),
                    new AbstractMap.SimpleEntry<>("area", area),
                    new AbstractMap.SimpleEntry<>("population", ""),
                    new AbstractMap.SimpleEntry<>("gdp", ""))));
        }

        for (var country : populationRecords)
        {
            var fields = country.getAsJsonObject().get("field").getAsJsonArray();
            var key = fields.get(0).getAsJsonObject().get("attributes").getAsJsonObject().get("key").getAsString();
            var population = fields.get(2).getAsJsonObject().get("value").getAsString();

            countries.get(key).put("population", population);
        }

        for (var country : gdpRecords)
        {
            var fields = country.getAsJsonObject().get("field").getAsJsonArray();
            var key = fields.get(0).getAsJsonObject().get("attributes").getAsJsonObject().get("key").getAsString();
            var gdp = fields.get(2).getAsJsonObject().get("value").getAsString();

            countries.get(key).put("gdp", gdp);
        }

        return countries.values().stream()
                .map(x -> new Country(x.get("name"),
                                      Double.parseDouble(x.get("area")),
                                      Long.parseLong(x.get("population")),
                                      Double.parseDouble(x.get("gdp"))))
                .collect(Collectors.toList());
    }

    public static void writeToJson(List<Country> countries, String countryFile) throws IOException
    {
        var builder = new GsonBuilder().setPrettyPrinting().create();
        var writer = new FileWriter(countryFile);
        builder.toJson(countries, writer);
        writer.close();
    }
}
