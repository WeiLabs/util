package org.weilabs.archiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP压缩文件(*.gz)
 */
public final class GZip extends Compressor {

	@Override
	public final void doCompress(File file, String destpath) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File gf = new File(destpath);
		FileOutputStream fos = new FileOutputStream(gf);
		GZIPOutputStream gzos = new GZIPOutputStream(fos);
		BufferedOutputStream bos = new BufferedOutputStream(gzos);
		readAndWrite(bis, bos);
	}

	@Override
	public final void doUnCompress(File srcFile, String destpath) throws IOException {
		FileInputStream fis = new FileInputStream(srcFile);
		GZIPInputStream gzis = new GZIPInputStream(fis);
		BufferedInputStream bis = new BufferedInputStream(gzis);

		File tf = new File(destpath);
		FileOutputStream fos = new FileOutputStream(tf);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		readAndWrite(bis, bos);
	}

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        String folderPath = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS.csv";
        String folderPath1 = "C:\\Users\\zhouwei\\Dev\\testDate\\t01_comp_org_info20160720.csv";
        String tarFilePath = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS.tar";
        String tarGzipFilePath = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS.tar.gz";
        String unCompressTarGzipFile = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS-.tar";

        try {
            Tar tar = new Tar();
            GZip gzip = new GZip();

            //解压打成Tar包
            File file = new File(folderPath);
            File file1 = new File(folderPath1);
            File[] files = {file,file1};
            tar.doArchiver(files, tarFilePath);

            //Tar包压缩gz
            File tarFile = new File(tarFilePath);
            gzip.doCompress(tarFile, tarGzipFilePath);

            //解压tar.gz成Tar包
            File tarGzipFile = new File(tarGzipFilePath);
            gzip.doUnCompress(tarGzipFile, unCompressTarGzipFile);


        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
