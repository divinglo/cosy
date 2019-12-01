package cosyfish.pro.common.util;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * 压缩/解压 工具包
 */
public class ZipUtils {
    private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 解压 ，带有ZLIB header and checksum fields
     */
    public static byte[] inflateStream(byte[] bs) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(bs);
            InputStream input = new InflaterInputStream(in);
            return IOUtils.toByteArray(input, true);
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

    /**
     * 压缩，带有 ZLIB header and checksum fields
     */
    public static byte[] deflateStream(byte[] input) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            OutputStream out = new DeflaterOutputStream(output);
            out.write(input);
            out.close();
            return output.toByteArray();
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

    /**
     * 解压
     *
     * @param nowrap 用于解压 If the parameter 'nowrap' is true then the ZLIB header and checksum fields
     * will not be used. This provides compatibility with the compression format used by both
     * GZIP and PKZIP.
     */
    public static byte[] inflate(byte[] bs, boolean nowrap) {
        try {
            // Create the decompressor and give it the data to compress
            Inflater decompressor = new Inflater(nowrap);
            decompressor.setInput(bs);

            // Create an expandable byte array to hold the decompressed data
            ByteArrayOutputStream bos = new ByteArrayOutputStream(bs.length);

            // Decompress the data
            byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }
            bos.close();
            // Get the decompressed data
            return bos.toByteArray();
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

    /**
     * 压缩
     *
     * @param nowrap 用于解压 If the parameter 'nowrap' is true then the ZLIB header and checksum fields
     * will not be used. This provides compatibility with the compression format used by both
     * GZIP and PKZIP.
     */
    public static byte[] deflate(byte[] input, boolean nowrap) {
        try {
            // Compress the bytes
            Deflater df = new Deflater(5, nowrap); // this function mainly
            // generate the byte code
            // df.setLevel(Deflater.BEST_COMPRESSION);
            df.setInput(input);

            ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length); // we
            // write
            // the
            // generated
            // byte
            // code
            // in
            // this
            // array
            df.finish();
            byte[] buff = new byte[1024]; // segment segment pop....segment set
            // 1024
            while (!df.finished()) {
                int count = df.deflate(buff); // returns the generated code...
                // index
                baos.write(buff, 0, count); // write 4m 0 to count
            }
            baos.close();
            byte[] output = baos.toByteArray();

            // System.out.println("Original: "+input.length);
            // System.out.println("Compressed: "+output.length);
            return output;
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

    public static byte[] gzip(byte[] bs) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(1000);
        GZIPOutputStream gzout = null;
        try {
            gzout = new GZIPOutputStream(bout);
            gzout.write(bs);
            gzout.flush();
        } catch (Exception e) {
            throw e;

        } finally {
            if (gzout != null) {
                try {
                    gzout.close();
                } catch (Exception ex) {
                }
            }
        }

        return bout.toByteArray();

    }

    public static byte[] ungzip(byte[] bs) throws Exception {
        GZIPInputStream gzin = null;
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(bs);
            gzin = new GZIPInputStream(bin);
            return FileUtils.readBytes(gzin);
        } catch (Exception e) {
            throw e;

        } finally {
        }

    }

