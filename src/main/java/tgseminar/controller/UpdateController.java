package tgseminar.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class UpdateController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		// TODO Auto-generated method stub
		String idString = super.request.getParameter("id");
		if(idString==null || "".equals(idString)){
	    	super.response.sendError(400, "id parameter isnot specified");
			return null;
		}
	    int id;
	    
	    try {
	        id = Integer.parseInt(idString);
	    } catch (NumberFormatException nfex) {
	    	super.response.sendError(400, "id parameter is not numeric value");
	        return null;
	    }
	    
		String title = super.request.getParameter("title");
		if(title==null || "".equals(title)){
	    	super.response.sendError(400, "title parameter isnot specified");
			return null;
		}
	    
	    Key key = Datastore.createKey("ToDo", id);
	    
	    /*
	    Entity entity;
	    try{
	    	entity = Datastore.get(key);
	    }catch(EntityNotFoundRuntimeException e){
	    	super.response.sendError(404, "id entity not exists");
	    	return null;
	    }
	    */
	    
	    Entity entity = Datastore.getOrNull(key);
	    if(entity==null){
	    	super.response.sendError(404, "entity not exists");
	    	return null;
	    }
		User user = UserServiceFactory.getUserService().getCurrentUser();
	    if(!user.getEmail().equals(entity.getProperty("createdBy"))){
	    	super.response.sendError(403, "entity is not your's");
	    	return null;	    	
	    }
	    
	    entity.setProperty("title", title);
	    Datastore.put(entity);
	    
	    //Datastore.delete(key);
		super.response.setStatus(200);
		return null;
	}

}
