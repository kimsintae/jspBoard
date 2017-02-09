package service;

import vo.Article;
import vo.ArticleContent;

public class ArticleData {

	private Article article;
	private ArticleContent articleContent;
	public ArticleData(Article article, ArticleContent articleContent) {
		super();
		this.article = article;
		this.articleContent = articleContent;
	}
	public Article getArticle() {
		return article;
	}
	public ArticleContent getArticleContent() {
		return articleContent;
	}
	
	
}
