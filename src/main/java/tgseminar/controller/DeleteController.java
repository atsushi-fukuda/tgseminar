package tgseminar.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

public class DeleteController extends Controller {

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
	    Key key = Datastore.createKey("ToDo", id);
	    Datastore.delete(key);
		super.response.setStatus(200);
		
		return null;
	}

}
