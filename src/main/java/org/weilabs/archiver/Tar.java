package org.weilabs.archiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.compress.archivers.tar.*;

public final class Tar extends Archiver {

	private void dfs(File[] files, TarArchiveOutputStream taos, String fpath)
			throws IOException {
		byte[] buf = new byte[1024];
		for (File child : files) {
			if (child.isFile()) { // 文件
				FileInputStream fis = new FileInputStream(child);
				BufferedInputStream bis = new BufferedInputStream(fis);
				TarArchiveEntry tae = new TarArchiveEntry(fpath
						+ child.getName());
				tae.setSize(child.length());
				taos.putArchiveEntry(tae);
				int len;
				while ((len = bis.read(buf)) > 0) {
					taos.write(buf, 0, len);
				}
				bis.close();
				taos.closeArchiveEntry();
				continue;
			}
			File[] fs = child.listFiles();
			String nfpath = fpath + child.getName() + "/";
			if (fs.length <= 0) { // 空目录
				taos.putArchiveEntry(new TarArchiveEntry(nfpath));
				taos.closeArchiveEntry();
			} else { // 目录非空，递归处理
				dfs(fs, taos, nfpath);
			}
		}
	}

	@Override
	public final void doArchiver(File[] files, String destpath)
			throws IOException {
		/*
		 * 定义一个TarArchiveOutputStream 对象
		 */
		FileOutputStream fos = new FileOutputStream(destpath);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		TarArchiveOutputStream taos = new TarArchiveOutputStream(bos);
		dfs(files, taos, "");
		taos.flush();
		taos.close();
	}

	@Override
	public void doUnArchiver(File srcfile, String destpath)
			throws IOException {
		byte[] buf = new byte[1024];
		FileInputStream fis = new FileInputStream(srcfile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		TarArchiveInputStream tais = new TarArchiveInputStream(bis);
		TarArchiveEntry tae = null;
		while ((tae = tais.getNextTarEntry()) != null) {
			File f = new File(destpath + "/" + tae.getName());
			if (tae.isDirectory()) {
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
				while ((len = tais.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}
				bos.flush();
				bos.close();
			}
		}
		tais.close();
	}

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        String folderPath = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS.csv";
        String folderPath1 = "C:\\Users\\zhouwei\\Dev\\testDate\\t01_comp_org_info20160720.csv";
        String tarFilePath = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS.tar";
        String unCompressTarGzipFile = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS-.tar";
        String unCompressTarFile = "C:\\Users\\zhouwei\\Dev\\testDate\\pro_profit_detail_201606_MPOS-tar";

        try {
            Tar tar = new Tar();

            //解压打成Tar包
            File file = new File(folderPath);
            File file1 = new File(folderPath1);
            File[] files = {file,file1};
            tar.doArchiver(files, tarFilePath);

            //解压Tar包
            File tarFile1 = new File(unCompressTarGzipFile);
            tar.doUnArchiver(tarFile1, unCompressTarFile);

        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
