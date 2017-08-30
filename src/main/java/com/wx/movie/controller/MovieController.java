package com.wx.movie.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wx.common.bean.Movie;
import com.wx.common.utils.GetAndPost;
import com.wx.common.utils.GetMovie;
import com.wx.common.web.model.JsonModel;

@RestController
public class MovieController {
	
	private GetMovie getMovie=new GetMovie();
	
	@RequestMapping("getMovie.action")
	public JsonModel getMovie(Movie movie,HttpSession session){
		JsonModel jm=new JsonModel();
		List<Movie> movieList=new ArrayList<Movie>();
		if(movie.getKeyword()!=null){
			try {
				movieList=getMovie.doPost(movie);
				session.removeAttribute("movieList");
				session.setAttribute("movieList", movieList);
			} catch (Exception e) {
				jm.setCode(0);
			}
		}if(movie.getUrl()!=null){
			try {
				movieList=getMovie.doGet(movie);
				session.removeAttribute("movieList");
				session.setAttribute("movieList", movieList);
			} catch (Exception e) {
				jm.setCode(0);
			}
		}if(movie.getName()!=null&&movie.getName().startsWith("refresh")){
			session.removeAttribute("movieList");
			jm.setCode(1);
		}
		return jm;
	}
}
