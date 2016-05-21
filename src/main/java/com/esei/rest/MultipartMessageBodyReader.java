package com.esei.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

@Consumes(MediaType.MULTIPART_FORM_DATA)
public abstract class MultipartMessageBodyReader<T> implements
MessageBodyReader<T>{

	private String getName(BodyPart part) throws MessagingException {
		String contentDisposition = part.getHeader("content-disposition")[0];

		String[] tokens = contentDisposition.split(";");
		for (String token: tokens) {
			String[] nameValues = token.trim().split("=");
			if (nameValues[0].equals("name")) {
				return nameValues[1].replaceAll("\"", "");
			}
		}
		return null;
	}

	private byte[] toByteArray(InputStream stream) throws IOException {
		byte[] buffer = new byte[1024];
		int readed = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((readed = stream.read(buffer))!=-1) {
			baos.write(buffer, 0, readed);
		}
		return baos.toByteArray();
	}

	private MimeMultipart createMimeMultipart(MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws MessagingException, IOException {
		String contentType = httpHeaders.get("Content-type").get(0);
		MimeMultipart mimemultipart =
				new MimeMultipart(
						new ByteArrayDataSource(entityStream, contentType));
		return mimemultipart;
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		try {
			return Class.forName(genericType.getTypeName()).isAssignableFrom(type);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		try {
			this.init();
			MimeMultipart mimemultipart = createMimeMultipart(httpHeaders, entityStream);
			for (int i = 0; i<mimemultipart.getCount(); i++) {
				BodyPart p = mimemultipart.getBodyPart(i);
				String name = getName(p);

				this.add(name, toByteArray(p.getInputStream()));
			}

			return this.build();

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	// Template methods
	protected abstract void init();
	protected abstract void add(String name, byte[] bs);
	protected abstract T build();
}