package service;

import java.util.List;

import vo.Article;

public class ArticlePage {
	
	private int total;
	private int currentPage;
	private List<Article> content;
	private int totalPages;
	private int startPage;
	private int endPage;
	
	public ArticlePage(int total, int currentPage,int size ,List<Article> content) {
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		
		if(total == 0){
			//게시글이 한개도 없을 경우
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		}else{
			totalPages = total/size;//size 한페이지에 보여질 게시글 수
			if(total % size>0){
				//총 게시글 수 / size를 했을때
				//나머지 값이 나올경우
				totalPages++;
			}
			
			// 보여질 페이지 수를 결정
			int modVal = currentPage % 5;
			startPage = (currentPage / 5)*5+1;
			if(modVal==0) startPage -=5;
			
			endPage = startPage+4;
			if(endPage>totalPages) endPage=totalPages;
		}
	}
	
	
	//게시글 존재 유무 판단
	public boolean hasNoArticles(){
		return total==0;
	}
	
	public boolean hasArticles(){
		return total>0;
	}
	
	
	public int getTotal() {
		return total;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public List<Article> getContent() {
		return content;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	

	

}
