package cn.wmkfe.blog.util;

/**
 * 分页工具�?
 * @author 
 *
 */
public  class PageSupport {
	//当前页码-来自用户输入
	private int currentPageNo=1;
	//页面容量
	private int pageSize=0;
	//总条�?
	private int total=0;
	//总页�?(total/pageSize +1)
	private int pageCount=1;


	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		if(currentPageNo>0) {
			this.currentPageNo = currentPageNo;
		}
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		if(total>0) {
			this.total = total;
			this.setPageCountRs();
		}
		
	}
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
	}
	
	public void setPageCountRs() {
		if(this.total%this.pageSize==0) {
			this.pageCount=this.total/this.pageSize;
		}
		else if(this.total%this.pageSize>0) {
			this.pageCount=this.total/this.pageSize+1;
		}else {
			this.pageCount=0;
		}
	}

	@Override
	public String toString() {
		return "PageSupport{" +
				"currentPageNo=" + currentPageNo +
				", pageSize=" + pageSize +
				", total=" + total +
				", pageCount=" + pageCount +
				'}';
	}
}