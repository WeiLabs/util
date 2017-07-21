package org.weilabs.archiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.compress.compressors.bzip2.*;

/**
 * BZIP2压缩文件(*.bz2)
 */
public final class BZip2 extends Compressor {

	@Override
	public final void doCompress(File file, String destpath) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File gf = new File(destpath);
		FileOutputStream fos = new FileOutputStream(gf);
		BZip2CompressorOutputStream gzos = new BZip2CompressorOutputStream(fos);
		BufferedOutputStream bos = new BufferedOutputStream(gzos);
		readAndWrite(bis, bos);
	}

	@Override
	public final void doUnCompress(File srcFile, String destpath) throws IOException {
		FileInputStream fis = new FileInputStream(srcFile);
		BZip2CompressorInputStream gzis = new BZip2CompressorInputStream(fis);
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
        String tarBzip2FilePath = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS.tar.bz2";
        String unCompressTarBzip2File = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS--.tar";

        try {
            Tar tar = new Tar();
            BZip2 bzip2 = new BZip2();

            //解压打成Tar包
            File file = new File(folderPath);
            File file1 = new File(folderPath1);
            File[] files = {file,file1};
            tar.doArchiver(files, tarFilePath);

            //Tar包压缩bz2
            File tarFile = new File(tarFilePath);
            bzip2.doCompress(tarFile, tarBzip2FilePath);

            //解压tar.gz成Tar包
            File tarBzip2File = new File(tarBzip2FilePath);
            bzip2.doUnCompress(tarBzip2File, unCompressTarBzip2File);

        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
