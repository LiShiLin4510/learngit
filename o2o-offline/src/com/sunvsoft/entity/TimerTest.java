package com.sunvsoft.entity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.PropertiesUtil;

public class TimerTest extends TimerTask {

	public JFrame jf = new JFrame("O2O线下收银系统");
	private boolean status = O2OMainMenu.internetPass;
	private String outPut;
	private boolean querystatus = O2OMainMenu.internetPass;
	// 实例化一个定时器
	private static Timer timer = new Timer();

	public TimerTest(boolean status) {
		this.status = status;
		/*MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
        String url = PropertiesUtil.getIp() + "/api/shop/member!checkInternet.do";
        ConnectOnlineMethod connection = new ConnectOnlineMethod();
        String outPut1 = null;
        try {
            outPut = connection.connectOnline(queryParam, url);
        } catch (IOException e) {
            outPut = null;
        }*/
	}

	@SuppressWarnings("static-access")
    @Override
	public void run() {
//      this.status = O2OMainMenu.internetPass;
//     this.status = true;
//     System.out.println("主页面设置：" + status);
       if (status) {
           if (querystatus) {
//             System.out.println("*****" + status);
               if (!isReachable(80,40000)) {
                   Object[] options = { "确定", "取消" };
                   int response = JOptionPane.showOptionDialog(jf,
                           "系统检测到网络不可用，是否切换到脱机模式？", "",
                           JOptionPane.YES_OPTION,
                           JOptionPane.QUESTION_MESSAGE, null, options,
                           options[1]);
                   if (response == 0) {
                       O2OMainMenu.internetPass = false;
                       querystatus = false;
                       new O2OMainMenu().changePerssion();
//                     System.out.println("已切换 " + O2OMainMenu.internetPass);
                       return;
                   } else {
                       return;
                   }
               }
           }
           if (!querystatus) {
               if (isReachable(80,40000)) {
                   Object[] options = { "确定", "取消" };
                   int response = JOptionPane.showOptionDialog(jf,
                           "系统检测到网络可用，是否切换到联机模式？", "", JOptionPane.YES_OPTION,
                           JOptionPane.QUESTION_MESSAGE, null, options,
                           options[1]);
                   if (response == 0) {
                       O2OMainMenu.internetPass = true;
                       querystatus = true;
                       new O2OMainMenu().changePerssion();
//                     System.out.println("已切换 " + O2OMainMenu.internetPass);
                       return;
                   } else {
                       return;
                   }
               }
           }
       }
   }

