package com.chandra.prakash.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import com.chandra.prakash.security.ValidationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //get your token from request context and send it to auth service via rest template
        //boolean validToken = restTemplate.exchange(or getForObject or other methods of restTemplate which you find suitable for method and return type of your auth service controller method)
        boolean validToken = true;
        if(!validToken) {
            ctx.setSendZuulResponse(false); //This makes request not forwarding to micro services
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            ValidationResponse validationResponse = new ValidationResponse();
            validationResponse.setSuccess(false);
            validationResponse.setMessage("Invalid Access...");
            ObjectMapper mapper = new ObjectMapper();
            String responseBody;
			try {
				responseBody = mapper.writeValueAsString(validationResponse);
				ctx.setResponseBody(responseBody);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ctx.getResponse().setContentType("application/json");
            //If you want to do any thing else like logging etc, you can do it.
        }
        return null;
    }

}
