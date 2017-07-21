package org.weilabs.archiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * ZIP压缩文件(*.zip)
 */
public final class Zip extends Archiver {

	private void dfs(File[] files, ZipOutputStream zos, String fpath)
			throws IOException {
		byte[] buf = new byte[1024];
		for (File child : files) {
			if (child.isFile()) { // 文件
				FileInputStream fis = new FileInputStream(child);
				BufferedInputStream bis = new BufferedInputStream(fis);
				zos.putNextEntry(new ZipEntry(fpath + child.getName()));
				int len;
				while((len = bis.read(buf)) > 0) {
					zos.write(buf, 0, len);
				}
				bis.close();
				zos.closeEntry();
				continue;
			}
			File[] fs = child.listFiles();
			String nfpath = fpath + child.getName() + "/";
			if (fs.length <= 0) { // 空目录
				zos.putNextEntry(new ZipEntry(nfpath));
				zos.closeEntry();
			} else { // 目录非空，递归处理
				dfs(fs, zos, nfpath);
			}
		}
	}

	@Override
	public final void doArchiver(File[] files, String destpath)
			throws IOException {
		/*
		 * 定义一个ZipOutputStream 对象
		 */
		FileOutputStream fos = new FileOutputStream(destpath);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		ZipOutputStream zos = new ZipOutputStream(bos);
		dfs(files, zos, "");
		zos.flush();
		zos.close();
	}

	@Override
	public final void doUnArchiver(File srcfile, String destpath) throws IOException {
		byte[] buf = new byte[1024];
		FileInputStream fis = new FileInputStream(srcfile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ZipInputStream zis = new ZipInputStream(bis);
		ZipEntry zn = null;
		while ((zn = zis.getNextEntry()) != null) {
			File f = new File(destpath + "/" + zn.getName());
			if (zn.isDirectory()) {
				f.mkdirs();
			} else {
				/*
				 * 父目录不存在则创建
				 */
				File parent = f.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}

				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				int len;
				while ((len = zis.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}
				bos.flush();
				bos.close();
			}
			zis.closeEntry();
		}
		zis.close();
	}

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        String folderPath = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS.csv";
        String folderPath1 = "C:\\Users\\zhouwei\\Dev\\testDate\\t01_comp_org_info20160720.csv";
        String zipFilePath = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS.zip";
         String unCompressZipFile = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS";

        try {
            Zip zip = new Zip();
            File file = new File(folderPath);
            File file1 = new File(folderPath1);
            File[] files = {file,file1};
            //文件打成Zip包
            zip.doArchiver(files, zipFilePath);
            File zipFile = new File(zipFilePath);

            //解压Zip包
            zip.doUnArchiver(zipFile, unCompressZipFile);

        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