	public static void main(String[] args) {
		// Timer timer = new Timer();
		// long delay1 = 1 * 1000;
		// long period1 = 1000;
		// // 从现在开始 1 秒钟之后，每隔 1 秒钟执行一次 job1
		// timer.schedule(new TimerTest("job1"), delay1, period1);
		// long delay2 = 2 * 1000;
		// long period2 = 2000;
		// // 从现在开始 2 秒钟之后，每隔 2 秒钟执行一次 job2
		// timer.schedule(new TimerTest("job2"), delay2, period2);
		// int response = JOptionPane.showMessageDialog(jf, "a");
		// try {
		// String url = "http://111.160.124.226:8011/".replaceAll("//", "/");
		// InetAddress ad =
		// InetAddress.getByName(url.split("/")[1].split(":")[0]);
		// boolean state = ad.isReachable(5000);// 测试是否可以达到该地址
		// System.err.println("连接成功");
		// } catch (Exception e) {
		// e.printStackTrace();
		// System.err.println("连接失败");
		// }
		 queryInternetPass(1000, 3000);
		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// stopTimer();

		// isReach2();
//		try {
//			String localIp = InetAddress.getLocalHost().getHostAddress();
//			InetAddress local = InetAddress.getByName(localIp);
//			InetAddress romote = InetAddress.getByName("118.26.171.52");
//			isReachable( 80, 5000,"118.26.171.52");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	/**
	 * 定时器开始
	 * 
	 * @param delay
	 * @param period
	 */
	public static void queryInternetPass(long delay, long period) {
		// Timer timer = new Timer();
		// TimerTest timetest = new TimerTest();
		// 从现在开始 1 秒钟之后，每隔 1 秒钟执行一次 job1
		timer.schedule(new TimerTest(O2OMainMenu.internetPass), delay, period);
	}

	/**
	 * 定时器停止
	 * 
	 * @param delay
	 * @param period
	 */
	public static void stopTimer() {
		timer.cancel();
		System.out.println("定时器停止了");
	}

	@SuppressWarnings("unused")
    public boolean isReach() {
		try {
			String url = PropertiesUtil.getIp().replaceAll("//", "/");
			// InetAddress ad =
			// InetAddress.getByName(url.split("/")[1].split(":")[0]);
			InetAddress ad = InetAddress.getByName("www.baidu.com");
			// InetAddress ad = InetAddress.getByName("120.76.203.153");
			boolean state = ad.isReachable(5000);// 测试是否可以达到该地址
			Thread.sleep(500);
			boolean stateAgain = ad.isReachable(5000);// 测试是否可以达到该地址
			if (state) {
				if (stateAgain) {
					System.err.println("连接状态：" + true);
					return true;
				}
			}
			System.err.println("连接状态：" + false);
			return true;
		} catch (Exception e) {
			System.err.println("连接失败");
			return false;
		}
	}

	public static void isReach2() {

		try {
			String ip = "118.26.171.52";
			InetAddress address = InetAddress.getByName(ip);// ping this IP

			if (address instanceof java.net.Inet4Address) {
				System.out.println(ip + " is ipv4 address");
			} else if (address instanceof java.net.Inet6Address) {
				System.out.println(ip + " is ipv6 address");
			} else {
				System.out.println(ip + " is unrecongized");
			}

			if (address.isReachable(5000)) {
				System.out.println("SUCCESS - ping " + ip
						+ " with no interface specified");
			} else {
				System.out.println("FAILURE - ping " + ip
						+ " with no interface specified");
			}

			System.out
					.println("\n-------Trying different interfaces--------\n");

			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				System.out.println("Checking interface, DisplayName:"
						+ ni.getDisplayName() + ", Name:" + ni.getName());
				if (address.isReachable(ni, 0, 5000)) {
					System.out.println("SUCCESS - ping " + ip);
				} else {
					System.out.println("FAILURE - ping " + ip);
				}

				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					System.out.println("IP: "
							+ ips.nextElement().getHostAddress());
				}
				System.out
						.println("-------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println("error occurs.");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
    public boolean isReachable(int port, int timeout) {
		boolean isReach = true;
		Socket socket = null;
		try {
			String localIp = InetAddress.getLocalHost().getHostAddress();
			InetAddress localInetAddr = InetAddress.getByName(localIp);
			String url = PropertiesUtil.getIp().replaceAll("//", "/");
			InetAddress remoteInetAddr = InetAddress.getByName(url.split("/")[1].split(":")[0]);
			//读取配置文件中的端口
			if(url.split("/")[1].split(":").length>1){
				port = Integer.parseInt(url.split("/")[1].split(":")[1]);
			}
//			InetAddress remoteInetAddr = InetAddress.getByName(remoteIp);
			socket = new Socket();
			// 端口号设置为 0 表示在本地挑选一个可用端口进行连接
			SocketAddress localSocketAddr = new InetSocketAddress(
					localInetAddr, 0);
			socket.bind(localSocketAddr);
			InetSocketAddress endpointSocketAddr = new InetSocketAddress(
					remoteInetAddr, port);
			socket.connect(endpointSocketAddr, timeout);
//			System.out.println("SUCCESS - connection established! Local: "
//					+ localInetAddr.getHostAddress() + " remote: "
//					+ remoteInetAddr.getHostAddress() + " port" + port);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}