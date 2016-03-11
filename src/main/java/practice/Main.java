package practice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;


import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;

class Main {

    static String readFile(String path, Charset encoding)  throws IOException 
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

     static File getResourcesDir()  throws Throwable {
        File dir = new File(Main.class.getResource("").toURI());
        File projectDir = dir.getParentFile().getParentFile().getParentFile().getParentFile();
        return new File(projectDir.getAbsolutePath()+"/src/resources/");
    }

    public static void main(String[] args) throws Throwable {
        System.out.println("######################################################");
	System.out.println("json validator");
        File dir = new File(Main.class.getResource("").toURI());
        File projectDir = dir.getParentFile().getParentFile().getParentFile().getParentFile();
        File dataDir = getResourcesDir();
        String json = readFile(dataDir.getAbsolutePath()+"/conformingDocument.json",StandardCharsets.UTF_8);
        System.out.println("json is "+json);        
        String schemaAsString = readFile(dataDir.getAbsolutePath()+"/basicSchema.json",StandardCharsets.UTF_8);
        System.out.println("schema is "+schemaAsString);     

        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema("file://"+dataDir.getAbsolutePath()+"/basicSchema.json");
        JsonNode goodJson = JsonLoader.fromPath(dataDir.getAbsolutePath()+"/conformingDocument.json");
        ProcessingReport report = schema.validate(goodJson);
	System.out.println("Good document report is "+report);

        JsonNode badJson = JsonLoader.fromPath(dataDir.getAbsolutePath()+"/bad.json");
        report = schema.validate(badJson);
	System.out.println("Bad document report is "+report);
    }

    



}
