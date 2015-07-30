package br.com.ruifpi.util;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName="opensessioninview", urlPatterns="/*")
public class Filtro implements Filter {
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filtro) throws IOException, ServletException {
		try {
			EntityManager entityManager = JpaUtil.getSessionAtual();
			entityManager.getTransaction().begin();
			filtro.doFilter(request, response);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Transação não finalizada ...");
			JpaUtil.getSessionAtual().getTransaction().rollback();
			new ServletException(e);
		}finally{
			JpaUtil.closeEntity();
		}
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	
}
