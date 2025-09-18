// 代码生成时间: 2025-09-18 15:28:39
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * FileUnzipTool class provides functionality to unzip files.
 * This class is designed to be used within a Struts 2 action.
 */
@ParentPackage("default")
@Namespace("/unzip")
public class FileUnzipTool {

    /**
     * Unzips the file specified by the 'filePath' parameter.
     *
     * @param filePath The path of the zip file to be unzipped.
     * @return A message indicating the success or failure of the operation.
     */
    @Action(value = "unzip", results = {
            @Result(name = "success", type = "json"),
            @Result(name = "error", type = "json")
    })
    public String unzip(String filePath) {
        try {
            File zipFile = new File(filePath);
            if (!zipFile.exists()) {
                return ERROR;
            }

            String destDir = zipFile.getParent();
            unzipFile(zipFile, destDir);
            return SUCCESS;
        } catch (Exception e) {
            // Log the exception details (omitted for brevity)
            return ERROR;
        }
    }

    /**
     * Unzips a file to the specified directory.
     *
     * @param zipFile The zip file to be unzipped.
     * @param destDir The destination directory.
     */
    private void unzipFile(File zipFile, String destDir) throws Exception {
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry entry = zipIn.getNextEntry();

        // Handle directories
        while (entry != null) {
            String filePath = destDir + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // If the entry is a file, extract it
                extractFile(zipIn, filePath);
            } else {
                // If the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a file from the zip input stream.
     *
     * @param zipIn The zip input stream.
     * @param filePath The path where the file will be extracted.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}