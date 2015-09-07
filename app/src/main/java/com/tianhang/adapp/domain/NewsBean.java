package com.tianhang.adapp.domain;

import java.util.ArrayList;

/**
 * 封装新闻中心的对象
 * 
 * 注意: 参数起名一定要和json中的命名一致, 否则gson无法解析
 * 
 * @author Kevin
 * 
 */
public class NewsBean {

	public ArrayList<NewsMenuBean> data;
	public ArrayList<String> extend;
	public int retcode;

	public class NewsMenuBean {
		public ArrayList<NewsMenuTab> children;
		public String id;
		public String title;
		public int type;
		public String url;
		public String url1;
		
		@Override
		public String toString() {
			return "NewsMenuBean [children=" + children + ", title=" + title
					+ "]";
		}
	}

	public class NewsMenuTab {
		public String id;
		public String title;
		public int type;
		public String url;
		
		@Override
		public String toString() {
			return "NewsMenuTab [id=" + id + ", title=" + title + "]";
		}
	}

	@Override
	public String toString() {
		return "NewsBean [data=" + data + ", retcode=" + retcode + "]";
	}
}
