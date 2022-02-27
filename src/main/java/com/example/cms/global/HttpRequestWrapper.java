package com.example.cms.global;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

@Slf4j
public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private final String body;
    public String getBody() {
        return body;
    }

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // body = readInputStreamInStringFormat(request.getInputStream(), Charset.forName(request.getCharacterEncoding()));
        body = readStream(request.getInputStream());
        // log.info("Request Body:\n{}", body);
    }

    private String readStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            // InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        return stringBuilder.toString();
    }

    private String readInputStreamInStringFormat(InputStream stream, Charset charset) throws IOException {
        final int MAX_BODY_SIZE = 1024 * 1024 * 5; // 5MB
        final StringBuilder bodyStringBuilder = new StringBuilder();
        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }

        stream.mark(MAX_BODY_SIZE + 1);
        final byte[] entity = new byte[MAX_BODY_SIZE + 1];
        final int bytesRead = stream.read(entity);

        if (bytesRead != -1) {
            bodyStringBuilder.append(new String(entity, 0, Math.min(bytesRead, MAX_BODY_SIZE), charset));
            if (bytesRead > MAX_BODY_SIZE) {
                bodyStringBuilder.append("...");
            }
        }
        stream.reset();

        return bodyStringBuilder.toString();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream () throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());

        return new ServletInputStream() {
            private boolean finished = false;

            @Override
            public boolean isFinished() {
                return finished;
            }

            @Override
            public int available() throws IOException {
                return byteArrayInputStream.available();
            }

            @Override
            public void close() throws IOException {
                super.close();
                byteArrayInputStream.close();
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }

            public int read () throws IOException {
                int data = byteArrayInputStream.read();
                if (data == -1) {
                    finished = true;
                }
                return data;
            }
        };
    }
}
