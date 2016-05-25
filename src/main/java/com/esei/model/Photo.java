/*import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class Photo extends Post {

	@Lob
	@Column(length=10000000)
	private byte[] content;

	@Transient
	private String contentMime;

	public void setContent(byte[] content) {
		this.content = content;
	}

	public byte[] getContent() {
		return content;
	}

	public String getContentMime() throws IOException {
		if (this.contentMime!=null){
			return this.contentMime;
		}

		ByteArrayInputStream bais = new
				ByteArrayInputStream(this.content);
		return URLConnection.guessContentTypeFromStream(bais);
	}
}*/

