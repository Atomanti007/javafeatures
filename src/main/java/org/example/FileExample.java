package org.example;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileExample {

    public static void main(String[] args) throws IOException {
        Files.writeString(Path.of("ki.txt"), "árvíztűrő tükörfúrógép");


        // Ez például Linuxon gyakran UTF-8, de Windows alatt tipikusan a Windows-1250/CP1252 lehet, így a magyar ékezeteknél könnyen gond lehet.
        // Files.write(Path.of("ki.txt"), "árvíztűrő tükörfúrógép".getBytes(StandardCharsets.UTF_8));
    }


}
