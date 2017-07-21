package org.weilabs.archiver;

import java.io.File;
import java.io.IOException;

public abstract class Archiver {
	
	/**
	 * 打包或压缩文件
	 * @param files 需要打包和压缩的文件数组
	 * @param destpath 目标文件路径
	 * @throws IOException
	 */
	public abstract void doArchiver(File[] files, String destpath) throws IOException;
	
	/**
	 * 解压或解包文件
	 * @param srcfile 需要解压或解包的源文件
	 * @param destpath 目标路径
	 * @throws IOException
	 */
	public abstract void doUnArchiver(File srcfile, String destpath) throws IOException;
		
}
