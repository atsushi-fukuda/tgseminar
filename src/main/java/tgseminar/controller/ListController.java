package tgseminar.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.memcache.Memcache;
import org.slim3.repackaged.org.json.JSONObject;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class ListController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		// TODO Auto-generated method stub
		User user = UserServiceFactory.getUserService().getCurrentUser();
		String createdBy = user.getEmail();
		
		List<Entity> list = Memcache.get(createdBy);
		if(list==null){
			System.out.println("no catche");
			list = Datastore.query("ToDo")
					.filter("createdBy", FilterOperator.EQUAL, createdBy)
					.sort("createdAt", SortDirection.DESCENDING)
					.asList();			
			Memcache.put(createdBy,list);			
		}else{
			System.out.println("catche exist");
		}
		
		
		Memcache.put(createdBy,list);
		
		List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
		for(Entity entity: list){
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			map.put("id", entity.getKey().getId()) ;
			map.put("title", entity.getProperty("title")) ;
			map.put("createdAt", entity.getProperty("createdAt")) ;
			newList.add(map);
		}

        String json = JSONObject.valueToString(newList);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println(json);
        
        response.flushBuffer();
        
		super.response.setStatus(200);		
		return null;
	}

}
