package com.application.baatna.resource;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.application.baatna.util.CommonLib;
import com.application.baatna.util.mailer.EmailModel;
import com.application.baatna.util.mailer.EmailUtil;

@Provider
public class BaseResource implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {

		StringWriter sWriter = new StringWriter();

		// create PrintWriter for StringWriter
		PrintWriter pWriter = new PrintWriter(sWriter);

		// now print the stacktrace to PrintWriter we just created
		e.printStackTrace(pWriter);

		// use toString method to get stacktrace to String from StringWriter
		// object
		String strStackTrace = sWriter.toString();

		EmailModel emailModel = new EmailModel();
		emailModel.setTo(CommonLib.BAPP_ID);
		emailModel.setFrom(CommonLib.BAPP_ID);
		emailModel.setSubject("Server Exception Encountered!");
		emailModel.setContent(strStackTrace);
		EmailUtil.getInstance().sendEmail(emailModel);

		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
	}
}