    public static byte[] zip(byte[] bs) throws Exception {

        ByteArrayOutputStream o = null;
        try {
            o = new ByteArrayOutputStream();
            Deflater compresser = new Deflater();
            compresser.setInput(bs);
            compresser.finish();
            byte[] output = new byte[1024];
            while (!compresser.finished()) {
                int got = compresser.deflate(output);
                o.write(output, 0, got);
            }
            o.flush();
            return o.toByteArray();
        } catch (Exception ex) {
            throw ex;

        } finally {
            if (o != null) {
                try {
                    o.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }

    }

    public static byte[] unzip(byte[] bs) throws Exception {

        ByteArrayOutputStream o = null;
        try {
            o = new ByteArrayOutputStream();
            Inflater decompresser = new Inflater();
            decompresser.setInput(bs);
            byte[] result = new byte[1024];
            while (!decompresser.finished()) {
                int resultLength = decompresser.inflate(result);
                o.write(result, 0, resultLength);
            }
            decompresser.end();
            o.flush();
            return o.toByteArray();
        } catch (Exception ex) {
            throw ex;

        } finally {
            if (o != null) {
                try {
                    o.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }

    }

    public static void zip(String path, String zipfilename) throws Exception {
        ZipOutputStream zout = null;
        try {
            if (!zipfilename.contains(File.separator)) {
                zipfilename = FileUtils.getParentPath(path) + "/" + zipfilename;
            }
            FileOutputStream fout = new FileOutputStream(zipfilename);
            zout = new ZipOutputStream(fout);
            dozip(new File(path), "", zout);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (zout != null) {
                zout.close();
            }
        }
    }

    private static void dozip(File file, String parent, ZipOutputStream zout) throws Exception {
        try {

            if (StringUtils.hasText(parent)) {
                parent = parent + "/";
            }
            if (file.isDirectory()) {

                ZipEntry ze = new ZipEntry(file.getName() + "/");
                zout.putNextEntry(ze);
                File[] f = file.listFiles();
                for (int i = 0; i < f.length; i++) {
                    dozip(f[i], file.getName(), zout);
                }
            } else {
                ZipEntry ze = new ZipEntry(parent + file.getName());
                zout.putNextEntry(ze);
                byte[] buf = FileUtils.readBytes(file);
                zout.write(buf);
            }
        } catch (Exception ex) {
            log.error("", ex);
            throw ex;
        }
    }

    public static void zip(String[] fileNames, String outZipFileName) throws Exception {
        ZipOutputStream zout = null;
        try {
            FileOutputStream fout = new FileOutputStream(outZipFileName);

            zout = new ZipOutputStream(fout);

            BufferedOutputStream out = new BufferedOutputStream(zout);
            for (int i = 0; i < fileNames.length; i++) {
                ZipEntry ze = new ZipEntry(FileUtils.getFileName(fileNames[i]));
                BufferedInputStream in = null;
                try {
                    in = new BufferedInputStream(new FileInputStream(fileNames[i]));
                    zout.putNextEntry(ze);
                    int readLen = 1024;
                    byte[] bs = new byte[readLen];

                    while (true) {
                        int result = in.read(bs);
                        if (result == -1) {
                            break;
                        }
                        out.write(bs, 0, result);

                    }
                } finally {
                    in.close();
                    out.flush();
                    zout.closeEntry();
                }
            }
        } catch (Exception ex) {
            log.error("", ex);
            throw ex;

        } finally {
            if (zout != null) {
                zout.close();
            }
        }
    }

    /**
     * 解压zip文件到指定的目录 File Unzip
     *
     * @param sToPath Directory path to be unzipted to
     * @param sZipFile zip File Name to be ziped
     * @param isflat 是否去掉zip的目录结构
     */
    public static void unZip(String sToPath, String sZipFile, boolean isflat) throws Exception {
        ZipFile zfile = new ZipFile(sZipFile);
        if (null == sToPath || ("").equals(sToPath.trim())) {
            // File objZipFile = new File(sZipFile);
            sToPath = FileUtils.getFileNameBeforeType(zfile.getName());
            // System.out.println(sToPath);
        }

        Enumeration<?> zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {

            ze = (ZipEntry) zList.nextElement();

            if (ze.isDirectory()) {
                // System.out.println(ze.getName());
                if (!isflat) {
                    String dir = FileUtils.getDirectoryPath(sToPath + "/" + ze.getName());
                    // System.out.println(dir);
                    FileUtils.makeDirectory(dir);
                }

                // System.out.println("1111");

            } else {
                // System.out.println("2222");
                String temp = ze.getName();
                if (isflat) {
                    temp = FileUtils.getFileName(ze.getName());
                }
                String filePath = FileUtils.getDirectoryPath(sToPath + "/" + temp);
                buf = IOUtils.toByteArray(zfile.getInputStream(ze), true);
                FileUtils.write(filePath, buf);

                // OutputStream os = new BufferedOutputStream(
                // new FileOutputStream(filePath));
                // InputStream is = new BufferedInputStream(zfile
                // .getInputStream(ze));
                // int readLen = 0;
                // while ((readLen = is.read(buf, 0, 1024)) != -1) {
                // os.write(buf, 0, readLen);
                // }
                // is.close();
                // os.close();
            }
        }
        zfile.close();
    }

    /**
     * 解压tar.gz包
     */
    public static void unzipTZFile(String outputDirectory, String zipfileName) {
        FileInputStream fis = null;
        ArchiveInputStream in = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            fis = new FileInputStream(zipfileName);
            GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
            bufferedInputStream = new BufferedInputStream(in);
            TarArchiveEntry entry = (TarArchiveEntry) in.getNextEntry();

            while (entry != null) {
                String name = entry.getName();
                String[] names = name.split("/");
                String fileName = outputDirectory;
                for (String str : names) {
                    fileName = fileName + File.separator + str;
                }
                if (name.endsWith("/")) {
                    FileUtils.makeDirectory(fileName);
                } else {
                    // File file = mkFile(fileName);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
                    int b;
                    while ((b = bufferedInputStream.read()) != -1) {
                        bufferedOutputStream.write(b);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
                entry = (TarArchiveEntry) in.getNextEntry();
            }

        } catch (Exception e) {
            log.error("zipfileName=" + zipfileName, e);
        }
    }
}
