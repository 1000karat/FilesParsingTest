import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipHelper {
    static ClassLoader cl = ZipHelper.class.getClassLoader();
    public static String readFileContentFromZip(String fileNameInZip) throws IOException {

        try (InputStream is = cl.getResourceAsStream("test.zip");
        ZipInputStream zis = new ZipInputStream(is, StandardCharsets.UTF_8)) {

            ZipEntry zipEntry;

            while ((zipEntry = zis.getNextEntry()) !=null) {
                if (zipEntry.getName().equals(fileNameInZip)) {
                    return new String(zis.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
        }
        return "File noy found!";
    }

    public static byte[] readFileContentByteFromZip(String fileNameInZip) throws IOException {

        try (InputStream is = cl.getResourceAsStream("test.zip");
             ZipInputStream zis = new ZipInputStream(is, StandardCharsets.UTF_8)) {

            ZipEntry zipEntry;

            while ((zipEntry = zis.getNextEntry()) !=null) {
                if (zipEntry.getName().equals(fileNameInZip)) {
                    return zis.readAllBytes();
                }
            }
        }
        return new byte[0];
    }
}
