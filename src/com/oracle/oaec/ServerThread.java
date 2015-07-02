package com.oracle.oaec;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @描述: 多个客户端连接的线程类
 * @作者: Kevin
 * @时间: 2015-7-2 上午10:56:37
 * @版本: V1.0.0.0
 */
public class ServerThread extends Thread {

	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		OutputStream os = null;
		PrintWriter pw = null;
		try {
			is = socket.getInputStream();// 通过socket获取输入流
			isr = new InputStreamReader(is);// 将字节流包装成字符流
			br = new BufferedReader(isr);// 为字符流创建缓冲

			String data = null;
			// 循环读取输入流内容
			while ((data = br.readLine()) != null) {
				System.out.println("FROM CLIENT:" + data);
			}

			socket.shutdownInput();

			os = socket.getOutputStream();// 通过socket获取输出流
			pw = new PrintWriter(os);
			// 向客户端发送信息
			pw.write("WELCOME!");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			try {
				if (is != null) {
					is.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
				if (os != null) {
					os.close();
				}
				if (pw != null) {
					pw.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
