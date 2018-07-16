package com.comp3350.rev_u_hub_tests.utils;
import com.comp3350.rev_u_hub.Application.Main;
import com.google.common.io.Files;

        import java.io.File;
        import java.io.IOException;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/DB.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB_SRC, target);

        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}
