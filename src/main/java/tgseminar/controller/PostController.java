package tgseminar.controller;

import java.util.Date;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class PostController extends Controller {



	@Override
	protected Navigation run() throws Exception {
		// TODO Auto-generated method stub
		String title = super.request.getParameter("title");
		
		if(title==null || "".equals(title)){
			super.response.setStatus(400);
			//super.response.setStatus(400, "title parameter is not specified");
			return null;
		}
		
		User user = UserServiceFactory.getUserService().getCurrentUser();
		String createdBy = user.getEmail();
		Date createdAt = new Date();
		
		Entity entity = new Entity("ToDo");
		entity.setProperty("title", title);
		entity.setProperty("createdBy", createdBy);
		entity.setProperty("createdAt", createdAt);
		
		Datastore.put(entity);
		super.response.setStatus(201);		
		//super.response.setStatus(201, "completes to save correctly");
		
		return null;
	}

}
